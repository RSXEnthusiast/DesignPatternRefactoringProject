package dungeon;

public class Commoner extends Hero {
	private static final long serialVersionUID = 2315532553629284741L;
	private final static int hitPoints = 75;
	private final static int attackSpeed = 2;
	private final static double chanceToHit = .4;
	private final static int damageMin = 20;
	private final static int damageMax = 40;
	private final static double chanceToBlock = .4;

	public Commoner() {
		super(hitPoints, attackSpeed, chanceToHit, damageMin, damageMax, chanceToBlock);
	}

	public void battleChoices(DungeonCharacter opponent) {
		int choice;
		super.battleChoices(opponent);
		do {
			boolean valid = false;
			while (!valid) {
				System.out.println("1. Attack Opponent");
				System.out.println("2. Lucky Hit on Opponent");
				System.out.print("Choose an option: ");
				choice = Keyboard.readInt();
				switch (choice) {
				case 1:
					attack(opponent);
					valid = true;
					break;
				case 2:
					luckyHit(opponent);
					valid = true;
					break;
				default:
					System.out.println("invalid choice!");
				}
			}

			setNumTurns(getNumTurns() - 1);
			if (getNumTurns() > 0)
				System.out.println("Number of turns remaining is: " + getNumTurns());

		} while (getNumTurns() > 0 && getHitPoints() > 0 && opponent.getHitPoints() > 0);
	}

	public void luckyHit(DungeonCharacter opponent) {
		if (Math.random() <= .1) {
			int blowPoints = (int) (Math.random() * 150) + 100;
			System.out.println(getName() + " lands a lucky hit for " + blowPoints + " damage!");
			opponent.subtractHitPoints(blowPoints);
		} else {
			System.out.println(getName() + " was un-lucky\n");
		}
	}
}