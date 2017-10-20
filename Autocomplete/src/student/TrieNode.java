package student;

public class TrieNode {
	
	//children nodes: You can implement this as a list too!
	private TrieNode[] children;
	
	//the (word, count) pair of this trie node
	private WordCountPair currentString;
	
	//preprocessed most probable autocomplete result for the entire subtree rooted at this node
	private WordCountPair mostFrequentCompletion;

	/**
	 * Create a default TrieNode instance
	 * @param word String which represents the word
	 */
	public TrieNode() {
		WordCountPair emptyPair = new WordCountPair("", 0);
		currentString = emptyPair;
		mostFrequentCompletion = null;
		children = new TrieNode[26];
	}

	/**
	 * Create a TrieNode instance with the input string
	 * @param word String which represents the word
	 */
	public TrieNode(String word) {
		word = word.toLowerCase();
		WordCountPair wordPair = new WordCountPair(word, 0);
		currentString = wordPair;
		mostFrequentCompletion = null;
		children = new TrieNode[26];
	}
	
	/**
	 * Helper method for prepareTrieForQueries. First sets 
	 * mostFrequentCompletion for all non-null children, and
	 * then uses the max mostFrequentCompletion among all children
	 * plus the current node as the mostFrequentCompletion for 
	 * the node. 
	 */
	public void setMostFrequentTrie() {
		if (noChildren()) {
			setMostFrequentCompletion(getCurrentString());
			return;
		}
		for (TrieNode i : children) {
			if (i != null) {
				i.setMostFrequentTrie();
				
			}
		}
		WordCountPair max = getCurrentString();
		for (TrieNode i: children) {
			if (i != null) {
				max =
					WordCountPair.max(max, i.getMostFrequentCompletion());
			}
		}
		setMostFrequentCompletion(max);
 		
	}

	/**
	 * Checks if a node has no children. 
	 * @return true if all children are null
	 */
	public boolean noChildren() {
		for (TrieNode i : getChildren()) {
			if (i != null) {
				return false;
			}
		}
		return true;
	}
	
	/**
	 * @return the currentString
	 */
	public WordCountPair getCurrentString() {
		return currentString;
	}

	/**
	 * @param currentString the currentString to set
	 */
	public void setCurrentString(WordCountPair currentString) {
		this.currentString = currentString;
	}

	/**
	 * @return the mostFrequentCompletion
	 */
	public WordCountPair getMostFrequentCompletion() {
		return mostFrequentCompletion;
	}

	/**
	 * @param mostFrequentCompletion the mostFrequentCompletion to set
	 */
	public void setMostFrequentCompletion(
	        WordCountPair mostFrequentCompletion) {
		this.mostFrequentCompletion = mostFrequentCompletion;
	}

	/**
	 * @return the children
	 */
	public TrieNode[] getChildren() {
		return children;
	}
	
	/**
	 * @param children the children to set
	 */
	public void setChildren(TrieNode[] children) {
		this.children = children;
	}
	
	/**
	 * increments the number of times this string has been completed
	 */
	public void incrementCurrentString() {
		getCurrentString().increment();
	}
	
	
}
