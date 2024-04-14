package com.github.siwonpawel.awir.services;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.github.siwonpawel.awir.domain.User;
import com.github.siwonpawel.awir.repositories.UserRepository;
import com.github.siwonpawel.awir.services.exceptions.EntityNotExistingException;
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

    public User save(User user, MultipartFile file)
    {
        if (file != null && !file.isEmpty())
        {
            fileService.store(file);
        }
        else if (user.getImage() != null)
        {
            var orgImage = userRepository.findById(user.getId())
                    .map(User::getImage)
                    .orElse(null);

            user.setImage(orgImage);
        }

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

    public List<User> findByName(String name)
    {
        return userRepository.findByName(name);
    }

    public void deleteByName(String name)
    {
        userRepository.deleteByName(name);
    }

    public User modifyUser(Long id, User user)
    {
        if (!userRepository.existsById(id))
        {
            throw new EntityNotExistingException(user.getId());
        }

        return userRepository.save(user);
    }
}
