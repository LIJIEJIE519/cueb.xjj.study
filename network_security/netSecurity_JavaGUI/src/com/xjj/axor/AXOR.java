package com.xjj.axor;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.KeyboardFocusManager;
import java.awt.Panel;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.util.Collections;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.filechooser.FileSystemView;

public class AXOR  extends JFrame {
	private Toolkit tool = Toolkit.getDefaultToolkit();
	private Dimension dim = tool.getScreenSize();
	private Font font = new Font("����", Font.BOLD, 20);
	private Color color = new Color(224,238,224);
	
	private int flag = 0;		// �ַ���Ϊ0�� �ļ�Ϊ1
	private int enOrde = 0;		// ����Ϊ0�� ����Ϊ1
	private Algorithm algo = new Algorithm();	// �����㷨
	// �˵���
	private JMenuBar jBar = new JMenuBar();
	private JMenu file = new JMenu("�ļ�");
	private JMenu edit = new JMenu("�༭");
	private JMenuItem open = new JMenuItem("��");
	private JMenuItem save = new JMenuItem("����");
	private JMenuItem view = new JMenuItem("��ͼ");
	private JMenuItem editStr = new JMenuItem("�ַ����ӽ���");
	private JMenuItem editFile = new JMenuItem("�ļ��ӽ���");
	private String FileName = "";
	// �л���
	private JTabbedPane top = new JTabbedPane(JTabbedPane.TOP);
	private JPanel encrypt = new JPanel();
	private JPanel decrypt = new JPanel();
	// ���ܽ���
	private JLabel jLabel1 = new JLabel("�������ַ���   :");
	private JTextArea plainText = new JTextArea(3,28);		// ����
	private JLabel jLabel2 = new JLabel("��Կ(6λ����)  :");	
	private JTextArea secretKey = new JTextArea(1,28);		// ��Կ
	private JButton encryptBtn = new JButton("����");
	private JLabel jLabel3 = new JLabel("����Ϊ: ");
	private JTextArea cipherText = new JTextArea(3,36);		// ����
	// ���ܽ���
	private JLabel jLabel11 = new JLabel("�������ַ���   :");
	private JTextArea cipherText1 = new JTextArea(3,28);		// ����
	private JLabel jLabel22 = new JLabel("��Կ(6λ����)  :");	
	private JTextArea secretKey1 = new JTextArea(1,28);		// ��Կ
	private JButton decryptBtn = new JButton("����");
	private JLabel jLabel33 = new JLabel("����Ϊ: ");
	private JTextArea plainText1 = new JTextArea(3,36);		// ����
	
	public AXOR() {
		super("AXOR������");
		this.setSize(Config.Width, Config.Height);
		this.setResizable(false);
		ImageIcon icon = new ImageIcon(this.getClass().getResource("/com/xjj/icon/AXOR.png"));
		this.setIconImage(icon.getImage());
		
		initEncrypt();
		initDecrypt();
		initMenu();
		clearListener();
		annotation();
		listener();
		menuListener();
		
		// ����������
		setLocation(((int)dim.getWidth()-Config.Width)/2, ((int)dim.getHeight()-Config.Height)/2);
	}
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AXOR frame = new AXOR();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	/**
	 * ��ʼ���˵���
	 */
	public void initMenu() {
		file.add(open);
		file.add(save);
		edit.add(view);
		edit.add(editFile);
		edit.add(editStr);
		jBar.add(file);
		jBar.add(edit);
		this.setJMenuBar(jBar);
		
		top.add("����", encrypt);
		top.add("����", decrypt);
		encrypt.setFont(font);
		this.add(top);
	}
	/**
	 * ��ʼ������������
	 */
	public void initEncrypt() {
		// ��ʼ�����ܽ������
		Panel fillTop = new Panel();
		fillTop.setPreferredSize(new Dimension(Config.Width, 10)); // �ϱ߾�
		FlowLayout fLayout = new FlowLayout();
		fLayout.setVgap(22);				// ������
		encrypt.setLayout(fLayout);
		encrypt.add(fillTop);
		plainText.setLineWrap(true);        //�����Զ����й��� 
		cipherText.setLineWrap(true);

		jLabel1.setFont(font);
		jLabel2.setFont(font);
		jLabel3.setFont(font);
		encryptBtn.setFont(font);
		plainText.setFont(font);
		secretKey.setFont(font);
		cipherText.setFont(font);
		
		encryptBtn.setPreferredSize(new Dimension(460,40));
		encryptBtn.setBackground(Color.cyan);
		encryptBtn.setForeground(Color.white);
		encrypt.add(jLabel1);
		encrypt.add(plainText);
		encrypt.add(jLabel2);
		encrypt.add(secretKey);
		encrypt.add(encryptBtn);
		encrypt.add(jLabel3);
		encrypt.add(cipherText);
		encrypt.setBackground(color);
	}
	/**
	 * ��ʼ������������
	 */
	public void initDecrypt() {
		// ��ʼ�����ܽ������
		Panel fillTop = new Panel();
		fillTop.setPreferredSize(new Dimension(Config.Width, 10)); // �ϱ߾�
		FlowLayout fLayout = new FlowLayout();
		fLayout.setVgap(22);
		decrypt.setLayout(fLayout);
		decrypt.add(fillTop);
		decryptBtn.setFont(font);
		plainText1.setLineWrap(true);        //�����Զ����й��� 
		cipherText1.setLineWrap(true);

		jLabel11.setFont(font);
		jLabel22.setFont(font);
		jLabel33.setFont(font);
		decryptBtn.setFont(font);
		plainText1.setFont(font);
		secretKey1.setFont(font);
		cipherText1.setFont(font);

		decryptBtn.setPreferredSize(new Dimension(440, 40));
		decryptBtn.setBackground(Color.cyan);
		decryptBtn.setForeground(Color.white);

		decrypt.add(jLabel11);
		decrypt.add(cipherText1);
		decrypt.add(jLabel22);
		decrypt.add(secretKey1);
		decrypt.add(decryptBtn);
		decrypt.add(jLabel33);
		decrypt.add(plainText1);
				
	}
	
