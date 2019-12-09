package dungeon;
public class Skeleton extends Monster {
	private static final long serialVersionUID = 2822440767123994225L;
	private final static String name = "Sargath the Skeleton";
	private final static int hitPoints = 100;
	private final static int attackSpeed = 3;
	private final static double chanceToHit = .8;
	private final static double chanceToHeal = .3;
	private final static int damageMin = 30;
	private final static int damageMax = 50;
	private final static int minHeal = 30;
	private final static int maxHeal = 50;

	public Skeleton() {
		super(name, hitPoints, attackSpeed, chanceToHit, chanceToHeal, damageMin, damageMax, minHeal, maxHeal);
	}
}