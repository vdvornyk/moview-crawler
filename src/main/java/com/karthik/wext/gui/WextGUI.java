package com.karthik.wext.gui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.GridLayout;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ListCellRenderer;
import javax.swing.ListSelectionModel;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;

import com.karthik.wext.configs.SiteConfigs;
import com.karthik.wext.configs.Vendor;

/**
 * @version 1.0 04/26/99
 */
public class WextGUI extends JFrame {
	final static JList list = new JList(createData());
	final static JTextArea textArea = new JTextArea(5, 10);

	public WextGUI() {

		list.setCellRenderer(new CheckListRenderer());
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		list.setBorder(new EmptyBorder(0, 4, 0, 0));
		list.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				int index = list.locationToIndex(e.getPoint());
				CheckableItem item = (CheckableItem) list.getModel().getElementAt(index);
				item.setSelected(!item.isSelected());
				Rectangle rect = list.getCellBounds(index, index);
				list.repaint(rect);
			}
		});
		JScrollPane sp = new JScrollPane(list);

		JScrollPane textPanel = new JScrollPane(textArea);

		JButton printButton = new JButton("Start process");
		printButton.addActionListener(new StartProcessListener());
		JButton clearButton = new JButton("clear");
		clearButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textArea.setText("");
			}
		});
		JPanel panel = new JPanel(new GridLayout(2, 1));
		panel.add(printButton);
		// panel.add(clearButton);

		getContentPane().add(sp, BorderLayout.CENTER);
		getContentPane().add(panel, BorderLayout.EAST);
		getContentPane().add(textPanel, BorderLayout.SOUTH);
	}

	private static CheckableItem[] createData() {
		// int n = Vendor.values().length;

		int n = SiteConfigs.ACTIVE_VENDORS.size();

		Vendor vendors[] = SiteConfigs.ACTIVE_VENDORS.toArray(new Vendor[n]);

		CheckableItem[] items = new CheckableItem[n];

		for (int i = 0; i < n; i++) {
			items[i] = new CheckableItem(vendors[i]);
		}
		return items;
	}

	class CheckListRenderer extends JCheckBox implements ListCellRenderer {

		public CheckListRenderer() {
			setBackground(UIManager.getColor("List.textBackground"));
			setForeground(UIManager.getColor("List.textForeground"));
		}

		public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean hasFocus) {
			setEnabled(list.isEnabled());
			setSelected(((CheckableItem) value).isSelected());
			setFont(list.getFont());
			setText(value.toString());
			return this;
		}
	}

}