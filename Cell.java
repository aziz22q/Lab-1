/**
 * 
 */

/**
 * @author Qudsia
 * 
 */
public class Cell {

	int row;
	int col;
	int value;
	String color;

	public Cell(int x, int y) {
		row = y;
		col = x;
	}

	public Cell(int x, int y, int value) {
		row = y;
		col = x;
		this.value = value;
	}
}
