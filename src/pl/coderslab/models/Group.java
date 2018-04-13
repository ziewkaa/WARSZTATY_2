package pl.coderslab.models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Group {
	
	protected int id;
	protected String name;
	
	public Group () {
		
	}

	public void saveToDB(Connection conn) throws SQLException {
		
		if (this.id == 0) {
			String sql = "INSERT INTO Groups (name) VALUES (?)";
			String generatedColumns[] = { "name" };
			PreparedStatement preparedStatement;
			preparedStatement = conn.prepareStatement(sql, generatedColumns);
			preparedStatement.setString(1, this.name);
			preparedStatement.executeUpdate();
			ResultSet rs = preparedStatement.getGeneratedKeys();
			if (rs.next()) {
				this.id = rs.getInt(1);
			} 
		} else {
			String sql2 = "UPDATE Groups SET name = ? WHERE id = ?";
			PreparedStatement preparedStatement2;
			preparedStatement2 = conn.prepareStatement(sql2);
			preparedStatement2.setString(1, this.name);
			preparedStatement2.setInt(2, this.id);
			preparedStatement2.executeUpdate();		
		}
	}
	
	static public Group loadGroupById(Connection conn, int id) throws SQLException {
		
		String sql = "SELECT * FROM Groups WHERE id = ?";
		PreparedStatement preparedStatement;
		preparedStatement = conn.prepareStatement(sql);
		preparedStatement.setInt(1, id);
		ResultSet resultSet = preparedStatement.executeQuery();
		if (resultSet.next()) {
			Group loadedGroup = new Group();
			loadedGroup.id = resultSet.getInt("id");
			loadedGroup.name = resultSet.getString("name");
			return loadedGroup;
		}
		return null;
	}
	
	static public Group[] loadAllGroups(Connection conn) throws SQLException {
		
		ArrayList<Group> groups = new ArrayList<Group>();
		String sql = "SELECT * FROM Groups"; 
		PreparedStatement preparedStatement;
		preparedStatement = conn.prepareStatement(sql);
		ResultSet resultSet = preparedStatement.executeQuery();
		while (resultSet.next()) {
			Group loadedGroup = new Group();
			loadedGroup.id = resultSet.getInt("id");
			loadedGroup.name = resultSet.getString("name");
			groups.add(loadedGroup);
		}
		Group[] gArray = new Group[groups.size()]; 
		gArray = groups.toArray(gArray);
		return gArray;
		
	}
	
	public void delete(Connection conn) throws SQLException {
		if (this.id != 0) {
			String sql = "DELETE FROM Groups WHERE id = ?";
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
