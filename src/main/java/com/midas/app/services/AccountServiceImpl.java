package com.midas.app.services;

import com.midas.app.activities.AccountActivityImpl;
import com.midas.app.exceptions.ResourceNotFoundException;
import com.midas.app.models.Account;
import com.midas.app.providers.external.stripe.StripePaymentProvider;
import com.midas.app.repositories.AccountRepository;
import com.midas.app.workflows.CreateAccountWorkflow;
import com.midas.app.workflows.CreateAccountWorkflowImpl;
import com.midas.app.workflows.UpdateAccountWorkflow;
import com.midas.app.workflows.UpdateAccountWorkflowImpl;
import io.temporal.client.WorkflowClient;
import io.temporal.client.WorkflowOptions;
import io.temporal.worker.Worker;
import io.temporal.worker.WorkerFactory;
import io.temporal.workflow.Workflow;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {
  private final Logger logger = Workflow.getLogger(AccountServiceImpl.class);

  private final WorkflowClient workflowClient;

  private final AccountRepository accountRepository;

  private final StripePaymentProvider stripePaymentProvider;

  private WorkerFactory newWorkerFactory(WorkflowClient client) {
    // Create a worker that listens to the task queue
    WorkerFactory factory = WorkerFactory.newInstance(client);
    return factory;
  }

  private WorkflowOptions createWorkflowOptions(String id, String queueName) {
    return WorkflowOptions.newBuilder().setTaskQueue(queueName).setWorkflowId(id).build();
  }

  /**
   * createAccount creates a new account in the system or provider.
   *
   * @param details is the details of the account to be created.
   * @return Account
   */
  @Override
  public Account createAccount(Account details) {

    WorkerFactory factory = newWorkerFactory(workflowClient);

    Worker worker = factory.newWorker(CreateAccountWorkflow.QUEUE_NAME);
    // Register workflow and activity implementations
    worker.registerWorkflowImplementationTypes(CreateAccountWorkflowImpl.class);
    worker.registerActivitiesImplementations(
        new AccountActivityImpl(accountRepository, stripePaymentProvider));
    // Start listening to the task queue
    factory.start();

    var options =
        createWorkflowOptions("create-" + details.getEmail(), CreateAccountWorkflow.QUEUE_NAME);

    logger.info("initiating workflow to create account for email: {}", details.getEmail());

    var workflow = workflowClient.newWorkflowStub(CreateAccountWorkflow.class, options);

    return workflow.createAccount(details);
  }

  /**
   * getAccounts returns a list of accounts.
   *
   * @return List<Account>
   */
  @Override
  public List<Account> getAccounts() {
    return accountRepository.findAll();
  }

  @Override
  public Account updateAccount(Account account) {

    Account currAccount =
        accountRepository.findById(account.getId()).orElseThrow(ResourceNotFoundException::new);

    currAccount.setEmail(account.getEmail());
    currAccount.setFirstName(account.getFirstName());
    currAccount.setLastName(account.getLastName());

    WorkerFactory factory = newWorkerFactory(workflowClient);

    Worker worker = factory.newWorker(UpdateAccountWorkflow.QUEUE_NAME);
    // Register workflow and activity implementations
    worker.registerWorkflowImplementationTypes(UpdateAccountWorkflowImpl.class);
    worker.registerActivitiesImplementations(
        new AccountActivityImpl(accountRepository, stripePaymentProvider));
    // Start listening to the task queue
    factory.start();

    var options =
        createWorkflowOptions("update-" + account.getEmail(), UpdateAccountWorkflow.QUEUE_NAME);

    logger.info("initiating workflow to update account for email: {}", account.getEmail());

    var workflow = workflowClient.newWorkflowStub(UpdateAccountWorkflow.class, options);

    return workflow.updateAccount(account);
  }
}
