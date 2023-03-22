package com.paymybuddy.PayMyBuddy.service;

import com.paymybuddy.PayMyBuddy.model.BankAccount;
import com.paymybuddy.PayMyBuddy.model.User;
import com.paymybuddy.PayMyBuddy.model.Wallet;
import com.paymybuddy.PayMyBuddy.repository.BankAccountRepository;
import com.paymybuddy.PayMyBuddy.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.security.Principal;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
public class BankAccountServiceTest {

    @Mock
    private UserService userService;
    @Mock
    private BankAccountRepository bankAccountRepository;

    @InjectMocks
    private BankAccountService bankAccountService;

    @Mock
    private Principal principal;

    @Test
    void testDeleteBankAccount(){
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

        when(userService.getUserByEmail(any())).thenReturn(user);
        when(principal.getName()).thenReturn("testEM@mail.com");
        when(bankAccountRepository.save(bankAccount)).thenReturn(bankAccount);

        boolean response = bankAccountService.deleteBankAccount(principal);
        assertThat(response).isTrue();
    }

    @Test
    void testAddBankAccount(){
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

        when(userService.getUserByEmail(any())).thenReturn(user);
        when(principal.getName()).thenReturn("testEM@mail.com");

        boolean response = bankAccountService.addBankAccount("testIBAN","testBic", principal);

        assertThat(response).isTrue();
    }
}
