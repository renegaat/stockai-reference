package de.pearlbay.stockaireference.application;

import de.pearlbay.stockaireference.domain.business.Order;
import de.pearlbay.stockaireference.domain.business.Status;
import de.pearlbay.stockaireference.domain.payroll.Employee;
import de.pearlbay.stockaireference.repository.EmployeeRepository;
import de.pearlbay.stockaireference.repository.OrderRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
public class LoadDatabase {
    
    @Bean
    CommandLineRunner initDatabase(EmployeeRepository employeeRepository, OrderRepository orderRepository) {
        return args -> {
            log.info("Preloading " + employeeRepository.save(new Employee("Bilbo Baggins", "burglar")));
            log.info("Preloading " + employeeRepository.save(new Employee("Frodo Baggins", "thief")));
            log.info("Preloading " + orderRepository.save(new Order("Description order 1", Status.IN_PROGRESS)));
            log.info("Preloading " + orderRepository.save(new Order("Description order 2", Status.IN_PROGRESS)));
        };
    }
}
