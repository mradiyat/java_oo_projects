package student;

import static org.junit.Assert.*;

import java.io.FileNotFoundException;

import javax.swing.table.TableModel;

import org.junit.Test;

public class GameTest {

	@Test
	public void test() throws FileNotFoundException, InvalidGameException {
		Game game = new Game("game_file_1.txt");
		EnemyTableModel model = (EnemyTableModel) game.getEnemyTableModel();
//		for (int i = 0; i < model.getNumberOfEnemies(); i++) {
//			System.out.println(model.getValueAt(i).getName());
//		}
		game.setSelectedEnemy(3);
		for (int i = 0; i < game.getEnemyTableModel().getRowCount(); i++) {
			System.out.println(game.getEnemyTableModel().getValueAt(i, 0));
		}
		System.out.println(game.getEnemyTableModel().getRowCount());
		System.out.println(game.battleCharacters());
		System.out.println(game.getEnemyTableModel().getRowCount());
		System.out.println(game.battleCharacters());
		System.out.println(game.getEnemyTableModel().getRowCount());
		for (int i = 0; i < game.getEnemyTableModel().getRowCount(); i++) {
			System.out.println(game.getEnemyTableModel().getValueAt(i, 0));
		}
	}

}
