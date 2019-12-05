package Dungeon;

import java.util.Random;

public class DungeonAdventure {
	public static void main(String[] args) {
		Hero theHero;
		Monster theMonster;
		do {
			theHero = chooseHero();
			theMonster = generateMonster();
			battle(theHero, theMonster);

		} while (playAgain());
		System.out.println("Thanks for playing!");
	}

	public static Hero chooseHero() {
		int choice;
		while (true) {
			System.out.print("Choose a hero:\n1. Warrior\n2. Sorceress\n3. Thief\nMake a selection: ");
			choice = Keyboard.readInt();
			switch (choice) {
			case 1:
				return new Warrior();
			case 2:
				return new Sorceress();
			case 3:
				return new Thief();
			default:
				System.out.println("invalid choice, try again.\n");
			}
		}
	}

	public static Monster generateMonster() {
		Random rand = new Random();
		switch (rand.nextInt(3)) {
		case 0:
			return new Ogre();
		case 1:
			return new Gremlin();
		default:
			return new Skeleton();
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

	public static void battle(Hero theHero, Monster theMonster) {

	}
}