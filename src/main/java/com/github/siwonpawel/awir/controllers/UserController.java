package com.github.siwonpawel.awir.controllers;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.Base64Utils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.github.siwonpawel.awir.domain.User;
import com.github.siwonpawel.awir.services.UserService;
import groovy.util.logging.Slf4j;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Slf4j
@Controller
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController
{

    private static final Logger log = LoggerFactory.getLogger(UserController.class);

    private final UserService userService;

    @GetMapping("")
    public String listUsers(Model model)
    {
        model.addAttribute("users", userService.findAll());

        return "list-users";
    }

    @GetMapping("/add")
    public String addUserForm(Model model)
    {
        model.addAttribute("user", new User());
        return "add-user";
    }

    @PostMapping("/add")
    public String addUserFormPost(
            @RequestParam(required = false) MultipartFile file,
            @ModelAttribute @Valid User user,
            BindingResult bindingResult,
            Model model) throws IOException
    {
        if (bindingResult.hasErrors())
        {
            return "add-user";
        }

        user.setImage(file.getBytes());
        user = userService.save(user, file);

        log.info("Added user: {}: {}", user.getId(), user.getName());
        model.addAttribute("user", user);
        return "redirect:/users/" + user.getId();
    }

    @GetMapping("/{id}")
    public String addUserFormPost(@PathVariable Long id, Model model)
    {
        User user = userService.getById(id);
        model.addAttribute("user", user);
        if (user.getImage() != null)
        {
            model.addAttribute("image", Base64Utils.encodeToString(user.getImage()));
        }
        return "user-details";
    }

    @GetMapping("/{id}/edit")
    public String editUserForm(@PathVariable Long id, Model model)
    {
        model.addAttribute("user", userService.getById(id));
        return "edit-user";
    }

    @PostMapping("/{id}/edit")
    public String editUserFormPost(@ModelAttribute @Valid User user, Model model, @RequestParam(required = false) MultipartFile file, BindingResult bindingResult) throws Exception
    {
        user = userService.save(user, file);
        model.addAttribute("user", user);

        return "user-details";
    }
}

