package de.pearlbay.stockaireference.application;

import de.pearlbay.stockaireference.domain.business.Order;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceAssembler;

public class OrderResourceAssembler implements ResourceAssembler<Order,Resource<Order>> {
    @Override
    public Resource<Order> toResource(Order order) {
        return null;
    }
}
