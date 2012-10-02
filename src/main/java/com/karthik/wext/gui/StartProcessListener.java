package com.karthik.wext.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.ListModel;

import org.perf4j.LoggingStopWatch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.karthik.wext.configs.SiteConfigs;
import com.karthik.wext.configs.SiteName;
import com.karthik.wext.configs.Vendor;
import com.karthik.wext.configs.VendorMapping;
import com.karthik.wext.core.AbstractSiteStrategy;
import com.karthik.wext.core.SiteFactory;
import com.karthik.wext.core.SiteStrategy;

public class StartProcessListener implements ActionListener {
	public static final Logger logger = LoggerFactory.getLogger(StartProcessListener.class);

	public void actionPerformed(ActionEvent e) {
		List<AbstractSiteStrategy> siteList = new ArrayList<AbstractSiteStrategy>();
		ListModel model = WextGUI.list.getModel();
		JButton currentButton = (JButton) e.getSource();

		List<Vendor> vendors = new ArrayList<Vendor>();
		int n = model.getSize();
		for (int i = 0; i < n; i++) {
			CheckableItem item = (CheckableItem) model.getElementAt(i);
			if (item.isSelected()) {
				// currentButton.setEnabled(false);
				vendors.add(item.getVendor());

				for (SiteName siteName : VendorMapping.VENDOR_SITE.get(item.getVendor())) {

					AbstractSiteStrategy siteInterface = SiteFactory.getInstance(siteName);
					if (siteInterface != null) {
						siteList.add(siteInterface);
					}
				}

				WextGUI.textArea.append("You will download " + item.toString() + " sites");
				WextGUI.textArea.append("Please wait while process finish... it take 5-30 mins");
				WextGUI.textArea.append(System.getProperty("line.separator"));
			}
		}

		LoggingStopWatch stopWatch = new LoggingStopWatch("codeBlock1");
		WextGUI.textArea.append("" + siteList.size());
		logger.info("size={}" + siteList.size());
		for (SiteStrategy siteStrategy : siteList) {
			if (siteStrategy != null && SiteConfigs.ACTIVE_SITES.contains(siteStrategy.getSiteName())) {
				siteStrategy.executeSite();
			}
		}
		stopWatch.stop();

	}
}
