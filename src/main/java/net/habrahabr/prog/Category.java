package net.habrahabr.prog;

import java.io.Serializable;

public class Category implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3158113721938694237L;
	private String name;
	private String link;
	private int amount;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	@Override
	public String toString() {
		return "Category [name=" + name + ", link=" + link + ", amount=" + amount
				+ "]";
	}

}
