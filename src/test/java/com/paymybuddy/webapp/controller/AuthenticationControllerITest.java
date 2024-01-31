package com.paymybuddy.webapp.controller;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)

class AuthenticationControllerITest {
	/*@Autowired
    private WebApplicationContext context;

    private MockMvc mvc;

    @BeforeEach
    public void setup() {
        mvc = MockMvcBuilders
          .webAppContextSetup(context)
          .apply(springSecurity())
          .build();
    }
    @WithMockUser("testuser2@gmail.com")
    @Test
    public void givenUserAuthenticated_whenRequestHomePage_shouldReturn200() throws Exception {
        mvc.perform(get("/account/home").contentType(MediaType.APPLICATION_JSON))
          .andExpect(status().isOk());
    }
    
    @WithMockUser("testuser1@gmail.com")
    @Test
    public void givenAdminAuthenticated_whenRequestHomePage_shouldReturn200() throws Exception {
        mvc.perform(get("/account/home").contentType(MediaType.APPLICATION_JSON))
          .andExpect(status().isOk());
    }
    
    @WithMockUser("testuser2@gmail.com")
    @Test
    public void givenUserAuthenticatedAndNoAuthorized_whenRequestTransactionBillingPage_shouldReturn403() throws Exception {
        mvc.perform(get("/admin/transactions-billing").contentType(MediaType.APPLICATION_JSON))
          .andExpect(status().isForbidden());
    }*/
}
