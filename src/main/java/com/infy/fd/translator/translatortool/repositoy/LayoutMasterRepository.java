package com.infy.fd.translator.translatortool.repositoy;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.infy.fd.translator.translatortool.model.LayoutMaster;

public interface LayoutMasterRepository extends JpaRepository<LayoutMaster, Integer>{

	public List<LayoutMaster> findByClientName(String client);
}

