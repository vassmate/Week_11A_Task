package serializable_classes;

import java.io.Serializable;

public class Person implements Serializable {

	private static final long serialVersionUID = 1026001883712147841L;

	public enum Gender {
		MALE, FEMALE
	}

	String firstName;
	String lastName;
	Gender gender;
	int salary;

	public Person(String firstName, String lastName, Gender gender, int salary) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.gender = gender;
		this.salary = salary;
	}

	public Person(String firstName, String lastName, Gender gender) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.gender = gender;
	}

	public String getFirstName() {
		return this.firstName;
	}

	public void setFirstName(String newFirstName) {
		this.firstName = newFirstName;
	}

	public String getLastName() {
		return this.lastName;
	}

	public void setLastName(String newLastName) {
		this.lastName = newLastName;
	}

	public Gender getGender() {
		return this.gender;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}

	public int getSalary() {
		return salary;
	}

	public void setSalary(int salary) {
		this.salary = salary;
	}

	@Override
	public String toString() {
		return firstName + " " + lastName;
	}
}
