package fr.ekinci.dataaccess.configurations.dozer;

import org.dozer.DozerConverter;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

/**
 * Note: The second parameter of convertTo and convertFrom methods are useless
 * Inspired by: https://stackoverflow.com/questions/22929237/convert-java-time-localdate-into-java-util-date-type
 *
 * @author Gokan EKINCI
 */
public class LocalDate_Date_DozerConverter extends DozerConverter<LocalDate, Date> {

	public LocalDate_Date_DozerConverter() {
		super(LocalDate.class, Date.class);
	}

	@Override
	public Date convertTo(LocalDate localDate, Date date) {
		return (localDate != null) ? Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant()) : null;
	}

	@Override
	public LocalDate convertFrom(Date date, LocalDate localDate) {
		return (date != null) ? Instant.ofEpochMilli(date.getTime()).atZone(ZoneId.systemDefault()).toLocalDate() : null;
	}
}
