package company.carsProject.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "carOwners")
public class CarOwner implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@Column(name = "name")
	private String name;

	@Column(name = "egn")
	private String egn;

	@Column(name = "yearsOfDrivingExperience")
	private int yearsOfDrivingExperience;

	public CarOwner(String name, String egn, int yearsOfDrivingExperience) {
		super();
		this.name = name;
		this.egn = egn;
		this.yearsOfDrivingExperience = yearsOfDrivingExperience;
	}

	public CarOwner() {
		// TODO Auto-generated constructor stub
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEgn() {
		return egn;
	}

	public void setEgn(String egn) {
		this.egn = egn;
	}

	public int getYearsOfDrivingExperience() {
		return yearsOfDrivingExperience;
	}

	public void setYearsOfDrivingExperience(int yearsOfDrivingExperience) {
		this.yearsOfDrivingExperience = yearsOfDrivingExperience;
	}

}
