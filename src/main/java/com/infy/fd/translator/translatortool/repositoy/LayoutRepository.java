package com.infy.fd.translator.translatortool.repositoy;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.infy.fd.translator.translatortool.model.Layout;

public interface LayoutRepository extends JpaRepository<Layout, String> {

	@Query("select distinct(client) from Layout")
	public List<String> getAllClient();
	@Query("select l from Layout l where l.client=?1")
	public List<Layout> findLayoutByClient(String client);

}
