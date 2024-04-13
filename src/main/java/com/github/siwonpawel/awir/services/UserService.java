package com.github.siwonpawel.awir.services;

import java.io.IOException;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.github.siwonpawel.awir.domain.User;
import com.github.siwonpawel.awir.repositories.UserRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService
{
    private final UserRepository userRepository;
    private final FileService fileService;

    public List<User> findAll()
    {
        return userRepository.findAllByOrderById();
    }

    public User save(User user, MultipartFile file) throws IOException
    {
        fileService.store(file);
        return userRepository.save(user);
    }

    public void deleteById(Long id)
    {
        userRepository.findById(id)
                .ifPresent(user -> fileService.deleteFileSilently(user.getFilepath()));

        userRepository.deleteById(id);
    }

    public User getById(Long id)
    {
        return userRepository.getReferenceById(id);
    }
}
