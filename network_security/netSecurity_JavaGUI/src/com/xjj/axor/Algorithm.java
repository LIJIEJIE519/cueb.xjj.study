package com.xjj.axor;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * AXOR加密算法
 * @author XJJ
 *
 */
public class Algorithm {
	
	public static List<Integer> pass;	// 保存加密ASCLL
	public int SecretNum ;	// 秘钥数字
	
	// 提取秘钥3个字符并处理
	public void ScreetKey(String Key) {
		String[] s = Key.split("");
		int num = Integer.parseInt(s[1]+s[3]+s[5]);
		if (num < 200) {
			SecretNum = num + 100;
			return;
		}
		SecretNum = num;
	}
	// 将ASCLL转换为字符串
	public static String AToString(int i){
	    return Character.toString((char)i);
	}
	// 将字符串转换为ASCLL
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
	// 加密
	public String Encrypt(String plainText) {		
		String XOR = "";				// 保存异或ASCLL累加值
		String cipherText = "";			// 保存密文
		StringToA(plainText);			// 获得密文的ASCLL值
		
		for (Integer integer : pass) {	// 真正ASCLL值与秘钥异或
			XOR += SecretNum ^ integer;
		}
		// 将异或字符串转换为数组，并每次取两位获得密文
		char[] XORs = XOR.toCharArray();	
		try {
			for(int i = 0; i < XORs.length; i += 2) {
				int num = Integer.parseInt(XORs[i]+""+XORs[i+1]);
				// 如果是特殊字符则直接用数值
				if (num < 33) {
					if (num < 10) {
						cipherText += "0"+num;
					}
					else {
						cipherText += num;
					}
				}
				// 否则避免数值值
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
	// 解密
	public String Decrypt(String plain) {
		
		String cipherText = plain;
		char[] cips = cipherText.toCharArray();
		String XOR = "";						// 获得密文的ASCLL累加值
		String plainText = "";					// 保存明文
		for(int i = 0; i < cips.length; i++) {
			int Acip = cips[i];					// 取得其ASCLL值
			if (Acip >= 48 && Acip <=57) {		// 如果是数值型则取原值
				try {
					XOR += cips[i]+""+cips[i+1];
					i++;
				} catch (Exception e) {
					XOR += cips[i];		// 处理最后一位
					i++;
				}
				continue;
			}
			XOR += Acip-25;				// 非数值则取ASCLL值减掉之前的25
		}
		char[] cXOR = XOR.toCharArray();		// 转化为字符串处理，异或后的值
		// 因为秘钥为3，故依次取3位与秘钥异或得到原ASCLL值
		for (int i = 0; i < cXOR.length; i += 3) {
			int plainInt = Integer.parseInt(cXOR[i] + "" + cXOR[i+1] + "" + cXOR[i+2]);
			// 获得明文
			plainText += AToString(SecretNum ^ plainInt);
		}
		return plainText;
	}
	
	public static void main(String[] args) {
		System.out.println("------欢迎使用AXOR加密程序------\n");
		System.out.println("输入加密字符串: ");
		Scanner scan = new Scanner(System.in);
		String plainText = scan.nextLine();
		System.out.println("输入秘钥(6位数字): ");
		String SecretKey = scan.nextLine();
		
		Algorithm algo = new Algorithm();
		algo.ScreetKey(SecretKey);
		System.out.println("\n密文为: ");
		System.out.println(algo.Encrypt(plainText));
		
		System.out.println("\n输入解密字符串");
		String cipherText = scan.nextLine();
		System.out.println("输入秘钥(6位数字)");
		SecretKey = scan.nextLine();
		algo.ScreetKey(SecretKey);
		System.out.println("明文为: ");
		System.out.println(algo.Decrypt(cipherText));
	}
}
