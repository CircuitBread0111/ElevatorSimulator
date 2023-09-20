///////////////////////////|
//|File: Application.java
//|Author: Jerrin C. Redmon
//|Language: Java
//|Version: 2.0
//|Date: September 20, 2023
///////////////////////////|

/* Description:
 * main file to run the elevator simulator
 * allows user input for requested number of floors
 * tracks time elapsed and overall efficiency
 */
 
//----------------------------------------------------------------

// Imports //
import java.util.Timer;
import java.util.TimerTask;
import java.util.Scanner;

/**Javadocs**
 * @author Jerrin C. Redmon
 * @version 2.0
 */
public class Application {

	static int time = 0;
	public static void main(String[] args) {
		Scanner input = new Scanner(System.in);
		String title = "===============================================================\n"
				+ "|||||  ||      |||||  ||  || |||||| ||||||  ||||||  |||||   \n"
				+ "||     ||      ||     ||  || ||  ||   ||    ||  ||  ||  ||  \n"
				+ "|||||  ||      |||||  ||  || ||||||   ||    ||  ||  ||||||  \n"
				+ "||     ||      ||     ||  || ||  ||   ||    ||  ||  ||   || \n"
				+ "|||||  |||||   |||||    ||   ||  ||   ||    ||||||  ||   || \n"
				+ "===============================================================\n"
				+ "||||| |||| |||||||| ||  || ||     |||||| |||||| |||||| |||||\n"
				+ "||     ||  || || || ||  || ||     ||  ||   ||   ||  || ||  ||\n"
				+ "|||||  ||  || || || ||  || ||     ||||||   ||   ||  || ||||||\n"
				+ "   ||  ||  ||    || ||  || ||     ||  ||   ||   ||  || ||   ||\n"
				+ "||||| |||| ||    || |||||| |||||| ||  ||   ||   |||||| ||   ||\n"
				+ "===============================================================";
		System.out.println(title);
		System.out.print("	\nPlease Enter the Number of Floors to simulate:  ");
		int building = input.nextInt();
		Elevator elevator = new Elevator(building);
		Timer timer = new Timer();
		TimerTask task = new TimerTask() {

			/**
			 * Runs Simulator
			 */
			public void run() {

				elevator.update();
				elevator.display();
				time++;
				System.out.printf("|||||(Time Elapsed: %3d)||||||||||||||||||||\n", time);
				System.out.println("|==========================================|");
				System.out.printf("|||||(Elevator efficiency: %.2f)||||||||||||\n", (double) elevator.served/time);
				System.out.println("|==========================================|");
			}
		};

		input.close();
		timer.scheduleAtFixedRate(task, 0, 1000);
	}
}
