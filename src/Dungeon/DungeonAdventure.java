package Dungeon;

import java.util.Queue;
import java.util.Scanner;

public class DungeonAdventure {
	public static void main(String[] args) {
		explainGame();
		Hero theHero;
		Dungeon theDungeon;
		do {
			int[] size = chooseDungeonSize();
			theHero = chooseHero();
			theDungeon = new Dungeon(size[0], size[1]);
			battle(theHero, theDungeon);
		} while (playAgain());
		System.out.println("Thanks for playing!");
	}

	private static void explainGame() {
		System.out.print("Would you like to hear the rules? Y/N: ");
		String next = Keyboard.readString().toUpperCase();
		if (next.equals("N")) {
			return;
		}
		System.out.println(
				"Either you entered yes or something else, in which case I'm assiming you're dumb enough that");
		System.out.println("you need to hear these.");
		System.out.println(
				"The game is simple. You're in a maze. You can move up, down, left, or right, unless you're at the edge.");
		System.out.println("If you aren't in combat, you can use a health potion.");
		System.out.println("In any given room you could come across a pit, health, a monster, a pillar, or the exit.");
		System.out.println("You must have collected all four pillars of OO before you're allowed to exit.");
		System.out.println("The symbols are as follows:");
		System.out.println("M - Multiple items");
		System.out.println("E - Empty room");
		System.out.println("P - Pit");
		System.out.println("X - Monster");
		System.out.println("H - Health Potion");
		System.out.println("I - Entrance");
		System.out.println("O - Exit");
		System.out.println("i - Pillar of OO");
		System.out.println("That's about it. Enjoy the game.");
	}

	private static int[] chooseDungeonSize() {
		while (true) {
			System.out.print("Enter a dungeon size in the format \"x y\" here: ");
			Scanner scanner = new Scanner(System.in);
			try {
				int[] result = new int[2];
				result[0] = Integer.parseInt(scanner.next());
				result[1] = Integer.parseInt(scanner.next());
				return result;
			} catch (NumberFormatException e) {
				System.out.println("Enter numbers.");
			}
		}
	}

	public static Hero chooseHero() {
		int choice;
		while (true) {
			System.out.print(
					"Choose a hero:\n1. Warrior\n2. Sorceress\n3. Thief\n4. Commoner\n5. Witch\nMake a selection: ");
			choice = Keyboard.readInt();
			switch (choice) {
			case 1:
				return new Warrior();
			case 2:
				return new Sorceress();
			case 3:
				return new Thief();
			case 4:
				return new Commoner();
			case 5:
				return new Witch();
			default:
				System.out.println("invalid choice, try again.\n");
			}
		}
	}

	public static boolean playAgain() {
		String again;
		while (true) {
			System.out.print("Play again (Y/N): ");
			again = Keyboard.readString().toUpperCase();
			switch (again) {
			case "Y":
				return true;
			case "N":
				return false;
			default:
				System.out.println("Make a valid selection!\n");
			}
		}
	}

	public static void battle(Hero theHero, Dungeon theDungeon) {
		do {
			Queue<String> events = theDungeon.curRoom().getOrderOfEvents();
			System.out.println(theDungeon.curRoom().toString());
			if (events.isEmpty()) {
				System.out.println("The room is empty.");
			} else {
				while (!events.isEmpty()) {
					String curEvent = events.poll();
					switch (curEvent) {
					case "pit":
						theHero.fellInPit();
						if (!theHero.isAlive()) {
							events.clear();
						}
						break;
					case "monster":
						theHero.fight();
						if (!theHero.isAlive()) {
							// hero is dead. no more events.
							events.clear();
						} else {
							theDungeon.curRoom().removeThing(curEvent);
						}
						System.out.println(theDungeon.curRoom().toString());
						break;
					case "heal":
						theHero.addHealPotion();
						theDungeon.curRoom().removeThing(curEvent);
						System.out.println(theDungeon.curRoom().toString());
						break;
					case "enter":
						System.out.println(
								"This is the entrance. This is where you start. Do you need more explination?");
						break;
					case "exit":
						if (theHero.getPillars() == 4 && exit()) {
							System.out
									.println("You've done it! You've won! What will you do now? Only you can decide!");
							System.out.println("The dungeon, after you ransacked it:\n");
							System.out.println(theDungeon.toString());
							return;
						} else if (theHero.getPillars() < 4) {
							System.out.println("This is the exit. Come back when you've collected all the pillars!");
						} else {
							System.out.println("Enjoy exploring! Return here to exit when you're ready!");
						}
						break;
					default:
						System.out.println("You've collected the " + curEvent + " pillar of OO!");
						theDungeon.curRoom().removeThing(curEvent);
						theHero.addPillar();
						if (theHero.getPillars() == 4) {
							System.out.println("You've got all four pillars! Look for the exit!");
						}
						System.out.println(theDungeon.curRoom().toString());
					}
				}
			}
			if (theHero.isAlive()) {
				characterChoices(theDungeon, theHero);
			}
		} while (theHero.isAlive());
		System.out.println("Yea, you died. Bummer. Maybe don't do that next time.");
	}

	private static boolean exit() {
		while (true) {
			System.out.print("You've collected all of the pillars of OO, Would you like to leave the dungeon? Y/N: ");
			String next = Keyboard.readString().toUpperCase();
			switch (next) {
			case "Y":
				return true;
			case "N":
				return false;
			default:
				System.out.println("Enter Y or N. It isn't that hard.");
			}
		}
	}

	private static void characterChoices(Dungeon theDungeon, Hero theHero) {
		while (true) {
			if (theHero.getHealPotions() > 0) {
				System.out.println("-h to use one of your " + theHero.getHealPotions() + " health potions.");
			}
			System.out.println("-n/e/s/w to move.");
			System.out.print("Make a selection: ");
			String selection = Keyboard.readString().toLowerCase();
			switch (selection) {
			case "n":
				theDungeon.moveNorth();
				return;
			case "e":
				theDungeon.moveEast();
				return;
			case "s":
				theDungeon.moveSouth();
				return;
			case "w":
				theDungeon.moveWest();
				return;
			case "h":
				if (theHero.getHealPotions() > 0) {
					theHero.useHealPotion();
					break;
				}
			default:
				System.out.println("Make a valid selection.");
			}
		}
	}
}