package com.edubot;

import java.util.Arrays;

import lombok.Data;

import org.apache.commons.lang.ArrayUtils;

@Data
public class SchoolRow {
	public static final String RESOURCES = "/home/hagen/workspace/com-odesk-JoshNewman/src/main/resources/result/";

	private String systemName;
	private String schoolName;
	private int row = -1;

	public String[] profile = new String[27];
	public String[] achievement = new String[116];
	public String[] attendanceAndGraduation = new String[61];
	public String[] discipline = new String[32];
	public String[] valueAdded = new String[142];
	public String[] teacher = new String[43];

	public SchoolRow(String systemName, String schoolName, int row) {
		this.systemName = systemName;
		this.schoolName = schoolName;
		this.row = row;

		Arrays.fill(profile, "FIELD NOT EXIST");
		Arrays.fill(achievement, "FIELD NOT EXIST");
		Arrays.fill(attendanceAndGraduation, "FIELD NOT EXIST");
		Arrays.fill(discipline, "FIELD NOT EXIST");
		Arrays.fill(valueAdded, "FIELD NOT EXIST");
		Arrays.fill(teacher, "FIELD NOT EXIST");
	}

	public SchoolRow(String systemName, String schoolName) {

		this.systemName = systemName;
		this.schoolName = schoolName;

		Arrays.fill(profile, "FIELD NOT EXIST");
		Arrays.fill(achievement, "FIELD NOT EXIST");
		Arrays.fill(attendanceAndGraduation, "FIELD NOT EXIST");
		Arrays.fill(discipline, "FIELD NOT EXIST");
		Arrays.fill(valueAdded, "FIELD NOT EXIST");
		Arrays.fill(teacher, "FIELD NOT EXIST");
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SchoolRow other = (SchoolRow) obj;
		if (schoolName == null) {
			if (other.schoolName != null)
				return false;
		} else if (!schoolName.equals(other.schoolName))
			return false;
		if (systemName == null) {
			if (other.systemName != null)
				return false;
		} else if (!systemName.equals(other.systemName))
			return false;
		return true;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((schoolName == null) ? 0 : schoolName.hashCode());
		result = prime * result + ((systemName == null) ? 0 : systemName.hashCode());
		return result;
	}

	@Override
	public String toString() {
		return "schoolName=" + schoolName;
	}

	public String getShFolder() {
		return RESOURCES + systemName + "/" + schoolName + "/";
	}

	public Object[] getFullColumnArray() {
		// return ArrayUtils.addAll(profile, ArrayUtils.addAll(achievement, valueAdded));
		return ArrayUtils.addAll(profile, ArrayUtils.addAll(achievement, ArrayUtils.addAll(valueAdded, ArrayUtils.addAll(attendanceAndGraduation, ArrayUtils.addAll(discipline, teacher)))));
	}
}
