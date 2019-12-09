package dungeon;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

import javax.swing.plaf.synth.SynthSplitPaneUI;

public class Room implements Serializable {
	private static final long serialVersionUID = -720726509227419545L;
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
		// pit
		if (rand.nextInt(100) <= chanceOfPit) {
			addThing("pit");
		}
		// monster
		if (rand.nextInt(100) <= chanceOfMonster) {
			addThing("monster");
		}
		// healing
		if (rand.nextInt(100) <= chanceOfHeal) {
			addThing("heal");
		}
		doors = new LinkedList<String>();
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
		result += getContentsLetter();
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

	public String getContentsLetter() {
		if (inRoom.size() > 1) {
			return "M";
		} else if (isPillar()) {
			return "i";
		} else if (inRoom.contains("pit")) {
			return "P";
		} else if (inRoom.contains("heal")) {
			return "H";
		} else if (inRoom.contains("monster")) {
			return "X";
		} else if (inRoom.contains("enter")) {
			return "I";
		} else if (inRoom.contains("exit")) {
			return "O";
		} else {
			return "E";
		}
	}

	private boolean isPillar() {
		if (inRoom.contains("encapsulation") || inRoom.contains("inheritance") || inRoom.contains("polymorphism")
				|| inRoom.contains("abstraction")) {
			return true;
		}
		return false;
	}

	public Queue<String> getOrderOfEvents() {
		LinkedList<String> temp = new LinkedList<String>(inRoom);
		Queue<String> order = new LinkedList<String>();
		if (temp.contains("pit")) {
			order.add("pit");
			temp.remove("pit");
		}
		if (temp.contains("monster")) {
			order.add("monster");
			temp.remove("monster");
		}
		if (temp.contains("heal")) {
			order.add("heal");
			temp.remove("heal");
		}
		for (String string : temp) {
			order.add(string);
		}
		return order;
	}

	public void removeThing(String curEvent) {
		inRoom.remove(curEvent);
	}

	public LinkedList<String> getInRoom() {
		return inRoom;
	}

	public void clearRoom() {
		inRoom.clear();
	}

	public LinkedList<String> getDoors() {
		return doors;
	}
}