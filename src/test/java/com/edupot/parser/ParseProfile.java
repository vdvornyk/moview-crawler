package com.edupot.parser;

import java.io.File;

import lombok.Data;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import com.edubot.SchoolRow;
import com.karthik.wext.core.LogUtils;

public class ParseProfile {
	public final static String[] genInfoProfileStrings = new String[] { "Grades Served", "Students: (ADM)", "Safe School Status" };
	public final static String[] studBodyDemog = new String[] { "African American", "Asian / Pacific Islander", "Hispanic", "Native American / Alaskan", "White", "Economically Disadvantaged", "Female", "Male" };

	public static void parse(SchoolRow school) {
		File profileFile = new File(school.getShFolder() + "Profile.html");
		try {
			Document profileDoc = Jsoup.parse(profileFile, "UTF-8");

			String grades = getProfileGeneralInfo(profileDoc, genInfoProfileStrings[0]);
			String studentADM = getProfileGeneralInfo(profileDoc, genInfoProfileStrings[1]);

			school.profile[1] = grades;
			school.profile[3] = studentADM;

			// ===============

			StudDem studDem[] = getProfileStudDemogr(profileDoc);
			school.profile[4] = studDem[0].ofStudents;
			school.profile[6] = studDem[1].ofStudents;
			school.profile[8] = studDem[2].ofStudents;
			school.profile[10] = studDem[3].ofStudents;
			school.profile[12] = studDem[4].ofStudents;
			school.profile[20] = studDem[5].ofStudents;
			school.profile[23] = studDem[6].ofStudents;
			school.profile[25] = studDem[7].ofStudents;

			school.profile[5] = studDem[0].percOfStudents;
			school.profile[7] = studDem[1].percOfStudents;
			school.profile[9] = studDem[2].percOfStudents;
			school.profile[11] = studDem[3].percOfStudents;
			school.profile[13] = studDem[4].percOfStudents;
			school.profile[21] = studDem[5].percOfStudents;
			school.profile[22] = studDem[6].percOfStudents;
			school.profile[26] = studDem[7].percOfStudents;

		} catch (Exception e) {
			// TODO Auto-generated catch block
			LogUtils.logger.error("PROBLEM WITH {} school {} in Profile.html", school.getSystemName(), school.getSchoolName());
		}
	}

	public static String getProfileGeneralInfo(Document profileDoc, String search) {
		return profileDoc.select("td:contains(" + search + ")").last().nextElementSibling().text().trim();
	}

	public static StudDem[] getProfileStudDemogr(Document profileDoc) throws NullPointerException {
		StudDem studDem[] = new StudDem[studBodyDemog.length];
		int i = 0;
		for (String searchKey : studBodyDemog) {
			Element perStudDemElem = profileDoc.select("td:contains(" + searchKey + ")").last().parent().select("td").last();
			Element studDemElem = perStudDemElem.previousElementSibling();
			studDem[i++] = new StudDem(studDemElem.text().trim(), perStudDemElem.text().trim());
		}
		return studDem;
	}

	@Data
	private static class StudDem {

		public StudDem(String ofStudents, String percOfStudents) {

			this.ofStudents = ofStudents;
			this.percOfStudents = percOfStudents;
		}

		public String ofStudents;
		public String percOfStudents;
	}

}
