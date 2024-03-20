package com.smart.DAO;



import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.query.Param;

import com.smart.entities.contact;

@EnableJpaRepositories
public interface ContactRepository extends JpaRepository<contact, Integer> {
	
	@Query("select c from contact c where c.user.id=:id")
	public Page<contact> findById1(@Param("id") int id,Pageable pagable);
}
