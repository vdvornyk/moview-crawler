package com.karthik.wext;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.UIManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.karthik.wext.gui.WextGUI;

public class Main {

	/**
	 * @param args
	 */

	public static final Logger logger = LoggerFactory.getLogger(Main.class);

	public static void main(String[] args) { // TODO Auto-generated method stub

		try {
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
		} catch (Exception evt) {
		}

		WextGUI frame = new WextGUI();
		frame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
		frame.setSize(500, 400);
		frame.setVisible(true);

	}

}
