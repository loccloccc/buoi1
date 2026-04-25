package re.cntt4.task1.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import re.cntt4.task1.model.Todo;

public interface ITodoRepository extends JpaRepository<Todo,Long> {


}
