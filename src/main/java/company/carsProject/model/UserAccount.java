package company.carsProject.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "userAccount")
public class UserAccount implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@Column(name = "role")
	private String role;
	
	@Column(name = "email")
	private String email;
	
	@Column(name = "password")
	private String password;
	
	@OneToOne(fetch = FetchType.EAGER, optional = true)
	@JoinColumn(name = "carOwner_id")
	private CarOwner carOwner;
	
	

	public UserAccount(String role, String email, String password, CarOwner carOwner) {
		super();
		this.role = role;
		this.email = email;
		this.password = password;
		this.carOwner = carOwner;
	}

	public UserAccount(String role, String email, String password) {
		super();
		this.role = role;
		this.email = email;
		this.password = password;
	}

	public UserAccount() {
		// TODO Auto-generated constructor stub
	}

	

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public CarOwner getCarOwner() {
		return carOwner;
	}

	public void setCarOwner(CarOwner carOwner) {
		this.carOwner = carOwner;
	}
	
	

}
