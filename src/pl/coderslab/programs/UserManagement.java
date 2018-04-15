package pl.coderslab.programs;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;
import pl.coderslab.models.User;

public class UserManagement {
	
	public static void main(String[] args) {
		
		try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/workshop_2?useSSL=false", "root", "coderslab")) {
			Scanner scan = new Scanner(System.in);
			
			
			String answer = ""; 
			boolean shouldContinue = true;
			
			while (shouldContinue) {
				
				showUsers(conn);
				System.out.println("Choose: add , edit, delete or quit");
				answer = scan.nextLine();
			
				switch (answer.toLowerCase()) {
				case "add" : 
					System.out.println("Type new Username");
					String username = scan.nextLine();
					System.out.println("Type new email");
					String email = scan.nextLine();
					System.out.println("Type new password");
					String password = scan.nextLine();
					System.out.println("Type group id of new User");					
					int group;
					checkIfInt(scan);
					group = scan.nextInt();
					User newUser = new User(username, email, password, group);
					newUser.saveToDB(conn);
					showUsers(conn);
					System.out.println("New user was added to the database,  what would you like to do next?");
					answer = scan.nextLine();
					break;
				case "edit" :
					System.out.println("Type id of chosen user");
					int idToEdit;
					checkIfInt(scan);
					idToEdit = Integer.parseInt(scan.nextLine());
					idToEdit = checkUserInDatabase(conn, scan, idToEdit);
					User toUpdate = User.loadUserById(conn, idToEdit);
					toUpdate = User.loadUserById(conn, idToEdit);
					System.out.println("Type new username");
					toUpdate.setUsername(scan.nextLine());
					System.out.println("Type new email");
					toUpdate.setEmail(scan.nextLine());
					System.out.println("Type new password");
					toUpdate.setPassword(scan.nextLine());
					System.out.println("Type group id of the User");
					int group2;
					checkIfInt(scan);
					group2 = scan.nextInt();
					toUpdate.setGroup(group2);
					toUpdate.saveToDB(conn);
					showUsers(conn);
					System.out.println("Chosen user was edited, what would you like to do next?");
					answer = scan.nextLine();
					break;
				case "delete" :
					System.out.println("Type id of chosen User");
					int idToDelete;
					checkIfInt(scan);
					idToDelete = Integer.parseInt(scan.nextLine());
					idToDelete = checkUserInDatabase(conn, scan, idToDelete);
					User toDelete = User.loadUserById(conn, idToDelete);
					toDelete.delete(conn);
					showUsers(conn);
					System.out.println("Chosen User was deleted, what would you like to do next?");
					answer = scan.nextLine();
					break;
				case "quit":
					shouldContinue = false;
					break;
				default :
					System.out.println("Incorrect input, please type again");
				}
			}
			System.out.println("Bye!");
			conn.close();
			scan.close();
			
			
		} catch ( Exception e) {
			System.out.println(e.getLocalizedMessage());
		}
		
	}

	public static int checkUserInDatabase(Connection conn, Scanner scan, int id) throws SQLException {
		while (User.loadUserById(conn, id) == null ) {
			showUsers(conn);
			System.out.println("No such User in the databse");
			System.out.println("Please type again the id of chosen User");
			id = Integer.parseInt(scan.nextLine());
		}
		return id;
	}

	private static void checkIfInt(Scanner scan) {
		while (!scan.hasNextInt()) {
			System.out.println("Incorrect value, please type a number");
			scan.nextLine();
		}
	}

	private static void showUsers(Connection conn) throws SQLException {
		for ( User user : User.loadAllUsers(conn)) {
			System.out.println(user.getId() + " " + user.getUsername() + " " + user.getEmail() + " " + user.getGroup());
		}
	}

}
