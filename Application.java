//Imports
import java.util.Timer;
import java.util.TimerTask;
import java.util.Scanner;

/**
 * main file to run the elevator simulator
 * allows user input for requested number of floors
 * @author Jerrin C. Redmon
 * @version 1.0
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