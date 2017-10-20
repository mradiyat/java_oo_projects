package student;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.junit.Test;

public class EnemyTableModelTest {

	@Test
	public void test() throws FileNotFoundException {
		Scanner scanner = new Scanner(new File("game_file_1.txt"));
		Hero hero = null;
		List<Enemy> enemiesList = new ArrayList<Enemy>();
		while (scanner.hasNextLine()) {
			String currentCharacter = scanner.nextLine();
			String[] characterAttributes = currentCharacter.split(";");
			if (characterAttributes[0].equals("Hero")) {
				hero = new Hero(characterAttributes[1], 
						Integer.parseInt(characterAttributes[2]), 
						Integer.parseInt(characterAttributes[3]), 
						Integer.parseInt(characterAttributes[4]), 
						characterAttributes[5], 
						characterAttributes[6], 4);
			}
			if (characterAttributes[0].equals("Enemy")) {
				Enemy enemy = new Enemy(characterAttributes[1], 
						Integer.parseInt(characterAttributes[2]), 
						Integer.parseInt(characterAttributes[3]), 
						Integer.parseInt(characterAttributes[4]), 
						characterAttributes[5], 
						characterAttributes[6]);
				enemiesList.add(enemy);
			}
		}
		scanner.close();
		EnemyTableModel model = new EnemyTableModel(hero, enemiesList);
		model.sortEnemies();
		for (int i = 0; i < model.getNumberOfEnemies(); i++) {
			String row = "";
			for (int j = 0; j < 6; j++) {
				row += model.getValueAt(i, j) + ", ";
			}
			System.out.println(row);
		}
		for (int i = 0; i < 6; i++) {
			System.out.println(model.getColumnName(i));
		}
		
	}
}
