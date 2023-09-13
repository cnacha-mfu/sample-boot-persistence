package th.mfu;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

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


    @GetMapping("/employee")
    public String listEmployees(Model model) {
        model.addAttribute("employees", employeeRepository.findAll());
        return "list-employee";
    }

    @GetMapping("/add-employee")
    public String showAddEmployeeForm(Model model) {
        // pass blank employee to a form
        model.addAttribute("employee", new Employee());
        return "add-employee-form";
    }

    @PostMapping("/employee")
    public String saveEmployee(@ModelAttribute Employee employee) {
        // In a real application, you would save the employee to a database or other storage
        employeeRepository.save(employee);
        return "redirect:/employee";
    }

    @GetMapping("/delete-employee/{id}")
    public String removeEmployee(@PathVariable int id) {
        // pass blank employee to a form
        employeeRepository.deleteById(id);
        return "redirect:/employee";
    }

    @GetMapping("/delete-employee")
    public String removeAllEmployee() {
        // pass blank employee to a form
        employeeRepository.deleteAll();
        return "redirect:/employee";
    }

    @GetMapping("/employee/{id}/address")
    public String showAddAddressForm(Model model, @PathVariable int id) {
        // pass blank employee to a form
        Employee employee = employeeRepository.findById(id).get();
        model.addAttribute("addresses", addressRepository.findByEmployeeId(id));
        Address address = new Address();
        address.setEmployee(employee);
        model.addAttribute("newaddress", address);
        return "address-mgmt";
    }
    
    @PostMapping("/employee/firstName")
    public String search(@RequestParam("keyword") String keyword, Model model) {
        List<Employee> searchResults = employeeRepository.findByFirstNameStartingWith(keyword);
        model.addAttribute("employees", searchResults);
        return "list-employee";
    }

    @PostMapping("/employee/{id}/address")
    public String saveEmployee(@ModelAttribute Address newaddress, @PathVariable int id) {
        Employee employee = employeeRepository.findById(id).get();
        newaddress.setEmployee(employee);
        addressRepository.save(newaddress);
        return "redirect:/employee/"+id+"/address";
    }


}
