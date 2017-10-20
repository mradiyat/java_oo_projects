package student;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.junit.Test;

public class SorterTest {

	@Test
	public void test() {
		
//		int[] nums = {54,65,7,78,67,6,5,43,3,3,34,45,5,6,7,8,9,0,9,8,7,65,45,34,3,2,2,2,3,4,45,5,456,67,7,7,57,567,64,35,24,54,46,76,78};
//		Sorter.heapSort(nums);
//		for (int i : nums) {
//			System.out.println(i);
//		}
		
		List<Integer> nums2 = new ArrayList<Integer>();
		Random x = new Random(1234);
		for (int n = 0; n < 100; n++) {
			nums2.add(x.nextInt(1000));
		}
		
		Sorter.heapSort(nums2);
		for (int i = 1; i < nums2.size(); i++) {
			assertEquals(true, nums2.get(i) >= nums2.get(i-1));
		}
		
	}

}
