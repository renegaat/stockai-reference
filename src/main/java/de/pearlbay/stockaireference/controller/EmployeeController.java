package de.pearlbay.stockaireference.controller;

import de.pearlbay.stockaireference.application.EmployeeNotFoundException;
import de.pearlbay.stockaireference.application.EmployeeResourceAssembler;
import de.pearlbay.stockaireference.domain.payroll.Employee;
import de.pearlbay.stockaireference.repository.EmployeeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@RestController
public class EmployeeController {

    private Logger logger = LoggerFactory.getLogger(EmployeeController.class);

    private final EmployeeRepository repository;
    private final EmployeeResourceAssembler employeeResourceAssembler;

    EmployeeController(EmployeeRepository repository, EmployeeResourceAssembler employeeResourceAssembler) {
        this.repository = repository;
        this.employeeResourceAssembler = employeeResourceAssembler;
    }

    @GetMapping("/employees")
    public Resources<Resource<Employee>> all() {
        List<Resource<Employee>> employees = repository.findAll().stream()
                .map(employee -> employeeResourceAssembler.toResource(employee))
                .collect(Collectors.toList());

        logger.info("route /employees");

        return new Resources<>(employees,
                linkTo(methodOn(EmployeeController.class).all()).withSelfRel());
    }

    @GetMapping("/employees/{id}")
    public Resource<Employee> one(@PathVariable Long id) {

        Employee employee = repository.findById(id)
                .orElseThrow(() -> new EmployeeNotFoundException(id));

        return employeeResourceAssembler.toResource(employee);
    }

    @PostMapping("/employees")
    public ResponseEntity<?> newEmployee(@RequestBody Employee newEmployee) throws URISyntaxException {

        Resource<Employee> employeeResource
                = employeeResourceAssembler.toResource(repository.save(newEmployee));

        return ResponseEntity
                .created(new URI(employeeResource.getId().expand().getHref()))
                .body(employeeResource);
    }
}
