package com.infy.fd.translator.translatortool.repositoy;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.infy.fd.translator.translatortool.model.MappingRules;

@Repository
public interface MappingRulesRepository extends JpaRepository<MappingRules, String>{
	
	public MappingRules findByFieldTag(String input);
}
