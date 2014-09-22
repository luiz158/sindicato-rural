package com.sindicato.converter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@FacesConverter(value="CalendarConverter")
public class CalendarConverter implements Converter {

	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String dataString) {
		
		SimpleDateFormat formatoData = new SimpleDateFormat("dd/MM/yyyy");
		Calendar dataCalendar = Calendar.getInstance();
		try {
			dataCalendar.setTime(formatoData.parse(dataString));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return dataCalendar;
	}
	
	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object obj) {
		if(obj != null && obj instanceof GregorianCalendar){
			String dataString = "";
			SimpleDateFormat formatoData = new SimpleDateFormat("dd/MM/yyyy");
			Calendar dataObj = (Calendar) obj;
			dataString = formatoData.format(dataObj.getTime());
			return dataString;
		}
		return null;
	}
}
