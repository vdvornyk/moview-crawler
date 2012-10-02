package com.edupot.parser;

import static com.karthik.wext.core.LogUtils.logger;

import java.io.File;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.edubot.SchoolRow;

public class ParseDiscipline {
	public final static String GradeTable0 = "Suspensions & Expulsions";

	public final static String[] grades0 = new String[] { "African American", "Asian / Pacific Islander", "Hispanic", "Native American", "White", "Female", "Male", "All Students" };

	public static void parse(SchoolRow school) {
		File profileFile = new File(school.getShFolder() + "Discipline.html");
		try {
			Document profileDoc = Jsoup.parse(profileFile, "UTF-8");
			int arrIndex = 0;
			try {
				Element tbl0 = profileDoc.select("tbody:contains(" + GradeTable0 + ")").last();

				for (String cat : grades0) {
					Elements elems = tbl0.select("tr:contains(" + cat + ")").last().children();
					for (int elemI = 1; elemI < elems.size(); elemI++) {
						school.discipline[arrIndex++] = elems.get(elemI).text().trim();
					}
				}
			} catch (NullPointerException ex) {

			}

		}

		catch (Exception e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
			logger.error("PROBLEM WITH {} school {} in Discipline.html", school.getSystemName(), school.getSchoolName());
		}
	}

	public static String getProfileGeneralInfo(Document profileDoc, String search) {
		return profileDoc.select("td:contains(" + search + ")").last().nextElementSibling().text().trim();
	}

}
