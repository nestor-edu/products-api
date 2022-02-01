package com.neim.app.controllers;

import com.neim.app.models.entity.Producto;
import com.neim.app.service.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
public class ProductoController {

    @Autowired
    protected ProductoService service;

    @GetMapping
    public ResponseEntity<?> listar() {
        return ResponseEntity.ok().body(service.findAll());
    }

    @GetMapping("/pagina")
    public ResponseEntity<?> listar(Pageable pageable) {
        return ResponseEntity.ok().body(service.findAll(pageable));
    }

    @PostMapping
    public ResponseEntity<?> crear(@RequestBody Producto producto) {
        Producto productoDB = service.save(producto);
        return ResponseEntity.status(HttpStatus.CREATED).body(productoDB);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> editar(@RequestBody Producto producto, @PathVariable Long id) {
        Optional<Producto> p = service.findById(id);

        if (p.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Producto productoDB = p.get();
        productoDB.setNombre(producto.getNombre());
        productoDB.setDescripcion(producto.getDescripcion());
        productoDB.setPrecio(producto.getPrecio());
        return ResponseEntity.status(HttpStatus.CREATED).body(service.save(productoDB));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id) {
        service.deleteById(id);
        return ResponseEntity.noContent().build(); // 204
    }

}
