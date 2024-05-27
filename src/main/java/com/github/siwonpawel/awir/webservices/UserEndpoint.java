package com.github.siwonpawel.awir.webservices;

import java.util.List;

import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import com.github.siwonpawel.awir.services.UserService;
import lombok.RequiredArgsConstructor;

@Endpoint
@RequiredArgsConstructor
public class UserEndpoint
{
    private static final String NAMESPACE_URI = "http://www.zut.edu.pl/springsoap/gen";

    private final UserService userService;

    @ResponsePayload
    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getUserRequest")
    public GetUserResponse getUser(@RequestPayload GetUserRequest request)
    {

        GetUserResponse response = new GetUserResponse();
        response.getUser().addAll(map(userService.findUserByName(request.getName())));

        return response;
    }

    private List<User> map(List<com.github.siwonpawel.awir.domain.User> users)
    {
        return users.stream()
                .map(u -> {
                    var usr = new User();
                    usr.setName(u.getName());
                    usr.setEmail(u.getEmail());

                    return usr;
                }).toList();
    }
}
