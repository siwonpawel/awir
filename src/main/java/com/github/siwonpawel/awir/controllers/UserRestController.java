package com.github.siwonpawel.awir.controllers;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
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
public class UserRestController implements UserApi
{
    private static final Logger log = LoggerFactory.getLogger(UserRestController.class);

    private final UserService userService;

    @Override
    public ResponseEntity<?> addUser(User user, MultipartFile file)
    {
        return ResponseEntity.ok(userService.save(user, file));
    }

    @Override
    public ResponseEntity<?> modifyUser(User user, Long id)
    {
        return ResponseEntity.ok(userService.modifyUser(id, user));
    }

    @Override
    public ResponseEntity<?> deleteUser(Long id)
    {
        userService.deleteById(id);
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<?> findByName(String name)
    {

        if (StringUtils.isBlank(name))
        {
            return ResponseEntity.ok(userService.findAll());
        }

        return ResponseEntity.ok(userService.findByName(name));

    }

    @Override
    public ResponseEntity<?> deleteByName(String name)
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

