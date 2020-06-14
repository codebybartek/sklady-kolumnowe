package pl.kielce.tu.cassandra.mapper;
import com.datastax.oss.driver.api.mapper.annotations.Dao;
import com.datastax.oss.driver.api.mapper.annotations.Delete;
import com.datastax.oss.driver.api.mapper.annotations.Insert;
import com.datastax.oss.driver.api.mapper.annotations.Select;
import com.datastax.oss.driver.api.mapper.annotations.Update;

@Dao
public interface StudentDao {
  @Select
  Student findById(Integer id);

  @Insert
  void save(Student student);

  @Update
  void update(Student student);
  
  @Delete
  void delete(Student student);
}
