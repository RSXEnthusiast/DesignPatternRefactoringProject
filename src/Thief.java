public class Thief extends Hero {

	private final static int hitPoints = 75;
	private final static int attackSpeed = 6;
	private final static double chanceToHit = .8;
	private final static int damageMin = 20;
	private final static int damageMax = 40;
	private final static double chanceToBlock = .5;
	
	public Thief() {
		super(hitPoints, attackSpeed, chanceToHit, damageMin, damageMax, chanceToBlock);
	}

	public void battleChoices(DungeonCharacter opponent) {
		super.battleChoices(opponent);
		int choice;

		do {
			boolean valid = false;
			while (!valid) {
				System.out.println("1. Attack Opponent");
				System.out.println("2. Surprise Attack");
				System.out.print("Choose an option: ");
				choice = Keyboard.readInt();
				switch (choice) {
				case 1:
					attack(opponent);
					valid = true;
					break;
				case 2:
					surpriseAttack(opponent);
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

	public void surpriseAttack(DungeonCharacter opponent) {
		double surprise = Math.random();
		if (surprise <= .4) {
			System.out.println("Surprise attack was successful!\n" + getName() + " gets an additional turn.");
			setNumTurns(getNumTurns() + 1);
			attack(opponent);
		} else if (surprise >= .9) {
			System.out.println("Uh oh! " + opponent.getName() + " saw you and" + " blocked your attack!");
		} else
			attack(opponent);
	}
}