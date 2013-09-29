import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.Stack;

/**
 * @author Qudsia
 * 
 */
public class World {

	int cellWidth = 200; // 20 centimeters = 200 millimeters
	static final int ROWS = 7;
	static final int COLS = 10;
	static int[][] grid;
	Cell currentCell;
	Cell goal;
	static Stack<Cell> st = new Stack<Cell>();// stack used in dfs
	private static Queue<Cell> path = new Queue<Cell>(); // the path found by dfs

	public World() {

		grid = new int[ROWS][COLS];
		populate();

	}

	// populates with -1 or 99
	public void populate() {
		// assign -1 to edges and 99 to inside cells
		for (int i = 0; i < ROWS; i++) {
			for (int j = 0; j < COLS; j++) {
				if (i == 0 || j == 0 || i == 6 || j == 9) {
					grid[i][j] = -1;
				} else {
					grid[i][j] = 99;
				}
			}
		}

	}

	// populate distance from goal using breadth first search
	public void bfs() {

		// Cell cell = currentCell;
		Queue<Cell> qe = new Queue<Cell>();
		goal.value = 0;
		qe.push(goal);

		while (!qe.isEmpty()) {
			Cell cell = (Cell) qe.pop();

			// if it is not a border or obstacle
			if (grid[cell.row][cell.col] == 99) {
				// populate the grid with the distance from goal
				grid[cell.row][cell.col] = cell.value;
			}
			// if top cell hasn't been populated
			if (cell.row - 1 > 0 && grid[cell.row - 1][cell.col] == 99) {
				// make a cell with distance value and add to queue
				qe.push(new Cell(cell.col, cell.row - 1, cell.value + 1));
			}
			// if bottom cell hasn't been populated
			if (cell.row + 1 < ROWS && grid[cell.row + 1][cell.col] == 99) {
				// make a cell with distance value and add to queue
				qe.push(new Cell(cell.col, cell.row + 1, cell.value + 1));
			}
			if (cell.col + 1 < COLS && grid[cell.row][cell.col + 1] == 99) {
				// make a cell with distance value and add to queue
				qe.push(new Cell(cell.col + 1, cell.row, cell.value + 1));
			}
			if (cell.col - 1 > 0 && grid[cell.row][cell.col - 1] == 99) {
				// make a cell with distance value and add to queue
				qe.push(new Cell(cell.col - 1, cell.row, cell.value + 1));
			}

		}
	}

	// prints the grid (debug code)
	public void printGrid() {
		// testing the grid
		String output = "";
		for (int row = 0; row < ROWS; row++) {
			for (int col = 0; col < COLS; col++) {
				output += "\t" + grid[row][col];
			}
			output += "\n";
		}
		System.out.println(output);
	}

	public double cellDistance(int toMove) {
		return (toMove * cellWidth);
	}

	// sets the goal
	public void setEnd(int x, int y) {
		grid[y][x] = 0;
		goal = new Cell(x, y);
		bfs();
	}

	// builds obstacles
	public void buildObstacle(int x1, int y1, int x2, int y2) {

		for (int i = y2; i <= y1; i++) {
			for (int j = x2; j <= x1; j++) {
				grid[i][j] = -1;
			}
		}
	}

	// sets the starting point the robot is on
	public void setStart(int x, int y) {
		currentCell = new Cell(x, y, grid[y][x]);
		st.clear();
		st.push(currentCell);
		dfs();
	}

	// depth first search that finds the path
	public static void dfs() {

		// pop the stack;
		Cell cell = st.pop();

		// go to the popped cell
		// mark the popped cell as gray
		cell.color = "grey";

		// if (item popped == final cell)
		if (cell.value == 0) {
			// done!
			return;
		}

		// else {
		else {

			Cell top, bottom, left, right;

			List<Cell> neighbors = new ArrayList<Cell>();

			if (cell.row - 1 > 0 && grid[cell.row - 1][cell.col] != -1) {
				top = new Cell(cell.col, cell.row - 1,
						grid[cell.row - 1][cell.col]);

				neighbors.add(top);
			}
			if (cell.row + 1 < ROWS && grid[cell.row + 1][cell.col] != -1) {
				bottom = new Cell(cell.col, cell.row + 1,
						grid[cell.row + 1][cell.col]);
				neighbors.add(bottom);
			}
			if (cell.col + 1 < COLS && grid[cell.row][cell.col + 1] != -1) {
				right = new Cell(cell.col + 1, cell.row,
						grid[cell.row][cell.col + 1]);
				neighbors.add(right);
			}
			if (cell.col - 1 > 0 && grid[cell.row][cell.col - 1] != -1) {
				left = new Cell(cell.col - 1, cell.row,
						grid[cell.row][cell.col - 1]);
				neighbors.add(left);
			}

			if (neighbors != null) {

				Cell c = neighbors.get(0);
				for (int i = 0; i < neighbors.size(); i++) {
					if (c.value > neighbors.get(i).value) {
						c = neighbors.get(i);
					}
				}

				getPath().push(c);

				st.push(c);

				// visit the child cell
				dfs();

			}

			// mark the popped cell as black
			cell.color = "black";

		}
	}

	public static Queue<Cell> getPath() {
		return path;
	}

	public static void setPath(Queue<Cell> path) {
		World.path = path;
	}
}
