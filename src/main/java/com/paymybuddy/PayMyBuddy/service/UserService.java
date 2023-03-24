package com.paymybuddy.PayMyBuddy.service;

import com.paymybuddy.PayMyBuddy.dto.ContactDTO;
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

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@DynamicUpdate
@Service
public class UserService implements UserDetailsService {

    private static final Logger logger = LogManager.getLogger("UserService");

    @Autowired
    UserRepository userRepository;

    /**
     * Save a Given user in the DB and check process
     *
     * @param user
     * @return true if the user saved, false if is not
     */
    @Transactional
    public boolean addUser(User user) {
        logger.info("adding a new user");
        if (userRepository.findByEmail(user.getEmail()) == null) {
            user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
            user = userRepository.save(user);
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

    public boolean updateProfil(String firstname, String lastname, String iban, String bic, Principal principal) {
        boolean isUpdated = false;

        User user = this.getUserByEmail(principal.getName());
        if (!Objects.equals(lastname, "") || !Objects.equals(firstname, "") || !Objects.equals(bic, "") || !Objects.equals(iban, "")) {
            if (!Objects.equals(lastname, "")) {
                user.setLastname(lastname);
                isUpdated = true;
                logger.info("lastname updated : " + lastname);
            }
            if (!Objects.equals(firstname, "")) {
                user.setFirstname(firstname);
                isUpdated = true;
                logger.info("firstname updated : " + firstname);
            }
            if (!Objects.equals(bic, "") && bic != null) {
                user.getWallet().getBankAccounts().setBic(bic);
                isUpdated = true;
                logger.info("bic updated : " + bic);
            }
            if (!Objects.equals(iban, "") && iban != null) {
                user.getWallet().getBankAccounts().setIban(iban);
                isUpdated = true;
                logger.info("iban updated : " + iban);
            }
            this.saveUser(user);
        }
        return isUpdated;
    }

    /**
     * Return an Optional User by a given ID
     *
     * @param id the user id to look for
     * @return Optional<User>
     */
    public Optional<User> getUserById(Integer id) {
        return userRepository.findById(id);
    }

    /**
     * Save a Given User in DB
     *
     * @param user the user object to save in DB
     * @return User saved
     */
    @Transactional
    public User saveUser(User user) {
        return userRepository.save(user);
    }

    /**
     * Delete a given User in DB
     *
     * @param user return void
     */
    public void deleteUser(User user) {
        userRepository.delete(user);
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

    public User getUserByEmail(String email) {
        logger.info("getting user by email : " + email);
        return userRepository.findByEmail(email);
    }

    @Transactional
    public List<ContactDTO> getContactsByUser(Principal principal) {
        User user = this.getUserByEmail(principal.getName());
        List<User> contacts = user.getContacts();
        List<ContactDTO> contactsDTO = new ArrayList<>();
        contacts.forEach(contact -> {
            contactsDTO.add(
                    new ContactDTO(
                            contact.getFirstname(),
                            contact.getLastname(),
                            contact.getEmail()
                    )
            );
        });
        return contactsDTO;
    }
}
