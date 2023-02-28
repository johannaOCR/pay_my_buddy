package com.paymybuddy.PayMyBuddy.service;

import com.paymybuddy.PayMyBuddy.model.User;
import com.paymybuddy.PayMyBuddy.repository.UserRepository;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@DynamicUpdate
@Service
public class UserService {
    @Autowired
    UserRepository userRepository;

    /**
     * Return all users in DB
     * @return Iterable<User>
     */
    public Iterable<User> getUsers(){
        return userRepository.findAll();
    }

    /**
     * Return an Optional User by a given ID
     * @param id
     * @return Optional<User>
     */
    public Optional<User> getUserById(Integer id){
        return userRepository.findById(id);
    }

    /**
     * Save a Given User in DB
     * @param user
     * @return User saved
     */
    public User saveUser(User user) {
        return userRepository.save(user);
    }

    /**
     * Delete a given User in DB
     * @param user
     * return void
     */
    public void deleteUser(User user){
        userRepository.delete(user);
    }

    /**
     * Delete all User in DB
     *
     */
    private void deleteAllUsers(){
        userRepository.deleteAll();
    }

    /**
     * Delete User by a ID given
     * @param id
     * return void
     */
    public void deleteUserById(Integer id){
        userRepository.deleteById(id);
    }

}
