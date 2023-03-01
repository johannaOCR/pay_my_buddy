package com.paymybuddy.PayMyBuddy.service;

import com.paymybuddy.PayMyBuddy.model.User;
import com.paymybuddy.PayMyBuddy.model.Wallet;
import com.paymybuddy.PayMyBuddy.repository.UserRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@DynamicUpdate
@Service
public class UserService implements UserDetailsService {
    private static final Logger logger = LogManager.getLogger("UserService");
    @Autowired
    UserRepository userRepository;

    @Autowired
    WalletService walletService;

    @Transactional
    public boolean addUser(User user) {
        logger.info("adding a new user");
        if(userRepository.findByEmail(user.getEmail())==null) {
            user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));

            Wallet wallet = new Wallet();
            wallet.setBalance(0);
            wallet.setUser(user);
            user.setWallet(wallet);
            userRepository.save(user);

            logger.info("user added");
            return true;
        }
        logger.error("Email address already used for an account");
        return false;
    }

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

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            logger.error("User not found");
            throw new UsernameNotFoundException("User Not Found");
        }
        logger.info(user.getEmail() + " is connecting, with password " +
                user.getPassword());
        return user;
    }

    public User getUserByEmail(String email){
        User user = userRepository.findByEmail(email);
        return user;
    }
}
