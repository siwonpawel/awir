package com.github.siwonpawel.awir.controllers;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.github.siwonpawel.awir.controllers.model.ErrorResponse;
import com.github.siwonpawel.awir.domain.User;
import com.github.siwonpawel.awir.services.UserService;
import groovy.util.logging.Slf4j;
import lombok.RequiredArgsConstructor;

@Slf4j
@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserRestController
{
    private static final Logger log = LoggerFactory.getLogger(UserRestController.class);

    private final UserService userService;

    @PostMapping
    public ResponseEntity<?> addUser(@RequestBody User user, @RequestParam(required = false) MultipartFile file)
    {
        return ResponseEntity.ok(userService.save(user, file));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> modifyUser(@RequestBody User user, @PathVariable Long id)
    {
        return ResponseEntity.ok(userService.modifyUser(id, user));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id)
    {
        userService.deleteById(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<?> findByName(@RequestParam(value = "name", required = false) String name)
    {

        if (StringUtils.isBlank(name))
        {
            return ResponseEntity.ok(userService.findAll());
        }

        return ResponseEntity.ok(userService.findByName(name));

    }

    @DeleteMapping
    public ResponseEntity<?> deleteByName(@RequestParam("name") String name)
    {
        userService.deleteByName(name);

        return ResponseEntity.ok().build();
    }

    @ExceptionHandler
    public ResponseEntity<?> handleException(Exception e)
    {
        return ResponseEntity.ok(new ErrorResponse(400, e.getMessage()));
    }
}

