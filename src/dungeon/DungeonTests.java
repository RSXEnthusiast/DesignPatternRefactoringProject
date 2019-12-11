package dungeon;

import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.util.LinkedList;
import java.util.Queue;

import org.junit.jupiter.api.Test;

public class DungeonTests {
	// Some of these unit tests could wrongly fail due to the random generation of dungeons,
	// however it is statistically nearly impossible with a dungeon of size
	// 1,000,000.
	private final static Dungeon dungeon = new Dungeon(1000, 1000);

	@Test
	public void dungeon_neverTwoPillarsInARoom() {
		// arrange

		// act

		// assert
		for (Room[] roomRows : dungeon.getRooms()) {
			for (Room room : roomRows) {
				assertTrue(hasOnePillar(room));
			}
		}
	}

	@Test
	public void dungeon_EntranceAndExitAreEmptyBesidesEnterOrExit() {
		// arrange

		// act

		// assert
		for (Room[] roomRows : dungeon.getRooms()) {
			for (Room room : roomRows) {
				if (room.getInRoom().contains("enter") || room.getInRoom().contains("exit")) {
					assertTrue(room.getInRoom().size() == 1);
				}
			}
		}
	}

	@Test
	public void dungeon_CantMoveIntoWall() {
		// arrange
		dungeon.setPlayerX(0);
		dungeon.setPlayerY(0);
		// act
		dungeon.moveNorth();
		dungeon.moveWest();
		// assert
		assertEquals(0, dungeon.getPlayerX());
		assertEquals(0, dungeon.getPlayerY());

		// arrange2
		dungeon.setPlayerX(dungeon.getRooms().length - 1);
		dungeon.setPlayerY(dungeon.getRooms()[0].length - 1);
		// act2
		dungeon.moveSouth();
		dungeon.moveEast();
		// assert2
		assertEquals(dungeon.getRooms().length - 1, dungeon.getPlayerX());
		assertEquals(dungeon.getRooms()[0].length - 1, dungeon.getPlayerY());
	}

	@Test
	public void dungeon_ToString_WorksForSquareDungeon() {
		// arrange
		Dungeon dungeon = new Dungeon(3, 3);
		for (Room[] roomRows : dungeon.getRooms()) {
			for (int i = 0; i < roomRows.length; i++) {
				roomRows[i].clearRoom();
			}
		}
		String expected = "*******\n*E|E|E*\n*-*-*-*\n*E|E|E*\n*-*-*-*\n*E|E|E*\n*******";

		// act

		// assert
		assertEquals(expected, dungeon.toString());
	}

	@Test
	public void dungeon_ToString_WorksForTallDungeon() {
		// arrange
		Dungeon dungeon = new Dungeon(3, 2);
		for (Room[] roomRows : dungeon.getRooms()) {
			for (int i = 0; i < roomRows.length; i++) {
				roomRows[i].clearRoom();
			}
		}
		String expected = "*******\n*E|E|E*\n*-*-*-*\n*E|E|E*\n*******";

		// act

		// assert
		assertEquals(expected, dungeon.toString());
	}

	@Test
	public void dungeon_ToString_WorksForWideDungeon() {
		// arrange
		Dungeon dungeon = new Dungeon(2, 3);
		for (Room[] roomRows : dungeon.getRooms()) {
			for (int i = 0; i < roomRows.length; i++) {
				roomRows[i].clearRoom();
			}
		}
		String expected = "*****\n*E|E*\n*-*-*\n*E|E*\n*-*-*\n*E|E*\n*****";

		// act

		// assert
		assertEquals(expected, dungeon.toString());
	}

	@Test
	public void room_ToString_WorksForAllRoomTypes() {
		// arrange
		Room multiple = new Room();
		multiple.setContents("monster");
		multiple.addThing("pit");
		Room pit = new Room();
		pit.setContents("pit");
		Room heal = new Room();
		heal.setContents("heal");
		Room monster = new Room();
		monster.setContents("monster");
		Room pillar = new Room();
		pillar.setContents("abstraction");
		Room enter = new Room();
		enter.setContents("enter");
		Room exit = new Room();
		exit.setContents("exit");
		Room empty = new Room();
		empty.clearRoom();

		// act

		// assert
		assertEquals("M", multiple.getContentsLetter());
		assertEquals("i", pillar.getContentsLetter());
		assertEquals("P", pit.getContentsLetter());
		assertEquals("H", heal.getContentsLetter());
		assertEquals("X", monster.getContentsLetter());
		assertEquals("I", enter.getContentsLetter());
		assertEquals("O", exit.getContentsLetter());
		assertEquals("E", empty.getContentsLetter());
	}

	@Test
	public void room_RemoveDoor_Works() {
		// arrange
		Room room = new Room();
		// act
		room.removeDoor("n");
		// assert
		assertFalse(room.getDoors().contains("n"));
		assertTrue(room.getDoors().contains("e"));
		assertTrue(room.getDoors().contains("s"));
		assertTrue(room.getDoors().contains("w"));
	}

	@Test
	public void room_OrderOfEvents_Works() {
		// arrange
		Room room = new Room();
		room.clearRoom();
		room.addThing("heal");
		room.addThing("monster");
		room.addThing("abstraction");
		room.addThing("pit");
		String[] expected = { "pit", "monster", "heal", "abstraction" };

		// act
		Queue<String> order = room.getOrderOfEvents();
		int qLength = order.size();

		// assert
		for (int i = 0; i < qLength; i++) {
			assertEquals(expected[i], order.poll());
		}
	}

	@Test
	public void dungeonAdventure_SaveAndLoadGame_Works() {
		// arrange
		new File("savedH.txt").delete();
		new File("savedD.txt").delete();
		Dungeon dungeonOG = new Dungeon(10, 10);
		DungeonAdventure.saveGame(dungeonOG, null);

		// act
		Hero heroLD = DungeonAdventure.loadHero();
		Dungeon dungeonLD = DungeonAdventure.loadDungeon();
		// cleanup
		new File("savedH.txt").delete();
		new File("savedD.txt").delete();

		// assert
		assertEquals(dungeonOG.toString(), dungeonLD.toString());
	}

	@Test
	public void dungeon_Contains_Works() {
		// arrange
		int[][] coords = { { 2, 2 }, { 1, 4 } };
		// act
		// assert
		assertTrue(dungeon.contains(coords, 1, 4));
		assertFalse(dungeon.contains(coords, 1, 1));
	}

	// Helper for neverTwoPillarsInARoom
	private static boolean hasOnePillar(Room room) {
		LinkedList<String> inRoomTemp = room.getInRoom();
		// remove irrelevant stuff
		inRoomTemp.remove("heal");
		inRoomTemp.remove("pit");
		inRoomTemp.remove("monster");
		inRoomTemp.remove("enter");
		inRoomTemp.remove("exit");
		return inRoomTemp.size() <= 1;
	}
}
