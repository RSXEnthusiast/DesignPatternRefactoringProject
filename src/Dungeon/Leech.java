package Dungeon;

public class Leech extends Monster {

	private final static String name = "Sargath the Skeleton";
	private final static int hitPoints = 10;
	private final static int attackSpeed = 5;
	private final static double chanceToHit = .2;
	private final static double chanceToHeal = .7;
	private final static int damageMin = 5;
	private final static int damageMax = 15;
	private final static int minHeal = 5;
	private final static int maxHeal = 15;

	public Leech() {
		super(name, hitPoints, attackSpeed, chanceToHit, chanceToHeal, damageMin, damageMax, minHeal, maxHeal);
	}
}