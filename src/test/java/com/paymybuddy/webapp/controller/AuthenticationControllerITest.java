package com.paymybuddy.webapp.controller;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.ActiveProfiles;

//@AutoConfigureJsonTesters

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
        mvc.perform(get("/home").contentType(MediaType.APPLICATION_JSON))
          .andExpect(status().isOk());
    }
    
    @WithMockUser("testuser1@gmail.com")
    @Test
    public void givenAdminAuthenticated_whenRequestHomePage_shouldReturn200() throws Exception {
        mvc.perform(get("/home").contentType(MediaType.APPLICATION_JSON))
          .andExpect(status().isOk());
    }
    
    @WithMockUser("testuser2@gmail.com")
    @Test
    public void givenUserAuthenticatedAndNoAuthorized_whenRequestTransactionBillingPage_shouldReturn403() throws Exception {
        mvc.perform(get("/home/transactions-billing").contentType(MediaType.APPLICATION_JSON))
          .andExpect(status().isForbidden());
    }*/
    
 /* @WithMockUser(username="testuser1@gmail.com",password="adminbuddy")
    @Test
    public void givenAdminAuthenticatedAndAuthorized_whenRequestTransactionBillingPage_shouldReturn200() throws Exception {
	  MockHttpServletResponse result = mockMvc.perform(MockMvcRequestBuilders.post("/person")
				.contentType(MediaType.APPLICATION_JSON).content(jsonPerson.write(personCreated).getJson()))
				.andReturn().getResponse();
    }*/
    
  /*  @WithMockUser(username="testuser1@gmail.com",password="adminbuddy")
    @Test
    public void givenAdminAuthenticatedAndAuthorized_whenRequestTransactionBillingPage_shouldReturn403() throws Exception{
    	   MockHttpServletResponse result = mvc.perform(MockMvcRequestBuilders.get("/home/transactions-billing")).andReturn().getResponse();
   		assertEquals(HttpStatus.OK, result.getStatus());
    }*/

   /* @WithMockUser("testuser1@gmail.com")
    @Test
    public void givenAdminAuthenticatedAndAuthorized_whenRequestTransactionBillingPage_shouldReturn403() throws Exception {
        mvc.perform(get("/home/contact").contentType(MediaType.APPLICATION_JSON))
          .andExpect(status().isOk());
    }*/
    
    }
