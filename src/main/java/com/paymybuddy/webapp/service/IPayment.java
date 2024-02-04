package com.paymybuddy.webapp.service;

public interface IPayment{
          void pay (String emailCreditUser, String emailBeneficiaryUser,double amount);
          void pay (String userEmail,double amount,String typeAccountBeneficairyUser);
          void calculBalance(String emailCreditUser, String emailBeneficiaryUser, double amount) throws Exception;
          void calculBalance(String userEmail,  double amount,String typeAccountBeneficairyUser)throws Exception;
        
}