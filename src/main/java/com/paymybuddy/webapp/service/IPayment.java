package com.paymybuddy.webapp.service;

public interface IPayment{
          void pay (String emailCreditUser, String emailBeneficiaryUser,double amount);
          void calculBalance(String emailCreditUser, String emailBeneficiaryUser, double amount);
}
