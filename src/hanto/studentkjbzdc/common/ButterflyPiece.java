/**
 * ButterflyPiece.java
 * @author Kyle Burns kjburns
 * @author Zac Chupka zdchupka
 */

package hanto.studentkjbzdc.common;

import hanto.common.*;
import static hanto.common.HantoPieceType.*;

/**
 * Class representing a Butterfly Hanto piece.
 */

public class ButterflyPiece implements HantoPiece {
	
	private HantoPlayerColor color;
	
	/**
	 * Constructor for a Butterfly piece. The type will always be Butterfly.
	 * @param color The color of the Butterfly piece.
	 */
	public ButterflyPiece(HantoPlayerColor color) {
		this.color = color;
	}
	/**
	 * @return The color of the piece.
	 */
	public HantoPlayerColor getColor() {
		return color;
	}
	
	/**
	 * @return The type of the piece.
	 */
	public HantoPieceType getType() {
		return BUTTERFLY;
	}

}
