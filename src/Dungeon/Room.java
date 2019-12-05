package Dungeon;

import java.util.LinkedList;
import java.util.Random;

import javax.swing.plaf.synth.SynthSplitPaneUI;

public class Room {
	private final double chanceOfHeal = 10;
	private final double chanceOfPit = 10;
	private final double chanceOfMonster = 10;
	private final String[] allowedItems = { "heal", "pit", "monster", "abstraction", "encapsulation", "inheritance",
			"polymorphism", "enter", "exit" };

	private LinkedList<String> inRoom;
	private LinkedList<String> doors;

	public Room() {
		inRoom = new LinkedList<String>();
		Random rand = new Random();
		// healing
		if (rand.nextInt(100) <= chanceOfHeal) {
			addThing("heal");
		}
		// pit
		if (rand.nextInt(100) <= chanceOfPit) {
			addThing("pit");
		}
		// monster
		if (rand.nextInt(100) <= chanceOfMonster) {
			addThing("monster");
		}
		// doors
		doors.add("n");
		doors.add("e");
		doors.add("s");
		doors.add("w");
	}

	public boolean addThing(String string) {
		if (isAllowedItem(string) && !inRoom.contains("exit") && !inRoom.contains("enter")) {
			inRoom.add(string);
			return true;
		}
		return false;
	}

	public void removeDoor(String string) {
		doors.remove(string);
	}

	private boolean isAllowedItem(String string) {
		for (String allowedItem : allowedItems) {
			if (string.equals(allowedItem)) {
				return true;
			}
		}
		return false;
	}

	public void setContents(String string) {
		if (isAllowedItem(string)) {
			inRoom.clear();
			inRoom.add(string);
		}
	}

	public String toString() {
		String result = "";
		// top left
		result += "*";
		// top
		if (doors.contains("n")) {
			result += "-";
		} else {
			result += "*";
		}
		// top right
		result += "*\n";
		// left
		if (doors.contains("w")) {
			result += "|";
		} else {
			result += "*";
		}
		// center
		if (inRoom.size() > 1) {
			result += "M";
		} else if (isPillar()) {
			result += "i";
		} else if (inRoom.contains("pit")) {
			result += "P";
		} else if (inRoom.contains("heal")) {
			result += "H";
		} else if (inRoom.contains("monster")) {
			result += "X";
		} else if (inRoom.contains("enter")) {
			result += "I";
		} else if (inRoom.contains("exit")) {
			result += "O";
		} else {
			result += "E";
		}
		// right
		if (doors.contains("e")) {
			result += "|";
		} else {
			result += "*";
		}
		// bottom left
		result += "\n*";
		// bottom
		if (doors.contains("s")) {
			result += "-";
		} else {
			result += "*";
		}
		// bottom right
		result += "*";
		return result;
	}

	private boolean isPillar() {
		if (inRoom.contains("encapsulation") || inRoom.contains("inheritance") || inRoom.contains("polymorphism")
				|| inRoom.contains("abstraction")) {
			return true;
		}
		return false;
	}
}