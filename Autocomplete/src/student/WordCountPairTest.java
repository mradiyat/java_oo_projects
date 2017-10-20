package student;

import static org.junit.Assert.*;

import org.junit.Test;

public class WordCountPairTest {

	@Test
	public void test() {
		WordCountPair pair1 = new WordCountPair("apple", 3);
		WordCountPair pair2 = new WordCountPair("banana", 4);
		WordCountPair pair3 = new WordCountPair("orange", 4);
		WordCountPair pair4 = null;
		WordCountPair pair5 = null;
		assertEquals(pair2, WordCountPair.max(pair1, pair2));
		assertEquals(pair2, WordCountPair.max(pair2, pair3));
		assertEquals(null, WordCountPair.max(pair4, pair5));
		assertEquals(pair3, WordCountPair.max(pair3, pair4));
		assertEquals(pair3,  WordCountPair.max(pair4, pair3));
	}

}
