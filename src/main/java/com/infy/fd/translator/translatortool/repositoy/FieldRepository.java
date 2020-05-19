package com.infy.fd.translator.translatortool.repositoy;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.infy.fd.translator.translatortool.model.Field;

public interface FieldRepository extends JpaRepository<Field, Integer> {
	
	/*
	 * @Query("select l from Field f where f.layout_id.layout_id=?1") public
	 * List<Field> findFieldByClient(Integer layOutId);
	 */

}
