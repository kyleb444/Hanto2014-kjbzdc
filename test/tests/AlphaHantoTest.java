package tests;
/**
 * AlphaHantoTest.java
 * @author Kyle Burns kjburns
 * @author Zac Chupka zdchupka
 */

import hanto.common.*;
import hanto.studentkjbzdc.HantoGameFactory;
import hanto.studentkjbzdc.alpha.AlphaHantoGame;
import hanto.*;
import static hanto.common.HantoPieceType.*;
import static hanto.common.MoveResult.*;
import static hanto.common.HantoPlayerColor.*;

import org.junit.*;

import hanto.studentkjbzdc.common.*;
import static org.junit.Assert.*;

/**
 * Class for testing functionality of AlphaHantoGame.
 */

public class AlphaHantoTest {
	
	private static HantoGameFactory factory;
	private HantoGame game;
	 
	@BeforeClass
	public static void initializeClass()
	{
	factory = HantoGameFactory.getInstance();
	}
	 
	@Before
	public void setup()
	{
	game = factory.makeHantoGame(HantoGameID.ALPHA_HANTO);
	}
	
	
	@Test
	public void MakeAlphaHantoGame() {
		assertTrue(game instanceof AlphaHantoGame);
	}
	
	@Test
	public void BlueButterflyMovesTo0_0() throws HantoException {
		MoveResult result = game.makeMove(BUTTERFLY, null, new Coordinate(0, 0));
		assertEquals(result, OK);
		assertEquals(game.getPrintableBoard(), "BLUE Butterfly (0, 0)\n");
	}
	
	@Test
	public void RedButterflyAdjacentToBlueButterflyEndsInDraw() throws HantoException {
		game.makeMove(BUTTERFLY, null, new Coordinate(0, 0));
		assertTrue(((AlphaHantoGame) game).isValid(new Coordinate(0, 1)));
		MoveResult redResult = game.makeMove(BUTTERFLY, null, new Coordinate(0, 1));
		assertEquals(redResult, DRAW);
		HantoPiece piece = game.getPieceAt(new Coordinate(0, 1));
		assertEquals(piece.getColor(), RED);
		assertEquals(piece.getType(), BUTTERFLY);
	}
	
	@Test (expected=HantoException.class)
	public void RedButterflyMovesToOccupiedSpace() throws HantoException {
		game.makeMove(BUTTERFLY, null, new Coordinate(0, 0));
		assertFalse(((AlphaHantoGame) game).isValid(new Coordinate(0, 0)));
		game.makeMove(BUTTERFLY, null, new Coordinate(0, 0));
	}
	
	@Test (expected=HantoException.class)
	public void RedButterflyMovesToNonAdjacentSpace() throws HantoException {
		game.makeMove(BUTTERFLY, null, new Coordinate(0, 0));
		assertFalse(((AlphaHantoGame) game).isValid(new Coordinate(0, 2)));
		assertFalse(((AlphaHantoGame) game).isValid(new Coordinate(2, 0)));
		assertFalse(((AlphaHantoGame) game).isValid(new Coordinate(1, 1)));
		assertFalse(((AlphaHantoGame) game).isValid(new Coordinate(-1, -1)));
		game.makeMove(BUTTERFLY, null, new Coordinate(1, 1));
	}
	
	@Test (expected=HantoException.class)
	public void BlueMakesInvalidFirstMove() throws HantoException {
		game.makeMove(BUTTERFLY, null, new Coordinate(0, 1));
	}
	
	@Test
	public void TestValidRedButterflyCoordinates() throws HantoException{
		game.makeMove(BUTTERFLY, null, new Coordinate(0, 0));
		assertTrue(((AlphaHantoGame) game).isValid(new Coordinate(1, 0)));
		assertTrue(((AlphaHantoGame) game).isValid(new Coordinate(-1, 0)));
		assertTrue(((AlphaHantoGame) game).isValid(new Coordinate(0, -1)));
		assertTrue(((AlphaHantoGame) game).isValid(new Coordinate(1, -1)));
		assertTrue(((AlphaHantoGame) game).isValid(new Coordinate(-1, 1)));
	}	
}
