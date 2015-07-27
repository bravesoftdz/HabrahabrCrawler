package net.habrahabr.prog.model;

import java.io.Serializable;

public class Category implements Serializable {

	private static final long serialVersionUID = 3158113721938694237L;
	private String name;
	private String link;

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


	@Override
	public String toString() {
		return "Category [name=" + name + ", link=" + link;
	}

}
