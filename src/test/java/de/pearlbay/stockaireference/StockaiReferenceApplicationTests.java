package de.pearlbay.stockaireference;

import static org.assertj.core.api.Assertions.assertThat;


import de.pearlbay.stockaireference.controller.EmployeeController;
import de.pearlbay.stockaireference.controller.OrderController;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class StockaiReferenceApplicationTests {
    
    @Autowired
    private EmployeeController employeeController;
    
    @Autowired
    private OrderController orderController;
    
	@Test
	public void contextLoads() {
        assertThat(employeeController).isNotNull();
        assertThat(orderController).isNotNull();
	}
}
