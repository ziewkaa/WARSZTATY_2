package pl.coderslab.models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Exercise {
	
	protected int id;
	protected String title;
	protected String description;
	
	public Exercise () {
		
	}
	
	public void saveToDB(Connection conn) throws SQLException {
		
		if (this.id == 0) {
			String sql = "INSERT INTO Exercises (title, description) VALUES (?, ?)";
			String generatedColumns[] = { "ID" };
			PreparedStatement preparedStatement;
			preparedStatement = conn.prepareStatement(sql, generatedColumns);
			preparedStatement.setString(1, this.title);
			preparedStatement.setString(2, this.description);
			preparedStatement.executeUpdate();
			ResultSet rs = preparedStatement.getGeneratedKeys();
			if (rs.next()) {
				this.id = rs.getInt(1);
			} 
		} else {
			String sql2 = "UPDATE Exercises SET title = ?, description = ?, where id = ?";
			PreparedStatement preparedStatement2;
			preparedStatement2 = conn.prepareStatement(sql2);
			preparedStatement2.setString(1, this.title);
			preparedStatement2.setString(2, this.description);
			preparedStatement2.setInt(3, this.id);
			preparedStatement2.executeUpdate();		
		}
	}
	
	static public Exercise loadExcerciseById(Connection conn, int id) throws SQLException {
		
		String sql = "SELECT * FROM Exercises where id = ?";
		PreparedStatement preparedStatement;
		preparedStatement = conn.prepareStatement(sql);
		preparedStatement.setInt(1, id);
		ResultSet resultSet = preparedStatement.executeQuery();
		if (resultSet.next()) {
			Exercise loadedExercise = new Exercise();
			loadedExercise.id = resultSet.getInt("id");
			loadedExercise.title = resultSet.getString("title");
			loadedExercise.description = resultSet.getString("description");
			return loadedExercise;
		}
		return null;
	}
	
	static public Exercise[] loadAllExcercises(Connection conn) throws SQLException {
		
		ArrayList<Exercise> exercises = new ArrayList<Exercise>();
		String sql = "SELECT * FROM Exercises"; PreparedStatement preparedStatement;
		preparedStatement = conn.prepareStatement(sql);
		ResultSet resultSet = preparedStatement.executeQuery();
		while (resultSet.next()) {
			Exercise loadedExercise = new Exercise();
			loadedExercise.id = resultSet.getInt("id");
			loadedExercise.title = resultSet.getString("title");
			loadedExercise.description = resultSet.getString("description");
			exercises.add(loadedExercise);}
			Exercise[] eArray = new Exercise[exercises.size()];
			eArray = exercises.toArray(eArray);
			return eArray;
		
	}
	
	public void delete(Connection conn) throws SQLException {
		if (this.id != 0) {
			String sql = "DELETE FROM Excercises WHERE id = ?";
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

	public String getTitle() {
		return title;
	}

	public String getDescription() {
		return description;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
