package de.pearlbay.stockaireference.controller;

import de.pearlbay.stockaireference.application.OrderNotFoundException;
import de.pearlbay.stockaireference.application.OrderResourceAssembler;
import de.pearlbay.stockaireference.domain.business.Order;
import de.pearlbay.stockaireference.repository.OrderRepository;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;


@RestController
public class OrderController {
    
    final OrderRepository orderRepository;
    final OrderResourceAssembler orderResourceAssembler;


    public OrderController(OrderRepository orderRepository, OrderResourceAssembler orderResourceAssembler) {
        this.orderRepository = orderRepository;
        this.orderResourceAssembler = orderResourceAssembler;
    }
    
    @GetMapping("/orders")
    public Resources<Resource<Order>>all(){
        
        List<Resource<Order>> orders =  orderRepository.findAll().stream()
                .map(order -> orderResourceAssembler.toResource(order))
                .collect(Collectors.toList());
        
        return new Resources<>(orders,  linkTo(methodOn(OrderController.class).all()).withSelfRel() );
    }
    
    @GetMapping("/orders/{id}")
    public Resource<Order> one(@PathVariable Long id) {
        Order order = orderRepository.findById(id).orElseThrow(() -> new OrderNotFoundException(id));
        return orderResourceAssembler.toResource(order) ;
    }
}
