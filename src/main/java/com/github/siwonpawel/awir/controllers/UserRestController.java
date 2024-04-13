package com.github.siwonpawel.awir.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.siwonpawel.awir.services.UserService;
import groovy.util.logging.Slf4j;
import lombok.RequiredArgsConstructor;

@Slf4j
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserRestController
{

    private static final Logger log = LoggerFactory.getLogger(UserRestController.class);

    private final UserService userService;

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> listUsers(@PathVariable Long id)
    {
        userService.deleteById(id);
        return ResponseEntity.ok().build();
    }
}

