package com.midas.app.activities;

import com.midas.app.models.Account;
import com.midas.app.providers.external.stripe.StripePaymentProvider;
import com.midas.app.providers.payment.CreateAccount;
import com.midas.app.repositories.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RequiredArgsConstructor
public class AccountActivityImpl implements AccountActivity {

  private final Logger logger = LoggerFactory.getLogger(AccountActivityImpl.class);

  private final AccountRepository accountRepository;

  private final StripePaymentProvider paymentProvider;

  @Override
  public Account saveAccount(Account account) {
    logger.info("Saving account");
    return accountRepository.save(account);
  }

  @Override
  public Account createPaymentAccount(Account account) {
    logger.info("Creating payment account");
    account.setProviderType(paymentProvider.providerName());
    CreateAccount newAccount =
        new CreateAccount(
            account.getId(), account.getFirstName(), account.getLastName(), account.getEmail());
    return paymentProvider.createAccount(newAccount);
  }

  @Override
  public Account updatePaymentAccount(Account account) {
    logger.info("Updating payment account");
    return paymentProvider.updateAccount(account);
  }
}
