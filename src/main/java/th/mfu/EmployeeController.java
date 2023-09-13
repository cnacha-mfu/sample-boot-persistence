package th.mfu;

import java.text.SimpleDateFormat;
import java.util.Date;
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

import th.mfu.domain.Employee;

@Controller
public class EmployeeController {
    
    @Autowired
    private EmployeeRepository repository;
    
    @InitBinder
    public final void initBinderUsuariosFormValidator(final WebDataBinder binder, final Locale locale) {
        final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", locale);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
    }


    @GetMapping("/employees")
    public String listEmployees(Model model) {
        model.addAttribute("employees", repository.findAll());
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
        repository.save(newemployee);
        return "redirect:/employees";
    }

    @GetMapping("/delete-employee/{id}")
    public String removeEmployee(@PathVariable int id) {
        // pass blank employee to a form
       repository.deleteById(id);
        return "redirect:/employees";
    }

    @GetMapping("/delete-employee")
    public String removeAllEmployee() {
        // pass blank employee to a form
        repository.deleteAll();
        return "redirect:/employees";
    }


}
