/**
 * AlphaHantoGame.java
 * @author Kyle Burns kjburns
 * @author Zac Chupka zdchupka
 */

package hanto.studentkjbzdc.alpha;

import java.util.HashMap;
import java.util.Map;

import hanto.common.*;
import hanto.studentkjbzdc.common.ButterflyPiece;
import static hanto.common.MoveResult.*;
import static hanto.common.HantoPlayerColor.*;
import static hanto.common.HantoPieceType.*;
import hanto.studentkjbzdc.common.*;

/**
 * 
 * Class that supports the logic for an Alpha version of Hanto.
 *
 */
public class AlphaHantoGame implements HantoGame {
	
	private int moveCount = 0;
	private Map<HantoCoordinate, HantoPiece> locations = new HashMap<HantoCoordinate, HantoPiece>();
	
	/**
	 * Executes a move in an Alpha Hanto game.
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
		
		if (moveCount == 1) {
			if (pieceType != BUTTERFLY || from != null || toCo.getX() != 0 || toCo.getY() != 0) {
				throw new HantoException("Butterfly must move to coordinate (0, 0) on first turn.");
			}				
			else {
				locations.put(toCo, new ButterflyPiece(BLUE)); // Add the Blue Butterfly to the hashmap
				result = OK;
			}					
		}
		if (moveCount == 2) {
			if (pieceType != BUTTERFLY || from != null || !isValid(toCo)) {  // Make sure that Red Butterfly moves to a space adjacent to Blue Butterfly
				throw new HantoException("Red Butterfly must move to spot adjacent to (0, 0).");
			}
			else {
				locations.put(toCo, new ButterflyPiece(RED)); // Add the Red Butterfly to the hashmap
				result = DRAW;  // After red moves, the game ends in a draw.
			}				
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
