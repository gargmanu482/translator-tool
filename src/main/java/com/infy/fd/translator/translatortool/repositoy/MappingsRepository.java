package com.infy.fd.translator.translatortool.repositoy;

import org.springframework.data.jpa.repository.JpaRepository;

import com.infy.fd.translator.translatortool.model.Mapping;

public interface MappingsRepository extends JpaRepository<Mapping, Integer> {

}
