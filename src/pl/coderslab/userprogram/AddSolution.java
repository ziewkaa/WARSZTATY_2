package pl.coderslab.userprogram;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Scanner;

import com.mysql.jdbc.StringUtils;

import pl.coderslab.models.Exercise;
import pl.coderslab.models.Solution;
import pl.coderslab.models.User;
import pl.coderslab.programs.ExerciseManagement;
import pl.coderslab.programs.SolutionManagement;
import pl.coderslab.programs.UserManagement;

public class AddSolution {
	
	public static void main(String[] args) {
		
		Scanner scan = new Scanner(System.in);
		System.out.println("Please type your User id");
		String answer = "";
		
		try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/workshop_2?useSSL=false", "root", "coderslab")) {
			
			answer = scan.nextLine();
			
			//checks if typed value is a number
			while(!answer.matches(".*[0-9].*") ) {
				System.out.println("Incorrect User id, please type again.");
				answer = scan.nextLine();
			}
			// checks if typed id is in the database
			while(User.loadUserById(conn, Integer.parseInt(answer)) == null ) {
				System.out.println("Incorrect User id, please type again.");
				answer = scan.nextLine();
			}
			
			int userId = Integer.parseInt(answer);
			System.out.println(userId);
			boolean shouldContinue = true;
			while(shouldContinue) {
				
				System.out.println("Choose what would you like to do: ");
				System.out.println(" - add");
				System.out.println(" - view");
				System.out.println(" - quit");
				answer = scan.nextLine();
				
				switch (answer.toLowerCase()) {
				
				case "add" :
					System.out.println("Exercises, to which you didn't add any Solutions: ");
					List<Exercise> toDo = new ArrayList<>();
					List<Integer> done = new ArrayList<>();
					for (Solution solution : Solution.loadAllSolutionsByUserId(conn, userId)) {	
						done.add(solution.getExercise_id());
					}
					int counter = 0;
					for ( Exercise exercise : Exercise.loadAllExcercises(conn)) {
						if (!done.contains(exercise.getId())) {
							System.out.println("Exercise: " + exercise.getId() + " title: " + exercise.getTitle());
						}
					}	
					System.out.println("Type Exercise id to which you would like to add a Solution");
					int exerciseId = Integer.parseInt(scan.nextLine());
					System.out.println("Please type a description for your Solution");
					String description = scan.nextLine();
					Solution newSolution = new Solution (userId, exerciseId, description, Date.valueOf((LocalDate.now().toString())));
					newSolution.saveToDB(conn);
					System.out.println("Your solution was added to the base");
					
				break;
				case "view" :
					System.out.println("Your solutions are listed below: ");
					SolutionManagement.showSolutionsForUser(conn, userId);
				break;
				case "quit" :
					System.out.println("Bye");
					shouldContinue = false;
				break;
				}
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
		
}
