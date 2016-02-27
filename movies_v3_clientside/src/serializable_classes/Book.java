package serializable_classes;

import java.io.Serializable;

public class Book extends Product implements Serializable {

	private static final long serialVersionUID = 6907418158658524416L;
	Person author;

	public Book(String title, Person person, Person author) {
		super(title, person);
		this.author = author;
	}

	public Person getAuthor() {
		return author;
	}

	public void setAuthor(Person author) {
		this.author = author;
	}

	public long getInvestment() {
		return author.getSalary();
	}

	@Override
	public String toString() {
		return "\n\t|Id: " + id + " |Title: " + title + " |Rented by: " + person + "|";
	}
}
