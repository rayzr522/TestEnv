
package com.rayzr522.testenv;

public class Chair implements Serializable {

	@Serialized
	private String	type	= "oak";

	@Serialized
	private double	weight	= 10;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public double getWeight() {
		return weight;
	}

	public void setWeight(double weight) {
		this.weight = weight;
	}

	@Override
	public String toString() {
		return "Chair [type=" + type + ", weight=" + weight + "]";
	}

}
