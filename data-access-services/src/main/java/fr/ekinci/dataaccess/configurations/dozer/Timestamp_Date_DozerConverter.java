package fr.ekinci.dataaccess.configurations.dozer;

import org.dozer.DozerConverter;
import java.sql.Timestamp;
import java.util.Date;

/**
 * @author Gokan EKINCI
 */
public class Timestamp_Date_DozerConverter extends DozerConverter<Timestamp, Date> {

	public Timestamp_Date_DozerConverter() {
		super(Timestamp.class, Date.class);
	}

	@Override
	public Date convertTo(Timestamp timestamp, Date date) {
		return new Date(timestamp.getTime());
	}

	@Override
	public Timestamp convertFrom(Date date, Timestamp timestamp) {
		return new Timestamp(date.getTime());
	}
}