	/**
	 * �˵�������
	 */
	public void menuListener() {
		JFileChooser chooser = new JFileChooser();
		FileSystemView fsv = FileSystemView.getFileSystemView(); 			//ע���ˣ�������Ҫ��һ��  
		chooser.setCurrentDirectory(fsv.getHomeDirectory());				//�õ�����·�� 
		chooser.setDialogTitle("AXOR");
		// ���ļ�����
		open.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int result = chooser.showOpenDialog(null);
				if (result == JFileChooser.APPROVE_OPTION) {
					flag = 1;
					FileName = chooser.getSelectedFile().getPath();
					if (top.getSelectedIndex() == 0) {
						jLabel1.setText("�������ļ�·��  :");
						plainText.setText(FileName);
						jLabel3.setText("�����ļ�:");
					}
					else if (top.getSelectedIndex() == 1) {
						jLabel11.setText("�������ļ�·��  :");
						cipherText1.setText(FileName);
						jLabel33.setText("�����ļ�:");
					}
				}
			}
		});
		// �������ļ���
		save.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int result = chooser.showSaveDialog(null);		// �������
				String cip = cipherText.getText();
				if (cip.length() == 0) {
					JOptionPane.showMessageDialog(null, "����Ϊ�գ����ȼ��ܣ�");
				}else {
					BufferedWriter bWriter = null;
					File file = null;
					String fileName = null;
					if (result == JFileChooser.APPROVE_OPTION) {
						file = chooser.getSelectedFile();
						fileName = chooser.getName(file);	// ���ѡ��Ļ�������ļ���
						if (fileName == null || fileName.trim().length() == 0) {
							JOptionPane.showMessageDialog(null, "�ļ���Ϊ�գ�");
						}else {
							try {
								bWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file)));
								bWriter.write(cip);
								bWriter.close();
								JOptionPane.showMessageDialog(null, "�������ĳɹ���");
							} catch (Exception e2) {
								e2.printStackTrace();
							}
						}
					}
				}
			}
		});
		
		// �����ļ�����
		editFile.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				flag = 1;		// ��ֹ�����ַ�������
				jLabel1.setText("�������ļ�·��  :");
				jLabel3.setText("�����ļ�:");
				jLabel11.setText("�������ļ�·��  :");
				jLabel33.setText("�����ļ�:");
				clear();
			}
		});
		// �����ַ�������
		editStr.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				flag = 0;
				jLabel1.setText("�������ַ���   :");
				jLabel3.setText("����Ϊ: ");
				jLabel11.setText("�������ַ���   :");
				jLabel33.setText("����Ϊ: ");
				clear();
			}
		});
	}
	/**
	 * ���ļ��ӽ��ܼ�д����
	 * @param pathname
	 * @param sc
	 * @return
	 * @throws Exception
	 */
	public String fileOperation(String pathname, String sc) throws Exception {
		String[] s = pathname.split("\\\\");
		String newPath = "";
		for(int i = 0; i < s.length-1; i++)
			newPath += s[i]+"\\";
		newPath += "New" + s[s.length-1];
		
		// �����ļ�
		File fileRead = new File(pathname);
		InputStreamReader reader = new InputStreamReader(new FileInputStream(fileRead), "utf-8");
		BufferedReader bReader = new BufferedReader(reader);
		StringBuffer text = new StringBuffer();
		algo.ScreetKey(sc);				// ��Կ
		String resultText = "";
		if (enOrde == 0) {
			// ����
			String t = "";
			while((t = bReader.readLine()) != null){
				text.append(t+"\n");
			}
			resultText = algo.Encrypt(text.toString());
		}else if (enOrde == 1) {
			// ����
			text.append(bReader.readLine());
			resultText = algo.Decrypt(text.toString());
		}
				
		bReader.close();
		reader.close();
		
        // д���ļ�
		File fileWrite = new File(newPath);
        if(!fileWrite.exists()){  
        	fileWrite.createNewFile();
        }
        //���FileWriter�Ĺ������Ϊtrue����ô�ͽ�������׷��;
        //���FileWriter�Ĺ������Ϊfalse,��ô�ͽ������ݵĸ���;
        FileWriter fw = new FileWriter(fileWrite,false);
        BufferedWriter bw = new BufferedWriter(fw);
        bw.write(resultText);
        bw.flush();  
        bw.close();
        fw.close();		
        return newPath;
	}
	
	/**
	 * �����ӽ��ܰ�ť
	 */
	public void listener() {
		// ���ܰ�ť
		encryptBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				enOrde = 0;		//����
				String sc = secretKey.getText();
				FileName = plainText.getText();
				if (sc.length() == 6 && isNum(sc) && !FileName.equals("") ) {
					if (flag == 0) {
						// �ַ�������
						algo.ScreetKey(sc);
						cipherText.setText(algo.Encrypt(FileName));
					}
					else if (flag == 1) {
						// �ļ�����
						try {
							String newPath = fileOperation(FileName, secretKey.getText());
							cipherText.setText(newPath);
						} catch (Exception e1) {
							JOptionPane.showMessageDialog(null, "�Ҳ����ļ�!");
						}
					}
				}else {
					JOptionPane.showMessageDialog(null, "��������ȷ��Կ");
					secretKey.setText("");
				}
			}
		});
		// ���ܰ�ť
		decryptBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				enOrde = 1;		// ����
				String sc = secretKey1.getText();
				String text = cipherText1.getText();
				if (sc.length() == 6 && isNum(sc) && !text.equals("") ) {
					if (flag == 0) {
						algo.ScreetKey(sc);
						plainText1.setText(algo.Decrypt(text));
					}else if (flag == 1) {
						try {
							String newPath = fileOperation(FileName, secretKey.getText());
							plainText1.setText(newPath);
						} catch (Exception e1) {
							JOptionPane.showMessageDialog(null, "�Ҳ����ļ�!");
						}
					}
				}else {
					JOptionPane.showMessageDialog(null, "��������ȷ��Կ");
					secretKey1.setText("");
				}
			}
		});
	}
	public void clear() {
		plainText.setText("");
		secretKey.setText("");
		cipherText.setText("");
		plainText1.setText("");
		cipherText1.setText("");
	}
	/**
	 * ����ı�
	 */
	public void clearListener() {
		JButton clearPlain = new JButton("�������");
		JButton clearSrecet = new JButton("�����Կ");
		JButton clearCipher = new JButton("�������");
		JButton copyCipher = new JButton("��������");
		
		encrypt.add(clearPlain);
		encrypt.add(clearSrecet);
		encrypt.add(clearCipher);
		encrypt.add(copyCipher);

		clearPlain.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				plainText.setText("");
			}
		});
		clearSrecet.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				secretKey.setText("");
			}
		});
		clearCipher.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				cipherText.setText("");
			}
		});
		copyCipher.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Clipboard clip = Toolkit.getDefaultToolkit().getSystemClipboard();  
		        Transferable tText = new StringSelection(cipherText.getText());  
		        clip.setContents(tText, null);  
			}
		});
		 
		
		JButton clearPlain1 = new JButton("�������");
		JButton clearCipher1 = new JButton("�������");
		
		decrypt.add(clearPlain1);
		decrypt.add(clearCipher1);
		
		clearPlain1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				plainText1.setText("");
			}
		});
		clearCipher1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				cipherText1.setText("");
			}
		});
	}
	/**
	 * �ж��Ƿ���ֵ
	 * @param str
	 * @return
	 */
	public boolean isNum(String str){
        Pattern pattern = Pattern.compile("[0-9]*");
        Matcher isNum = pattern.matcher(str);
        if(!isNum.matches() ){
            return false;
        }
        return true;
 }
	/**
	 * ע��
	 */
	public void annotation() {
		JLabel what = new JLabel("�������Կ��");
		font = new Font("����", Font.BOLD, 16);
		what.setFont(font);
		what.setForeground(Color.red);
		JLabel author = new JLabel("2018-11-11 by XJJ");
		encrypt.add(what);
		encrypt.add(author);
	}
	
}
