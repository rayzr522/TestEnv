
package com.rayzr522.testenv;

public class SkillData implements Serializable {

	@Serialized
	private int	skillMining	= 0;

	@Serialized
	private int	skillSwords	= 0;

	@Serialized
	private int	skillBow	= 0;

	public int getSkillMining() {
		return skillMining;
	}

	public void setSkillMining(int skillMining) {
		this.skillMining = skillMining;
	}

	public int getSkillSwords() {
		return skillSwords;
	}

	public void setSkillSwords(int skillSwords) {
		this.skillSwords = skillSwords;
	}

	public int getSkillBow() {
		return skillBow;
	}

	public void setSkillBow(int skillBow) {
		this.skillBow = skillBow;
	}

	@Override
	public String toString() {
		return "SkillData [skillMining=" + skillMining + ", skillSwords=" + skillSwords + ", skillBow=" + skillBow + "]";
	}

}
