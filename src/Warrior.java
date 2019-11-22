public class Warrior extends Hero {

	public Warrior() {
		super("Warrior", 125, 4, .8, 35, 60, .2);
	}

	public void battleChoices(DungeonCharacter opponent) {
		int choice;
		super.battleChoices(opponent);
		do {
			boolean valid = false;
			while (!valid) {
				System.out.println("1. Attack Opponent");
				System.out.println("2. Crushing Blow on Opponent");
				System.out.print("Choose an option: ");
				choice = Keyboard.readInt();
				switch (choice) {
				case 1:
					attack(opponent);
					valid = true;
					break;
				case 2:
					crushingBlow(opponent);
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

	public void crushingBlow(DungeonCharacter opponent) {
		if (Math.random() <= .4) {
			int blowPoints = (int) (Math.random() * 76) + 100;
			System.out.println(getName() + " lands a CRUSHING BLOW for " + blowPoints + " damage!");
			opponent.subtractHitPoints(blowPoints);
		} else {
			System.out.println(getName() + " failed to land a crushing blow\n");
		}
	}
}