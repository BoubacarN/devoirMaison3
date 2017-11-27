package fr.ekinci.dataaccess.account.entities;

import lombok.Data;
import javax.persistence.*;

/**
 * @author Gokan EKINCI
 */
@Data
@Entity
@Table(name = "account_types")
public class AccountTypeEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "short_name")
	private String shortName;

	private String name;

	@OneToOne(mappedBy = "type")
	private AccountEntity account;
}
