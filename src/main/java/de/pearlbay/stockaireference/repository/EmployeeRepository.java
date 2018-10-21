package de.pearlbay.stockaireference.repository;

import de.pearlbay.stockaireference.domain.payroll.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
}
