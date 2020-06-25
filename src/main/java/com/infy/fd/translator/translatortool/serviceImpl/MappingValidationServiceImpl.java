package com.infy.fd.translator.translatortool.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.infy.fd.translator.translatortool.model.MappingRules;
import com.infy.fd.translator.translatortool.repositoy.MappingRulesRepository;
import com.infy.fd.translator.translatortool.service.MappingValidationService;

@Service
public class MappingValidationServiceImpl implements MappingValidationService {

	@Autowired
	private MappingRulesRepository mappingRepository;

	@Override
	public boolean validateMappings(String input, String output) {
		System.out.println(input);
		MappingRules rules = mappingRepository.findByFieldTag(input.trim());
		
		if(rules!=null) {
			return validate(rules, input, output);
		}else {
			MappingRules rule = mappingRepository.findByFieldTag(output.trim());
			return validate(rule, input, output);
		}
	}

	public boolean validate(MappingRules mr, String input, String output) {
						if ((input.trim().equalsIgnoreCase(mr.getFieldName()) && output.trim().equalsIgnoreCase(mr.getFieldTag()))
					|| (input.trim().equalsIgnoreCase(mr.getFieldTag())
							&& output.trim().equalsIgnoreCase(mr.getFieldName()))) {
							
				return true;
			}
		return false;
	}

}
