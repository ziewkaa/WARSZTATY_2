package pl.coderslab.programs;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
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
			
				switch (answer.toLowerCase()) {
				case "add" : 
					showGroups(conn);
					System.out.println("Type new Group name");
					String name = scan.nextLine();
					Group newGroup = new Group(name);
					newGroup.saveToDB(conn);
					showGroups(conn);
					System.out.println("New Group was added to the database, what would you like to do next?");
					System.out.println("Type: add , edit, delete or quit");
					break;
				case "edit" :
					System.out.println("Type id of chosen Group");
					int idToEdit; 
					checkIfInt(scan);
					idToEdit = Integer.parseInt(scan.nextLine());
					idToEdit = checkGroupInDatabase(conn ,scan, idToEdit);
					Group toUpdate = Group.loadGroupById(conn, idToEdit);
					System.out.println("Type new Group name");
					toUpdate.setName(scan.nextLine());
					toUpdate.saveToDB(conn);
					showGroups(conn);
					System.out.println("Chosen Group was edited,  what would you like to do next?");
					System.out.println("Type: add , edit, delete or quit");
					break;
				case "delete" :
					System.out.println("Type id of chosen Group");
					int idToDelete;
					checkIfInt(scan);
					idToDelete = Integer.parseInt(scan.nextLine());
					idToDelete = checkGroupInDatabase(conn ,scan, idToDelete);
					Group toDelete = Group.loadGroupById(conn, idToDelete);
					toDelete.delete(conn);
					showGroups(conn);
					System.out.println("Chosen Group was deleted, what would you like to do next?");
					break;
				case "quit":
					shouldContinue = false;
					break;
				default :
					System.out.println("Incorrect value, try again");
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
	
	private static void checkIfInt(Scanner scan) {
		while (!scan.hasNextInt()) {
			System.out.println("Incorrect value, please type a number");
			scan.nextLine();
		}
	}
	private static int checkGroupInDatabase(Connection conn, Scanner scan, int id) throws SQLException {
		while (Group.loadGroupById(conn, id) == null ) {
			System.out.println("No such Group in the databse");
			showGroups(conn);
			System.out.println("Please type again the id of chosen Group");
			id = Integer.parseInt(scan.nextLine());
		}
		return id;
	}

}
