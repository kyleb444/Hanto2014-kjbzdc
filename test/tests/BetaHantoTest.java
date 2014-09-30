/**
 * BetaHantoTest.java
 * @author Kyle Burns kjburns
 * @author Zac Chupka zdchupka
 */
package tests;

import static hanto.common.HantoPieceType.*;
import static hanto.common.HantoPlayerColor.RED;
import static hanto.common.MoveResult.OK;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import hanto.common.HantoException;
import hanto.common.HantoGame;
import hanto.common.HantoGameID;
import hanto.common.HantoPiece;
import hanto.common.HantoPlayerColor;
import hanto.common.MoveResult;
import hanto.studentkjbzdc.HantoGameFactory;
import hanto.studentkjbzdc.alpha.AlphaHantoGame;
import hanto.studentkjbzdc.beta.BetaHantoGame;
import hanto.studentkjbzdc.common.Coordinate;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Class for testing functionality of AlphaHantoGame.
 */

public class BetaHantoTest {
	
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
	game = factory.makeHantoGame(HantoGameID.BETA_HANTO);
	}
	
	
	@Test
	public void MakeBetaHantoGame() {
		assertTrue(game instanceof BetaHantoGame);
	}
	
	@Test
	public void MakeBetaHantoGameRedFirst() {
		game = factory.makeHantoGame(HantoGameID.BETA_HANTO, HantoPlayerColor.RED);
		assertTrue(game instanceof BetaHantoGame);
	}
	
	
	@Test
	public void BlueButterflyMovesTo0_0() throws HantoException {
		MoveResult result = game.makeMove(BUTTERFLY, null, new Coordinate(0, 0));
		assertEquals(result, OK);
		HantoPiece piece = game.getPieceAt(new Coordinate(0, 0));
		assertEquals(piece.getColor(), HantoPlayerColor.BLUE);
		assertEquals(piece.getType(), BUTTERFLY);
		assertEquals(game.getPrintableBoard(), "BLUE Butterfly (0, 0)\n");
	}	
	
	@Test
	public void BlueSparrowMovesTo0_0() throws HantoException {
		MoveResult result = game.makeMove(SPARROW, null, new Coordinate(0, 0));
		assertEquals(result, OK);
		assertEquals(game.getPrintableBoard(), "BLUE Sparrow (0, 0)\n");
	}
	
	@Test
	public void CheckSecondMoveColor() throws HantoException {
		MoveResult result = game.makeMove(SPARROW, null, new Coordinate(0, 0));
		assertEquals(result, OK);
		game.makeMove(BUTTERFLY, null, new Coordinate(0, 1));
		assertEquals(game.getPrintableBoard(), "BLUE Sparrow (0, 0)\nRED Butterfly (0, 1)\n");
	}
	
	@Test (expected=HantoException.class)
	public void CheckSecondMoveValidity() throws HantoException {
		MoveResult result = game.makeMove(SPARROW, null, new Coordinate(0, 0));
		assertEquals(result, OK);
		game.makeMove(BUTTERFLY, null, new Coordinate(0, 2));
	}
	
	@Test (expected=HantoException.class)
	public void FirstMoveBlueButterflyMovesTo0_1() throws HantoException {
		MoveResult result = game.makeMove(BUTTERFLY, null, new Coordinate(0, 1));
	}
	
	@Test (expected=HantoException.class)
	public void FirstButterflyNotPlacedByTurn4() throws HantoException {
		game.makeMove(SPARROW, null, new Coordinate(0, 0));
		game.makeMove(SPARROW, null, new Coordinate(0, 1));
		game.makeMove(SPARROW, null, new Coordinate(0, 2));
		game.makeMove(SPARROW, null, new Coordinate(0, 3));	
		game.makeMove(SPARROW, null, new Coordinate(0, 4));	
		game.makeMove(SPARROW, null, new Coordinate(0, 5));	
		game.makeMove(SPARROW, null, new Coordinate(0, 6));	
	}
	
	@Test (expected=HantoException.class)
	public void SecondButterflyNotPlacedByTurn4() throws HantoException {
		game.makeMove(SPARROW, null, new Coordinate(0, 0));
		game.makeMove(SPARROW, null, new Coordinate(0, 1));
		game.makeMove(SPARROW, null, new Coordinate(0, 2));
		game.makeMove(SPARROW, null, new Coordinate(0, 3));
		game.makeMove(SPARROW, null, new Coordinate(0, 4));	
		game.makeMove(SPARROW, null, new Coordinate(0, 5));	
		game.makeMove(BUTTERFLY, null, new Coordinate(0, 6));				
		game.makeMove(SPARROW, null, new Coordinate(0, 7));		
	}
	
	@Test (expected=HantoException.class)
	public void MoveToOccupiedSpace() throws HantoException {
		game.makeMove(SPARROW, null, new Coordinate(0, 0));
		game.makeMove(SPARROW, null, new Coordinate(0, 0));
	}
	
	@Test
	public void TestValidAdjacentCoordinates() throws HantoException{
		game.makeMove(BUTTERFLY, null, new Coordinate(0, 0));
		assertTrue(((BetaHantoGame) game).isValid(new Coordinate(1, 0)));
		assertTrue(((BetaHantoGame) game).isValid(new Coordinate(-1, 0)));
		assertTrue(((BetaHantoGame) game).isValid(new Coordinate(0, -1)));
		assertTrue(((BetaHantoGame) game).isValid(new Coordinate(1, -1)));
		assertTrue(((BetaHantoGame) game).isValid(new Coordinate(-1, 1)));
	}	
	
	@Test
	public void BlueButterflySurroundedRedWins() throws HantoException {
		game.makeMove(BUTTERFLY, null, new Coordinate(0, 0));
		game.makeMove(SPARROW, null, new Coordinate(0, 1));
		game.makeMove(SPARROW, null, new Coordinate(1, 0));
		game.makeMove(SPARROW, null, new Coordinate(1, -1));
		game.makeMove(SPARROW, null, new Coordinate(-1, 0));	
		game.makeMove(SPARROW, null, new Coordinate(0, -1));	
		MoveResult result = game.makeMove(BUTTERFLY, null, new Coordinate(-1, 1));				
		assertEquals(result, MoveResult.RED_WINS);	
	}
	
	@Test
	public void RedButterflySurroundedBlueWins() throws HantoException {
		game.makeMove(BUTTERFLY, null, new Coordinate(0, 0));
		game.makeMove(BUTTERFLY, null, new Coordinate(0, 1));
		game.makeMove(SPARROW, null, new Coordinate(0, 2));
		game.makeMove(SPARROW, null, new Coordinate(1, 1));
		game.makeMove(SPARROW, null, new Coordinate(1, 0));	
		game.makeMove(SPARROW, null, new Coordinate(-1, 2));	
		game.makeMove(SPARROW, null, new Coordinate(-1, 1));	
		MoveResult result = game.makeMove(SPARROW, null, new Coordinate(2, 0));				
		assertEquals(result, MoveResult.BLUE_WINS);	
	}
	
	@Test
	public void TieGameBothButterfliesSurrounded() throws HantoException {
		game.makeMove(BUTTERFLY, null, new Coordinate(0, 0));
		game.makeMove(BUTTERFLY, null, new Coordinate(0, 1));
		game.makeMove(SPARROW, null, new Coordinate(0, 2));
		game.makeMove(SPARROW, null, new Coordinate(1, 1));
		game.makeMove(SPARROW, null, new Coordinate(1, 0));	
		game.makeMove(SPARROW, null, new Coordinate(-1, 2));	
		game.makeMove(SPARROW, null, new Coordinate(1, -1));	
		game.makeMove(SPARROW, null, new Coordinate(0,-1));	
		game.makeMove(SPARROW, null, new Coordinate(-1, 0));	
		MoveResult result = game.makeMove(SPARROW, null, new Coordinate(-1, 1));
		assertEquals(result, MoveResult.DRAW);	
	}
	
	@Test
	public void DrawAfter12Moves() throws HantoException {
		game.makeMove(BUTTERFLY, null, new Coordinate(0, 0));
		game.makeMove(BUTTERFLY, null, new Coordinate(0, 1));
		game.makeMove(SPARROW, null, new Coordinate(0, 2));
		game.makeMove(SPARROW, null, new Coordinate(0, 3));
		game.makeMove(SPARROW, null, new Coordinate(0, 4));	
		game.makeMove(SPARROW, null, new Coordinate(0, 5));	
		game.makeMove(SPARROW, null, new Coordinate(0, 6));	
		game.makeMove(SPARROW, null, new Coordinate(0,7));	
		game.makeMove(SPARROW, null, new Coordinate(0, 8));	
		game.makeMove(SPARROW, null, new Coordinate(0, 9));	
		game.makeMove(SPARROW, null, new Coordinate(0, 10));	
		MoveResult result =game.makeMove(SPARROW, null, new Coordinate(0, 11));	

		assertEquals(result, MoveResult.DRAW);	
	}

}
