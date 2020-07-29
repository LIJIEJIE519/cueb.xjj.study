package com.xjj.algorithm;

import java.util.ArrayList;
import java.util.List;

public class Algorithm01 {
	
	public static List<Integer> pass = new ArrayList<>();		// �������ASCLL
	public static String SecretKey = "465";						// ��Կ�ַ�
	public static int SecretNum = Integer.parseInt(SecretKey);	// ��Կ����
	
	
	// ��ȡ��Կǰ3���ַ�
	public static String ScreetKey(String ScreetKey) {
		
		return StringToA(ScreetKey);
	}
	// ��ASCLLת��Ϊ�ַ���
	public static String AToString(int i){
	    return Character.toString((char)i);
	}
	// ���ַ���ת��ΪASCLL
	public static String StringToA(String content){
		String result = "";
		int max = content.length();
		System.out.println("max: " + max);
		for (int i = 0; i < max; i++) {
			char c = content.charAt(i);
			int b = (int) c;
			pass.add(b);
			result = result + b;
		}
		System.out.println("ԭʼASCLL: " + result + " " + result.length());
		return result;
	  }
	// ����
	public static String Encrypt(String plainText) {
		System.out.println("\n--------����-------");
		
		String XOR = "";				// �������ASCLL�ۼ�ֵ
		String cipherText = "";			// ��������
		StringToA(plainText);			// ������ĵ�ASCLLֵ
		
		for (Integer integer : pass) {	// ����ASCLLֵ����Կ���
			XOR += SecretNum ^ integer;
		}
		System.out.println("���ASCLLXOR: " + XOR + " " + XOR.length());
		// ������ַ���ת��Ϊ���飬��ÿ��ȡ��λ�������
		char[] XORs = XOR.toCharArray();	
		try {
			for(int i = 0; i < XORs.length; i += 2) {
				int num = Integer.parseInt(XORs[i]+""+XORs[i+1]);
				// ����������ַ���ֱ������ֵ
				if (num < 33) {
					if (num < 10) {
						cipherText += "0"+num;
					}
					else {
						cipherText += num;
					}
				}
				// ���������ֵֵ
				else {
					num += 25;
					cipherText += AToString(num);
				}
			}
		} catch (Exception e) {
			int last = Integer.parseInt(XORs[XORs.length-1]+"");
			if (last < 33) {
				cipherText += last;
			}
			else {
				cipherText += AToString(last);
			}
		}
		System.out.println("��������Ϊ: " + cipherText + " " + cipherText.length());
		
		return cipherText;
	}
	// ����
	public static void Decrypt(String plain) {
		System.out.println("\n-------����---------");
		
		String cipherText = Encrypt(plain);
		char[] cips = cipherText.toCharArray();
		String XOR = "";						// ������ĵ�ASCLL�ۼ�ֵ
		String plainText = "";					// ��������
		for(int i = 0; i < cips.length; i++) {
			int Acip = cips[i];					// ȡ����ASCLLֵ
			if (Acip >= 48 && Acip <=57) {		// �������ֵ����ȡԭֵ
				try {
					XOR += cips[i]+""+cips[i+1];
					i++;
				} catch (Exception e) {
					XOR += cips[i];		// �������һλ
					i++;
				}
				continue;
			}
			XOR += Acip-25;				// ����ֵ��ȡASCLLֵ����֮ǰ��25
		}
		System.out.println("XOR: "+XOR+" "+XOR.length());
		char[] cXOR = XOR.toCharArray();		// ת��Ϊ�ַ������������ֵ
		// ��Ϊ��ԿΪ3��������ȡ3λ����Կ���õ�ԭASCLLֵ
		for (int i = 0; i < cXOR.length; i += 3) {
			int plainInt = Integer.parseInt(cXOR[i] + "" + cXOR[i+1] + "" + cXOR[i+2]);
			// �������
			plainText += AToString(SecretNum ^ plainInt);
		}
		System.out.println("A1���� :" + plainText);
	}

	public static void main(String[] args) {
		System.out.println("this is a password");
		String str = "this is a password";
		
		Decrypt(str);
	}

}
