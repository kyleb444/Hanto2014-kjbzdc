/**
 * Coordinate.java
 * @author Kyle Burns kjburns
 * @author Zac Chupka zdchupka
 */

package hanto.studentkjbzdc.common;

import hanto.common.HantoCoordinate;

/**
 * Class representing a coordinate on a Hanto board.
 */

public class Coordinate implements HantoCoordinate{
	
	int x;
	int y;
	
	/**
	 * 
	 * @param x The x value of the coordinate.
	 * @param y The y value of the coordinate.
	 */
	public Coordinate(int x, int y) {
		this.x = x;
		this.y = y;
	}

	/**
	 * @return The x value of the coordinate.
	 */
	public int getX() {
		return x;
	}

	/**
	 * @return The y value of the coordinate.
	 */
	public int getY() {
		return y;
	}
	
	/**
	 * @return Hashcode for a coordinate.
	 */
	@Override
	public int hashCode() {
		return (x*100) + (y*101);
	}
	
	/**
	 * @return Whether two coordinates are equal.
	 */
	@Override
	public boolean equals(Object obj) {
		boolean flag = false;
		Coordinate crd = (Coordinate) obj;
		if (crd.x == x && crd.y == y) {
			flag = true;
		}
		return flag;
	}
	
}
