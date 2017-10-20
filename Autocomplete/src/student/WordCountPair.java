package student;

public class WordCountPair {

	private String word;
	private int count;
	
	/**
	 * WordCountPair is a simple class with two variables: word, count, and their getter and compare methods
	 * NOTE: You must implement the compare method (max) below.
	 * @param word
	 * @param count
	 */
	public WordCountPair (String word, int count) {
		this.word = word;
		this.count = count;
	}
	
	public String getWord() {
		return word;
	}
	
	public void setWord(String word) {
		this.word = word;
	}
	
	public void increment() {
		count++;
	}
	
	public int getCount() {
		return count;
	}
	
	public String toString() {
		return word + ", " + count;
	}
	
	/**
	 * Given two WordCountPair instances, returns the one with more count.
	 * If they have the same count, return one with lexicographically smaller word
	 * @param wcp1
	 * @param wcp2
	 * @return wcp1 or wcp2
	 */
	public static WordCountPair max(WordCountPair wcp1, WordCountPair wcp2) {
		if (wcp1 == null) {
			return wcp2;
		}
		if (wcp2 == null) {
			return wcp1;
		}
		int count1 = wcp1.getCount();
		int count2 = wcp2.getCount();
		if (count1 == count2) {
			return WordCountPair.smallerWord(wcp1, wcp2);
		}
		if (count1 > count2) {
			return wcp1;
		}
		else {
			return wcp2;
		}
		
	}
	
	/**
	 * Helper method for max(wcp1, wcp2). Only called if the two wordcountpairs have
	 * the same count. Returns lexicographically smaller word.
	 * @param wcp1
	 * @param wcp2
	 * @return wcp1 or wcp2
	 */
	private static WordCountPair smallerWord(WordCountPair wcp1, WordCountPair wcp2) {
		String string1 = wcp1.getWord();
		String string2 = wcp2.getWord();
		if (string1.compareTo(string2) < 0) {
			return wcp1;
		}
		else {
			return wcp2;
		}
	}
}
