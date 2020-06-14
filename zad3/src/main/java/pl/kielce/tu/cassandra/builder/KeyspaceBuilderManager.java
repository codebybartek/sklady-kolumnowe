package pl.kielce.tu.cassandra.builder;

import static com.datastax.oss.driver.api.querybuilder.QueryBuilder.selectFrom;

import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.cql.ResultSet;
import com.datastax.oss.driver.api.core.cql.Row;
import com.datastax.oss.driver.api.core.cql.SimpleStatement;
import com.datastax.oss.driver.api.querybuilder.SchemaBuilder;
import com.datastax.oss.driver.api.querybuilder.schema.CreateKeyspace;
import com.datastax.oss.driver.api.querybuilder.schema.Drop;
import com.datastax.oss.driver.api.querybuilder.select.Select;

import pl.kielce.tu.cassandra.simple.SimpleManager;

public class KeyspaceBuilderManager extends SimpleManager {

	private String keyspaceName;

	public KeyspaceBuilderManager(CqlSession session, String keyspaceName) {
		super(session);
		this.keyspaceName = keyspaceName;
	}

	public void selectKeyspaces() {
		Select query = selectFrom("system_schema", "keyspaces").column("keyspace_name");
		SimpleStatement statement = query.build();
		ResultSet resultSet = session.execute(statement);

		System.out.print("Keyspaces = ");
		for (Row row : resultSet) {
			System.out.print(row.getString("keyspace_name") + ", ");
		}
		System.out.println();
	}

	public void createKeyspace() {
		CreateKeyspace create = SchemaBuilder.createKeyspace(keyspaceName).withSimpleStrategy(1);
		executeSimpleStatement(create.build());
	}

	public void useKeyspace() {
		executeSimpleStatement("USE " + keyspaceName + ";");
	}

	public void dropKeyspace() {
		Drop drop = SchemaBuilder.dropKeyspace(keyspaceName).ifExists();
		executeSimpleStatement(drop.build());
	}
}
