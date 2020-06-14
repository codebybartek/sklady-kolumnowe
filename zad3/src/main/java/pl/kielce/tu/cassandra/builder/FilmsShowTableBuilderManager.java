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

import pl.kielce.tu.cassandra.mapper.Ticket;
import pl.kielce.tu.cassandra.simple.SimpleManager;

import java.util.List;

public class FilmsShowTableBuilderManager extends SimpleManager {

	public FilmsShowTableBuilderManager(CqlSession session) {
		super(session);
	}

	public void createTable() {
		CreateType createType = SchemaBuilder.createType("ticket").withField("type", DataTypes.TEXT)
				.withField("place", DataTypes.TEXT).withField("creation_date", DataTypes.TEXT);

		session.execute(createType.build());

		CreateTable createTable = SchemaBuilder.createTable("film_show")
				.withPartitionKey("id", DataTypes.INT)
				.withColumn("film_name", DataTypes.TEXT)
				.withColumn("hour", DataTypes.TEXT)
				.withColumn("date", DataTypes.TEXT)
				.withColumn("ticket", DataTypes.frozenListOf(QueryBuilder.udt("ticket")))
				.withColumn("cinema_hall", DataTypes.TEXT);
		session.execute(createTable.build());
	}

	public void insertIntoTable() {
		Main.counter++;
		Insert insert = QueryBuilder.insertInto("cinema", "film_show")
				.value("id", QueryBuilder.raw(""+Main.counter+""))
				.value("film_name", QueryBuilder.raw("'Obcy 3'"))
				.value("hour", QueryBuilder.raw("'11:50'"))
				.value("date", QueryBuilder.raw("'2020-06-07'"))
				.value("ticket", QueryBuilder.raw("[{type : 'ulgowy', place : '17/20', creation_date : '2020-06-07'}, {type : 'ulgowy', place : '19/20', creation_date : '2020-06-07'}]"))
				.value("cinema_hall", QueryBuilder.raw("'Sala12'"));
		session.execute(insert.build());
		Main.counter++;
		Insert insert2 = QueryBuilder.insertInto("cinema", "film_show")
				.value("id", QueryBuilder.raw(""+Main.counter+""))
				.value("film_name", QueryBuilder.raw("'Szybcy i wsciekli'"))
				.value("hour", QueryBuilder.raw("'12:50'"))
				.value("date", QueryBuilder.raw("'2020-06-07'"))
				.value("ticket",  QueryBuilder.raw("[{type : 'ulgowy', place : '15/34', creation_date : '2020-02-07'}, {type : 'normalny', place : '13/24', creation_date : '2020-01-01'}]"))
				.value("cinema_hall", QueryBuilder.raw("'Sala1'"));
		session.execute(insert2.build());
		Main.counter++;
		Insert insert3 = QueryBuilder.insertInto("cinema", "film_show")
				.value("id", QueryBuilder.raw(""+Main.counter+""))
				.value("film_name", QueryBuilder.raw("'Och Karol'"))
				.value("hour", QueryBuilder.raw("'14:50'"))
				.value("date", QueryBuilder.raw("'2020-01-07'"))
				.value("ticket",  QueryBuilder.raw("[{type : 'ulgowy', place : '15/34', creation_date : '2020-02-07'}, {type : 'normalny', place : '13/24', creation_date : '2020-01-01'}]"))
				.value("cinema_hall", QueryBuilder.raw("'Sala1'"));
		session.execute(insert2.build());
	}

	public void addFilmShow(String name, String hour, String date, List<Ticket> tickets, String hall) {
		Insert insert5 = QueryBuilder.insertInto("cinema", "film_show")
				.value("id", QueryBuilder.raw(""+Main.counter+""))
				.value("film_name", QueryBuilder.raw("'"+name+"'"))
				.value("hour", QueryBuilder.raw("'"+hour+"'"))
				.value("date", QueryBuilder.raw("'"+date+"'"))
				.value("ticket", QueryBuilder.raw(""+tickets.toString()+""))
				.value("cinema_hall", QueryBuilder.raw("'"+hall+"'"));
		session.execute(insert5.build());
		Main.counter++;
	}

