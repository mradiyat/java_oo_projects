package student;

import javax.swing.ImageIcon;

public abstract class Character{
	
	private String name;
	protected int strength;
	protected int agility;
	protected int intelligence;
	private String taunt;
	protected ImageIcon icon;
	
	public Character(String name, int strength, int agility, int intelligence,
			String taunt, String imgLoc) {
		this.name = name;
		this.strength = strength;
		this.agility = agility;
		this.intelligence = intelligence;
		this.taunt = taunt;
		icon = new ImageIcon(imgLoc);
	}
	
	/**
	 * @return The name of this character
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return The amount of strength (out of 100) this character has
	 */
	public int getStrength() {
		return strength;
	}

	/**
	 * @return The amount of agility (out of 100) this character has
	 */
	public int getAgility() {
		return agility;
	}

	/**
	 * @return The amount of agility (out of 100) this character has
	 */
	public int getIntelligence() {
		return intelligence;
	}
	
	/**
	 * @return The taunt that will be displayed if this character wins a battle
	 */
	public String getTaunt() {
		return taunt;
	}
	
	/**
	 * @return The ImageIcon that will be displayed 
	 */
	public ImageIcon getIcon() {
		return icon;
	}
}
