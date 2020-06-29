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
		List<MappingRules> rules = mappingRepository.findByFieldTag(input.trim());
		
		if(!rules.isEmpty()) {
			return validate(rules, input, output);
		}else {
			List<MappingRules> rule = mappingRepository.findByFieldTag(output.trim());
			return validate(rule, input, output);
		}
	}

	public boolean validate(List<MappingRules> mappingRulesList, String input, String output) {
		boolean flag=false;
			for(MappingRules mappingRule:mappingRulesList) {
						if ((input.trim().equalsIgnoreCase(mappingRule.getFieldName()) && output.trim().equalsIgnoreCase(mappingRule.getFieldTag()))
					|| (input.trim().equalsIgnoreCase(mappingRule.getFieldTag())
							&& output.trim().equalsIgnoreCase(mappingRule.getFieldName()))) {
							
							
				flag= true;
				break;
			}
			}
		return flag;
	}

}
