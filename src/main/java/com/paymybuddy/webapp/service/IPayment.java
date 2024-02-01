package com.paymybuddy.webapp.service;

import com.paymybuddy.webapp.domain.model.Transaction;

public interface IPayment {
          void pay (String emailCreditUser, String emailBeneficiaryUser,double balanceCalculatedCreditUser,double balanceCalculatedBeneficiaryUser,  double amount, String description);
}
