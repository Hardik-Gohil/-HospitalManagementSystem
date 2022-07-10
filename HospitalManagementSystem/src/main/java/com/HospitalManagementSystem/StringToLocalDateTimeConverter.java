package com.HospitalManagementSystem;

import java.text.ParseException;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.HospitalManagementSystem.utility.CommonUtility;

@Component
public class StringToLocalDateTimeConverter implements Converter<String, LocalDateTime> {

	@Autowired
	private CommonUtility commonUtility;

	@Override
	public LocalDateTime convert(String source) {
		try {
			return commonUtility.getLocalDateTime(source);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}
}