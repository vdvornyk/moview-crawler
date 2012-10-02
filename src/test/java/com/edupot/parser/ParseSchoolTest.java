package com.edupot.parser;

import java.util.Arrays;

import org.junit.Test;

import com.edubot.SchoolRow;
import com.karthik.wext.core.LogUtils;

public class ParseSchoolTest {

	// @Test
	public void parseProfile() {
		SchoolRow schoolRow = new SchoolRow("CLEVELAND", "ARNOLD");
		ParseProfile.parse(schoolRow);
		LogUtils.logger.info(Arrays.toString(schoolRow.profile));
	}

	// @Test
	public void parseAchievements() {
		SchoolRow schoolRow = new SchoolRow("BRADFORD", "BRADFORD HIGH SCHOOL");
		ParseAchievements.parse(schoolRow);
		LogUtils.logger.info(Arrays.toString(schoolRow.achievement));
	}

	// @Test
	public void parseValueAdded() {
		SchoolRow schoolRow = new SchoolRow("BRADFORD", "BRADFORD HIGH SCHOOL");
		ParseValueAdded.parse(schoolRow);
		LogUtils.logger.info(Arrays.toString(schoolRow.valueAdded));
	}

	// @Test
	public void parseAttendance() {
		SchoolRow schoolRow = new SchoolRow("BRADFORD", "BRADFORD HIGH SCHOOL");
		ParseAttendance.parse(schoolRow);
		LogUtils.logger.info(Arrays.toString(schoolRow.attendanceAndGraduation));
	}

	@Test
	public void parseDiscipline() {
		SchoolRow schoolRow = new SchoolRow("BRADFORD", "BRADFORD HIGH SCHOOL");
		ParseDiscipline.parse(schoolRow);
		LogUtils.logger.info(Arrays.toString(schoolRow.discipline));
	}

	@Test
	public void parseTeacher() {
		SchoolRow schoolRow = new SchoolRow("BRADFORD", "BRADFORD HIGH SCHOOL");
		ParseTeacher.parse(schoolRow);
		LogUtils.logger.info(Arrays.toString(schoolRow.teacher));
	}

}
