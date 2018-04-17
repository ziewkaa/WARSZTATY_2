package pl.coderslab.models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.sql.Date;

public class Solution {
	
	protected int id;
	protected Date created;
	protected Date updated;
	protected String description;
	protected int exercise_id;
	protected int user_id;
	
	public Solution (String description,int exercise_id, int user_id ) {
		this.description = description;
		this.exercise_id = exercise_id;
		this.user_id = user_id;
		
	}
	public Solution (int user_id, int exercise_id  ) {
		this.user_id = user_id;
		this.exercise_id = exercise_id;
		
	}
	
	public Solution () {
		
	}

	public Solution(int user_id, int exercise_id, String description, Date updated) {
		
		this.user_id = user_id;
		this.exercise_id = exercise_id;
		this.description = description;
		this.updated = updated;
		
	}
	
	public void saveToDB(Connection conn) throws SQLException {
		
		if (this.id == 0) {
			String sql = "INSERT INTO Solutions (description, exercise_id, user_id) VALUES ( ?, ?, ? )";
			String generatedColumns[] = { "ID" };
			PreparedStatement preparedStatement;
			preparedStatement = conn.prepareStatement(sql, generatedColumns);
			preparedStatement.setString(1, this.description);
			preparedStatement.setInt(2, this.exercise_id);
			preparedStatement.setInt(3, this.user_id);
			preparedStatement.executeUpdate();
			ResultSet rs = preparedStatement.getGeneratedKeys();
			if (rs.next()) {
				this.id = rs.getInt(1);
			} 
		} else {
			String sql2 = "UPDATE Solutions SET updated = NOW(), description=?, exercise_id = ?, user_id = ? WHERE id = ?";
			PreparedStatement preparedStatement2;
			preparedStatement2 = conn.prepareStatement(sql2);
			preparedStatement2.setString(1, this.description);
			preparedStatement2.setInt(2, this.exercise_id);
			preparedStatement2.setInt(3, this.user_id);
			preparedStatement2.setInt(4, this.id);
			preparedStatement2.executeUpdate();		
		}
	}
	
	static public Solution loadSolutionById(Connection conn, int id) throws SQLException {
		
		String sql = "SELECT * FROM Solutions where id = ?";
		PreparedStatement preparedStatement;
		preparedStatement = conn.prepareStatement(sql);
		preparedStatement.setInt(1, id);
		ResultSet resultSet = preparedStatement.executeQuery();
		if (resultSet.next()) {
			Solution loadedSolution = new Solution();
			loadedSolution.id = resultSet.getInt("id");
			loadedSolution.created = resultSet.getDate("created");
			loadedSolution.updated = resultSet.getDate("updated");
			loadedSolution.description = resultSet.getString("description");
			loadedSolution.exercise_id = resultSet.getInt("exercise_id");
			loadedSolution.user_id = resultSet.getInt("user_id");
			return loadedSolution;
		}
		return null;
	}
	
	static public Solution[] loadAllSolutions(Connection conn) throws SQLException {
		
		ArrayList<Solution> solutions = new ArrayList<Solution>();
		String sql = "SELECT * FROM Solutions"; 
		PreparedStatement preparedStatement;
		preparedStatement = conn.prepareStatement(sql);
		ResultSet resultSet = preparedStatement.executeQuery();
		while (resultSet.next()) {
			Solution loadedSolution = new Solution();
			loadedSolution.id = resultSet.getInt("id");
			loadedSolution.created = resultSet.getDate("created");
			loadedSolution.updated = resultSet.getDate("updated");
			loadedSolution.description = resultSet.getString("description");
			loadedSolution.exercise_id = resultSet.getInt("exercise_id");
			loadedSolution.user_id = resultSet.getInt("user_id");
			solutions.add(loadedSolution);}
			Solution[] sArray = new Solution[solutions.size()]; 
			sArray = solutions.toArray(sArray);
			return sArray;
		
	}
	
	 static public Solution[] loadAllSolutionsByUserId (Connection conn, int id) throws SQLException {
		
		ArrayList<Solution> solutions = new ArrayList<Solution>();
		String sql = "SELECT * FROM Solutions WHERE user_id = ?"; 
		PreparedStatement preparedStatement;
		preparedStatement = conn.prepareStatement(sql);
		preparedStatement.setInt(1, id);
		ResultSet resultSet = preparedStatement.executeQuery();
		while (resultSet.next()) {
			Solution loadedSolution = new Solution();
			loadedSolution.id = resultSet.getInt("id");
			loadedSolution.created = resultSet.getDate("created");
			loadedSolution.updated = resultSet.getDate("updated");
			loadedSolution.description = resultSet.getString("description");
			loadedSolution.exercise_id = resultSet.getInt("exercise_id");
			solutions.add(loadedSolution);
		}
		Solution[] sUserArray = new Solution[solutions.size()]; 
		sUserArray = solutions.toArray(sUserArray);
		return sUserArray;
		
	}
	 
	 static public Solution[] loadAllSolutionsByExerciseId (Connection conn, int id) throws SQLException {
			
			ArrayList<Solution> solutions = new ArrayList<Solution>();
			String sql = "SELECT * FROM Solutions WHERE exercise_id = ? ORDER BY updated DESC"; 
			PreparedStatement preparedStatement;
			preparedStatement = conn.prepareStatement(sql);
			preparedStatement.setInt(1, id);
			ResultSet resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				Solution loadedSolution = new Solution();
				loadedSolution.id = resultSet.getInt("id");
				loadedSolution.created = resultSet.getDate("created");
				loadedSolution.updated = resultSet.getDate("updated");
				loadedSolution.description = resultSet.getString("description");
				loadedSolution.user_id = resultSet.getInt("user_id");
				solutions.add(loadedSolution);
			}
			Solution[] sExerciseArray = new Solution[solutions.size()]; 
			sExerciseArray = solutions.toArray(sExerciseArray);
			return sExerciseArray;
			
		}
	
	public void delete(Connection conn) throws SQLException {
		if (this.id != 0) {
			String sql = "DELETE FROM Solutions WHERE id = ?";
			PreparedStatement preparedStatement;
			preparedStatement = conn.prepareStatement(sql);
			preparedStatement.setInt(1, this.id);
			preparedStatement.executeUpdate();
		this.id=0;
		}
	}
	
	public int getId() {
		return id;
	}

	public Date getCreated() {
		return created;
	}

	public Date getUpdated() {
		return updated;
	}

	public String getDescription() {
		return description;
	}
	
	public int getExercise_id() {
		return exercise_id;
	}

	public int getUser_id() {
		return user_id;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public void setUpdated(Date updated) {
		this.updated = updated;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	public void setExercise_id(int exercise_id) {
		this.exercise_id = exercise_id;
	}

	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}

}
