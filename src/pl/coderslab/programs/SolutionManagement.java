package pl.coderslab.programs;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;

import pl.coderslab.models.Exercise;
import pl.coderslab.models.Solution;
import pl.coderslab.models.User;

public class SolutionManagement {
	
	public static void main(String[] args) {
		
		try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/workshop_2?useSSL=false", "root", "coderslab")) {
			Scanner scan = new Scanner(System.in);
			String answer = ""; 
			boolean shouldContinue = true;
			
			while (shouldContinue) {
				
				System.out.println("Choose: add , view or quit");
				answer = scan.nextLine();
			
				switch (answer.toLowerCase()) {
				case "add" :
					System.out.println("To add a Solution please choose User id and Exercise id:");
					showUsers(conn);
					System.out.println("Type id of chosen User");
					int idUser;
					checkIfInt(scan);
					idUser = Integer.parseInt(scan.nextLine());
					idUser = UserManagement.checkUserInDatabase(conn, scan, idUser);
					showExercises(conn);
					System.out.println("Type id of chosen Exercise");
					int idExercise;
					checkIfInt(scan);
					idExercise = Integer.parseInt(scan.nextLine());
					idExercise = checkExerciseInDatabase(conn, scan, idExercise);
					Solution newSolution = new Solution (idUser, idExercise);
					newSolution.saveToDB(conn);
					System.out.println("New solution was added to the base");
					System.out.println("What would you like to do next?");
					break;
				case "view" :
					showUsers(conn);
					System.out.println("Type id of chosen User to view their Solutions");
					int id;
					checkIfInt(scan);
					id = Integer.parseInt(scan.nextLine());
					id = UserManagement.checkUserInDatabase(conn, scan, id);
					showSolutionsForUser(conn, id);
					System.out.println("What would you like to do next?");
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
	
	public static void showExercises(Connection conn) throws SQLException {
		for ( Exercise exercise : Exercise.loadAllExcercises(conn)) {
			System.out.println("Exercise id: " + exercise.getId() + " Exercise title: " + exercise.getTitle());
		}
	}
	
	public static int checkExerciseInDatabase(Connection conn, Scanner scan, int id) throws SQLException {
		while (Exercise.loadExcerciseById(conn, id) == null ) {
			showExercises(conn);
			System.out.println("No such Exercise in the databse");
			System.out.println("Please type again the id of chosen Exercise");
			id = Integer.parseInt(scan.nextLine());
		}
		return id;
	}

	private static void showSolutionsForUser(Connection conn, int id) throws SQLException {
		if (Solution.loadAllSolutionsByUserId(conn, id).length == 0) {
			System.out.println("No solutions for this User");
		} else {
			for ( Solution solution : Solution.loadAllSolutionsByUserId(conn, id)) {
				System.out.println(solution.getId() + " Created: " + solution.getCreated() + " Description: " + solution.getDescription() + " Exercise_id: " + solution.getExercise_id());
			}
		}
	}
	
	private static void checkIfInt(Scanner scan) {
		while (!scan.hasNextInt()) {
			System.out.println("Incorrect value, please type a number");
			scan.nextLine();
		}
	}

}
