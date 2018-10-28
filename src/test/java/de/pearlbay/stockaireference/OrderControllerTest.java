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
public class OrderControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;


    @Test
    public void shouldReturnAllOrders() {
        assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/orders",
                String.class)).contains("Description order 1");
    }

    @Test
    public void shouldReturnOneOrder() {
        assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/orders/3",
                String.class)).contains("Description order 1");
    }

    @Test
    public void shouldChangeOrderStatus() {
        assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/orders/3/cancel",
                String.class)).contains("CANCELLED");
    }
}
