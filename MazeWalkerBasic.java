import lejos.nxt.Motor;
import lejos.nxt.NXTRegulatedMotor;

public class MazeWalkerBasic {
	
	static NXTRegulatedMotor leftMotor = Motor.C;
	static NXTRegulatedMotor rightMotor = Motor.A;
	static int speedDrive = 300;
	static int minDistance = 430;
	static int TURNING_DISTANCE = 185;

	public static void main(String[] args) {

		World legoworld = new World();
		legoworld.buildObstacle(5, 3, 5, 1);
		legoworld.buildObstacle(6, 5, 6, 5);
		legoworld.setEnd(8, 1);
		legoworld.setStart(1, 1); // (row, column)
		
		int row=legoworld.currentCell.row;
		int col=legoworld.currentCell.col;
		boolean rowtravel = false;
		
		while(!legoworld.getPath().empty())
		{
			Cell c = (Cell) legoworld.getPath().pop();
			
			if ((c.col==col &&  rowtravel) || (c.row==row &&  !rowtravel))
			{
				travel(minDistance);
			}
			else if (c.col>col && c.row==row && rowtravel){
				//turn right
				turn(false);
				travel(minDistance);
				rowtravel=false;
			}
			else if(c.col<col && c.row==row && rowtravel)
			{
				//turn left
				turn(true);
				travel(minDistance);
				rowtravel=false;
			}
			
			col = c.col;
			row = c.row;
		}
		//travel(minDistance);
		//turn(true);
		//turn(false);
	}

	public static void travel(int distance) {

		int numDegrees = distance;
		leftMotor.setSpeed(speedDrive); // set left motor
		leftMotor.resetTachoCount();
		rightMotor.setSpeed(speedDrive); // set right motor
		rightMotor.resetTachoCount();

		leftMotor.forward(); // engines on!
		rightMotor.forward();

		while ((leftMotor.getTachoCount() <= numDegrees) || // TachoCount =
															// motor angle in
															// degrees
				(rightMotor.getTachoCount() <= numDegrees)) {// check both
			if (leftMotor.getTachoCount() > numDegrees) {
				// if left is
				leftMotor.stop(); // done turn off
			}

			if (rightMotor.getTachoCount() > numDegrees) {
				// if right is
				rightMotor.stop(); // done turn off
			}

		}
		rightMotor.stop(); // to be sure
		leftMotor.stop(); // to be sure

	}

	// if the given parameter is true ...turn robot to the left.
	// if the given parameter is false...turn robot to the right.
	public static void turn(boolean left) {
		leftMotor.setSpeed(speedDrive); // set left motor
		leftMotor.resetTachoCount();
		rightMotor.setSpeed(speedDrive); // set right motor
		rightMotor.resetTachoCount();

		if (left) { // turn left
			leftMotor.backward(); // engines on!
			rightMotor.forward();

			while ((leftMotor.getTachoCount() >= -TURNING_DISTANCE) || // TachoCount
																		// =
																		// motor
																		// angle
																		// in
																		// degrees
					(rightMotor.getTachoCount() <= TURNING_DISTANCE)) // TachoCount
																		// =
																		// motor
																		// angle
																		// in
																		// degrees

			{// check both
				if (leftMotor.getTachoCount() < -TURNING_DISTANCE) {
					// if left is
					leftMotor.stop(); // done turn off
				}

				if (rightMotor.getTachoCount() > TURNING_DISTANCE) {
					// if right is
					rightMotor.stop(); // done turn off
				}

			}
			rightMotor.stop(); // to be sure
			leftMotor.stop(); // to be sure
		} else { // turn right
			leftMotor.forward(); // engines on!
			rightMotor.backward();

			while ((rightMotor.getTachoCount() >= -TURNING_DISTANCE) || // TachoCount
																		// =
																		// motor
																		// angle
																		// in
																		// degrees
					(leftMotor.getTachoCount() <= TURNING_DISTANCE)) // TachoCount
																		// =
																		// motor
																		// angle
																		// in
																		// degrees

			{// check both
				if (rightMotor.getTachoCount() < -TURNING_DISTANCE) {
					// if left is
					rightMotor.stop(); // done turn off
				}

				if (leftMotor.getTachoCount() > TURNING_DISTANCE) {
					// if right is
					leftMotor.stop(); // done turn off
				}

			}
			rightMotor.stop(); // to be sure
			leftMotor.stop(); // to be sure
		}
	}
}