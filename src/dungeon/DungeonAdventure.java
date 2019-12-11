package dungeon;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Queue;
import java.util.Scanner;

public class DungeonAdventure {
	public static void main(String[] args) throws ClassNotFoundException, IOException {
		explainGame();
		Hero theHero = null;
		Dungeon theDungeon = null;
		do {
			boolean saveGame = false;
			if (isSaveGame() && yesOrNo("Would you like to load your saved game? Y/N: ")) {
				theDungeon = loadDungeon();
				theHero = loadHero();
				saveGame = true;
			}
			if (!saveGame) {
				int[] size = chooseDungeonSize();
				theHero = chooseHero();
				theDungeon = new Dungeon(size[0], size[1]);
			}
			playGame(theDungeon, theHero);
		} while (playAgain());
		System.out.println("Thanks for playing!");
	}

	private static boolean yesOrNo(String q) {
		while (true) {
			System.out.print(q);
			Scanner scanner = new Scanner(System.in);
			String sel = scanner.next().toUpperCase();
			switch (sel) {
			case "Y":
				return true;
			case "N":
				return false;
			default:
				System.out.println("Make a valid selection.");
			}
		}
	}

	static Dungeon loadDungeon() {
		try {
			ObjectInputStream inD = new ObjectInputStream(new FileInputStream("savedD.txt"));
			Dungeon dun = (Dungeon) inD.readObject();
			inD.close();
			return dun;
		} catch (ClassNotFoundException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	static Hero loadHero() {
		try {
			ObjectInputStream inH = new ObjectInputStream(new FileInputStream("savedH.txt"));
			Hero hero = (Hero) inH.readObject();
			inH.close();
			return hero;
		} catch (ClassNotFoundException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	private static boolean isSaveGame() {
		File file = new File("savedD.txt");
		if (file.exists()) {
			return true;
		}
		file.delete();
		return false;
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
			try {
				int[] result = new int[2];
				while (result[0] * result[1] < 6) {
					System.out.print("Enter a dungeon size in the format \"x y\" here: ");
					Scanner scanner = new Scanner(System.in);
					result[0] = Integer.parseInt(scanner.next());
					result[1] = Integer.parseInt(scanner.next());
					if (result[0] < 1 || result[1] < 1) {
						System.out.println("Enter positive numbers.");
						result[0] = 0;
					} else if (result[0] * result[1] < 6) {
						System.out.println("Your dungeon is too small. It needs to have at least 6 rooms.");
					} else if (result[0] * result[1] > 1000) {
						System.out.println("You dungeon is too massive. It must be 1000 rooms or less.");
						result[0] = 0;
					} else if (result[0] * result[1] > 50) {
						if (!yesOrNo("Your dungeon is massive, are you sure you'd like to continue? Y/N: ")) {
							result[0] = 0;
						}
					}
				}
				return result;
			} catch (NumberFormatException e) {
				System.out.println("Enter numbers.");
			}
		}
	}

	private static Hero chooseHero() {
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

	private static boolean playAgain() {
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

	private static void playGame(Dungeon theDungeon, Hero theHero) {
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
								"This is the entrance. This is where you start. Do you need more explanation?");
						break;

					case "exit":
						if (theHero.getPillars() == 4 && exit()) {
							System.out
									.println("You've done it! You've won! What will you do now? Only you can decide!");
							System.out.println("The dungeon, after you ransacked it:\n");
							System.out.println(theDungeon.toString());
							File file1 = new File("savedD.txt");
							File file2 = new File("savedH.txt");
							file1.delete();
							file2.delete();
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
		System.out.println(theDungeon.toString());
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
			System.out.println("-z to save and quit.");
			System.out.println("-w/a/s/d to move.");
			System.out.print("Make a selection: ");
			String selection = Keyboard.readString().toLowerCase();
			switch (selection) {
			case "w":
				theDungeon.moveNorth();
				return;
			case "d":
				theDungeon.moveEast();
				return;
			case "s":
				theDungeon.moveSouth();
				return;
			case "a":
				theDungeon.moveWest();
				return;
			case "z":
				saveGame(theDungeon, theHero);
				System.exit(0);
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

	static void saveGame(Dungeon theDungeon, Hero theHero) {
		if (isSaveGame() && !yesOrNo("Would you like to overwrite your previous save? Y/N: ")) {
			return;
		}
		try {
			ObjectOutputStream outD = new ObjectOutputStream(new FileOutputStream("savedD.txt"));
			ObjectOutputStream outH = new ObjectOutputStream(new FileOutputStream("savedH.txt"));
			outD.writeObject(theDungeon);
			outH.writeObject(theHero);
			outD.close();
			outH.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("Save successful! Bye!");
	}
}