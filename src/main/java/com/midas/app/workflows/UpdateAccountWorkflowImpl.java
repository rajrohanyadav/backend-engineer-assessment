package com.midas.app.workflows;

import com.midas.app.activities.AccountActivity;
import com.midas.app.models.Account;
import io.temporal.activity.ActivityOptions;
import io.temporal.workflow.Workflow;
import java.time.Duration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UpdateAccountWorkflowImpl implements UpdateAccountWorkflow {

  private final Logger logger = LoggerFactory.getLogger(UpdateAccountWorkflowImpl.class);

  @Override
  public Account updateAccount(Account details) {

    ActivityOptions actOpts =
        ActivityOptions.newBuilder().setStartToCloseTimeout(Duration.ofSeconds(5)).build();

    logger.info("Account details from workflow: {}", details);
    // start account creation activity
    logger.info("updateAccount");

    details =
        Workflow.newActivityStub(AccountActivity.class, actOpts).updatePaymentAccount(details);

    logger.info("Persist account details: {}", details);

    details = Workflow.newActivityStub(AccountActivity.class, actOpts).saveAccount(details);

    logger.info("end activities");

    return details;
  }
}
