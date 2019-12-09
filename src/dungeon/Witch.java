package dungeon;

public class Witch extends Hero {
	private static final long serialVersionUID = -3250917472749810133L;
	private final static int hitPoints = 125;
	private final static int attackSpeed = 4;
	private final static double chanceToHit = .8;
	private final static int damageMin = 35;
	private final static int damageMax = 60;
	private final static double chanceToBlock = .2;

	public Witch() {
		super(hitPoints, attackSpeed, chanceToHit, damageMin, damageMax, chanceToBlock);
	}

	public void battleChoices(DungeonCharacter opponent) {
		int choice;
		super.battleChoices(opponent);
		do {
			boolean valid = false;
			while (!valid) {
				System.out.println("1. Attack Opponent");
				System.out.println("2. Use Black Magic on Opponent");
				System.out.print("Choose an option: ");
				choice = Keyboard.readInt();
				switch (choice) {
				case 1:
					attack(opponent);
					valid = true;
					break;
				case 2:
					blackMagic(opponent);
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

	public void blackMagic(DungeonCharacter opponent) {
		if (Math.random() <= .4) {
			int blowPoints = (int) (Math.random() * 76) + 100;
			System.out.println(
					getName() + " hits " + opponent.getName() + " with black magic for " + blowPoints + " damage!");
			opponent.subtractHitPoints(blowPoints);
		} else {
			System.out.println(getName() + " failed to use black magic\n");
		}
	}
}