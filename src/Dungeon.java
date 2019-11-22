import java.util.Random;

/**
 * Description: Driver file for Heroes and Monsters project
 *
 * Copyright: Copyright (c) 2001 Company: Code Dogs Inc. I.M. Knurdy
 *
 * History: 11/4/2001: Wrote program --created DungeonCharacter class --created
 * Hero class --created Monster class --had Hero battle Monster --fixed attack
 * quirks (dead monster can no longer attack) --made Hero and Monster abstract
 * --created Warrior --created Ogre --made Warrior and Ogre battle --added
 * battleChoices to Hero --added special skill to Warrior --made Warrior and
 * Ogre battle --created Sorceress --created Thief --created Skeleton --created
 * Gremlin --added game play features to Dungeon.java (this file) 11/27/2001:
 * Finished documenting program version 1.0
 */

/*
 * This class is the driver file for the Heroes and Monsters project. It will do
 * the following:
 * 
 * 1. Allow the user to choose a hero 2. Randomly select a monster 3. Allow the
 * hero to battle the monster
 * 
 * Once a battle concludes, the user has the option of repeating the above
 */
public class Dungeon {
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

	/*-------------------------------------------------------------------
	chooseHero allows the user to select a hero, creates that hero, and
	returns it.  It utilizes a polymorphic reference (Hero) to accomplish
	this task
	---------------------------------------------------------------------*/
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

	/*-------------------------------------------------------------------
	generateMonster randomly selects a Monster and returns it.  It utilizes
	a polymorphic reference (Monster) to accomplish this task.
	---------------------------------------------------------------------*/
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

	/*-------------------------------------------------------------------
	playAgain allows gets choice from user to play another game.  It returns
	true if the user chooses to continue, false otherwise.
	---------------------------------------------------------------------*/
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

	/*-------------------------------------------------------------------
	battle is the actual combat portion of the game.  It requires a Hero
	and a Monster to be passed in.  Battle occurs in rounds.  The Hero
	goes first, then the Monster.  At the conclusion of each round, the
	user has the option of quitting.
	---------------------------------------------------------------------*/
	public static void battle(Hero theHero, Monster theMonster) {
		String quit = "";
		System.out.println(theHero.getName() + " battles " + theMonster.getName());
		System.out.println("---------------------------------------------");

		// do battle
		while (theHero.isAlive() && theMonster.isAlive() && !quit.equals("Q")) {
			// hero goes first
			theHero.battleChoices(theMonster);

			// monster's turn (provided it's still alive!)
			if (theMonster.isAlive()) {
				theMonster.attack(theHero);
				theMonster.heal();
			} else {
				System.out.println(theHero.getName() + " was victorious!");
				// monster isn't alive, so don't prompt to quit.
				continue;
			}
			// let the player bail out if desired
			System.out.print("\nQ to quit, anything else to continue: ");
			quit = Keyboard.readString().toUpperCase();
		}

		if (!theHero.isAlive())
			System.out.println(theHero.getName() + " was defeated :-(");
		if (quit.equals("Q"))
			System.out.println("Quitters never win ;-)");
	}
}