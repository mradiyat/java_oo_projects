package student;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Console {
	public static void main(String args[]) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		Trie trie = null;
		boolean success = false;
		while(!success) {
			try {
				trie = new Trie();
				trie.createTrieFromFile(args[0]);
				success = true;
			}
			catch(Exception e) {
				success = false;
				System.out.println("An Error occurred. Please check file on filesystem : "+args[0]);
				System.out.println("Message: " + e.getMessage());
			}
		}

		boolean keepGoing = true;
		String input = "";
		while(keepGoing) {
			System.out.println();
			System.out.println("Please enter your command");
			System.out.println("1. \"lookup <word>\": Lookup a word in trie. Shows the number of appearances of the word");
			System.out.println("2. \"autocomplete <word>\": Autocomplete a word in trie. Shows the most probable autocomplete result");
			System.out.println("3. \"print <filename>\": Print the trie to a file");
			System.out.println("4. \"challenge <digits>\": Performs numeric challenge");
			System.out.println("5. \"reset <filename>\": Clear the current trie and create a new one with the file");
			System.out.println("6. \"quit\": Quit this console");
			input = br.readLine();
			
			try {
				if(input.startsWith("lookup")) {
					String query = input.substring(7);
					int count = trie.lookup(query);
					System.out.println("The word \"" + query + "\" appears " + count + " times in trie.");
				}
				else if(input.startsWith("autocomplete")) {
					String word = input.substring(12);
					WordCountPair complete = trie.findComplete(word);
					if(complete != null && complete.getWord() != null) {
						System.out.println("The autocomplete result for \"" + word + "\" is \"" + complete.getWord() + "\".");
					}
					else {
						System.out.println("Cannot find the autocomplete result for \"" + word + "\".");
					}
				}
				else if(input.startsWith("challenge")) {
					String digits = input.substring(10);
					if(digits.length() < 10) {
						String word = trie.challenge(digits);
						if(word != null) {
							System.out.println("The challenge result is: \"" + word + "\".");
						}
						else {
							System.out.println("No result for the challenge.");
						}
					}
					else {
						System.out.println("Too many digits. Please limit the digit string to size < 10.");
					}
				}
				else if(input.startsWith("reset")) {
					String filename = input.substring(6);
					trie = new Trie();
					trie.createTrieFromFile(filename);
				}
				else if(input.equals("quit")) {
					System.out.println("Goodbye.");
					keepGoing = false;
				}
				else {
					System.out.println("Invalid command.");
				}
			}
			catch(Exception e) {
				System.out.println("An Error occurred. Please re-try.");
				System.out.println("Message: " + e.getMessage());
			}
		}
	}
}
