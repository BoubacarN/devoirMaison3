package fr.ekinci.client.models.account;

import lombok.*;

import javax.validation.constraints.Pattern;

/**
 * @author Gokan EKINCI
 */
@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class AccountTypeDto {
	@Pattern(regexp = "[0-9]{1,}")
	private String id;
	private String name;
	private String shortName;
}
