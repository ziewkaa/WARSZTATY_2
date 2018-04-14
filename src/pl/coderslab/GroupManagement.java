package pl.coderslab;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Scanner;

import pl.coderslab.models.Group;

public class GroupManagement {
	
	public static void main(String[] args) {
		
		try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/workshop_2?useSSL=false", "root", "coderslab")) {
			Scanner scan = new Scanner(System.in);
			
			showGroups(conn);
			
			System.out.println("Type: add , edit, delete or quit");
			String answer = ""; 
			boolean shouldContinue = true;
			
			while (shouldContinue) {
				
				answer = scan.nextLine();
			
				switch (answer) {
				case "add" : 
					System.out.println("Type new Group name");
					String name = scan.nextLine();
					Group newGroup = new Group(name);
					newGroup.saveToDB(conn);
					showGroups(conn);
					System.out.println("New Group was added to the database,  what would you like to do next?");
					break;
				case "edit" :
					System.out.println("Type id of chosen Group");
					String id = scan.nextLine();
					Group toUpdate = Group.loadGroupById(conn, Integer.parseInt(id));
					System.out.println("Type new Group name");
					toUpdate.setName(scan.nextLine());
					toUpdate.saveToDB(conn);
					showGroups(conn);
					System.out.println("Chosen Group was edited,  what would you like to do next?");
					break;
				case "delete" :
					System.out.println("Type id of chosen Group");
					Group toDelete = Group.loadGroupById(conn, scan.nextInt());
					toDelete.delete(conn);
					showGroups(conn);
					System.out.println("Chosen Group was deleted, what would you like to do next?");
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

	private static void showGroups(Connection conn) throws SQLException {
		for ( Group group : Group.loadAllGroups(conn)) {
			System.out.println("Group id: " + group.getId() + " name: " + group.getName());
		}
	}
	
	

}
