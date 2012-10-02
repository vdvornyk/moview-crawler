package com.karthik.wext.config;

import java.util.Set;

import org.junit.Assert;
import org.junit.Test;

import com.karthik.wext.configs.SiteName;
import com.karthik.wext.configs.Vendor;
import com.karthik.wext.configs.VendorMapping;

public class XlsConfigReaderTest {

	@Test
	public void nameConfig() {
		for (Vendor vendor : VendorMapping.VENDOR_SITE.keySet()) {
			Set<SiteName> siteNameSet = VendorMapping.VENDOR_SITE.get(vendor);
			for (SiteName siteName : siteNameSet) {
				System.out.println(vendor);
			}
		}
	}

	@Test
	public void testBoolean() {
		Assert.assertTrue(Boolean.parseBoolean("True"));
		Assert.assertTrue(Boolean.parseBoolean("TRUE"));
		Assert.assertTrue(Boolean.parseBoolean("TrUe"));

	}
}
