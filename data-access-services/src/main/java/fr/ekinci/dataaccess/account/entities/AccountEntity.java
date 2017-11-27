package fr.ekinci.dataaccess.account.entities;

import fr.ekinci.dataaccess.user.entities.UserEntity;
import fr.ekinci.dataaccess.usertransaction.entities.TransactionEntity;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;


/**
 * Spring Data default mode is Lazy ! -> https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#jpa.entity-graph
 *
 * @author Gokan EKINCI
 */
@Getter
@Setter
@Entity
@Table(name = "accounts")
public class AccountEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn(
		name = "fk_id_user",
		referencedColumnName = "id"
	)
	private UserEntity user;

	@OneToOne
	@JoinColumn(
		name = "fk_id_account_type",
		referencedColumnName = "id"
	)
	private AccountTypeEntity type;

	private Long amount;

	@OneToMany(mappedBy = "account")
	private List<TransactionEntity> transactionLogs;
}