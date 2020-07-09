package com.infy.fd.translator.translatortool.serviceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.infy.fd.translator.translatortool.model.Field;
import com.infy.fd.translator.translatortool.model.Layout;
import com.infy.fd.translator.translatortool.repositoy.FieldRepository;
import com.infy.fd.translator.translatortool.repositoy.LayoutRepository;
import com.infy.fd.translator.translatortool.service.LayoutService;

@Service
public class LayoutServiceImpl implements LayoutService {

	@Autowired
	private LayoutRepository layoutRepository;

	@Autowired
	private FieldRepository fieldRepository;

	@Override
	public Layout saveLayout(Layout layout) throws Exception {
		
		return layoutRepository.save(layout);
	}

	@Override
	public Optional<Layout> findLayoutById(String id) throws Exception {
		return layoutRepository.findById(id);
	}

	@Override
	public List<String> getAllClient() throws Exception {
		return layoutRepository.getAllClient();
	}

	@Override
	public List<Layout> findLayoutByClient(String client) throws Exception {
		List<Layout> layouts = layoutRepository.findLayoutByClient(client);
		/*
		 * for(Layout layout : layouts) {
		 * 
		 * List<Field> fields =
		 * fieldRepository.findFieldByClient(Integer.valueOf(layout.getLayoutId()));
		 * List<Field> fieldValues=new ArrayList<>(); for (Field field : fields) { Field
		 * fieldValue= new Field(); fieldValue.setFieldId(field.getFieldId());
		 * fieldValue.setIdentification(field.getIdentification());
		 * fieldValue.setLayout(layout); fieldValue.setLength(field.getLength());
		 * fieldValue.setName(field.getName());
		 * fieldValue.setTagName(field.getTagName()); fieldValues.add(fieldValue);
		 * 
		 * }
		 * 
		 * layout.setFields(null); }
		 */
		return layouts;
	}
	

}
