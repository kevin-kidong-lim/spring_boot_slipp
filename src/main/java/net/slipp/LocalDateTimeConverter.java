package net.slipp;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class LocalDateTimeConverter implements AttributeConverter<LocalDateTime, Timestamp> {

	@Override
	public Timestamp convertToDatabaseColumn(LocalDateTime localDateTime) {
		// TODO Auto-generated method stub
		return localDateTime != null ? Timestamp.valueOf(localDateTime) : null;
	}

	@Override
	public LocalDateTime convertToEntityAttribute(Timestamp timestamp) {
		// TODO Auto-generated method stub
		
		return timestamp != null ? timestamp.toLocalDateTime(): null;
	}
	
	
}
