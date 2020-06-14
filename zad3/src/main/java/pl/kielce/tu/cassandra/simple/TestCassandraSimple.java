package pl.kielce.tu.cassandra.simple;

import com.datastax.oss.driver.api.core.CqlSession;

public class TestCassandraSimple {
	public static void main(String[] args) {
		try (CqlSession session = CqlSession.builder().build()) {
			KeyspaceSimpleManager keyspaceManager = new KeyspaceSimpleManager(session, "university");
			keyspaceManager.dropKeyspace();
			keyspaceManager.selectKeyspaces();
			keyspaceManager.createKeyspace();
			keyspaceManager.useKeyspace();
			
			StudentsTableSimpleManager tableManager = new StudentsTableSimpleManager(session);
			tableManager.createTable();
			tableManager.insertIntoTable();
			tableManager.updateIntoTable();
			tableManager.selectFromTable();
			tableManager.deleteFromTable();
			tableManager.dropTable();
		}
	}
}