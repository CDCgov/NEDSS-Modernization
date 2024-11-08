package com.example.save_dedupe_configuration.service;

import com.example.save_dedupe_configuration.model.Method;
import com.example.save_dedupe_configuration.repository.MethodRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class MethodService {

    private final MethodRepository methodRepository;

    @Autowired
    public MethodService(MethodRepository methodRepository) {
        this.methodRepository = methodRepository;
    }

    public Method saveMethod(Method method) {
        return methodRepository.save(method);
    }

    public List<Method> findAllMethods() {
        return StreamSupport.stream(methodRepository.findAll().spliterator(), false)
                .collect(Collectors.toList());
    }

    public Optional<Method> getMethodById(Long id) {
        return methodRepository.findById(id);
    }

    public Optional<Method> findByName(String methodName) {
        return methodRepository.findByName(methodName);
    }

    public Method updateMethod(Long id, Method updatedMethod) {
        return methodRepository.findById(id)
                .map(method -> {
                    method.setMethodType(updatedMethod.getMethodType());
                    method.setName(updatedMethod.getName());
                    method.setValue(updatedMethod.getValue());
                    return methodRepository.save(method);
                }).orElseThrow(() -> new RuntimeException("Method not found with id: " + id));
    }


    public void deleteMethod(Long id) {
        methodRepository.deleteById(id);
    }
}