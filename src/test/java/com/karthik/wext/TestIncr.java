package com.karthik.wext;

import java.util.List;

import org.junit.Assert;

import com.karthik.wext.configs.SiteConfigs;
import com.karthik.wext.pojo.SiteBaseData;

public class TestIncr {

	// @Test
	public void testIncr() {
		int i = 1;
		Assert.assertEquals(3, (i++) + 2);

		Assert.assertEquals(2, i);

		int c = 2;

		Assert.assertEquals(2, c);
		System.out.println("package name:" + TestIncr.class.getPackage().getName());

	}

	// @Test
	public void tableRowCount() {

		List<SiteBaseData> list = SiteConfigs.baseData;
		for (SiteBaseData data : list) {
			System.out.println(data.toString());
		}

	}
}
