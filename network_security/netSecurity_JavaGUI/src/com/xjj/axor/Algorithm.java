package com.xjj.axor;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * AXOR�����㷨
 * @author XJJ
 *
 */
public class Algorithm {
	
	public static List<Integer> pass;	// �������ASCLL
	public int SecretNum ;	// ��Կ����
	
	// ��ȡ��Կ3���ַ�������
	public void ScreetKey(String Key) {
		String[] s = Key.split("");
		int num = Integer.parseInt(s[1]+s[3]+s[5]);
		if (num < 200) {
			SecretNum = num + 100;
			return;
		}
		SecretNum = num;
	}
	// ��ASCLLת��Ϊ�ַ���
	public static String AToString(int i){
	    return Character.toString((char)i);
	}
	// ���ַ���ת��ΪASCLL
	public static String StringToA(String content){
		String result = "";
		pass = new ArrayList<>();
		int max = content.length();
		for (int i = 0; i < max; i++) {
			char c = content.charAt(i);
			int b = (int) c;
			pass.add(b);
			result = result + b;
		}
		return result;
	  }
	// ����
	public String Encrypt(String plainText) {		
		String XOR = "";				// �������ASCLL�ۼ�ֵ
		String cipherText = "";			// ��������
		StringToA(plainText);			// ������ĵ�ASCLLֵ
		
		for (Integer integer : pass) {	// ����ASCLLֵ����Կ���
			XOR += SecretNum ^ integer;
		}
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
		
		return cipherText;
	}
	// ����
	public String Decrypt(String plain) {
		
		String cipherText = plain;
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
		char[] cXOR = XOR.toCharArray();		// ת��Ϊ�ַ������������ֵ
		// ��Ϊ��ԿΪ3��������ȡ3λ����Կ���õ�ԭASCLLֵ
		for (int i = 0; i < cXOR.length; i += 3) {
			int plainInt = Integer.parseInt(cXOR[i] + "" + cXOR[i+1] + "" + cXOR[i+2]);
			// �������
			plainText += AToString(SecretNum ^ plainInt);
		}
		return plainText;
	}
	
	public static void main(String[] args) {
		System.out.println("------��ӭʹ��AXOR���ܳ���------\n");
		System.out.println("��������ַ���: ");
		Scanner scan = new Scanner(System.in);
		String plainText = scan.nextLine();
		System.out.println("������Կ(6λ����): ");
		String SecretKey = scan.nextLine();
		
		Algorithm algo = new Algorithm();
		algo.ScreetKey(SecretKey);
		System.out.println("\n����Ϊ: ");
		System.out.println(algo.Encrypt(plainText));
		
		System.out.println("\n��������ַ���");
		String cipherText = scan.nextLine();
		System.out.println("������Կ(6λ����)");
		SecretKey = scan.nextLine();
		algo.ScreetKey(SecretKey);
		System.out.println("����Ϊ: ");
		System.out.println(algo.Decrypt(cipherText));
	}
}
