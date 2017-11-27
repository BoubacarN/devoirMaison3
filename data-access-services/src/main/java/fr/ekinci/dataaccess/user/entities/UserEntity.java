package fr.ekinci.dataaccess.user.entities;

import fr.ekinci.dataaccess.account.entities.AccountEntity;
import fr.ekinci.dataaccess.usertransaction.entities.TransactionEntity;
import lombok.Data;
import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Data
@Entity
@Table(name = "users")
public class UserEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "last_name")
	private String lastName;

	@Column(name = "first_name")
	private String firstName;

	@Temporal(TemporalType.TIMESTAMP)
	private Date birthDate;

	private String address;

	@Column(name = "cell_phone")
	private String cellPhone;

	@Column(name = "home_phone")
	private String homePhone;

	@OneToMany(mappedBy = "user")
	private List<AccountEntity> accounts;
}