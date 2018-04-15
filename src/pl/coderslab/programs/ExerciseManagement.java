package pl.coderslab;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;
import pl.coderslab.models.Exercise;
import pl.coderslab.models.Solution;
import pl.coderslab.models.User;

public class ExerciseManagement {
	
	public static void main(String[] args) {
		
		try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/workshop_2?useSSL=false", "root", "coderslab")) {
			Scanner scan = new Scanner(System.in);
			
			System.out.println("Choose: add , view or quit");
			String answer = ""; 
			boolean shouldContinue = true;
			
			while (shouldContinue) {
				
				answer = scan.nextLine();
			
				switch (answer) {
				case "add" : 
					showUsers(conn);
					System.out.println("Type id of chosen User");
					int idUser = scan.nextInt();
					showExercises(conn);
					System.out.println("Type id of chosen Exercise");
					int idExercise = scan.nextInt();
					Solution newSolution = new Solution (idUser, idExercise);
					newSolution.saveToDB(conn);
					System.out.println("New solution was added to the base");
					System.out.println("What would you like to do next? Type add, view or quit");
					answer = scan.nextLine();
					break;
				case "view" :
					showUsers(conn);
					System.out.println("Type id of chosen User to view Solutions");
					int id = scan.nextInt();
					showSolution(conn, id);
					System.out.println("What would you like to do next? Type add, view or quit");
					answer = scan.nextLine();
					break;
				case "quit":
					shouldContinue = false;
					break;
				default :
					System.out.println("Incorrect values, try again");
				}
			}
			System.out.println("Bye!");
			conn.close();
			scan.close();
			
			
		} catch ( Exception e) {
			System.out.println(e.getLocalizedMessage());
		}
		
	}

	private static void showUsers(Connection conn) throws SQLException {
		for ( User user : User.loadAllUsers(conn)) {
			System.out.println("User id: " + user.getId() + " Username: " + user.getUsername());
		}
	}
	
	private static void showExercises(Connection conn) throws SQLException {
		for ( Exercise exercise : Exercise.loadAllExcercises(conn)) {
			System.out.println("Exercise id: " + exercise.getId() + " Exercise title: " + exercise.getTitle());
		}
	}
	
	private static void showSolution(Connection conn, int id) throws SQLException {
		for ( Solution solution : Solution.loadAllSolutionsByUserId(conn, id)) {
			System.out.println(solution.getId() + " Created: " + solution.getCreated() + " Description: " + solution.getDescription() + " Exercise_id: " + solution.getExercise_id());
		}
	}
	
	

}
