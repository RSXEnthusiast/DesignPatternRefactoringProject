package dungeon;

import java.util.Random;

public abstract class Hero extends DungeonCharacter {
	private static final long serialVersionUID = 7246227628651766785L;
	private double chanceToBlock;
	private int numTurns;
	private int healPotions;
	private int pillarsFound;

	public Hero(int hitPoints, int attackSpeed, double chanceToHit, int damageMin, int damageMax,
			double chanceToBlock) {
		super(readName(), hitPoints, attackSpeed, chanceToHit, damageMin, damageMax);
		this.chanceToBlock = chanceToBlock;
		this.healPotions = 0;
		this.pillarsFound = 0;
	}
	
	public Hero(String name,int hitPoints, int attackSpeed, double chanceToHit, int damageMin, int damageMax,
			double chanceToBlock) {
		super(name, hitPoints, attackSpeed, chanceToHit, damageMin, damageMax);
		this.chanceToBlock = chanceToBlock;
		this.healPotions = 0;
		this.pillarsFound = 0;
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
			setNumTurns(1);
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

	public void fellInPit() {
		Random rand = new Random();
		int damage = rand.nextInt(20) + 1;
		System.out.println(super.getName() + " fell in a pit.");
		super.subtractHitPoints(damage);
	}

	public void fight() {
		Monster monster = generateMonster();
		System.out.println(getName() + " battles " + monster.getName());
		System.out.println("---------------------------------------------");
		while (isAlive() && monster.isAlive()) {
			battleChoices(monster);
			if (monster.isAlive()) {
				monster.attack(this);
				monster.heal();
			} else {
				System.out.println(getName() + " was victorious!");
				return;
			}
		}
		System.out.println(getName() + " was defeated.");
	}

	public static Monster generateMonster() {
		Random rand = new Random();
		switch (rand.nextInt(5)) {
		case 0:
			return new Ogre();
		case 1:
			return new Gremlin();
		case 2:
			return new Gnat();
		case 3:
			return new Leech();
		default:
			return new Skeleton();
		}
	}

	public void addHealPotion() {
		healPotions++;
		System.out.println("You gained a heal potion!");
	}

	public void addPillar() {
		pillarsFound++;
	}

	public int getPillars() {
		return pillarsFound;
	}

	public int getHealPotions() {
		return healPotions;
	}

	public void useHealPotion() {
		Random rand = new Random();
		int heal = rand.nextInt(10) + 10;
		System.out.println(super.getName() + " healed for " + heal + "HP,");
		System.out.println("and now has " + getHitPoints() + " HP left.");
		addHitPoints(heal);
		healPotions--;
	}
}