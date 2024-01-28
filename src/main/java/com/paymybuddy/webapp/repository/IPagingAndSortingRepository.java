package com.paymybuddy.webapp.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.CrudRepository;

public interface IPagingAndSortingRepository<T,ID>  extends CrudRepository<T,ID>{
	 Iterable<T> findAll(Sort sort);
	 Page<T> findAll(Pageable pageable);

}