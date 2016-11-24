package com.yazilimokulu.mvc.converters;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.core.convert.converter.Converter;

public class JulianDateConverter implements Converter<String, Date> {

	@Override
	public Date convert(String strDate) {
		Date tmpDate = null;
		try {
			System.out.println("Converting julian date");
			tmpDate = new SimpleDateFormat("yyyyDDD").parse(strDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return tmpDate;
	}

}
