package pl.coderslab;

import java.sql.Connection;
import java.sql.DriverManager;

public class Main1 {

	public static void main(String[] args) {
		 
		try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/workshop_2?useSSL=false", "root", "coderslab")) {
			
			
			
		} catch ( Exception e) {
			System.out.println(e.getLocalizedMessage());
		}
		
	}

}