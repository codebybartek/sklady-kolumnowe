package pl.kielce.tu.cassandra.mapper;
import com.datastax.oss.driver.api.core.CqlIdentifier;
import com.datastax.oss.driver.api.mapper.annotations.DaoFactory;
import com.datastax.oss.driver.api.mapper.annotations.DaoKeyspace;
import com.datastax.oss.driver.api.mapper.annotations.Mapper;

@Mapper
public interface StudentMapper {
  @DaoFactory
  StudentDao studentDao(@DaoKeyspace CqlIdentifier keyspace);
}
