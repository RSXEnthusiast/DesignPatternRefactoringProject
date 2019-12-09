package dungeon;

public class Gnat extends Monster {
	private static final long serialVersionUID = 2093659130020719482L;
	private final static String name = "Matt the gnat";
	private final static int hitPoints = 1;
	private final static int attackSpeed = 10;
	private final static double chanceToHit = 1;
	private final static double chanceToHeal = 0;
	private final static int damageMin = 1;
	private final static int damageMax = 2;
	private final static int minHeal = 0;
	private final static int maxHeal = 0;

	public Gnat() {
		super(name, hitPoints, attackSpeed, chanceToHit, chanceToHeal, damageMin, damageMax, minHeal, maxHeal);
	}
}