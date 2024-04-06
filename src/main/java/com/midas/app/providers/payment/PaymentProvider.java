package com.midas.app.providers.payment;

import com.midas.app.models.Account;
import com.midas.app.models.ProviderTypeEnum;

public interface PaymentProvider {
  /** providerName is the name of the payment provider */
  ProviderTypeEnum providerName();

  /**
   * createAccount creates a new account in the payment provider.
   *
   * @param details is the details of the account to be created.
   * @return Account
   */
  Account createAccount(CreateAccount details);

  /**
   * updateAccount creates a new account in the payment provider.
   *
   * @param details is the details of the account to be updated.
   * @return Account
   */
  Account updateAccount(Account details);
}
