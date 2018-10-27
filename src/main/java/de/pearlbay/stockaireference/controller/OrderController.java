package de.pearlbay.stockaireference.controller;

import de.pearlbay.stockaireference.application.OrderNotFoundException;
import de.pearlbay.stockaireference.application.OrderResourceAssembler;
import de.pearlbay.stockaireference.domain.business.Order;
import de.pearlbay.stockaireference.domain.business.Status;
import de.pearlbay.stockaireference.repository.OrderRepository;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.hateoas.Resources;
import org.springframework.hateoas.VndErrors;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    
    
    @GetMapping("/orders/{id}/cancel")
    public ResponseEntity<ResourceSupport> cancel(@PathVariable long id){
        
        Order order = orderRepository.findById(id).orElseThrow(() -> new OrderNotFoundException(id));

        if(order.getStatus() == Status.IN_PROGRESS){
          order.setStatus(Status.CANCELLED);
          return ResponseEntity.ok(orderResourceAssembler.toResource(orderRepository.save(order)));
        }
        
        return ResponseEntity
                .status(HttpStatus.METHOD_NOT_ALLOWED)
                .body(new VndErrors.VndError("Method not allowed", "You can't cancel an order that is in the " + order.getStatus() + " status"));
    }

    @GetMapping("/orders/{id}/complete")
    public ResponseEntity<ResourceSupport> complete(@PathVariable long id){

        Order order = orderRepository.findById(id).orElseThrow(() -> new OrderNotFoundException(id));

        if(order.getStatus() == Status.IN_PROGRESS){
            order.setStatus(Status.COMPLETED);
            return ResponseEntity.ok(orderResourceAssembler.toResource(orderRepository.save(order)));
        }

        return ResponseEntity
                .status(HttpStatus.METHOD_NOT_ALLOWED)
                .body(new VndErrors.VndError("Method not allowed", "You can't complete an order that is in the " + order.getStatus() + " status"));
    }
    
    @PostMapping("/orders")
    ResponseEntity<Resource<Order>> newOrder(@RequestBody Order order) {

        order.setStatus(Status.IN_PROGRESS);
        Order newOrder = orderRepository.save(order);

        return ResponseEntity
                .created(linkTo(methodOn(OrderController.class).one(newOrder.getId())).toUri())
                .body(orderResourceAssembler.toResource(newOrder));
    }
}
