package com.infy.fd.translator.translatortool.repositoy;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.infy.fd.translator.translatortool.model.Login;

@Repository
public interface LoginRepository extends JpaRepository<Login, String>{
	
	public Login findByName(String name);
	
}
