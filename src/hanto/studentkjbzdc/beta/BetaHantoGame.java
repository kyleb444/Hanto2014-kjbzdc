/**
 * BetaHantoGame.java
 * @author Kyle Burns kjburns
 * @author Zac Chupka zdchupka
 */

package hanto.studentkjbzdc.beta;

import java.util.HashMap;
import java.util.Map;

import hanto.common.*;
import hanto.studentkjbzdc.common.ButterflyPiece;
import hanto.studentkjbzdc.common.Coordinate;
import static hanto.common.MoveResult.*;
import static hanto.common.HantoPlayerColor.*;
import static hanto.common.HantoPieceType.*;
import hanto.studentkjbzdc.common.*;

/**
 * Class that supports the logic for a Beta version of Hanto.
 */

public class BetaHantoGame implements HantoGame{
	
	private HantoPlayerColor movesFirst;
	private HantoPlayerColor movesSecond;
	private boolean isFirstButterflyPlaced = false;
	private boolean isSecondButterflyPlaced = false;
	private int moveCount = 0;	
	private Map<HantoCoordinate, HantoPiece> locations = new HashMap<HantoCoordinate, HantoPiece>();
	
	/**
	 * 
	 * Constructor for BetaHantoGame.
	 *
	 * @param movesFirst Player that moves first.
	 */
	public BetaHantoGame(HantoPlayerColor movesFirst) {
		
		this.movesFirst = movesFirst;
		
		switch(movesFirst){
			case RED:
				movesSecond = HantoPlayerColor.BLUE;	
				break;
			case BLUE:
				movesSecond = HantoPlayerColor.RED;
				break;
		}
	}

	/**
	 * Executes a move in a Beta Hanto game.
	 * @param pieceType Specifies the type of piece being moved.
	 * @param from The coordinate of the piece before moving.
	 * @param to The intended coordinate of the piece after moving.
	 * @return The result of the move.
	 * @throws HantoException If the move is invalid.
	 */
	public MoveResult makeMove(HantoPieceType pieceType, HantoCoordinate from,	HantoCoordinate to) throws HantoException {
		
		moveCount++;
		MoveResult result = OK;
		Coordinate toCo = new Coordinate(to.getX(), to.getY());  // Convert any implementation of HantoCoordinate to Coordinate for hashing purposes
		
		if(isFirstMove()){
			if ( from != null || toCo.getX() != 0 || toCo.getY() != 0) {
				throw new HantoException(pieceType.getPrintableName() + " must move to coordinate (0, 0) on first turn.");
			}				
			else {
				locations.put(toCo, determinePiece(pieceType)); // Add the piece to the hashmap
				result = OK;
			}				
		}		
		//if the game is not on the first move and it is not from a null coordinate or if the piece being placed is not on an adjacent spot
		if(!isFirstMove() && (from != null || !isValid(toCo))) {
			throw new HantoException(pieceType.getPrintableName() + " must move to adjacent spot.");
		}
		//otherwise put the piece in the hash map
		else {			
			locations.put(toCo, determinePiece(pieceType)); // Add the piece to the hashmap
			isButterflyPlaced();
			result = OK;
		}	
		
		result = checkForEndGameResult(result);
		
		return result;
	}
	
	/**
	 * 
	 * checks to see if it is the first move.
	 *
	 * @return true if it is the first move
	 */
	private boolean isFirstMove(){
		return moveCount == 1;
	}
	
	/**
	 * 
	 * Determines the piece being placed.
	 *
	 * @param pieceType the type of piece
	 * @return the piece being placed
	 */
	private HantoPiece determinePiece(HantoPieceType pieceType){
		HantoPiece pieceBeingPlaced = null;
		
		//If the butterfly is placed, determine the color and set the butterfly placed flag to true for that color
		switch(pieceType){
		case BUTTERFLY:
			pieceBeingPlaced = new ButterflyPiece(determineColor());
			checkButterflyFlags();
			break;
		case SPARROW:
			pieceBeingPlaced = new SparrowPiece(determineColor());
			break;
		}
		
		return pieceBeingPlaced;			
	}
	
	/**
	 * 
	 * Determines the color making the move.
	 *
	 * @return the player color
	 */
	private HantoPlayerColor determineColor(){
		if (moveCount % 2 == 1) {
			return movesFirst;
		}
		else {
			return movesSecond;
		}
	}
	
	/**
	 * Checks whether a Blue Butterfly is surrounded.
	 */
	private boolean isBlueButterflySurrounded() {
		HantoCoordinate butterflyCo = null;		
		for(Map.Entry<HantoCoordinate, HantoPiece> entry : locations.entrySet()) {
			if (entry.getValue().getType() == BUTTERFLY && entry.getValue().getColor() == BLUE) {
				butterflyCo = entry.getKey();
				return checkSides(butterflyCo);
			}			
		}
		return false;
	}
	
	/**
	 * Checks whether a Red Butterfly is surrounded.
	 */
	private boolean isRedButterflySurrounded() {
		HantoCoordinate butterflyCo = null;		
		for(Map.Entry<HantoCoordinate, HantoPiece> entry : locations.entrySet()) {
			if (entry.getValue().getType() == BUTTERFLY && entry.getValue().getColor() == RED) {
				butterflyCo = entry.getKey();
				return checkSides(butterflyCo);
			}
		}
		return false;
	}
	
