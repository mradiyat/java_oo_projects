package student;

import static org.junit.Assert.*;

import org.junit.Test;

public class BattleTest {

	@Test
	public void test() {
		Hero cloud = new Hero("Cloud", 15, 10, 5, null, null, 0);
		Enemy sephiroth = new Enemy("Sephiroth", 20, 15, 10, null, "notfound");
		Battle ff7 = new Battle(cloud, sephiroth);
		
		Hero vincent = new Hero("Vincent", 50, 90, 75, null, null, 0);
		Enemy ultimecia = new Enemy("Ultimecia", 5, 5, 5, null, null);
		Battle ff8 = new Battle(vincent, ultimecia);
		
		System.out.println(ff8.compareTo(ff7));
		
	}

}
