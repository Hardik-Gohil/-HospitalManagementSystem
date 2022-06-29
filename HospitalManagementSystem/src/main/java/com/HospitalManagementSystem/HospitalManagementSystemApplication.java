package com.HospitalManagementSystem;

import org.modelmapper.Conditions;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class HospitalManagementSystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(HospitalManagementSystemApplication.class, args);
	}

	@Bean
	public ModelMapper modelMapper() {
		ModelMapper modelMapper = new ModelMapper();
		modelMapper
		.getConfiguration()
		.setPropertyCondition(Conditions.isNotNull())
		.setDeepCopyEnabled(true)
		.setSkipNullEnabled(true)
		.setMatchingStrategy(MatchingStrategies.STRICT);
		return modelMapper;
	}
}
