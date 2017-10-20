package student;

import java.util.List;

public class Sorter {
	
	/**
	 * Sorts the items in <i>lst</i> such that calling a.compareTo(b) will 
	 * always be negative or 0 if a occurs in the list before b
	 * @param <T> A type of Comparable that whose compareTo method will be used
	 * for sorting
	 * @param lst The list that will be sorted by this method
	 */
	public static <T extends Comparable<T>> void sort(List<T> lst) {
		heapSort(lst);
	}
	
	/**
	 * Implementation of selection sort for a generic list 
	 * @param lst List to sort
	 */
	public static <T extends Comparable<T>> void selectionSort(List<T> lst) {
		for (int i = 0; i < lst.size(); i++) {
			T temp = lst.get(i);
			int minIndex = i;
			for (int j = i; j < lst.size(); j++) {
				if (lst.get(j).compareTo(lst.get(minIndex)) == -1)  {
					minIndex = j;
				}
			}
			lst.set(i, lst.get(minIndex));
			lst.set(minIndex, temp);
		}
	}
	
	/**
	 * Implementation of heapsort for a generic list
	 * @param lst List to sort
	 */
	public static <T extends Comparable<T>> void heapSort(List<T> lst) {
		int heapSize = lst.size();
		/*First, heapify the array. Start at floor(length/2) and go backwards to make sure
		 *we heapify the whole array. 
		 */
		for (int i = heapSize / 2 - 1; i >= 0; i--) {
			heapify(lst, i, heapSize);
		}
		/* Now we have a heap with largest elements at the top. Push this largest
		 * one to the end by swapping, and heapify the array again from the top, making
		 * sure to stop heapifying once we reach the end of the array (where the largest
		 * elements we've swapped and pushed are being held).
		 */
		for (int i = 0; i < heapSize; i++) {
			swap(lst, heapSize - i - 1, 0);
			heapify(lst, 0, heapSize - i-1);
		}
			
	}
	
	/**Turns a list into a heap. Only heapifies from a node, labeled
	 * by index i, and its children.
	 * 
	 * @param lst List to heapify
	 * @param i The node to heapify against
	 * @param heapSize An index of the list to specify the point at which
	 * we stop heapifying. In heapsort, after an initial heapify, we start placing
	 * largest elements in the back of the list. We heapify the rest, making
	 * sure not to include the later elements of the list by cutting off after
	 * heapSize.
	 */
	private static <T extends Comparable<T>> void heapify(List<T> lst, int i, int heapSize) {
		int leftChild = 2*i+1;
		int rightChild = 2*i+2;
		int largestIndex = i;
		if (leftChild < heapSize && lst.get(leftChild).compareTo(lst.get(largestIndex)) == 1) {
			largestIndex = leftChild;
		}
		if (rightChild < heapSize && lst.get(rightChild).compareTo(lst.get(largestIndex)) == 1) {
			largestIndex = rightChild;
		}
		if (largestIndex != i) {
			swap(lst, i, largestIndex);
			heapify(lst, largestIndex, heapSize);
		}
	}
	
	private static <T extends Comparable<T>> void swap(List<T> lst, int i, int j) {
		T temp = lst.get(i);
		lst.set(i, lst.get(j));
		lst.set(j, temp);
	}
	
	//The following are sorts for integer arrays. Same ideas as above.
	
	public static void insertionSort(int[] nums) {
		for (int i = 1; i < nums.length; i++) {
			int temp = nums[i];
			int j = i;
			while (j > 0 && temp < nums[j-1]) {
					nums[j] = nums[j-1];
					j--;
			}
			nums[j] = temp;
		}
	}
	
	public static void selectionSort(int[] nums) {
		for (int i = 0; i < nums.length; i++) {
			int temp = nums[i];
			int minIndex = i;
			for (int j = i; j < nums.length; j++) {
				if (nums[j] < nums[minIndex]) {
					minIndex = j;
				}
			}
			nums[i] = nums[minIndex];
			nums[minIndex] = temp;
		}
		
		
	}
	
	public static void heapSort(int[] nums) {
		int heapSize = nums.length;
		for (int i = nums.length / 2 - 1; i >= 0; i--) {
			heapify(nums, i, heapSize);
		}
		
		for (int i = 0; i < heapSize; i++) {
			swap(nums, heapSize - i - 1, 0);
			heapify(nums, 0, heapSize - i-1);
			}
			
		}
	
	
	private static void heapify(int[] nums, int i, int heapSize) {
		int leftChild = 2*i+1;
		int rightChild = 2*i+2;
		int largestIndex = i;
		if (leftChild < heapSize && nums[leftChild] > nums[largestIndex]) {
			largestIndex = leftChild;
		}
		if (rightChild < heapSize && nums[rightChild] > nums[largestIndex]) {
			largestIndex = rightChild;
		}
		if (largestIndex != i) {
			swap(nums, i, largestIndex);
			heapify(nums, largestIndex, heapSize);
		}
	}
	
	private static void swap(int[] nums, int i, int j) {
		int temp = nums[i];
		nums[i] = nums[j];
		nums[j] = temp;
	}
 }
