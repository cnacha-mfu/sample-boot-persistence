package th.mfu;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import th.mfu.domain.Employee;

public interface EmployeeRepository extends CrudRepository<Employee, Integer> {
    // find by starting with firstname
    List<Employee> findByFirstNameStartingWith(String firstName);
    
}
