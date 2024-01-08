package com.paymybuddy.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.paymybuddy.api.domain.model.Contact;

public interface IContactRepository extends JpaRepository<Contact, String> {
	
	/*@Transactional
	@Modifying//annotation pour indiquer a JPA qu il s agit d une requete PUT
	@Query("insert into Contact c  set c.user=?1 where c.email =?2")	
	 public void setContacts(UserApp user, String email );*/
	
	/* @Query("SELECT c.email FROM Contact c  WHERE c.userId= ?1")
		public  Contact  findContactByEmail(String email);*/
	//public List<Contact>findByUser(String id);
}
