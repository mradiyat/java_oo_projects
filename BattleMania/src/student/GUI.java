package student;

import java.io.File;
import java.io.FileNotFoundException;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;

public class GUI extends JFrame{

	private JTable enemyTable;
	
	//Hero display items
	private JLabel heroNameLbl;
	private JLabel heroStrengthLbl;
	private JLabel heroAgilityLbl;
	private JLabel heroIntelligenceLbl;
	private JLabel heroImg;
	private JLabel numberOfVictoriesLbl;
	private JLabel livesLeftLbl;
	
	private JButton battleButton;
	
	//Enemy display items
	private JLabel enemyNameLbl;
	private JLabel enemyStrengthLbl;
	private JLabel enemyAgilityLbl;
	private JLabel enemyIntelligenceLbl;
	private JLabel enemyImg;
	
	private Game game;

	/**Default constructor for a BattleMania GUI. 
	 * Sets up the game if the game file is valid, and sets up several panels to hold
	 * a battle button, a table of enemies, and current battle characters. If user clicks the
	 * battle button, the game battles the two characters and updates accordingly. Win condition
	 * is all enemies defeated, lose condition is all lives are lost.
	 */
	public GUI() {
		super("BattleMania");
		initialSetup();
		handleBattles();
		heroSetup();
		enemySetup();
		tableSetup();
		
		//start creating panels to add to GUI
		JPanel battlePanel = new JPanel(); 
		battlePanel.add(battleButton);
		JPanel battleHeroAndEnemy = new JPanel(); 
		battleHeroAndEnemy.setLayout(new BorderLayout()); 

		// Set up initial hero panel
		JPanel heroPanel = new JPanel(); // holds hero stats
		heroPanel.setLayout(new BoxLayout(heroPanel, BoxLayout.PAGE_AXIS)); 
		heroPanel.add(heroNameLbl);
		heroPanel.add(heroStrengthLbl);
		heroPanel.add(heroAgilityLbl);
		heroPanel.add(heroIntelligenceLbl);
		heroPanel.add(numberOfVictoriesLbl);
		heroPanel.add(livesLeftLbl);
		heroPanel.add(heroImg);

		// Set up initial enemy panel
		JPanel enemyPanel = new JPanel(); // holds enemy stats
		enemyPanel.setLayout(new BoxLayout(enemyPanel, BoxLayout.PAGE_AXIS));
		enemyPanel.add(enemyNameLbl);
		enemyPanel.add(enemyStrengthLbl);
		enemyPanel.add(enemyAgilityLbl);
		enemyPanel.add(enemyIntelligenceLbl);
		enemyPanel.add(enemyImg);

		// set up the enemy table in the GUI
		JPanel tablePanel = new JPanel(); 
		Dimension d = enemyTable.getPreferredSize();
		JScrollPane enemyPane = new JScrollPane(enemyTable);
		enemyPane.setPreferredSize(
		        new Dimension(d.width, enemyTable.getRowHeight()
		                * (enemyTable.getRowCount() + 2)));
		tablePanel.add(enemyPane);
		
		// add all the panels into the GUI
		heroPanel.setBorder(new EmptyBorder(5,5,5,5));
		enemyPanel.setBorder(new EmptyBorder(5,5,5,5));
		battleHeroAndEnemy.add(heroPanel, BorderLayout.WEST);
		battleHeroAndEnemy.add(battlePanel, BorderLayout.NORTH);
		battleHeroAndEnemy.add(enemyPanel, BorderLayout.EAST);
		add(tablePanel);
		add(battleHeroAndEnemy);
		pack();
		setLocationRelativeTo(null); // centers game
		setVisible(true);
	}
	
	public static void main(String[] args) {
		GUI g = new GUI();
		
	}
	
