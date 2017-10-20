package student;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

public class Trie {

	//Helper variable to denote mapping from digits to characters on a typical mobile keypad
	private static char[][] digitToCharMapping = {{}, {}, {'a', 'b', 'c'}, {'d', 'e', 'f'}, {'g', 'h', 'i'}, {'j', 'k', 'l'}, {'m', 'n', 'o'},
		{'p', 'q', 'r', 's'}, {'t', 'u', 'v'}, {'w', 'x', 'y', 'z'}};
	
	//Pointer to the root of the trie
	private TrieNode root;
	
	/** This function takes a complete word, initializes the root if it is null, and inserts the word into the trie pointed to by the root
	 * @param inputWord
	 * @return
	 */
	public void insert(String inputWord){
		if (inputWord == null || inputWord == "") {
			return;
		}
		//make sure word only has characters
		for (int i = 0; i < inputWord.length(); i++) {
			if (!((inputWord.charAt(i) >= 'A' && inputWord.charAt(i) <= 'Z')
					|| (inputWord.charAt(i) >= 'a' && inputWord.charAt(i) <= 'z'))) {
				return;
			}
		}
		if (root == null) {
			root = new TrieNode();
		}
		TrieNode current = root; //keep track of current node
		inputWord = inputWord.toLowerCase(); //make the word fully lower case
		int i = 0; //keep track of index of inputWord we're working with
		while (i < inputWord.length()) {
			char c = inputWord.charAt(i);
			//find the child node corresponding to the next character. if null, instantiate.
			TrieNode child = current.getChildren()[c-'a'];
			if (child == null) {
				child = new TrieNode(inputWord.substring(0, i+1));
				current.getChildren()[c - 'a'] = child;
			}
			current = child;
			i++;
		}
		current.incrementCurrentString();
		return;
	}
	
	/** This takes a filename, and calls insert for each word it discovers in the file
	 * @param filename
	 * @return
	 * @throws IOException, FileNotFoundException
	 */
	public void createTrieFromFile(String filename) throws IOException, FileNotFoundException {
		return;
	}
	
	/** This takes a complete query, and descends through the trie pointed by root, and returns its appearances
	 * in query history, if found. 0 otherwise.
	 * @param inputWord
	 * @return numAppearances
	 */
	public int lookup(String inputWord){
		if (root == null || inputWord == null || inputWord.length() < 1) {
			return 0;
		}
		TrieNode current = root;
		int i = 0; //keep track of our index
		//descend down the trie incrementally
		while (i < inputWord.length()) {
			char c = inputWord.charAt(i);
			//find the child node corresponding to the next character. if null, instantiate.
			TrieNode child = current.getChildren()[c-'a'];
			//if we hit a null, this word has never been entered
			if (child == null) {
				return 0;
			}
			current = child;
			i++;
		}
		return current.getCurrentString().getCount();
	}
	
	/** This takes a partial query, and descends through the trie pointed by root, and returns mostFrequentCompletion info
	 * @param inputPrefix
	 * @return WordCountPair of mostFrequentCompletion
	 */
	public WordCountPair findComplete(String inputPrefix){
		if (root == null || inputPrefix == null) {
			return null;
		}
		TrieNode current = root;
		int i = 0;
		while (i < inputPrefix.length()) {
			char c = inputPrefix.charAt(i);
			//find the child node corresponding to the next character. if null, instantiate.
			TrieNode child = current.getChildren()[c-'a'];
			if (child == null) {
				return null;
			}
			current = child;
			i++;
		}
		return current.getMostFrequentCompletion();
	}
	
	/** This takes a numeric prefix, and first maps into several possible character prefixes (you may use a list to store these)
	 * Next, it does findComplete appropriately and returns the string that has the most frequent
	 * appearance over all these prefixes.
	 * @param digits
	 * @return completeString
	 */
	public String challenge(String digits){
		if (digits == null || digits.length() < 1) {
			return null;
		}
		if (verifyNumbers(digits) == false) {
			return null;
		}
		ArrayList<String> permutations = new ArrayList<String>();
		permutate(permutations, digits, "");
		WordCountPair max = null;
		for (String i : permutations) {
			max = WordCountPair.max(max, findComplete(i));
		}
		if (max == null) {
			return null;
		}
		return max.getWord();
	}
	
	/** This recursively populates the mostFrequentCompletion in each TrieNode, to make the findComplete method more efficient
	 * @param 
	 * @return 
	 */
	public void prepareTrieForQueries(){
		if (root == null) {
			return;
		}
		root.setMostFrequentTrie();
	}
	
	/**
	 * Helper method for challenge. 
	 * Generates a string array of all possible permutations 
	 * of the characters based on digital input
	 * @param digits
	 * @return
	 */
	public void permutate(ArrayList<String> list, String digits, String initial) {
		//if initial is as long as digits is, then add initial to the list
		if (initial.length() == digits.length()) {
			list.add(initial);
			return;
		}
		/* If initial length is less than digits, we still need
		 * to add characters to initial. We find what digit we're
		 * working with next. This digit will be the digit at 
		 * index (initial.length()). For all chars at this digit, 
		 * we permutate(list, digits, initial + char 
		 */
		int currentDigit = digits.charAt(initial.length()) - '0';
		for (int i = 0; i < digitToCharMapping[currentDigit].length; i++) {
			permutate(list, digits, initial + digitToCharMapping[currentDigit][i]);
		}
		
	}
	
	/**
	 * Helper method for challenge. If a character is not between
	 * 2 and 9, the digit sequence is invalid.
	 * @param digits
	 * @return whether the digit string is a valid challenge entry.
	 * 
	 */
	public boolean verifyNumbers(String digits) {
		for (int i = 0; i < digits.length(); i++) {
			if (digits.charAt(i) > '9' || digits.charAt(i) < '2') {
				return false;
			}
		}
		return true;
	}
	/**
	 * Return root node of the Trie
	 * @return
	 */
	public TrieNode getRoot() {
		return root;
	}
}
