/**
 * SparrowPiece.java
 * @author Kyle Burns kjburns
 * @author Zac Chupka zdchupka
 */

package hanto.studentkjbzdc.common;

import hanto.common.*;
import static hanto.common.HantoPieceType.*;

/**
 * Class representing a Sparrow Hanto piece.
 */

public class SparrowPiece implements HantoPiece {
	
	private HantoPlayerColor color;
	
	/**
	 * Constructor for a Sparrow piece. The type will always be Butterfly.
	 * @param color The color of the Sparrow piece.
	 */
	public SparrowPiece(HantoPlayerColor color) {
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
		return SPARROW;
	}

}
