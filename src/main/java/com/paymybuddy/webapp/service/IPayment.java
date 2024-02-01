package com.paymybuddy.webapp.service;

import com.paymybuddy.webapp.domain.model.Transaction;

public interface IPayment {
          void pay (String emailCreditUser, String emailBeneficiaryUser, double amount, String description,
      			Transaction transactionCreated);
}
