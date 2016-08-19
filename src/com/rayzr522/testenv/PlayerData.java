
package com.rayzr522.testenv;

public class PlayerData implements Serializable {

	@Serialized
	private String		name;

	@Serialized
	private int			level;

	@Serialized
	private double		money;

	@Serialized
	private SkillData	skillData	= new SkillData();

	@Serialized
	public Chair		chair		= new Chair();

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public double getMoney() {
		return money;
	}

	public void setMoney(double money) {
		this.money = money;
	}

	public SkillData getSkillData() {
		return skillData;
	}

	public void setSkillData(SkillData skillData) {
		this.skillData = skillData;
	}

	@Override
	public String toString() {
		return "PlayerData [name=" + name + ", level=" + level + ", money=" + money + ", skillData=" + skillData + ", chair=" + chair + "]";
	}

}
