package dungeon;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

public class Dungeon implements Serializable {
	private static final long serialVersionUID = -3532566332016735961L;
	private Room[][] rooms;
	private int playerX;
	private int playerY;

	public Dungeon(int sizeX, int sizeY) {
		rooms = new Room[sizeX][sizeY];
		Random rand = new Random();
		// populate array with default rooms
		for (Room[] roomRows : getRooms()) {
			for (int i = 0; i < roomRows.length; i++) {
				roomRows[i] = new Room();
			}
		}
		// generate pillar locations
		int[][] pillarLocations = new int[4][2];
		for (int[] coords : pillarLocations) {
			for (int i = 0; i < coords.length; i++) {
				coords[i] = -1;
			}
		}
		for (int[] thisLoc : pillarLocations) {
			int x = rand.nextInt(sizeX);
			int y = rand.nextInt(sizeY);
			while (contains(pillarLocations, x, y)) {
				x = rand.nextInt(sizeX);
				y = rand.nextInt(sizeY);
			}
			thisLoc[0] = x;
			thisLoc[1] = y;
		}
		// pillar names
		Queue<String> specialRoomThings = new LinkedList<String>();
		specialRoomThings.add("abstraction");
		specialRoomThings.add("encapsulation");
		specialRoomThings.add("inheritance");
		specialRoomThings.add("polymorphism");
		// place pillars
		for (int[] coords : pillarLocations) {
			getRooms()[coords[0]][coords[1]].addThing(specialRoomThings.poll());
		}
		// generate enter and exit locations
		int[][] enterExitLocations = new int[2][2];
		for (int[] coords : enterExitLocations) {
			for (int i = 0; i < coords.length; i++) {
				coords[i] = -1;
			}
		}
		for (int[] thisLoc : enterExitLocations) {
			int x = rand.nextInt(sizeX);
			int y = rand.nextInt(sizeY);
			while (contains(pillarLocations, x, y) || contains(enterExitLocations, x, y)) {
				x = rand.nextInt(sizeX);
				y = rand.nextInt(sizeY);
			}
			thisLoc[0] = x;
			thisLoc[1] = y;
		}
		// enter and exit names
		specialRoomThings.add("enter");
		specialRoomThings.add("exit");
		// place enter and exit
		for (int[] coords : enterExitLocations) {
			getRooms()[coords[0]][coords[1]].setContents(specialRoomThings.poll());
		}
		playerX = enterExitLocations[0][0];
		playerY = enterExitLocations[0][1];
		// removing doors around edges
		for (Room room : getRooms()[0]) {
			room.removeDoor("w");
		}
		for (Room room : getRooms()[getRooms().length - 1]) {
			room.removeDoor("e");
		}
		for (Room[] roomRows : getRooms()) {
			roomRows[0].removeDoor("n");
			roomRows[roomRows.length - 1].removeDoor("s");
		}
	}

	boolean contains(int[][] pillarLocations, int x, int y) {
		for (int[] coords : pillarLocations) {
			if (coords[0] == x && coords[1] == y) {
				return true;
			}
		}
		return false;
	}

	public Room curRoom() {
		return getRooms()[playerX][playerY];
	}

	public void moveNorth() {
		if (playerY > 0) {
			playerY--;
		} else {
			System.out.println("Congratulations. You just bashed your head into a wall.");
		}
	}

	public void moveEast() {
		if (playerX < getRooms().length - 1) {
			playerX++;
		} else {
			System.out.println("Congratulations. You just bashed your head into a wall.");
		}
	}

	public void moveSouth() {
		if (playerY < getRooms()[0].length - 1) {
			playerY++;
		} else {
			System.out.println("Congratulations. You just bashed your head into a wall.");
		}
	}

	public void moveWest() {
		if (playerX > 0) {
			playerX--;
		} else {
			System.out.println("Congratulations. You just bashed your head into a wall.");
		}
	}

	public String toString() {
		String result = "";
		// top
		for (int i = 0; i < getRooms().length * 2 + 1; i++) {
			result += "*";
		}
		// rows
		for (int i = 0; i < getRooms()[0].length; i++) {
			result += "\n*";
			// rooms in row
			for (int j = 0; j < getRooms().length; j++) {
				result += getRooms()[j][i].getContentsLetter();
				if (j == getRooms().length - 1) { // end of row
					result += "*\n*";
				} else { // not end of row
					result += "|";
				}
			}
			// between rows
			if (i != getRooms()[0].length - 1) { // not last row
				for (int j = 0; j < getRooms().length; j++) {
					result += "-*";
				}
			}
		}
		// bottom
		for (int i = 0; i < getRooms().length * 2; i++) {
			result += "*";
		}
		return result;
	}

	public Room[][] getRooms() {
		return rooms;
	}

	public void setPlayerX(int i) {
		playerX = i;
	}

	public void setPlayerY(int i) {
		playerY = i;
	}

	public int getPlayerX() {
		return playerX;
	}

	public int getPlayerY() {
		return playerY;
	}
}