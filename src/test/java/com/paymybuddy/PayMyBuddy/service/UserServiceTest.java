package com.paymybuddy.PayMyBuddy.service;
import com.paymybuddy.PayMyBuddy.dto.ContactDTO;
import com.paymybuddy.PayMyBuddy.model.BankAccount;
import com.paymybuddy.PayMyBuddy.model.User;
import com.paymybuddy.PayMyBuddy.model.Wallet;
import com.paymybuddy.PayMyBuddy.repository.UserRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.*;
import static org.mockito.ArgumentMatchers.any;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
public class UserServiceTest {

    @InjectMocks
    private UserService userService;
    @Mock
    private UserRepository userRepository;

    @Mock
    private Principal principal;

    private static final Logger logger = LogManager.getLogger("UserServiceTest");
    @Test
    public void testAddUser()  {
        User user = new User();
        user.setFirstname("testFN");
        user.setLastname("testLN");
        user.setPassword("testPW");
        user.setEmail("testEM@mail.com");
        user.setUserId(1);

        when(userRepository.save(any())).thenReturn(user);
        boolean response = userService.addUser(user);
        verify(userRepository,times(1)).save(any());
        assertThat(response).isTrue();
    }

    @Test
    void testGetUserByEmail() {
        User user = new User();
        user.setFirstname("testFN");
        user.setLastname("testLN");
        user.setPassword("testPW");
        user.setEmail("testEM@mail.com");
        user.setUserId(1);

        when(userRepository.findByEmail(user.getEmail())).thenReturn(user);
        User result = userService.getUserByEmail(user.getEmail());
        assertThat(result.getLastname()).isEqualTo(user.getLastname());
    }

    @Test
    void testGetUserById(){
        User user = new User();
        user.setFirstname("testFN");
        user.setLastname("testLN");
        user.setPassword("testPW");
        user.setEmail("testEM@mail.com");
        user.setUserId(1);

        when (userRepository.findById(any())).thenReturn(Optional.of(user));
        Optional<User> result = userService.getUserById(1);
        assertThat(result).isNotNull();
    }
    @Test
    void testLoadUserByUsername(){
        User user = new User();
        user.setFirstname("testFN");
        user.setLastname("testLN");
        user.setPassword("testPW");
        user.setEmail("testEM@mail.com");
        user.setUserId(1);

        when (userRepository.findByEmail(any())).thenReturn(user);
        UserDetails result = userService.loadUserByUsername(user.getEmail());
        assertThat(result).isNotNull();
    }

    @Test
    void testGetContactsByUser(){
        User user = new User();
        user.setFirstname("testFN");
        user.setLastname("testLN");
        user.setPassword("testPW");
        user.setEmail("testEM@mail.com");
        user.setUserId(1);

        User contact = new User();
        contact.setFirstname("contacttestFN");
        contact.setLastname("contacttestLN");
        contact.setPassword("contacttestPW");
        contact.setEmail("contacttestEM@mail.com");
        contact.setUserId(2);

        user.addContact(contact);

        when(userRepository.findByEmail(any())).thenReturn(user);
        when(principal.getName()).thenReturn("testEM@mail.com");

        List<ContactDTO> listContactDTO;
        listContactDTO = userService.getContactsByUser(principal);

        assertThat(listContactDTO.get(0).getLastname()).isEqualTo(contact.getLastname());
    }

    @Test
    void testSaveUser(){
        User user = new User();
        user.setFirstname("testFN");
        user.setLastname("testLN");
        user.setPassword("testPW");
        user.setEmail("testEM@mail.com");
        user.setUserId(1);

        when(userRepository.save(any())).thenReturn(user);
        userService.saveUser(user);
        verify(userRepository,times(1)).save(any());
    }

    @Test
    void testDeleteUser(){
        User user = new User();
        user.setFirstname("testFN");
        user.setLastname("testLN");
        user.setPassword("testPW");
        user.setEmail("testEM@mail.com");
        user.setUserId(1);

        userService.deleteUser(user);
        verify(userRepository,times(1)).delete(user);
    }

    @Test
    void testUpdateProfile(){
        User user = new User();
        user.setFirstname("testFN");
        user.setLastname("testLN");
        user.setPassword("testPW");
        user.setEmail("testEM@mail.com");
        user.setUserId(1);

        Wallet wallet = new Wallet();
        wallet.setUser(user);
        wallet.setBalance(50);
        wallet.setWalletId(1);
        user.setWallet(wallet);

        BankAccount bankAccount = new BankAccount();
        bankAccount.setWallet(wallet);
        bankAccount.setIban("testIBAN");
        bankAccount.setBic("testBIC");
        bankAccount.setBankAccountId(1);
        wallet.setBankAccounts(bankAccount);


        when(userRepository.findByEmail(any())).thenReturn(user);
        when(principal.getName()).thenReturn("testEM@mail.com");

        boolean response = userService.updateProfil("newFirstname", "newLastname","","TEST",principal);
        assertThat(response).isTrue();
    }
}

