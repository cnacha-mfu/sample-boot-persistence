package th.mfu;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import th.mfu.domain.Address;
import th.mfu.domain.Employee;

@Controller
public class EmployeeController {
    
    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private AddressRepository addressRepository;
    
    @InitBinder
    public final void initBinderUsuariosFormValidator(final WebDataBinder binder, final Locale locale) {
        final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", locale);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
    }


    @GetMapping("/employees")
    public String listEmployees(Model model) {
        model.addAttribute("employees", employeeRepository.findAll());
        return "list-employee";
    }

    @GetMapping("/add-employee")
    public String showAddEmployeeForm(Model model) {
        // pass blank employee to a form
        model.addAttribute("newemployee", new Employee());
        return "add-employee-form";
    }

    @PostMapping("/employees")
    public String saveEmployee(@ModelAttribute Employee newemployee) {
        // In a real application, you would save the employee to a database or other storage
        employeeRepository.save(newemployee);
        return "redirect:/employees";
    }

    @Transactional
    @GetMapping("/delete-employee/{id}")
    public String removeEmployee(@PathVariable int id) {
        //remove related addresses
        addressRepository.deleteByEmployeeId(id);
        //remove employee from database
        employeeRepository.deleteById(id);
        
        return "redirect:/employees";
    }

    @GetMapping("/delete-employee")
    public String removeAllEmployee() {
        // pass blank employee to a form
        employeeRepository.deleteAll();
        return "redirect:/employees";
    }

    @GetMapping("/employees/{id}/addresses")
    public String showAddAddressForm(Model model, @PathVariable int id) {
        // pass blank employee to a form
        Employee employee = employeeRepository.findById(id).get();
        model.addAttribute("addresses", addressRepository.findByEmployeeId(id));
        Address address = new Address();
        address.setEmployee(employee);
        model.addAttribute("newaddress", address);
        return "address-mgmt";
    }
    
    @PostMapping("/employees/firstName")
    public String search(@RequestParam("keyword") String keyword, Model model) {
        List<Employee> searchResults = employeeRepository.findByFirstNameStartingWith(keyword);
        model.addAttribute("employees", searchResults);
        return "list-employee";
    }

    @PostMapping("/employees/{id}/addresses")
    public String saveEmployee(@ModelAttribute Address newaddress, @PathVariable int id) {
        Employee employee = employeeRepository.findById(id).get();
        newaddress.setEmployee(employee);
        addressRepository.save(newaddress);
        return "redirect:/employees/"+id+"/addresses";
    }


}
