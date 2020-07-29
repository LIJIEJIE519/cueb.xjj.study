package com.xjj.axor;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JTabbedPane;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JTextArea;
import java.awt.Font;
import javax.swing.SwingConstants;
import java.awt.FlowLayout;
import javax.swing.AbstractAction;
import java.awt.event.ActionEvent;
import javax.swing.Action;
import java.awt.event.ActionListener;
import javax.swing.JLayeredPane;
import java.awt.Color;

public class AXOR01 extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AXOR01 frame = new AXOR01();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public AXOR01() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, Config.Width, Config.Height);
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu menu = new JMenu("\u6587\u4EF6");
		menuBar.add(menu);
		
		JMenuItem menuItem = new JMenuItem("\u6253\u5F00");
		menu.add(menuItem);
		
		JMenuItem menuItem_1 = new JMenuItem("\u4FDD\u5B58");
		menu.add(menuItem_1);
		
		JMenu menu_1 = new JMenu("\u7F16\u8F91");
		menuBar.add(menu_1);
		
		JMenuItem menuItem_2 = new JMenuItem("\u89C6\u56FE");
		menu_1.add(menuItem_2);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.LEFT);
		tabbedPane.setBorder(null);
		contentPane.add(tabbedPane, BorderLayout.CENTER);
		
		JPanel panel = new JPanel();
		panel.setBorder(null);
		FlowLayout flowLayout = (FlowLayout) panel.getLayout();
		flowLayout.setVgap(25);
		flowLayout.setAlignment(FlowLayout.LEFT);
		tabbedPane.addTab("\u52A0\u5BC6", null, panel, null);
		
		JLabel lblNewLabel = new JLabel("´ý¼ÓÃÜ×Ö·û´®   :");
		lblNewLabel.setFont(new Font("ºÚÌå", Font.BOLD, 20));
		panel.add(lblNewLabel);
		
		JTextArea textArea = new JTextArea();
		textArea.setColumns(28);
		textArea.setRows(3);
		panel.add(textArea);
		
		JLabel lblNewLabel_1 = new JLabel("ÃØÔ¿(3~6Î»Êý×Ö):");
		lblNewLabel_1.setFont(new Font("ºÚÌå", Font.BOLD, 20));
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.LEFT);
		lblNewLabel_1.setVerticalAlignment(SwingConstants.TOP);
		panel.add(lblNewLabel_1);
		
		JTextArea textArea_1 = new JTextArea();
		textArea_1.setColumns(28);
		textArea_1.setEditable(false);
		panel.add(textArea_1);
		
		JButton btnNewButton = new JButton("\u52A0\u5BC6");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnNewButton.setHorizontalAlignment(SwingConstants.LEADING);
		btnNewButton.setFont(new Font("ºÚÌå", Font.BOLD, 20));
		btnNewButton.setVerticalAlignment(SwingConstants.BOTTOM);
		panel.add(btnNewButton);
		
		JLayeredPane layeredPane = new JLayeredPane();
		layeredPane.setBackground(Color.WHITE);
		tabbedPane.addTab("\u89E3\u5BC6", null, layeredPane, null);
		
		JLabel lblNewLabel_2 = new JLabel("\u5F85\u89E3\u5BC6\u5B57\u7B26\u4E32   :");
		lblNewLabel_2.setBounds(29, 30, 100, 30);
		layeredPane.add(lblNewLabel_2);
		
		JTextArea textArea_2 = new JTextArea();
		textArea_2.setRows(3);
		textArea_2.setColumns(28);
		textArea_2.setBounds(150, 34, 260, 30);
		layeredPane.add(textArea_2);
	}

}
