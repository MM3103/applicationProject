package my.pr.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import my.pr.model.Order;
import my.pr.service.OrderService;
import org.keycloak.adapters.springsecurity.token.KeycloakAuthenticationToken;
import org.springdoc.api.OpenApiResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;


@RestController
@SecurityRequirement(name = "bearer-key")
@ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Successfully updated schema"),
        @ApiResponse(responseCode = "404", description = "Schema not found"),
        @ApiResponse(responseCode = "401", description = "Access error"),
        @ApiResponse(responseCode = "403", description = "Access error"),
        @ApiResponse(responseCode = "400", description = "Missing or invalid request body"),
        @ApiResponse(responseCode = "500", description = "Internal error")})
public class OrderController {

    @Autowired
    OrderService service;

    @GetMapping("/adm")
    @Operation(summary = "Get all orders")
    public List<Order> getAllOrders() {
        return service.getAll();
    }

    @GetMapping("/adm/{id}")
    @Operation(summary = "Get order by id")
    public ResponseEntity<Order> getOrder(@PathVariable(value = "id") UUID id) throws OpenApiResourceNotFoundException {
        return ResponseEntity.ok().body(service.get(id));
    }

    @GetMapping("/all")
    @Operation(summary = "Get all orders by email")
    public List<Order> getOrdersByEmail(KeycloakAuthenticationToken authentication) {
        return service.getByEmail(authentication);
    }

    @PostMapping("/all")
    @Operation(summary = "Add new order")
    public Order addOrder(@RequestBody Order newOrder, KeycloakAuthenticationToken authentication) {
        return service.add(newOrder, authentication);
    }

    @DeleteMapping("/all/{id}")
    @Operation(summary = "Delete order by id")
    public Map<String, Boolean> deleteBook(@PathVariable UUID id) throws OpenApiResourceNotFoundException {
        service.delete(id);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", true);
        return response;
    }

    @PatchMapping("/all/{id}")
    @Operation(summary = "Update order  by id")
    public Order updateOrder(@PathVariable UUID id, @RequestBody Order newOrder) throws OpenApiResourceNotFoundException {
        return service.update(id, newOrder);
    }
}