package fr.ekinci.restserver.exceptions;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * Exception Message
 *
 * @author Gokan EKINCI
 */
@Data
@Builder
public class ExceptionMessage {
	private String message;
	private String className;
	private String path;
	private LocalDateTime date;
}