	/**Initial setup of the game. Extracts a game from a user-chosen game file.
	 * 
	 */
	public void initialSetup() {
		//choose file to use for the game
				JFileChooser fc = new JFileChooser();
				boolean validGame = false;
				// if invalid file, try again until valid file chosen or window closed
				// by user
				while (!validGame) {
					try {
						int i = fc.showOpenDialog(this);
						if (i == JFileChooser.CANCEL_OPTION) {
							System.exit(0);
						}
						game = new Game(fc.getSelectedFile().getName());
						validGame = true;
					} catch (InvalidGameException | FileNotFoundException
					        | NullPointerException e) {
						System.out.println("exception");
					}
				}

				setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				setLayout(new GridLayout(2, 1));
				
	}
	
	
	/**Code to handle all battles. Sets up the battle button and implements
	 * a listener to handle battles.
	 * 
	 */
	public void handleBattles() {
		battleButton = new JButton("Battle!");
		// implement action listener for the button
		battleButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (game.isEnemySelected()) {
					// need to get this before enemy is removed
					int outcome = game.battleCharacters();
					if (outcome == Battle.HERO_WON) {
						JOptionPane.showMessageDialog(battleButton, game.getHeroName() + 
								" wins!\n" + "\"" + game.getHeroTaunt() + "\"");
						// update all the labels to reflect a hero win
						heroStrengthLbl.setText(
		                        "Str = " + game.getHeroStrength());
						heroAgilityLbl.setText(
		                        "Agl = " + game.getHeroAgility());
						heroIntelligenceLbl.setText("Int = "
		                        + game.getHeroIntelligence());
						numberOfVictoriesLbl
		                        .setText(game.getNumberOfVictories()
		                                + " victories");
						enemyNameLbl.setText("");
						enemyStrengthLbl.setText("");
						enemyAgilityLbl.setText("");
						enemyIntelligenceLbl.setText("");
						enemyImg.setIcon(null);

						// check if there are no more enemies
						if (game.getEnemyTableModel()
		                        .getRowCount() == 0) {
							JOptionPane.showMessageDialog(battleButton, 
									"Congratulations, you win!");
							System.exit(0);
						}
					}
					if (outcome == Battle.ENEMY_WON) {
						JOptionPane.showMessageDialog(battleButton, game.getEnemyName() + 
								" wins!\n" + "\"" + game.getEnemyTaunt() + "\"");
						livesLeftLbl.setText(game.getNumberOfLives()
		                        + " lives left");
						if (game.getNumberOfLives() == 0) {
							JOptionPane.showMessageDialog(battleButton, 
									"Too bad, you lose!");
							System.exit(0);
						}
					}
				}

			}
		});
	}
	
	/**Sets up hero labels initially. 
	 * 
	 */
	public void heroSetup() {
		// get hero labels
		heroNameLbl = new JLabel(game.getHeroName());
		heroStrengthLbl = new JLabel(
		        "Str = " + game.getHeroStrength());
		heroAgilityLbl = new JLabel("Agl = " + game.getHeroAgility());
		heroIntelligenceLbl = new JLabel(
		        "Int = " + game.getHeroIntelligence());
		heroImg = new JLabel(game.getHeroIcon());
		numberOfVictoriesLbl = new JLabel(
		        game.getNumberOfVictories() + " victories");
		livesLeftLbl = new JLabel(game.getNumberOfLives() + " lives left");
	}
	
	/**Sets up enemy labels initially. These will be blank until
	 * an enemy is selected (this process is handled in tableSetup().
	 */
	public void enemySetup() {
		//get enemy labels
		enemyNameLbl = new JLabel("");
		enemyStrengthLbl = new JLabel("");
		enemyAgilityLbl = new JLabel("");
		enemyIntelligenceLbl = new JLabel("");
		enemyImg = new JLabel("");
	}
	
	/**Creates the enemy table. Implements a listener to bring up 
	 * enemy stats if a user clicks on the enemy.
	 */
	public void tableSetup() {
		enemyTable = new JTable(game.getEnemyTableModel());
		//update table after clicking on a row
				enemyTable.addMouseListener(new MouseAdapter() {
					public void mouseClicked(MouseEvent e) {
						if (e.getClickCount() == 1) {
							JTable target = (JTable) e.getSource();
							int row = target.getSelectedRow();
							game.setSelectedEnemy(row);
							enemyNameLbl.setText(game.getEnemyName());
							enemyStrengthLbl.setText("Str = " + game.getEnemyStrength());
							enemyAgilityLbl.setText("Agl = " + game.getEnemyAgility());
							enemyIntelligenceLbl.setText("Int = " + game.getEnemyIntelligence());
							enemyImg.setIcon(game.getEnemyIcon());
						}
					}
				});
	}
	
}