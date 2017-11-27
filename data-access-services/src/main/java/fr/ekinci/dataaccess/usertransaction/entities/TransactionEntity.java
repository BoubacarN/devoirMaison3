package fr.ekinci.dataaccess.usertransaction.entities;

import fr.ekinci.client.models.usertransaction.TransactionType;
import fr.ekinci.dataaccess.account.entities.AccountEntity;
import lombok.Data;
import javax.persistence.*;
import java.util.Date;

/**
 * @author Gokan EKINCI
 */
@Data
@Entity
@Table(name = "transaction_logs")
public class TransactionEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn(
		name = "fk_id_account",
		referencedColumnName = "id"
	)
	private AccountEntity account;

	@Temporal(TemporalType.TIMESTAMP)
	private Date date;

	@Enumerated(EnumType.ORDINAL)
	private TransactionType type;

	private Long amount;
	private Long amountAfterTransaction;
}
