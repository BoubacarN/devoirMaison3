package fr.ekinci.client.models.user;

import lombok.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.time.LocalDate;

/**
 * @author Gokan EKINCI
 */
@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
	@Pattern(regexp = "[0-9]{1,}")
	private String id;

	@NotNull
	private String lastName;

	@NotNull
	private String firstName;

	@NotNull
	private LocalDate birthDate;

	@NotNull
	private String address;

	@NotNull
	private String cellPhone;

	@NotNull
	private String homePhone;
	@NotNull
	private String role;

}
