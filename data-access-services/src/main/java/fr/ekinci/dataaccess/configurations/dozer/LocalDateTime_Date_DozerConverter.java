package fr.ekinci.dataaccess.configurations.dozer;

import org.dozer.DozerConverter;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

/**
 * Note: The second parameter of convertTo and convertFrom methods are useless
 * Inspired by: https://stackoverflow.com/questions/22929237/convert-java-time-localdate-into-java-util-date-type
 *
 * Dozer has some polymorphism problem, {@link java.sql.Timestamp} is {@link java.util.Date}
 *
 * @author Gokan EKINCI
 */
public class LocalDateTime_Date_DozerConverter extends DozerConverter<LocalDateTime, Date> {

	public LocalDateTime_Date_DozerConverter() {
		super(LocalDateTime.class, Date.class);
	}

	@Override
	public Date convertTo(LocalDateTime localDateTime, Date date) {
		return (localDateTime != null) ? Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant()) : null;
	}

	@Override
	public LocalDateTime convertFrom(Date date, LocalDateTime localDateTime) {
		return (date != null) ? Instant.ofEpochMilli(date.getTime()).atZone(ZoneId.systemDefault()).toLocalDateTime() : null;
	}
}
