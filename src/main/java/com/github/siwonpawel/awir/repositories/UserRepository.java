package com.github.siwonpawel.awir.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.github.siwonpawel.awir.domain.User;
import jakarta.transaction.Transactional;

@Repository
@Transactional
public interface UserRepository extends JpaRepository<User, Long>
{

    List<User> findAllByOrderById();

}
