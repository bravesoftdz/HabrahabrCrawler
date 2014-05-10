package net.habrahabr.prog;

public class Hub extends Category {
	private int postAmount;
	private int subscribers;
	private double index;

	public int getPostAmount() {
		return postAmount;
	}

	public void setPostAmount(int postAmount) {
		this.postAmount = postAmount;
	}

	public int getSubscribers() {
		return subscribers;
	}

	public void setSubscribers(int subscribers) {
		this.subscribers = subscribers;
	}

	public double getIndex() {
		return index;
	}

	public void setIndex(double index) {
		this.index = index;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("\nHub :\n").append("Name : ").append(this.getName()).append("\n")
		.append("Link : ").append(this.getLink()).append("\n").append("Posts : ")
		.append(this.getPostAmount()).append("\n").append("Subscribers : ")
		.append(this.getSubscribers());
		return builder.toString();
	}
}
