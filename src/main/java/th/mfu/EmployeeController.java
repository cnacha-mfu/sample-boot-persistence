package th.mfu;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class EmployeeController {
    private int nextId = 1;
    private HashMap<Integer,Employee> employees = new HashMap<Integer,Employee>(); // In-memory storage for simplicity

    
    @InitBinder
    public final void initBinderUsuariosFormValidator(final WebDataBinder binder, final Locale locale) {
        final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", locale);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
    }


    @GetMapping("/employees")
    public String listEmployees(Model model) {
        model.addAttribute("employees", employees.values());
        return "list-employee";
    }

    @GetMapping("/add-employee")
    public String showAddEmployeeForm(Model model) {
        // pass blank employee to a form
        model.addAttribute("employee", new Employee());
        return "add-employee-form";
    }

    @PostMapping("/employees")
    public String saveEmployee(@ModelAttribute Employee employee) {
        // In a real application, you would save the employee to a database or other storage
        employee.setId(nextId);
        employees.put(nextId, employee);
        nextId++;
        return "redirect:/employees";
    }

    @GetMapping("/delete-employee/{id}")
    public String removeEmployee(@PathVariable int id) {
        // pass blank employee to a form
        employees.remove(id);
        return "redirect:/employees";
    }

    @GetMapping("/delete-employee")
    public String removeAllEmployee() {
        // pass blank employee to a form
        employees.clear();
        nextId = 1;
        return "redirect:/employees";
    }


}
