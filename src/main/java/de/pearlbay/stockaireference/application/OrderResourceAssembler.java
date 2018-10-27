package de.pearlbay.stockaireference.application;

import de.pearlbay.stockaireference.controller.OrderController;
import de.pearlbay.stockaireference.domain.business.Order;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;


@Component
public class OrderResourceAssembler implements ResourceAssembler<Order,Resource<Order>> {
    @Override
    public Resource<Order> toResource(Order order) {
        Resource<Order> orderResource = new Resource<>(order,
                linkTo(methodOn(OrderController.class).one(order.getId())).withSelfRel(),
                linkTo(methodOn(OrderController.class).all()).withRel("orders")
        );

        return orderResource;
    }
}
