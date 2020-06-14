package pl.kielce.tu.cassandra.builder;

import com.datastax.oss.driver.api.core.CqlSession;

public class TestCassandraBuilder {
	public static void main(String[] args) {
		try (CqlSession session = CqlSession.builder().build()) {
			KeyspaceBuilderManager keyspaceManager = new KeyspaceBuilderManager(session, "university");
			keyspaceManager.dropKeyspace();
			keyspaceManager.selectKeyspaces();
			keyspaceManager.createKeyspace();
			keyspaceManager.useKeyspace();
			
			StudentsTableBuilderManager tableManager = new StudentsTableBuilderManager(session);
			tableManager.createTable();
			tableManager.insertIntoTable();
			tableManager.updateIntoTable();
			tableManager.selectFromTable();
			tableManager.deleteFromTable();
			tableManager.dropTable();
		}
	}
}