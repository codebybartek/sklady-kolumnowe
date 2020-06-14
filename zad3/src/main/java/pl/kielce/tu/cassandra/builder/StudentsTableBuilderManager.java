package pl.kielce.tu.cassandra.builder;

import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.cql.ResultSet;
import com.datastax.oss.driver.api.core.cql.Row;
import com.datastax.oss.driver.api.core.cql.SimpleStatement;
import com.datastax.oss.driver.api.core.data.UdtValue;
import com.datastax.oss.driver.api.core.type.DataTypes;
import com.datastax.oss.driver.api.querybuilder.QueryBuilder;
import com.datastax.oss.driver.api.querybuilder.SchemaBuilder;
import com.datastax.oss.driver.api.querybuilder.delete.Delete;
import com.datastax.oss.driver.api.querybuilder.insert.Insert;
import com.datastax.oss.driver.api.querybuilder.schema.CreateTable;
import com.datastax.oss.driver.api.querybuilder.schema.CreateType;
import com.datastax.oss.driver.api.querybuilder.schema.Drop;
import com.datastax.oss.driver.api.querybuilder.select.Select;
import com.datastax.oss.driver.api.querybuilder.update.Update;

import pl.kielce.tu.cassandra.simple.SimpleManager;

public class StudentsTableBuilderManager extends SimpleManager {

	public StudentsTableBuilderManager(CqlSession session) {
		super(session);
	}

	public void createTable() {
		CreateType createType = SchemaBuilder.createType("address").withField("street", DataTypes.TEXT)
				.withField("houseNumber", DataTypes.INT).withField("apartmentNumber", DataTypes.INT);

		session.execute(createType.build());

		CreateTable createTable = SchemaBuilder.createTable("student")
				.withPartitionKey("id", DataTypes.INT)
				.withColumn("names", DataTypes.listOf(DataTypes.TEXT))
				.withColumn("age", DataTypes.INT)
				.withColumn("address", QueryBuilder.udt("address"))
				.withColumn("awards", DataTypes.setOf(DataTypes.TEXT))
				.withColumn("marks", DataTypes.mapOf(DataTypes.TEXT, DataTypes.DOUBLE));
		session.execute(createTable.build());
	}

	public void insertIntoTable() {
		Insert insert = QueryBuilder.insertInto("university", "student")
				.value("id", QueryBuilder.raw("1"))
				.value("names", QueryBuilder.raw("['Jan', 'Builder', 'Kowalski']"))
				.value("age", QueryBuilder.raw("0"))
				.value("address", QueryBuilder.raw("{street : 'Kielecka', houseNumber : 1, apartmentNumber : 1}"))
				.value("awards", QueryBuilder.raw("{'The best student award', 'Nobel Prize'}"))
				.value("marks", QueryBuilder.raw("{'physics':3.0, 'chemistry':5.0}"));
		session.execute(insert.build());
		Insert insert2 = QueryBuilder.insertInto("university", "student")
				.value("id", QueryBuilder.raw("2"))
				.value("names", QueryBuilder.raw("['Janusz', 'Builder', 'Nowak']"))
				.value("age", QueryBuilder.raw("18"))
				.value("address", QueryBuilder.raw("{street : 'Warszawska', houseNumber : 1, apartmentNumber : 1}"))
				.value("awards", QueryBuilder.raw("{'Fields Medal'}"))
				.value("marks", QueryBuilder.raw("{'physics':4.0, 'chemistry':4.0}"));
		session.execute(insert2.build());
	}

	public void updateIntoTable() {
		Update update = QueryBuilder.update("student").setColumn("age", QueryBuilder.literal(21)).whereColumn("id").isEqualTo(QueryBuilder.literal(1));
		session.execute(update.build());
	}

	public void deleteFromTable() {
		Delete delete = QueryBuilder.deleteFrom("student").whereColumn("id").isEqualTo(QueryBuilder.literal(1));
		session.execute(delete.build());
	}

	public void selectFromTable() {
		Select query = QueryBuilder.selectFrom("student").all();
		SimpleStatement statement = query.build();
		ResultSet resultSet = session.execute(statement);
		for (Row row : resultSet) {
			System.out.print("student: ");
			System.out.print(row.getInt("id") + ", ");
			System.out.print(row.getList("names", String.class) + ", ");
			System.out.print(row.getInt("age") + ", ");
			UdtValue address = row.getUdtValue("address");
			System.out.print("{" + address.getString("street") + ", " + address.getInt("houseNumber") + ", "
					+ address.getInt("apartmentNumber") + "}" + ", ");
			System.out.print(row.getSet("awards", String.class) + ", ");
			System.out.print(row.getMap("marks", String.class, Double.class) + ", ");
			System.out.println();
		}
		System.out.println("Statement \"" + statement.getQuery() + "\" executed successfully");
	}

	public void dropTable() {
		Drop drop = SchemaBuilder.dropTable("student");
		executeSimpleStatement(drop.build());
	}
}