	public void updateIntoTable(Integer id, String name) {
		Update update = QueryBuilder.update("film_show").setColumn("film_name", QueryBuilder.literal(name)).whereColumn("id").isEqualTo(QueryBuilder.literal(id));
		session.execute(update.build());
	}

	public void deleteFromTable(Integer id) {
		Delete delete = QueryBuilder.deleteFrom("film_show").whereColumn("id").isEqualTo(QueryBuilder.literal(id));
		session.execute(delete.build());
	}

	public void showAll() {
		Select query = QueryBuilder.selectFrom("film_show").all();
		SimpleStatement statement = query.build();
		ResultSet resultSet = session.execute(statement);

		for (Row row : resultSet) {
			System.out.print("Film show: ");
			System.out.print(row.getInt("id") + ", ");
			System.out.print(row.getString("film_name") + ", ");
			System.out.print(row.getString("hour") + ", ");
			System.out.print(row.getString("date") + ", ");
			List<UdtValue> tickets = row.getList("ticket", UdtValue.class );
			System.out.print("[");
			for (UdtValue ticket:tickets){
				System.out.print("{");
				System.out.print(ticket.getString("type" ) + ", ");
				System.out.print(ticket.getString("place")+ ", ");
				System.out.print(ticket.getString("creation_date"));
				System.out.print("},");
			}
			System.out.print("], ");
			System.out.print(row.getString("cinema_hall") + ", ");
			System.out.println();
		}
		System.out.println("Statement \"" + statement.getQuery() + "\" executed successfully");
	}

	public void show(Integer id){
		Select query = QueryBuilder.selectFrom("film_show").all().whereColumn("id").isEqualTo(QueryBuilder.literal(id));
		SimpleStatement statement = query.build();
		ResultSet row = session.execute(statement);
		Row row1 = row.one();
		System.out.print("Film show: ");
		System.out.print(row1.getInt("id") + ", ");
		System.out.print(row1.getString("film_name") + ", ");
		System.out.print(row1.getString("hour") + ", ");
		System.out.print(row1.getString("date") + ", ");
		List<UdtValue> tickets = row1.getList("ticket", UdtValue.class );
		System.out.print("[");
		for (UdtValue ticket:tickets){
			System.out.print("{");
			System.out.print(ticket.getString("type" ) + ", ");
			System.out.print(ticket.getString("place")+ ", ");
			System.out.print(ticket.getString("creation_date"));
			System.out.print("},");
		}
		System.out.print("], ");
		System.out.print(row1.getString("cinema_hall") + ", ");
		System.out.println();
	}

	public void showBefore(Integer hour){
		Select query = QueryBuilder.selectFrom("film_show").all();
		SimpleStatement statement = query.build();
		ResultSet resultSet = session.execute(statement);

		for (Row row : resultSet) {
			String[] parts = row.getString("hour").split(":");
			if(Integer.parseInt(parts[0]) < hour) {
				System.out.print("Film show: ");
				System.out.print(row.getInt("id") + ", ");
				System.out.print(row.getString("film_name") + ", ");
				System.out.print(row.getString("hour") + ", ");
				System.out.print(row.getString("date") + ", ");
				List<UdtValue> tickets = row.getList("ticket", UdtValue.class);
				System.out.print("[");
				for (UdtValue ticket : tickets) {
					System.out.print("{");
					System.out.print(ticket.getString("type") + ", ");
					System.out.print(ticket.getString("place") + ", ");
					System.out.print(ticket.getString("creation_date"));
					System.out.print("},");
				}
				System.out.print("], ");
				System.out.print(row.getString("cinema_hall") + ", ");
				System.out.println();
			}
		}
		System.out.println("Statement \"" + statement.getQuery() + "\" executed successfully");
	}

	public void dropTable() {
		Drop drop = SchemaBuilder.dropTable("film_show");
		executeSimpleStatement(drop.build());
	}
}
