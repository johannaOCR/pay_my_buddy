package com.paymybuddy.PayMyBuddy.service;

import com.paymybuddy.PayMyBuddy.model.User;
import com.paymybuddy.PayMyBuddy.model.Wallet;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.security.Principal;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
public class WalletServiceTest {
    @InjectMocks
    private WalletService walletService;

    @Mock
    private UserService userService;

    @Mock
    private Principal principal;

    @Test
    void testSupplyWalletBalance() {
        User user = new User();
        user.setFirstname("testFN");
        user.setLastname("testLN");
        user.setPassword("testPW");
        user.setEmail("testEM@mail.com");
        user.setUserId(1);

        Wallet wallet = new Wallet();
        wallet.setBalance(50);
        wallet.setWalletId(1);
        wallet.setUser(user);
        user.setWallet(wallet);

        float amount = 12f;

        when(userService.getUserByEmail(any())).thenReturn(user);
        when(principal.getName()).thenReturn(user.getEmail());

        boolean response = walletService.supplyWalletBalance(principal, amount);
        assertThat(response).isTrue();
    }
}
