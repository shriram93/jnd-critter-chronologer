package com.udacity.jdnd.course3.critter.user.repository;

import com.udacity.jdnd.course3.critter.user.data.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
    List<User> findAll();
}
