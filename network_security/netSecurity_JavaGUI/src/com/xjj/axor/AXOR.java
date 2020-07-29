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
	private Font font = new Font("黑体", Font.BOLD, 20);
	private Color color = new Color(224,238,224);
	
	private int flag = 0;		// 字符串为0， 文件为1
	private int enOrde = 0;		// 加密为0， 解密为1
	private Algorithm algo = new Algorithm();	// 加密算法
	// 菜单栏
	private JMenuBar jBar = new JMenuBar();
	private JMenu file = new JMenu("文件");
	private JMenu edit = new JMenu("编辑");
	private JMenuItem open = new JMenuItem("打开");
	private JMenuItem save = new JMenuItem("保存");
	private JMenuItem view = new JMenuItem("视图");
	private JMenuItem editStr = new JMenuItem("字符串加解密");
	private JMenuItem editFile = new JMenuItem("文件加解密");
	private String FileName = "";
	// 切换栏
	private JTabbedPane top = new JTabbedPane(JTabbedPane.TOP);
	private JPanel encrypt = new JPanel();
	private JPanel decrypt = new JPanel();
	// 加密界面
	private JLabel jLabel1 = new JLabel("待加密字符串   :");
	private JTextArea plainText = new JTextArea(3,28);		// 明文
	private JLabel jLabel2 = new JLabel("秘钥(6位数字)  :");	
	private JTextArea secretKey = new JTextArea(1,28);		// 秘钥
	private JButton encryptBtn = new JButton("加密");
	private JLabel jLabel3 = new JLabel("密文为: ");
	private JTextArea cipherText = new JTextArea(3,36);		// 密文
	// 解密界面
	private JLabel jLabel11 = new JLabel("待解密字符串   :");
	private JTextArea cipherText1 = new JTextArea(3,28);		// 密文
	private JLabel jLabel22 = new JLabel("秘钥(6位数字)  :");	
	private JTextArea secretKey1 = new JTextArea(1,28);		// 秘钥
	private JButton decryptBtn = new JButton("解密");
	private JLabel jLabel33 = new JLabel("明文为: ");
	private JTextArea plainText1 = new JTextArea(3,36);		// 明文
	
	public AXOR() {
		super("AXOR加密器");
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
		
		// 放在正中央
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
	 * 初始化菜单栏
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
		
		top.add("加密", encrypt);
		top.add("解密", decrypt);
		encrypt.setFont(font);
		this.add(top);
	}
	/**
	 * 初始化组件加密组件
	 */
	public void initEncrypt() {
		// 初始化加密界面组件
		Panel fillTop = new Panel();
		fillTop.setPreferredSize(new Dimension(Config.Width, 10)); // 上边距
		FlowLayout fLayout = new FlowLayout();
		fLayout.setVgap(22);				// 组件间距
		encrypt.setLayout(fLayout);
		encrypt.add(fillTop);
		plainText.setLineWrap(true);        //激活自动换行功能 
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
	 * 初始化组件解密组件
	 */
	public void initDecrypt() {
		// 初始化解密界面组件
		Panel fillTop = new Panel();
		fillTop.setPreferredSize(new Dimension(Config.Width, 10)); // 上边距
		FlowLayout fLayout = new FlowLayout();
		fLayout.setVgap(22);
		decrypt.setLayout(fLayout);
		decrypt.add(fillTop);
		decryptBtn.setFont(font);
		plainText1.setLineWrap(true);        //激活自动换行功能 
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
	 * 菜单栏监听
	 */
	public void menuListener() {
		JFileChooser chooser = new JFileChooser();
		FileSystemView fsv = FileSystemView.getFileSystemView(); 			//注意了，这里重要的一句  
		chooser.setCurrentDirectory(fsv.getHomeDirectory());				//得到桌面路径 
		chooser.setDialogTitle("AXOR");
		// 打开文件监听
		open.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int result = chooser.showOpenDialog(null);
				if (result == JFileChooser.APPROVE_OPTION) {
					flag = 1;
					FileName = chooser.getSelectedFile().getPath();
					if (top.getSelectedIndex() == 0) {
						jLabel1.setText("待加密文件路径  :");
						plainText.setText(FileName);
						jLabel3.setText("加密文件:");
					}
					else if (top.getSelectedIndex() == 1) {
						jLabel11.setText("待解密文件路径  :");
						cipherText1.setText(FileName);
						jLabel33.setText("加密文件:");
					}
				}
			}
		});
		// 保存密文监听
		save.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int result = chooser.showSaveDialog(null);		// 保存界面
				String cip = cipherText.getText();
				if (cip.length() == 0) {
					JOptionPane.showMessageDialog(null, "密文为空，请先加密！");
				}else {
					BufferedWriter bWriter = null;
					File file = null;
					String fileName = null;
					if (result == JFileChooser.APPROVE_OPTION) {
						file = chooser.getSelectedFile();
						fileName = chooser.getName(file);	// 获得选择的或输入的文件名
						if (fileName == null || fileName.trim().length() == 0) {
							JOptionPane.showMessageDialog(null, "文件名为空！");
						}else {
							try {
								bWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file)));
								bWriter.write(cip);
								bWriter.close();
								JOptionPane.showMessageDialog(null, "保存密文成功！");
							} catch (Exception e2) {
								e2.printStackTrace();
							}
						}
					}
				}
			}
		});
		
		// 加密文件监听
		editFile.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				flag = 1;		// 禁止加密字符串监听
				jLabel1.setText("待加密文件路径  :");
				jLabel3.setText("加密文件:");
				jLabel11.setText("待解密文件路径  :");
				jLabel33.setText("解密文件:");
				clear();
			}
		});
		// 加密字符串监听
		editStr.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				flag = 0;
				jLabel1.setText("待加密字符串   :");
				jLabel3.setText("密文为: ");
				jLabel11.setText("待解密字符串   :");
				jLabel33.setText("明文为: ");
				clear();
			}
		});
	}
	/**
	 * 对文件加解密及写读入
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
		
		// 读入文件
		File fileRead = new File(pathname);
		InputStreamReader reader = new InputStreamReader(new FileInputStream(fileRead), "utf-8");
		BufferedReader bReader = new BufferedReader(reader);
		StringBuffer text = new StringBuffer();
		algo.ScreetKey(sc);				// 秘钥
		String resultText = "";
		if (enOrde == 0) {
			// 加密
			String t = "";
			while((t = bReader.readLine()) != null){
				text.append(t+"\n");
			}
			resultText = algo.Encrypt(text.toString());
		}else if (enOrde == 1) {
			// 解密
			text.append(bReader.readLine());
			resultText = algo.Decrypt(text.toString());
		}
				
		bReader.close();
		reader.close();
		
        // 写入文件
		File fileWrite = new File(newPath);
        if(!fileWrite.exists()){  
        	fileWrite.createNewFile();
        }
        //如果FileWriter的构造参数为true，那么就进行内容追加;
        //如果FileWriter的构造参数为false,那么就进行内容的覆盖;
        FileWriter fw = new FileWriter(fileWrite,false);
        BufferedWriter bw = new BufferedWriter(fw);
        bw.write(resultText);
        bw.flush();  
        bw.close();
        fw.close();		
        return newPath;
	}
	
	/**
	 * 监听加解密按钮
	 */
	public void listener() {
		// 加密按钮
		encryptBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				enOrde = 0;		//加密
				String sc = secretKey.getText();
				FileName = plainText.getText();
				if (sc.length() == 6 && isNum(sc) && !FileName.equals("") ) {
					if (flag == 0) {
						// 字符串加密
						algo.ScreetKey(sc);
						cipherText.setText(algo.Encrypt(FileName));
					}
					else if (flag == 1) {
						// 文件加密
						try {
							String newPath = fileOperation(FileName, secretKey.getText());
							cipherText.setText(newPath);
						} catch (Exception e1) {
							JOptionPane.showMessageDialog(null, "找不到文件!");
						}
					}
				}else {
					JOptionPane.showMessageDialog(null, "请输入正确秘钥");
					secretKey.setText("");
				}
			}
		});
		// 解密按钮
		decryptBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				enOrde = 1;		// 解密
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
							JOptionPane.showMessageDialog(null, "找不到文件!");
						}
					}
				}else {
					JOptionPane.showMessageDialog(null, "请输入正确秘钥");
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
	 * 清空文本
	 */
	public void clearListener() {
		JButton clearPlain = new JButton("清空明文");
		JButton clearSrecet = new JButton("清空秘钥");
		JButton clearCipher = new JButton("清空密文");
		JButton copyCipher = new JButton("复制密文");
		
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
		 
		
		JButton clearPlain1 = new JButton("清空明文");
		JButton clearCipher1 = new JButton("清空密文");
		
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
	 * 判断是否数值
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
	 * 注释
	 */
	public void annotation() {
		JLabel what = new JLabel("请详记秘钥！");
		font = new Font("宋体", Font.BOLD, 16);
		what.setFont(font);
		what.setForeground(Color.red);
		JLabel author = new JLabel("2018-11-11 by XJJ");
		encrypt.add(what);
		encrypt.add(author);
	}
	
}
