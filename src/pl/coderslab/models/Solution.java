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
	
	public Solution () {
		
	}

	public void saveToDB(Connection conn) throws SQLException {
		
		if (this.id == 0) {
			String sql = "INSERT INTO Solutions (created, updated, description) VALUES (?, ?, ?)";
			String generatedColumns[] = { "ID" };
			PreparedStatement preparedStatement;
			preparedStatement = conn.prepareStatement(sql, generatedColumns);
			preparedStatement.setDate(1, this.created);
			preparedStatement.setDate(2, this.updated);
			preparedStatement.setString(3, this.description);
			preparedStatement.executeUpdate();
			ResultSet rs = preparedStatement.getGeneratedKeys();
			if (rs.next()) {
				this.id = rs.getInt(1);
			} 
		} else {
			String sql2 = "UPDATE Solutions SET created=?, updated=?, description=? WHERE id = ?";
			PreparedStatement preparedStatement2;
			preparedStatement2 = conn.prepareStatement(sql2);
			preparedStatement2.setDate(1, this.created);
			preparedStatement2.setDate(2, this.updated);
			preparedStatement2.setString(3, this.description);
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
			return loadedSolution;
		}
		return null;
	}
	
	static public Solution[] loadAllSolutions(Connection conn) throws SQLException {
		
		ArrayList<Solution> solutions = new ArrayList<Solution>();
		String sql = "SELECT * FROM Solutions"; PreparedStatement preparedStatement;
		preparedStatement = conn.prepareStatement(sql);
		ResultSet resultSet = preparedStatement.executeQuery();
		while (resultSet.next()) {
			Solution loadedSolution = new Solution();
			loadedSolution.id = resultSet.getInt("id");
			loadedSolution.created = resultSet.getDate("created");
			loadedSolution.updated = resultSet.getDate("updated");
			loadedSolution.description = resultSet.getString("description");
			solutions.add(loadedSolution);}
			Solution[] sArray = new Solution[solutions.size()]; 
			sArray = solutions.toArray(sArray);
			return sArray;
		
	}
	
	 public Solution[] loadAllSolutionsByUserId (Connection conn, int id) throws SQLException {
		
		ArrayList<Solution> solutions = new ArrayList<Solution>();
		String sql = "SELECT * FROM Solutions WHERE users_id = ?"; 
		PreparedStatement preparedStatement;
		preparedStatement = conn.prepareStatement(sql);
		preparedStatement.setInt(1, this.id);
		ResultSet resultSet = preparedStatement.executeQuery();
		while (resultSet.next()) {
			Solution loadedSolution = new Solution();
			loadedSolution.id = resultSet.getInt("id");
			loadedSolution.created = resultSet.getDate("created");
			loadedSolution.updated = resultSet.getDate("updated");
			loadedSolution.description = resultSet.getString("description");
			solutions.add(loadedSolution);
		}
		Solution[] sUserArray = new Solution[solutions.size()]; 
		sUserArray = solutions.toArray(sUserArray);
		return sUserArray;
		
	}
	 
	 public Solution[] loadAllSolutionsByExerciseId (Connection conn, int id) throws SQLException {
			
			ArrayList<Solution> solutions = new ArrayList<Solution>();
			String sql = "SELECT * FROM Solutions WHERE exercise_id = ?"; 
			PreparedStatement preparedStatement;
			preparedStatement = conn.prepareStatement(sql);
			preparedStatement.setInt(1, this.id);
			ResultSet resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				Solution loadedSolution = new Solution();
				loadedSolution.id = resultSet.getInt("id");
				loadedSolution.created = resultSet.getDate("created");
				loadedSolution.updated = resultSet.getDate("updated");
				loadedSolution.description = resultSet.getString("description");
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

	public void setCreated(Date created) {
		this.created = created;
	}

	public void setUpdated(Date updated) {
		this.updated = updated;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