	/**
	 * 
	 * Checks the adjacent sides of a space to see if a butterfly is surrounded.
	 *
	 * @param hc The HantoCoordinate to check.
	 * @return Whether the butterfly is surrounded.
	 */
	private boolean checkSides(HantoCoordinate hc) {
		boolean side1 = false, side2 = false, side3 = false, side4 = false, side5 = false, side6 = false;
		for(Map.Entry<HantoCoordinate, HantoPiece> entry : locations.entrySet()) {
			if (entry.getKey().getX() == hc.getX() && entry.getKey().getY() == hc.getY() + 1) {
				side1 = true;
			}
			if (entry.getKey().getX() == hc.getX() && entry.getKey().getY() == hc.getY() - 1) {
				side2 = true;
			}
			if (entry.getKey().getX() == hc.getX() + 1 && entry.getKey().getY() == hc.getY()) {
				side3 = true;		
			}
			if (entry.getKey().getX() == hc.getX() - 1 && entry.getKey().getY() == hc.getY()) {
				side4 = true;
			}
			if (entry.getKey().getX() == hc.getX() + 1 && entry.getKey().getY() == hc.getY() - 1) {
				side5 = true;
			}
			if (entry.getKey().getX() == hc.getX() - 1 && entry.getKey().getY() == hc.getY() + 1) {
				side6 = true;
			}			
		}
			return side1 && side2 && side3 && side4 && side5 && side6;
	}
	
	
	
	/**
	 * 
	 * Checks to see if the butterflies have been placed by the fourth turn.
	 *
	 * @throws HantoException throws exception if the butterfly has not been placed by the fourth turn
	 */
	private void isButterflyPlaced() throws HantoException
	{
		if(moveCount == 7 && !isFirstButterflyPlaced) {
			throw new HantoException("Butterfly must be placed by fourth turn.");
		}
		if(moveCount == 8 && !isSecondButterflyPlaced) {
			throw new HantoException("Butterfly must be placed by fourth turn.");
		}
	}
	
	/**
	 * 
	 * Sets the butterflyPlaced flags when the players uses their butterfly.
	 *
	 */
	private void checkButterflyFlags(){
		if (determineColor() == movesFirst) {
			isFirstButterflyPlaced = true;
		}
		else {
			isSecondButterflyPlaced = true;
		}
	}
	
	/**
	 * 
	 * Looks for a winner or draw.
	 *
	 * @param result the game result
	 * @return the game winner/draw or OK if no one has won yet
	 */
	private MoveResult checkForEndGameResult(MoveResult result){
		if (isBlueButterflySurrounded() && isRedButterflySurrounded()) {
			result = DRAW;
		}
		else if (isBlueButterflySurrounded()) {
			result = RED_WINS;
		}
		else if (isRedButterflySurrounded()) {
			result = BLUE_WINS;
		}
		else if (moveCount == 12 && !isBlueButterflySurrounded() && !isRedButterflySurrounded()) {
			result = DRAW;
		}
		return result;
	}
	
	/**
	 * Determines whether a coordinate is a valid location for a piece to be placed.
	 * @param dest The destination coordinate.
	 * @return Whether the destination coordinate is adjacent to an existing piece.
	 */
	public boolean isValid(HantoCoordinate dest) {
		boolean valid = false;
		boolean spaceTaken = false;
		int x = dest.getX();
		int y = dest.getY();
		Coordinate destCo = new Coordinate(dest.getX(), dest.getY()); // Convert any implementation of HantoCoordinate to Coordinate for hashing purposes
		
		if (locations.containsKey(destCo)) { // Check whether a piece is already on the destination coordinate
			spaceTaken = true;
		}
		
		for(Map.Entry<HantoCoordinate, HantoPiece> entry : locations.entrySet()) {
			if (spaceTaken) {
				break;  // No need to check the map if the destination coordinate is already taken
			}
			if ((entry.getKey().getX() == x && entry.getKey().getY() == y + 1) ||
				(entry.getKey().getX() == x && entry.getKey().getY() == y - 1) ||
				(entry.getKey().getX() == x + 1 && entry.getKey().getY() == y) || // Check the coordinates of the six adjacent hexagonal spaces
				(entry.getKey().getX() == x - 1 && entry.getKey().getY() == y) ||
				(entry.getKey().getX() == x + 1 && entry.getKey().getY() == y - 1) ||
				(entry.getKey().getX() == x - 1 && entry.getKey().getY() == y + 1)) {
					valid = true;
					break;
				}
		}
		return valid;
	}
	
	/**
	 * @param where The coordinate to check.
	 * @return The piece at the specified coordinate.
	 */
	public HantoPiece getPieceAt(HantoCoordinate where) {
		Coordinate whereCo = new Coordinate(where.getX(), where.getY()); // Convert any implementation of HantoCoordinate to Coordinate for hashing purposes
		HantoPiece piece = locations.get(whereCo);
		return piece;
	}

	/**
	 * @return A string representation of the board.
	 */
	public String getPrintableBoard() {
		String s = "";
		for(Map.Entry<HantoCoordinate, HantoPiece> entry : locations.entrySet()) {
			s = s + entry.getValue().getColor() + " " + entry.getValue().getType().getPrintableName() + " (" + entry.getKey().getX() + ", " + entry.getKey().getY() + ")\n";
		}
		return s;
	}

}
