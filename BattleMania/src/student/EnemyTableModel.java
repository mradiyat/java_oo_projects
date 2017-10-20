package student;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

public class EnemyTableModel extends AbstractTableModel {
		
	private Hero hero;
	private List<Enemy> enemies;
	private String[] colnames = {"Name", "Strength", "Agility", "Intelligence",
			"Likelihood of Winning", "Points Awarded"};  
	
	public EnemyTableModel(Hero hero, List<Enemy> enemies) {
		this.hero = hero;
		this.enemies = enemies;
	}
	/**
	 * Returns the name of the column to be displayed on the JTable given the 
	 * column index
	 * @param column The column index whose name should be returned
	 * @return The name of the column associated with the given index
	 */
	public String getColumnName(int column) {
		if (column < 0 || column > 5) {
			return "";
		}
		return colnames[column];
	}
	
	/**
	 * Returns the Enemy object associated with the given index
	 * @param rowIndex The index which the user selected
	 * @return The Enemy object contained the given row. If rowIndex is an
	 * invalid index for the enemies list, return null.
	 */
	public Enemy getValueAt(int rowIndex) {
		if (rowIndex < 0 || rowIndex > enemies.size() - 1) {
			return null;
		}
		return enemies.get(rowIndex);
	}

	/**
	 * @return The number of enemies that are contained within this table model
	 */
	public int getNumberOfEnemies() {
		return enemies.size();
	}
	
	/**
	 * Returns the number of enemies contained within this table model
	 */
	@Override
	public int getRowCount() {
		return enemies.size();
	}

	/**
	 * Returns the number of columns in this table model
	 */
	@Override
	public int getColumnCount() {
		return 6;
	}
	
	/**
	 * Returns the value to be displayed in a JTable given a row index and
	 * column index in the table. Each column holds a specific value for each
	 * enemy. 
	 */
	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		if (columnIndex < 0 || columnIndex > 5) {
			return null;
		}
		Enemy current = getValueAt(rowIndex);
		if (current == null) {
			return null;
		}
		
		Object value = null;
		
		switch (columnIndex) {
		case 0:
			value = current.getName();
			break;
		case 1: 
			value = current.getStrength();
			break;
		case 2: 
			value = current.getAgility();
			break;
		case 3: 
			value = current.getIntelligence();
			break;
		case 4: 
			Battle battle = new Battle(hero, current);
			value = battle.getProbHeroWinning();
			break;
		case 5: 
			Battle battle2 = new Battle(hero, current);
			value = battle2.getPointsHeroWin();
			break;
		}
		return value;
	}
	
	/**
	 * Removes a given enemy, which is called after the Hero defeats the enemy
	 * @param enemy The enemy that was defeated and that will be removed from
	 * the list of available enemies
	 */
	public void removeEnemy(Enemy enemy) {
		enemies.remove(enemy);
		fireTableDataChanged();
	}
	
	/**
	 * Sorts the enemies in descending order based on how likely the hero will
	 * beat them. This method should call <i>fireTableDataChanged</i> to
	 * indicate that the enemies are sorted in this table model
	 */
	public void sortEnemies() {
		List<Battle> battles = new ArrayList<Battle>();
		for (Enemy enemy : enemies) {
			Battle battle = new Battle(hero, enemy);
			battles.add(battle);
		}
		Sorter.heapSort(battles);
		int i = 0;
		for (Battle battle : battles) {
			Enemy enemy = battle.getEnemy();
			enemies.set(i, enemy);
			i++;
		}
		fireTableDataChanged();
	}
	
}
