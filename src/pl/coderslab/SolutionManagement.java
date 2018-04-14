package pl.coderslab;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Scanner;

import pl.coderslab.models.Solution;
import pl.coderslab.models.User;

public class SolutionManagement {
	
	public static void main(String[] args) {
		
		try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/workshop_2?useSSL=false", "root", "coderslab")) {
			Scanner scan = new Scanner(System.in);
			
			showSolution(conn);
			
			System.out.println("Type: add , edit, delete or quit");
			String answer = ""; 
			boolean shouldContinue = true;
			while (shouldContinue) {
				
				answer = scan.nextLine();
			
				switch (answer) {
				case "add" : 
					System.out.println("Type description of new solution");
					String description = scan.nextLine();
					System.out.println("Type exercise id connected to this solution");
					int exercise_id = Integer.parseInt(scan.nextLine());
					System.out.println("Type user id connected to this solution");
					int user_id = Integer.parseInt(scan.nextLine());
					Solution solution = new Solution(description, exercise_id, user_id);
					solution.saveToDB(conn);
					showSolution(conn);
					System.out.println("New solution was added to the database,  what would you like to do next?");
					break;
				case "edit" :
					System.out.println("Type id of chosen solution");
					String id = scan.nextLine();
						Solution toUpdate = Solution.loadSolutionById(conn, Integer.parseInt(id));
						System.out.println("Type new description");
						toUpdate.setDescription(scan.nextLine());
						System.out.println("Type new exercise_id");
						toUpdate.setExercise_id(Integer.parseInt(scan.nextLine()));
						System.out.println("Type new user_id");
						toUpdate.setUser_id(Integer.parseInt(scan.nextLine()));
						toUpdate.saveToDB(conn);
						showSolution(conn);
						System.out.println("Chosen solution was edited,  what would you like to do next?");
					break;
				case "delete" :
					System.out.println("Type id of chosen solution");
					Solution toDelete = Solution.loadSolutionById(conn, scan.nextInt());
					toDelete.delete(conn);
					showSolution(conn);
					System.out.println("Chosen solution was deleted, what would you like to do next?");
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

	private static void showSolution(Connection conn) throws SQLException {
		for ( Solution solution : Solution.loadAllSolutions(conn)) {
			System.out.println(solution.getId() + " created: " + solution.getCreated() + " description: " + solution.getDescription() + " exercise_id: " + solution.getExercise_id() + " user_id: " + solution.getUser_id());
		}
	}
	
	

}
