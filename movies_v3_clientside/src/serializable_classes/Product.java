package serializable_classes;

import java.io.Serializable;

public abstract class Product implements Serializable {

	private static final long serialVersionUID = -2258314904639755416L;
	String id;
	String title;
	Person person;

	public Product(String title, Person person) {
		this.title = title;
		this.person = person;
		id = IdGenerator.generator(this);
	}

	public String getTitle() {
		return title;
	}

	public Person getPerson() {
		return person;
	}

	public abstract long getInvestment();
}
