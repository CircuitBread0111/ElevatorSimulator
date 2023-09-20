///////////////////////////|
//|File: Elevator.java
//|Author: Jerrin C. Redmon
//|Language: Java
//|Version: 2.0
//|Date: September 20, 2023
///////////////////////////|

/* Description:
 * simulates an elevator along with building generation
 * uses a disk scheduling algorithm to perform its functions
 */
 
//----------------------------------------------------------------

// Imports //
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

/**Javadocs**
 * @author Jerrin C. Redmon
 * @version 2.0
 */
public class Elevator {

	private final int CAPACITY = 10; 		// elevators max capacity
	private int direction; 					// elevators current moving direction
	private int floor; 						// current floor the elevator is at
	private final int numFloors; 			// number of floors in the building
	private int[] cab = new int[CAPACITY];	// creates an array cab for passengers (-1 means its empty)
	private int numPassengers; 				// represents the number of passengers on the elevator
	public int served; 						// represents the number of passengers that got off the elevator
	private Random rand;					// Random number generator
	private Queue<Integer>[] buildingUp; 	// Queue for people going up
	private Queue<Integer>[] buildingDown; 	// Queue for people going down
	

	/**
	 * Constructor which creates a building with the desired number of floors
	 * @param numFloors the number of floors the building will have
	 */
	@SuppressWarnings("unchecked")
	public Elevator(int numFloors) {
		rand = new Random();
		direction = 1;
		floor = numFloors / 2;

		this.buildingUp = new Queue[numFloors];
		for (int i = 0; i < numFloors; i++) {
			buildingUp[i] = new LinkedList<Integer>();
		}

		this.buildingDown = new Queue[numFloors];
		for (int i = 0; i < numFloors; i++) {
			buildingDown[i] = new LinkedList<Integer>();
		}

		for (int i = 0; i < CAPACITY; i++) {
			cab[i] = -1;
		}

		this.numFloors = numFloors;
		served = 0;
	}
	

	/**
	 * Adds passengers to the elevator cab
	 * @param passenger the passenger to add on the cab
	 */
	public void addToCab(int passenger) {

		for (int i = 0; i < CAPACITY; i++) {

			if (cab[i] == -1) {
				cab[i] = passenger;
				numPassengers++;
				break;
			}
		}
	}


	/**
	 * removes passengers from the cab
	 */
	public void removeFromCab() {

		for (int i = 0; i < CAPACITY; i++) {

			if (cab[i] == floor) {
				cab[i] = -1;
				numPassengers--;
				served++;
			}
		}
	}
	

	/**
	 * Randomly generates occupants on any given floor
	 * Randomly generates occupants desired floor
	 */
	public void generateOccupant() {

		int currentFloor = rand.nextInt(numFloors);
		int requestedFloor = rand.nextInt(numFloors);

		while (currentFloor == requestedFloor) {
			requestedFloor = rand.nextInt(numFloors);
		}

		if (requestedFloor < currentFloor) {
			buildingDown[currentFloor].add(requestedFloor);
		}
		
		else {
			buildingUp[currentFloor].add(requestedFloor);
		}
	}
	

	/**
	 * prints the current passengers in the cab
	 */
	public void printCab() {

		boolean first = true;
		System.out.print("       ELEVATOR[");

		for (int i = 0; i < CAPACITY; i++) {

			if (cab[i] != -1) {

				if (first) {
					System.out.printf("%d", cab[i]);
					first = false;
				} 

				else {
					System.out.printf(", %d", cab[i]);
				}
			}
		}
		System.out.print("]");
	}
	

	/**
	 * displays the elevator and all its information
	 */
	public void display() {

		System.out.print("\033c"); // <-- linux terminal clear screen code for simulation
		System.out.println("|==========================================|");
		System.out.println("|||||||||||(ELEVATOR SIMULATOR)|||||||||||||");
		System.out.println("|==========================================|");
		System.out.println("||(FLOORS)||(UP)||(DOWN)||(ELEVATOR SHAFT)||");
		System.out.println("|==========================================|");

		for (int i = numFloors - 1; i >= 0; i--) {
			System.out.printf("|  Floor %d:  %s    %s", i, buildingUp[i], buildingDown[i]);

			if (i == floor) {
				printCab();
			}
			System.out.println();
		}

		System.out.println("|==========================================|");
		System.out.println(directionAsString());
		System.out.println("|==========================================|");
		System.out.printf("||||(Number of passengers: %d)|||||||||||||||\n", getSize());
		System.out.println("|==========================================|");
		System.out.printf("||||(Elevator's Location: Floor %2d)|||||||||\n", floor);
		System.out.println("|==========================================|");
		String occupant_str = String.format("||||(Occupants served: %d)", served);
		System.out.print(occupant_str);
		int numChars = 44 - occupant_str.length();

		for (int i = 0; i < numChars; i++) {
			System.out.print("|");
		}

		System.out.println();
		System.out.println("|==========================================|");
	}

	
	/**
	 * sets the direction as a string
	 * @return direction
	 */
	public String directionAsString() {

		return direction == 1 ? "||||(Elevator Direction : Moving up)||||||||" : "||||(Elevator Direction : Moving down)||||||";
	}
	

	/**
	 * moves the elevator both up or down
	 */
	public void move() {
		floor += direction;

		if (floor == 0 || floor == numFloors - 1) {
			direction = -direction;
		}
	}
	

	/**
	 * checks if the cab is empty
	 * @return True if empty, otherwise false
	 */
	public boolean isEmpty() {

		return numPassengers == 0;
	}
	

	/**
	 * checks if the cab is full
	 * @return True if full, otherwise false
	 */
	public boolean isFull() {

		return numPassengers == CAPACITY;
	}
	

	/**
	 * checks the current number of passengers on the cab
	 * @return the number of passengers
	 */
	public int getSize() {

		return numPassengers;
	}
	

	/**
	 * method witch fills the cab as the elevator checks each floor
	 */
	private void fillCab() {

		if (direction == 1) {

			while (numPassengers < CAPACITY && !buildingUp[floor].isEmpty()) {
				addToCab(buildingUp[floor].poll());
			}

		} 

		else if (direction == -1) {

			while (numPassengers < CAPACITY && !buildingDown[floor].isEmpty()) {
				addToCab(buildingDown[floor].poll());
			}
		}
	}
	

	/**
	 * updates the elevator
	 */
	public void update() {

		if (rand.nextDouble() < 0.5) {
			generateOccupant();
		}

		removeFromCab();
		fillCab();
		move();
	}
}
