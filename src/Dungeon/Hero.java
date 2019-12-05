package Dungeon;

public abstract class Hero extends DungeonCharacter {
	private double chanceToBlock;
	private int numTurns;
	private int healPotions;
	private int pillarsFound;

	public Hero(int hitPoints, int attackSpeed, double chanceToHit, int damageMin, int damageMax,
			double chanceToBlock) {
		super(readName(), hitPoints, attackSpeed, chanceToHit, damageMin, damageMax);
		this.chanceToBlock = chanceToBlock;
	}

	public static String readName() {
		System.out.print("Enter character name: ");
		return Keyboard.readString();
	}

	public boolean defend() {
		return Math.random() <= chanceToBlock;
	}

	public void subtractHitPoints(int hitPoints) {
		if (defend()) {
			System.out.println(getName() + " BLOCKED the attack!");
		} else {
			super.subtractHitPoints(hitPoints);
		}
	}

	public void battleChoices(DungeonCharacter opponent) {
		setNumTurns(getAttackSpeed() / opponent.getAttackSpeed());
		if (getNumTurns() == 0) {
			setNumTurns(getNumTurns() + 1);
		}
		System.out.println("Number of turns this round is: " + getNumTurns());
	}

	public int getNumTurns() {
		return numTurns;
	}

	public void setNumTurns(int numTurns) {
		this.numTurns = numTurns;
	}

	public String toString() {
		return super.toString() + "\nHealing Potions: " + healPotions + "\nPillars of OO found: " + pillarsFound;
	}
}