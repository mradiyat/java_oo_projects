package student;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

import javax.swing.ImageIcon;
import javax.swing.table.TableModel;

public class Game {

	private Random rng;

	// the starting number of lives of the hero
	private static final int NUMBER_LIVES = 5;

	private Hero hero;
	private Enemy selectedEnemy;

	private EnemyTableModel enemyTableModel;

	public Game(String fileName)
	        throws InvalidGameException, FileNotFoundException {
		rng = new Random(123456789);
		initializeGame(fileName);
	}

	/**
	 * Initializes the game using a given text file which lists the hero and
	 * enemies of the game. Throws an InvalidGameException if the given board
	 * file name is invalid
	 * @param gameFileName The file name that will initialize the game
	 * @throws InvalidGameException
	 * @throws FileNotFoundException 
	 */
	private void initializeGame(String gameFileName) throws InvalidGameException, 
	FileNotFoundException {
		try {	
			Scanner scanner = new Scanner(new File(gameFileName)); 
			Hero hero = null;
			List<Enemy> enemiesList = new ArrayList<Enemy>();
			int numHeroes = 0; //make sure heroes doesnt go above 1
			//for each line of the file, construct a character
			while (scanner.hasNextLine()) {
				String[] characterAttributes = scanner.nextLine()
				        .split(";");
				//If there aren't exactly 7 attributes, invalid game
				if (characterAttributes.length != 7) {
					throw new InvalidGameException();
				}
				//Handle the case that we have a hero this time
				if (characterAttributes[0].equals("Hero")) {
					//if we already have a hero, invalid game
					if (numHeroes == 1) {
						throw new InvalidGameException();
					}
					numHeroes++;
					hero = new Hero(characterAttributes[1],
					        Integer.parseInt(characterAttributes[2]),
					        Integer.parseInt(characterAttributes[3]),
					        Integer.parseInt(characterAttributes[4]),
					        characterAttributes[5],
					        characterAttributes[6], NUMBER_LIVES);
				}
				//Handle case of an enemy, making sure to add it to our list
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
			//Finish scanning the file.
			scanner.close();
			//Do we have a hero? If not, invalid game
			if (numHeroes != 1) {
				throw new InvalidGameException();
			}
			//Do we have any enemies? If not, invalid game
			if (enemiesList.size() == 0) {
				throw new InvalidGameException();
			}
			//finally, initialize the game's fields
			enemyTableModel = new EnemyTableModel(hero, enemiesList);
			enemyTableModel.sortEnemies();
			this.hero = hero;
		}
		catch (InvalidGameException | NumberFormatException | 
				FileNotFoundException e) {
			throw new InvalidGameException();
		}
	}

	/**
	 * Gets the table model associated with the enemies
	 * 
	 * @return The TableModel object that holds the enemies to be displayed in a
	 *         GUI's JTable
	 */
	public TableModel getEnemyTableModel() {
		return enemyTableModel;
	}

	/**
	 * Finds the selected Enemy object based on an index that was selected
	 * within the JTable
	 * 
	 * @param index
	 *            The index of the row selected within the JTable
	 */
	public void setSelectedEnemy(int index) {
		selectedEnemy = enemyTableModel.getValueAt(index);
	}

	/**
	 * Battles the hero with the selected enemy. If the hero wins, the selected
	 * enemy is removed from the table model, the hero's number of victories is
	 * incremented and received the number of attributes for beating the
	 * selected enemy, the enemies are resorted in the table model, and the
	 * selected enemy is removed. If the enemy wins, the hero loses a life.
	 * 
	 * @return Battle.HERO_WON if the hero wins, Battle.ENEMY_WON if the enemy
	 *         wins, or -1 if there is no enemy to battle
	 */
	public int battleCharacters() {
		if (selectedEnemy == null) {
			return -1;
		}
		Battle battle = new Battle(hero, selectedEnemy);
		int outcome = battle.getOutcome();
		int points = battle.getPointsHeroWin();
		if (outcome == Battle.HERO_WON) {
			enemyTableModel.removeEnemy(selectedEnemy);
			enemyTableModel.sortEnemies();
			selectedEnemy = null;
			hero.incrementVictories();
			addAttributes(points);
		}
		if (outcome == Battle.ENEMY_WON) {
			hero.decrementLives();
		}
		return outcome;
	}

	/**
	 * Adds <i>pointAwarded</i> points that are randomly allocated to the hero's
	 * attributes
	 * 
	 * @param pointsAwarded
	 *            The amount of points allocated to the hero
	 */
	private void addAttributes(int pointsAwarded) {
		for (int i = 0; i < pointsAwarded; i++) {
			int numSelected = rng.nextInt(3);
			switch (numSelected) {
			case 0:
				hero.incrementStrength();
				break;
			case 1:
				hero.incrementAgility();
				break;
			case 2:
				hero.incrementIntelligence();
				break;
			default:
				break;
			}
		}
	}

	/**
	 * @return True if the hero has no more lives left to play, false otherwise
	 */
	public boolean heroLostGame() {
		return (hero.getNumberLives() == 0);
	}

	/**
	 * @return True if the hero beat all the enemies, false otherwise
	 */
	public boolean heroWonGame() {
		return (enemyTableModel.getRowCount() == 0);
	}

	/**
	 * @return True if there is an enemy that is selected, false otherwise
	 */
	public boolean isEnemySelected() {
		return (selectedEnemy != null);
	}

	/**
	 * @return The name of the hero
	 */
	public String getHeroName() {
		return hero.getName();
	}

	/**
	 * @return The ImageIcon representing an image for the hero that will be
	 *         displayed in the GUI
	 */
	public ImageIcon getHeroIcon() {
		return hero.getIcon();
	}

	/**
	 * @return The hero's strength ranking
	 */
	public int getHeroStrength() {
		return hero.getStrength();
	}

	/**
	 * @return The hero's agility ranking
	 */
	public int getHeroAgility() {
		return hero.getAgility();
	}

	/**
	 * @return The hero's intelligence ranking
	 */
	public int getHeroIntelligence() {
		return hero.getIntelligence();
	}

	/**
	 * @return The taunt that will be displayed in a window if the hero wins
	 */
	public String getHeroTaunt() {
		return hero.getTaunt();
	}

	/**
	 * @return The name of the selected enemy. Returns null if there is no
	 *         selected enemy.
	 */
	public String getEnemyName() {
		return selectedEnemy.getName();
	}

	/**
	 * @return The ImageIcon representing an image for the selected enemy that
	 *         will be displayed in the GUI
	 */
	public ImageIcon getEnemyIcon() {
		return selectedEnemy.getIcon();
	}

	/**
	 * @return The selected enemy's strength ranking. Returns 0 if there is no
	 *         selected enemy.
	 */
	public int getEnemyStrength() {
		return selectedEnemy.getStrength();
	}

	/**
	 * @return The selected enemy's agility ranking. Returns 0 if there is no
	 *         selected enemy.
	 */
	public int getEnemyAgility() {
		return selectedEnemy.getAgility();
	}

	/**
	 * @return The selected enemy's intelligence ranking. Returns 0 if there is
	 *         no selected enemy.
	 */
	public int getEnemyIntelligence() {
		return selectedEnemy.getIntelligence();
	}

	/**
	 * @return The taunt that will be displayed in a window if the selected
	 *         enemy wins. Returns null if there is no selected enemy.
	 */
	public String getEnemyTaunt() {
		return selectedEnemy.getTaunt();
	}

	/**
	 * @return The number of victories associated with the hero in this game.
	 */
	public int getNumberOfVictories() {
		return hero.getNumberOfVictories();
	}

	/**
	 * @return The number of victories associated with the hero in this game.
	 */
	public int getNumberOfLives() {
		return hero.getNumberLives();
	}
}
