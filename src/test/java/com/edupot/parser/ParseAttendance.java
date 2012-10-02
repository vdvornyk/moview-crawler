package com.edupot.parser;

import static com.karthik.wext.core.LogUtils.logger;

import java.io.File;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.edubot.SchoolRow;

public class ParseAttendance {
	public final static String GradeTable0 = "Grades K-8 Non-Academic Indicators";
	public final static String GradeTable1 = "Grades 9-12 Non-Academic Indicators";
	public final static String GradeTable2 = "Graduation Rate by Subgroup";

	public final static String[] grades0 = new String[] { "Attendance Rate(%) (Grade N not included)", "Promotion Rate(%)" };
	public final static String[] grades1 = new String[] { "Attendance Rate(%) (Grade N not included)", "Cohort Dropout (%)", "Graduation Rate (NCLB) (%)", "Event Dropout (%)", "*** Under Revision" };
	public final static String[] grades2 = new String[] { "White", "African American", "Hispanic", "Asian", "Hawaiian/Pacific Islander", "Native American", "Male", "Female", "Economically Disadvantaged", "Students With Disabilities",
			"Limited English Proficient" };

	public static void parse(SchoolRow school) {
		File profileFile = new File(school.getShFolder() + "Attendance and Graduation.html");
		try {
			Document profileDoc = Jsoup.parse(profileFile, "UTF-8");
			int arrIndex = 0;
			try {
				Element tbl0 = profileDoc.select("tbody:contains(" + GradeTable0 + ")").last();

				for (String cat : grades0) {
					Elements elems = tbl0.select("tr:contains(" + cat + ")").last().children();
					for (int elemI = 1; elemI < elems.size(); elemI++) {
						school.attendanceAndGraduation[arrIndex++] = elems.get(elemI).text().trim();
					}
				}
			} catch (NullPointerException ex) {

			}

			// ===========================
			// ===========================
			// ===========================
			try {
				arrIndex = 7;
				Element tbl1 = profileDoc.select("tbody:contains(" + GradeTable1 + ")").last();

				for (String cat : grades1) {
					Elements elems = tbl1.select("tr:contains(" + cat + ")").last().children();
					for (int elemI = 1; elemI < elems.size(); elemI++) {
						school.attendanceAndGraduation[arrIndex++] = elems.get(elemI).text().trim();
					}
				}
			} catch (NullPointerException ex) {

			}

			// ===========================
			try {
				arrIndex = 28;
				Element tbl2 = profileDoc.select("tbody:contains(" + GradeTable2 + ")").last();
				for (String cat : grades2) {
					Elements elems = tbl2.select("tr:contains(" + cat + ")").last().children();
					for (int elemI = 1; elemI < elems.size(); elemI++) {
						school.attendanceAndGraduation[arrIndex++] = elems.get(elemI).text().trim();
					}
				}
			} catch (NullPointerException ex) {

			}
			logger.info("index" + arrIndex);
		}

		catch (Exception e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
			logger.error("PROBLEM WITH {} school {} in ValueAdded.html", school.getSystemName(), school.getSchoolName());
		}
	}

	public static String getProfileGeneralInfo(Document profileDoc, String search) {
		return profileDoc.select("td:contains(" + search + ")").last().nextElementSibling().text().trim();
	}

}
