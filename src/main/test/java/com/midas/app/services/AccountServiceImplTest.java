package com.midas.app.services;

// import com.midas.app.models.Account;
// import com.midas.app.models.ProviderTypeEnum;
// import com.midas.app.repositories.AccountRepository;
// import java.util.Arrays;
// import java.util.List;
// import java.util.UUID;
// import org.junit.jupiter.api.Test;
// import org.mockito.InjectMocks;
// import org.mockito.Mock;
// import org.mockito.Mockito;
// import org.springframework.boot.test.context.SpringBootTest;

// @SpringBootTest
// class AccountServiceImplTest {
//   @Mock private AccountRepository accountRepository;

//   @InjectMocks private AccountServiceImpl accountService;

  // @Test
  // void testGetAccounts() {
  //   Account account1 =
  //       new Account(
  //           UUID.fromString("0221d0b8-5a4c-4ab3-868b-9506c7b0877d"),
  //           "John",
  //           "Doe",
  //           "john@example.com",
  //           null,
  //           null,
  //           ProviderTypeEnum.valueOf("STRIPE"),
  //           "abc");
  //   Account account2 =
  //       new Account(
  //           UUID.fromString("0221d0b8-5a4c-4ab3-868b-9506c7b0877d"),
  //           "John2",
  //           "Doe2",
  //           "john@example.com",
  //           null,
  //           null,
  //           ProviderTypeEnum.valueOf("STRIPE"),
  //           "abc");
  //   List<Account> expectedAccounts = Arrays.asList(account1, account2);

  //   Mockito.when(accountRepository.findAll()).thenReturn(expectedAccounts);

  //   List<Account> actualAccounts = accountService.getAccounts();

  //   assert actualAccounts != null;
  //   assert actualAccounts.size() == 2;
  //   assert actualAccounts.contains(account1);
  //   assert actualAccounts.contains(account2);
  //   assert true;
  // }
// }
