package student;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import org.junit.Test;

public class TrieTest {

	@Test
	public void test() throws FileNotFoundException {
		Trie trie1 = new Trie();
		trie1.insert("apple");
		trie1.insert("appl");
		trie1.insert("apple");
		trie1.insert("appl" );
		trie1.insert("appl");
		trie1.insert("banana");
		trie1.insert("banana");
		trie1.insert("banana");
		trie1.insert("banana");
		trie1.prepareTrieForQueries();
		assertEquals("banana", trie1.findComplete("").getWord());
		assertEquals("appl", trie1.findComplete("a").getWord());
		
		Trie trie2 = new Trie();
		Scanner scanner = new Scanner(new File("queryhistory.txt"));
		while (scanner.hasNextLine()) {
			String x = scanner.nextLine();
			trie2.insert(x);
		}
		trie2.prepareTrieForQueries();
		System.out.println(trie2.challenge("53"));
		/*
		ArrayList x = new ArrayList<String>();
		trie2.permutate(x, "345", "");
		for (int i = 0; i < x.size(); i++) {
			System.out.println(x.get(i));
		}
		*/
	}
}
