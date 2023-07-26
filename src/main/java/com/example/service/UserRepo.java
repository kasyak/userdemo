package com.example.service;

import com.example.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepo extends CrudRepository<User, Long> {
    @Override
    public Optional<User> findById(Long id);

    @Override
    public List<User> findAll() ;
}
