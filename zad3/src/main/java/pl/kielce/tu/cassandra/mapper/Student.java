package pl.kielce.tu.cassandra.mapper;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.datastax.oss.driver.api.mapper.annotations.Entity;
import com.datastax.oss.driver.api.mapper.annotations.PartitionKey;

@Entity
public class Student {

	@PartitionKey
	private Integer id;

	private Integer age;
	
	private List<String> names;
	
	private Address address;
	
	private Set<String> awards;
	
	private Map<String, Double> marks;

	public Student() {
	}

	public Student(Integer id, List<String> names, Integer age, Address address, Set<String> awards, Map<String, Double> marks) {
		this.id = id;
		this.names = names;
		this.age = age;
		this.address = address;
		this.awards = awards;
		this.marks = marks;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public List<String> getNames() {
		return names;
	}

	public void setNames(List<String> names) {
		this.names = names;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public Set<String> getAwards() {
		return awards;
	}

	public void setAwards(Set<String> awards) {
		this.awards = awards;
	}

	public Map<String, Double> getMarks() {
		return marks;
	}

	public void setMarks(Map<String, Double> marks) {
		this.marks = marks;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder()
			.append("student {")
			.append(id + ",")
			.append(names + ",")
			.append(age + ",")
			.append(address + ",")
			.append(awards + ",")
			.append(marks + ",")
			.append("}");
		return builder.toString();
	}
}
