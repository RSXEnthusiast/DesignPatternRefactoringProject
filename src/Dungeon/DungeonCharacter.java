package Dungeon;

public abstract class DungeonCharacter {

	private String name;
	private int hitPoints;
	private int attackSpeed;
	private double chanceToHit;
	private int damageMin, damageMax;

	public DungeonCharacter(String name, int hitPoints, int attackSpeed, double chanceToHit, int damageMin,
			int damageMax) {
		this.name = name;
		this.hitPoints = hitPoints;
		this.attackSpeed = attackSpeed;
		this.chanceToHit = chanceToHit;
		this.damageMin = damageMin;
		this.damageMax = damageMax;
	}

	public String getName() {
		return name;
	}

	public int getHitPoints() {
		return hitPoints;
	}

	public int getAttackSpeed() {
		return attackSpeed;
	}

	public void addHitPoints(int hitPoints) {
		if (hitPoints <= 0)
			System.out.println("Hitpoint amount must be positive.");
		else {
			this.hitPoints += hitPoints;
		}
	}

	public void subtractHitPoints(int hitPoints) {
		if (hitPoints < 0)
			System.out.println("Hitpoint amount must be positive.");
		else if (hitPoints > 0) {
			this.hitPoints -= hitPoints;
			if (this.hitPoints < 0)
				this.hitPoints = 0;
			System.out.println(getName() + " took " + hitPoints + " points damage,");
			System.out.println("and now has " + this.hitPoints + " hit points remaining.");
			System.out.println();
		}
		if (this.hitPoints == 0)
			System.out.println(getName() + " has been killed.");

	}

	public boolean isAlive() {
		return (hitPoints > 0);
	}

	public void attack(DungeonCharacter opponent) {
		System.out.println(name + " attacks " + opponent.name + ":");
		if (Math.random() <= chanceToHit) {
			opponent.subtractHitPoints((int) (Math.random() * (damageMax - damageMin + 1)) + damageMin);
			System.out.println();
		} else {
			System.out.println(name + "'s attack on " + opponent.name + " failed!");
			System.out.println();
		}
	}

	public void setName(String name) {
		this.name = name;
	}

	public String toString() {
		return name + "\nHealth: " + hitPoints;
	}
}