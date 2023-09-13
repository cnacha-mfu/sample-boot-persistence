package th.mfu;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import th.mfu.domain.Address;

public interface AddressRepository extends CrudRepository<Address, Integer>{
    
    public List<Address> findByEmployeeId(int employeeId);
}
