package com.paymybuddy.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.paymybuddy.api.domain.model.Contact;

public interface IContactRepository extends JpaRepository<Contact, String>{
	
	/*@Transactional
	@Modifying//annotation pour indiquer a JPA qu il s agit d une requete PUT
	@Query("insert into Contact c  set c.user=?1 where c.email =?2")	
	 public void setContacts(UserApp user, String email );*/
	
	/*@Transactional
	 @Query("SELECT Contact.id_contact FROM UserApp JOIN Contact ON( UserApp.email=Contact.user_id ) WHERE (UserApp.email=?1)")
		public  Contact  findByUser(String email);
	//public List<Contact>findByUser(String id);*/
}
