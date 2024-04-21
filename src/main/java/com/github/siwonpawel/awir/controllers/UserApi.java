package com.github.siwonpawel.awir.controllers;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.github.siwonpawel.awir.domain.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

public interface UserApi
{
    @PostMapping
    @Tag(name = "user", description = "the user API")
    @Operation(summary = "Add a new user", description = "Add a new user to the system")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "successful operation",
                    content = {
                            @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = User.class)
                            ),
                            @Content(
                                    mediaType = MediaType.APPLICATION_XML_VALUE,
                                    schema = @Schema(implementation = User.class)
                            )
                    }
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid user",
                    content = @Content
            )
    })
    ResponseEntity<?> addUser(@Parameter(description = "User entity to be saved.") @RequestBody @Valid User user, @Parameter(description = "File containing an Avatar of the user") @RequestParam(required = false) MultipartFile file);

    @PutMapping("/{id}")
    @Tag(name = "user", description = "update user by id")
    @Operation(summary = "Update user by given id", description = "Add a new user to the system")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "successful operation",
                    content = {
                            @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = User.class)
                            ),
                            @Content(
                                    mediaType = MediaType.APPLICATION_XML_VALUE,
                                    schema = @Schema(implementation = User.class)
                            )
                    }
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "User not found",
                    content = @Content
            )
    })
    ResponseEntity<?> modifyUser(@RequestBody User user, @PathVariable Long id);

    @DeleteMapping("/{id}")
    @Tag(name = "user", description = "delete user")
    @Operation(summary = "Delete user", description = "Add a new user to the system")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "successful operation",
                    content = {
                            @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = User.class)
                            ),
                            @Content(
                                    mediaType = MediaType.APPLICATION_XML_VALUE,
                                    schema = @Schema(implementation = User.class)
                            )
                    }
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "User not found",
                    content = @Content
            )
    })
    ResponseEntity<?> deleteUser(@PathVariable Long id);

    @GetMapping
    @Tag(name = "user", description = "Get users, optionally by name")
    @Operation(summary = "Get users", description = "Get all users or get all by given name")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "successful operation",
                    content = {
                            @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = User.class)
                            ),
                            @Content(
                                    mediaType = MediaType.APPLICATION_XML_VALUE,
                                    schema = @Schema(implementation = User.class)
                            )
                    }
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "User not found",
                    content = @Content
            )
    })
    ResponseEntity<?> findByName(@Parameter(description = "Name, will be used as a filter to search users") @RequestParam(value = "name", required = false) String name);

    @DeleteMapping
    @Tag(name = "user", description = "delete users by name")
    @Operation(summary = "Delete users by name", description = "Deletes all users by given name")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "successful operation",
                    content = {
                            @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = User.class)
                            ),
                            @Content(
                                    mediaType = MediaType.APPLICATION_XML_VALUE,
                                    schema = @Schema(implementation = User.class)
                            )
                    }
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "User not found",
                    content = @Content
            )
    })
    ResponseEntity<?> deleteByName(@Parameter(description = "Name parameter, will be used to delete users") @RequestParam("name") String name);
}
