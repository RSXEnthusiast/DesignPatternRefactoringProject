package dungeon;
public class Ogre extends Monster {
	private static final long serialVersionUID = -3854637284609425753L;
	private final static String name = "Oscar the Ogre";
	private final static int hitPoints = 200;
	private final static int attackSpeed = 2;
	private final static double chanceToHit = .6;
	private final static double chanceToHeal = .1;
	private final static int damageMin = 30;
	private final static int damageMax = 50;
	private final static int minHeal = 30;
	private final static int maxHeal = 50;

	public Ogre() {
		super(name, hitPoints, attackSpeed, chanceToHit, chanceToHeal, damageMin, damageMax, minHeal, maxHeal);
	}
}