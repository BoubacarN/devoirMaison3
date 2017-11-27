package fr.ekinci.client.models.usertransaction;

import fr.ekinci.client.models.account.AccountDto;
import lombok.*;

import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;
import java.time.LocalDateTime;

/**
 * @author Gokan EKINCI
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TransactionDto {
	@Pattern(regexp = "[0-9]{1,}")
	private String id;
	private AccountDto account;
	private LocalDateTime date;
	private TransactionType type;

	@Min(0)
	private Long amount;
	private Long amountAfterTransaction;
}
