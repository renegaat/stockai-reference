package de.pearlbay.stockaireference;

import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class EmployeeControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;


    @Test
    public void shouldReturnAllEmployees() {
        assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/employees",
                String.class)).contains("Bilbo Baggins");
    }

    @Test
    public void shouldReturnOneEmployee() {
        assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/employees/2",
                String.class)).contains("Frodo Baggins");
    }
}
