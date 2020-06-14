package pl.kielce.tu.cassandra.simple;

import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.cql.ResultSet;
import com.datastax.oss.driver.api.core.cql.Row;
import com.datastax.oss.driver.api.core.data.UdtValue;

public class StudentsTableSimpleManager extends SimpleManager{

	public StudentsTableSimpleManager(CqlSession session) {
		super(session);
	}

	public void createTable() {
		executeSimpleStatement( 
				"CREATE TYPE address (\n" + 
				"    street text,\n" + 
				"    houseNumber int,\n" + 
				"    apartmentNumber int\n" + 
				");");
		executeSimpleStatement( 
				"CREATE TABLE student (\n" + 
				"    id int PRIMARY KEY,\n" +  
				"    names list<text>,\n" + 
				"    age int,\n" +
				"    address frozen<address>,\n" +
				"    awards set<text>,\n" + 
				"    marks map<text, double>\n" + 
				");");
	}
	
	public void insertIntoTable() {
		executeSimpleStatement("INSERT INTO student (id, names, age, address, awards, marks) " +  
				" VALUES (1, ['Jan', 'Simple', 'Kowalski'], 0, {street : 'Kielecka', houseNumber : 1, apartmentNumber : 1}, {'The best student award', 'Nobel Prize'}, {'physics':3.0, 'chemistry':5.0});");
		executeSimpleStatement("INSERT INTO student (id, names, age, address, awards, marks) " +  
				" VALUES (2, ['Janusz', 'Simple', 'Kowalski'], 0, {street : 'Warszawska', houseNumber : 1, apartmentNumber : 1}, {'Fields Medal'}, {'physics':4.0, 'chemistry':4.0});");
	}

	public void updateIntoTable() {
		executeSimpleStatement("UPDATE student SET age = 21 WHERE id = 1;");
	}
	
	public void deleteFromTable() {
		executeSimpleStatement("DELETE FROM student WHERE id = 1;");
	}
	
	public void selectFromTable() {
		String statement = "SELECT * FROM student;";
		ResultSet resultSet = session.execute(statement);
		for (Row row : resultSet) {
			System.out.print("student: ");
			System.out.print(row.getInt("id") + ", ");
			System.out.print(row.getList("names", String.class) + ", ");
			System.out.print(row.getInt("age") + ", ");
			UdtValue address = row.getUdtValue("address");
			System.out.print("{" + address.getString("street") + ", " + address.getInt("houseNumber")  + ", " + address.getInt("apartmentNumber") + "}" +  ", ");
			System.out.print(row.getSet("awards", String.class) + ", ");
			System.out.print(row.getMap("marks", String.class, Double.class) + ", ");
		}
		System.out.println();
		System.out.println("Statement \"" + statement + "\" executed successfully");
	}
	
	public void dropTable() {
		executeSimpleStatement("DROP TABLE student;");
	}
}
