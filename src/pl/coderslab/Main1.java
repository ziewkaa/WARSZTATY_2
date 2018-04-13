package pl.coderslab;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Savepoint;
import java.util.Arrays;

import pl.coderslab.models.User;

public class Main1 {

	public static void main(String[] args) {
		 
		try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/workshop_2?useSSL=false", "root", "coderslab")) {
			
			User myUser = User.loadUserById(conn, 4);
			myUser.delete(conn);
			System.out.print(myUser.getId());
			
			
			
		} catch ( Exception e) {
			System.out.println(e.getLocalizedMessage());
		}
		
	}

}