package fr.ekinci.client.models.account;

import fr.ekinci.client.models.user.UserDto;
import lombok.*;
import javax.validation.constraints.Pattern;

/**
 * Amount is in cent
 *
 * @author Gokan EKINCI
 */
@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class AccountDto {
	@Pattern(regexp = "[0-9]{1,}")
	private String id;
	private UserDto user;
	private AccountTypeDto type;
	private Long amount;
}
