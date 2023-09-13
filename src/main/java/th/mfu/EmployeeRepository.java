package th.mfu;

import org.springframework.data.repository.CrudRepository;

import th.mfu.domain.Employee;

public interface EmployeeRepository extends CrudRepository<Employee, Integer> {
    
}
