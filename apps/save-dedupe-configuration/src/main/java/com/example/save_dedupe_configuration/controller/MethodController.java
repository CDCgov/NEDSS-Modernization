package com.example.save_dedupe_configuration.controller;

import com.example.save_dedupe_configuration.model.Method;
import com.example.save_dedupe_configuration.service.MethodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api/methods")
public class MethodController {

    private final MethodService methodService;

    @Autowired
    public MethodController(MethodService methodService) {
        this.methodService = methodService;
    }

    @PostMapping
    public ResponseEntity<Method> createMethod(@RequestBody Method method) {
        Method savedMethod = methodService.saveMethod(method);
        return ResponseEntity.ok(savedMethod);
    }

    @GetMapping
    public ResponseEntity<List<Method>> getAllMethods() {
        List<Method> methods = methodService.getAllMethods();
        return ResponseEntity.ok(methods);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Method> getMethodById(@PathVariable Long id) {
        return methodService.getMethodById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Method> updateMethod(@PathVariable Long id, @RequestBody Method method) {
        Method updatedMethod = methodService.updateMethod(id, method);
        return ResponseEntity.ok(updatedMethod);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMethod(@PathVariable Long id) {
        methodService.deleteMethod(id);
        return ResponseEntity.noContent().build();
    }
}
