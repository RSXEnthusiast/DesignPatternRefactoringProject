package Dungeon;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

public class Dungeon {
	private Room[][] rooms;
	private int playerX;
	private int playerY;

	public Dungeon(int sizeX, int sizeY) {
		rooms = new Room[sizeX][sizeY];
		Random rand = new Random();
		// populate array with default rooms
		for (Room[] roomRows : rooms) {
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
			rooms[coords[0]][coords[1]].addThing(specialRoomThings.poll());
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
			rooms[coords[0]][coords[1]].setContents(specialRoomThings.poll());
		}
		playerX = enterExitLocations[0][0];
		playerY = enterExitLocations[0][1];
		// removing doors around edges
		for (Room room : rooms[0]) {
			room.removeDoor("w");
		}
		for (Room room : rooms[rooms.length - 1]) {
			room.removeDoor("e");
		}
		for (Room[] roomRows : rooms) {
			roomRows[0].removeDoor("n");
			roomRows[roomRows.length - 1].removeDoor("s");
		}
	}

	private boolean contains(int[][] pillarLocations, int x, int y) {
		for (int[] coords : pillarLocations) {
			if (coords[0] == x && coords[1] == y) {
				return true;
			}
		}
		return false;
	}

	public Room curRoom() {
		return rooms[playerX][playerY];
	}

	public void moveNorth() {
		if (playerY > 0) {
			playerY--;
		} else {
			System.out.println("Congratulations. You just bashed your head into a wall.");
		}
	}

	public void moveEast() {
		if (playerX < rooms.length - 1) {
			playerX++;
		} else {
			System.out.println("Congratulations. You just bashed your head into a wall.");
		}
	}

	public void moveSouth() {
		if (playerY < rooms[0].length - 1) {
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
		for (int i = 0; i < rooms.length * 2 + 1; i++) {
			result += "*";
		}
		for (int i = 0; i < rooms[0].length; i++) {
			result += "\n*";
			for (int j = 0; j < rooms.length; j++) {
				result += rooms[j][i].getContentsLetter();
				if (j == rooms.length - 1) {
					result += "*\n*";
				} else {
					result += "|";
				}
			}
			if (i != rooms[0].length - 1) {
				for (int j = 0; j < rooms.length; j++) {
					result += "-*";
				}
			}
		}
		for (int i = 0; i < rooms.length * 2; i++) {
			result += "*";
		}
		return result;
	}
}