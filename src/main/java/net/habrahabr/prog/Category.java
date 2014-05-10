package net.habrahabr.prog;

public class Category {
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
