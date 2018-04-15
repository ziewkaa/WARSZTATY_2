package pl.coderslab;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Scanner;

import pl.coderslab.models.User;

public class UserManagement {
	
	public static void main(String[] args) {
		
		try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/workshop_2?useSSL=false", "root", "coderslab")) {
			Scanner scan = new Scanner(System.in);
			
			showUsers(conn);
			
			System.out.println("Type: add , edit, delete or quit");
			String answer = ""; 
			boolean shouldContinue = true;
			
			while (shouldContinue) {
				
				answer = scan.nextLine();
			
				switch (answer) {
				case "add" : 
					System.out.println("Type new Username");
					String username = scan.nextLine();
					System.out.println("Type new email");
					String email = scan.nextLine();
					System.out.println("Type new password");
					String password = scan.nextLine();
					System.out.println("Type group id of new User");
					int group = scan.nextInt();
					User newUser = new User(username, email, password, group);
					newUser.saveToDB(conn);
					showUsers(conn);
					System.out.println("New user was added to the database,  what would you like to do next?");
					break;
				case "edit" :
					System.out.println("Type id of chosen user");
					String id = scan.nextLine();
					User toUpdate = User.loadUserById(conn, Integer.parseInt(id));
					System.out.println("Type new username");
					toUpdate.setUsername(scan.nextLine());
					System.out.println("Type new password");
					toUpdate.setPassword(scan.nextLine());
					System.out.println("Type new email");
					toUpdate.setEmail(scan.nextLine());
					System.out.println("Type group id of the User");
					toUpdate.setGroup(scan.nextInt());
					toUpdate.saveToDB(conn);
					showUsers(conn);
					System.out.println("Chosen user was edited,  what would you like to do next?");
					break;
				case "delete" :
					System.out.println("Type id of chosen user");
					User toDelete= User.loadUserById(conn, scan.nextInt());
					toDelete.delete(conn);
					showUsers(conn);
					System.out.println("Chosen user was deleted, what would you like to do next?");
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
			System.out.println(user.getId() + " " + user.getUsername() + " " + user.getEmail() + " " + user.getGroup());
		}
	}
	
	

}
