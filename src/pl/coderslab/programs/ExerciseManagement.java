package pl.coderslab.programs;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;
import pl.coderslab.models.Exercise;

public class ExerciseManagement {
	
	public static void main(String[] args) {
		
		try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/workshop_2?useSSL=false", "root", "coderslab")) {
			Scanner scan = new Scanner(System.in);
			
			String answer = ""; 
			boolean shouldContinue = true;
			
			while (shouldContinue) {
				
				showExercises(conn);
				System.out.println("Choose: add , edit, delete or quit");
				answer = scan.nextLine();
			
				switch (answer.toLowerCase()) {
				case "add" : 
					System.out.println("Type title of new Exercise");
					String title = scan.nextLine();
					System.out.println("Type description of new Exercise");
					String description = scan.nextLine();
					Exercise newExercise = new Exercise(title, description);
					newExercise.saveToDB(conn);
					showExercises(conn);
					System.out.println("New Exercise was added to the database, what would you like to do next?");
					answer = scan.nextLine();
					break;
				case "edit" :
					System.out.println("Type id of chosen Exercise");
					int exerciseId;
					checkIfInt(scan);
					exerciseId = Integer.parseInt(scan.nextLine());
					exerciseId = checkExerciseInDatabase(conn, scan, exerciseId);
					Exercise toUpdate = Exercise.loadExcerciseById(conn, exerciseId);
					System.out.println("Type new description of chosen Exercise");
					toUpdate.setDescription(scan.nextLine());
					toUpdate.setTitle("Type new title of chosen Exercise");
					toUpdate.saveToDB(conn);
					showExercises(conn);
					System.out.println("Chosen Exercise was edited,  what would you like to do next?");
					break;
				case "delete" :
					System.out.println("Type id of chosen Exercise");
					int deleteId;
					checkIfInt(scan);
					deleteId = Integer.parseInt(scan.nextLine());
					deleteId = checkExerciseInDatabase(conn, scan, deleteId);
					Exercise toDelete = Exercise.loadExcerciseById(conn, deleteId);
					toDelete.delete(conn);
					showExercises(conn);
					System.out.println("Chosen Exercise was deleted, what would you like to do next?");
					answer = scan.nextLine();
					break;
				case "quit" :
					shouldContinue = false;
					break;
				default :
					System.out.println("Incorrect option, try again");
					break;
				}
			}
			System.out.println("Bye!");
			conn.close();
			scan.close();
			
			
		} catch ( Exception e) {
			System.out.println(e.getLocalizedMessage());
		}
		
	}
	
	public static void showExercises(Connection conn) throws SQLException {
		for ( Exercise exercise : Exercise.loadAllExcercises(conn)) {
			System.out.println("Exercise id: " + exercise.getId() + " title: " + exercise.getTitle());
		}
	}
	
	private static void checkIfInt(Scanner scan) {
		while (!scan.hasNextInt()) {
			System.out.println("Incorrect value, please type a number");
			scan.nextLine();
		}
	}
	
	private static int checkExerciseInDatabase(Connection conn, Scanner scan, int id) throws SQLException {
		while (Exercise.loadExcerciseById(conn, id) == null ) {
			ExerciseManagement.showExercises(conn);
			System.out.println("No such Exercise in the databse");
			System.out.println("Please type again the id of chosen Exercise");
			id = Integer.parseInt(scan.nextLine());
		}
		return id;
	}
	

}
