package pl.kielce.tu.cassandra.mapper;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.datastax.oss.driver.api.core.CqlIdentifier;
import com.datastax.oss.driver.api.core.CqlSession;

import pl.kielce.tu.cassandra.simple.StudentsTableSimpleManager;

public class TestCassandraMapper {
	public static void main(String[] args) {
		try (CqlSession session = CqlSession.builder().build()) {			
			KeyspaceManager keyspaceManager = new KeyspaceManager(session, "university");
			keyspaceManager.dropKeyspace();
			keyspaceManager.selectKeyspaces();
			keyspaceManager.createKeyspace();
			keyspaceManager.useKeyspace();

			StudentsTableSimpleManager tableManager = new StudentsTableSimpleManager(session);
			tableManager.createTable();
			
			CodecManager codecManager = new CodecManager(session);
			codecManager.registerAddressCodec();
			
			StudentMapper studentMapper = new StudentMapperBuilder(session).build();
			StudentDao dao = studentMapper.studentDao(CqlIdentifier.fromCql("university"));
			
			Student student = createStudent();
			dao.save(student);
			
			student.setAge(21);
			dao.update(student);
			
			Student s = dao.findById(1);
			System.out.println(s);
			
			dao.delete(student);
		}
	}
	
	private static Student createStudent() {
		int id = 1;
		List<String> names = Arrays.asList("Jan", "Mapper", "Kowalski");
		int age = 0;
		Address address = new Address("Kielecka", 1, 1);
		Set<String> awards = new HashSet<String>();
		awards.add("The best student award");
		awards.add("Nobel Prize");
		Map<String, Double> marks = new HashMap<String, Double>();
		marks.put("physics", 3.0d);
		marks.put("chemistry", 5.0d);
		Student student = new Student(id, names, age, address, awards, marks);
		return student;
	}
}
