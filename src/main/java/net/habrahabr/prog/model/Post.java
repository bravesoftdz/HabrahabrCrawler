package net.habrahabr.prog.model;

public class Post extends Category {

	private static final long serialVersionUID = 1L;

	@Override
	public String toString() {
		return getName() + " | " + getLink() + "\n";
	}
}
