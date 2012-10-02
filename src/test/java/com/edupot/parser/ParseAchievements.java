package com.edupot.parser;

import static com.karthik.wext.core.LogUtils.logger;

import java.io.File;

import javassist.NotFoundException;
import lombok.Data;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.edubot.SchoolRow;

public class ParseAchievements {
	public final static String[] Grades3_8Arr = new String[] { "Math", "Reading/Language", "Social Studies", "Science" };
	public final static String[] Grades5_8Arr = new String[] { "Writing 5th", "Writing 8th" };
	public final static String[] Grades11Arr = new String[] { "Writing 11th" };
	public final static String[] Grades9_12Arr = new String[] { "Grades Served", "Students: (ADM)", "Safe School Status" };

	// check if exist
	public final static String AcademicACTAchievement = "Academic ACT Achievement";
	public final static String AcademicAchievementGrades = "Academic Achievement Grades";

	public final static String[] grades = new String[] { "Grades 3-8", "Grades 5 & 8", "Grades 11" };

	public final static String[] Grades9_12Tables = new String[] { "Grades 9-12: ACT - 3 - Year Averages", "Grades 9-12: ACT - Individual Years" };

	public final static String[] Grades9_12ACTArr = new String[] { "Composite", "English", "Math", "Reading", "Science/Reasoning" };

	public static void parse(SchoolRow school) {
		File profileFile = new File(school.getShFolder() + "Achievement.html");
		try {
			Document profileDoc = Jsoup.parse(profileFile, "UTF-8");
			int arrIndex = 0;
			if (profileDoc.select("h2:contains(" + AcademicAchievementGrades + ")").isEmpty()) {
				new NotFoundException("file dont contatin any data");
			}
			// ===========================
			// ===========================
			// ===========================
			Element tbl1 = profileDoc.select("tbody:contains(" + grades[0] + ")").last();

			for (String cat : Grades3_8Arr) {
				Elements elems = tbl1.select("tr:contains(" + cat + ")").last().children();
				for (int elemI = 1; elemI < elems.size(); elemI++) {
					school.achievement[arrIndex++] = elems.get(elemI).text().trim();
				}
			}
			// ===========================
			// ===========================
			// ===========================
			Element tbl2 = profileDoc.select("tbody:contains(" + grades[1] + ")").last();

			for (String cat : Grades5_8Arr) {
				Elements elems = tbl2.select("tr:contains(" + cat + ")").last().children();
				for (int elemI = 1; elemI < elems.size(); elemI++) {
					school.achievement[arrIndex++] = elems.get(elemI).text().trim();
				}
			}

			// ===========================
			// ===========================
			// ===========================

			Element tbl3 = profileDoc.select("tbody:contains(" + grades[2] + ")").last();
			for (String cat : Grades11Arr) {
				Elements elems = tbl3.select("tr:contains(" + cat + ")").last().children();
				for (int elemI = 1; elemI < elems.size(); elemI++) {
					school.achievement[arrIndex++] = elems.get(elemI).text().trim();
				}
			}

			if (!profileDoc.select("h3:contains(" + AcademicACTAchievement + ")").isEmpty()) {
				logger.info("not empty 2 part");
				for (String table : Grades9_12Tables) {
					Element tbl = profileDoc.select("tbody:contains(" + table + ")").last();
					for (String cat : Grades9_12ACTArr) {
						Elements elems = tbl.select("tr:contains(" + cat + ")").last().children();
						for (int elemI = 1; elemI < elems.size(); elemI++) {
							school.achievement[arrIndex++] = elems.get(elemI).text().trim();
						}
					}
				}
			} else {
				logger.info("empty 2 part");
			}
		}

		catch (Exception e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
			logger.error("PROBLEM WITH {} school {} in Achievements.html", school.getSystemName(), school.getSchoolName());
		}
	}

	public static String getProfileGeneralInfo(Document profileDoc, String search) {
		return profileDoc.select("td:contains(" + search + ")").last().nextElementSibling().text().trim();
	}

	@Data
	private static class Card {
		String score;
		String grade;
		String trend;
	}

}
