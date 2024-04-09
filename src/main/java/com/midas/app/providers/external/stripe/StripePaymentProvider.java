package com.midas.app.providers.external.stripe;

import com.midas.app.exceptions.ApiException;
import com.midas.app.models.Account;
import com.midas.app.models.ProviderTypeEnum;
import com.midas.app.providers.payment.CreateAccount;
import com.midas.app.providers.payment.PaymentProvider;
import com.stripe.exception.StripeException;
import com.stripe.model.Customer;
import com.stripe.net.RequestOptions;
import com.stripe.param.CustomerCreateParams;
import com.stripe.param.CustomerUpdateParams;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Getter
public class StripePaymentProvider implements PaymentProvider {
  private final Logger logger = LoggerFactory.getLogger(StripePaymentProvider.class);

  private final StripeConfiguration configuration;

  private RequestOptions requestOptions() {
    return RequestOptions.builder().setApiKey(configuration.getApiKey()).build();
  }

  /** providerName is the name of the payment provider */
  @Override
  public ProviderTypeEnum providerName() {
    return ProviderTypeEnum.STRIPE;
  }

  /**
   * createAccount creates a new account in the payment provider.
   *
   * @param details is the details of the account to be created.
   * @return Account
   * @throws ApiException
   */
  @Override
  public Account createAccount(CreateAccount details) {

    logger.info("Creating stripe payment account for {}" + details);

    Account acc = new Account();

    acc.setFirstName(details.getFirstName());
    acc.setLastName(details.getLastName());
    acc.setEmail(details.getEmail());
    acc.setId(details.getUserId());

    RequestOptions options = requestOptions();

    CustomerCreateParams params =
        CustomerCreateParams.builder()
            .setName(details.getFirstName() + " " + details.getLastName())
            .setEmail(details.getEmail())
            .build();

    try {
      Customer customer = Customer.create(params, options);
      logger.info("Customer created: {}" + customer);
      acc.setProviderId(customer.getId());
      acc.setProviderType(providerName());
    } catch (StripeException e) {
      e.printStackTrace();
      logger.error("Error creating customer {}: {}", details.getEmail(), e.getMessage());
      throw new ApiException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
    }
    return acc;
  }

  /**
   * Updates the customer information
   * @param details the customer information
   * @return Account
   * @throws ApiException
   */
  @Override
  public Account updateAccount(Account details) {

    logger.info("Updating account {}: {}", details.getEmail(), details);

    RequestOptions options = requestOptions();

    try {
      Customer resource = Customer.retrieve(details.getProviderId(), options);
      CustomerUpdateParams params =
          CustomerUpdateParams.builder()
              .setEmail(details.getEmail())
              .setName(details.getFirstName() + " " + details.getLastName())
              .build();
      Customer customer = resource.update(params, options);
      // If the customer contains some confidential information, will have to redact it from logs.
      logger.info("Updated customer {}: {}", details.getEmail(), customer);
    } catch (StripeException e) {
      e.printStackTrace();
      logger.error("Error getting customer {}: {}", details.getEmail(), e);
      throw new ApiException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
    }
    return details;
  }
}
