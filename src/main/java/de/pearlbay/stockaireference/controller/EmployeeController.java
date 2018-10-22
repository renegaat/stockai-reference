package de.pearlbay.stockaireference.controller;

import de.pearlbay.stockaireference.application.EmployeeNotFoundException;
import de.pearlbay.stockaireference.domain.payroll.Employee;
import de.pearlbay.stockaireference.repository.EmployeeRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class EmployeeController {

    private final EmployeeRepository repository;

    EmployeeController(EmployeeRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/employees")
    List<Employee> all() {
        return repository.findAll();
    }


    @GetMapping("/employees/{id}")
    Employee one(@PathVariable Long id) throws EmployeeNotFoundException {
        
        return repository.findById(id)
                .orElseThrow(() -> new EmployeeNotFoundException(id));
    }
}
