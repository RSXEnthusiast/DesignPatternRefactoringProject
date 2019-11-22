public class Sorceress extends Hero {
	public final int MIN_ADD = 25;
	public final int MAX_ADD = 50;

//-----------------------------------------------------------------
	public Sorceress() {
		super("Sorceress", 75, 5, .7, 25, 50, .3);
	}

	public void battleChoices(DungeonCharacter opponent) {
		super.battleChoices(opponent);
		int choice;

		do {
			System.out.println("1. Attack Opponent");
			System.out.println("2. Increase Hit Points");
			System.out.print("Choose an option: ");
			choice = Keyboard.readInt();

			while (choice != 1 || choice != 2) {
				switch (choice) {
				case 1:
					attack(opponent);
					break;
				case 2:
					increaseHitPoints();
					break;
				default:
					System.out.println("Invalid choice, try again!");
				}
			}
			setNumTurns(getNumTurns() - 1);
			if (getNumTurns() > 0)
				System.out.println("Number of turns remaining is: " + getNumTurns());
		} while (getNumTurns() > 0 && getHitPoints() > 0 && opponent.getHitPoints() > 0);
	}

	public void attack(DungeonCharacter opponent) {
		System.out.println(getName() + " casts a spell of fireball at " + opponent.getName() + ":");
		super.attack(opponent);
	}

	public void increaseHitPoints() {
		int hPoints = (int) (Math.random() * (MAX_ADD - MIN_ADD + 1)) + MIN_ADD;
		addHitPoints(hPoints);
		System.out.println(getName() + " added [" + hPoints + "] points.\n" + "Total hit points remaining are: "
				+ getHitPoints() + "\n");
	}
}