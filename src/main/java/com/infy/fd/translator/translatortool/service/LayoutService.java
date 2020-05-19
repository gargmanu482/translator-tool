package com.infy.fd.translator.translatortool.service;

import java.util.List;
import java.util.Optional;

import com.infy.fd.translator.translatortool.model.Layout;

public interface LayoutService {

	public Layout saveLayout(Layout layout) throws Exception;

	public Optional<Layout> findLayoutById(String id) throws Exception;

	public List<String> getAllClient() throws Exception;

	public List<Layout> findLayoutByClient(String client) throws Exception;

}
