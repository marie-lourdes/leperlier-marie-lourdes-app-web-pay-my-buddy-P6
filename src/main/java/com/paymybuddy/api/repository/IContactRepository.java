package com.paymybuddy.api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.paymybuddy.api.domain.model.Contact;

public interface IContactRepository extends JpaRepository<Contact, String>{
	
	/*@Transactional
	@Modifying//annotation pour indiquer a JPA qu il s agit d une requete PUT
	@Query("insert into Contact c  set c.user=?1 where c.email =?2")	
	 public void setContacts(UserApp user, String email );*/
	
	
	/*@Query("SELECT * FROM paymybuddy_test.user_app JOIN contacts ON (user_app.email=contacts.user_id )WHERE(user_app.email='testuser2@gmail.com')")
	public List<Contact> findAll(String idContact);*/
	
}
