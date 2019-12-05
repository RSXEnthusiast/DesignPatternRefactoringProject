package Dungeon;

import java.util.Random;
import java.util.Scanner;

public class DungeonAdventure {
	public static void main(String[] args) {
		Hero theHero;
		Monster theMonster;
		Dungeon theDungeon;
		do {
			int[] size = chooseDungeonSize();
			theHero = chooseHero();
			theMonster = generateMonster();
			theDungeon = new Dungeon(size[0], size[1]);
			battle(theHero, theMonster, theDungeon);
		} while (playAgain());
		System.out.println("Thanks for playing!");
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

	public static Monster generateMonster() {
		Random rand = new Random();
		switch (rand.nextInt(5)) {
		case 0:
			return new Ogre();
		case 1:
			return new Gremlin();
		case 2:
			return new Gnat();
		case 3:
			return new Leech();
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

	public static void battle(Hero theHero, Monster theMonster, Dungeon theDungeon) {
		
	}
}