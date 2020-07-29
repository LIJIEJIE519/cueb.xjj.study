package com.xjj.algorithm;

import java.util.ArrayList;
import java.util.List;

public class Algorithm01 {
	
	public static List<Integer> pass = new ArrayList<>();		// 保存加密ASCLL
	public static String SecretKey = "465";						// 秘钥字符
	public static int SecretNum = Integer.parseInt(SecretKey);	// 秘钥数字
	
	
	// 提取秘钥前3个字符
	public static String ScreetKey(String ScreetKey) {
		
		return StringToA(ScreetKey);
	}
	// 将ASCLL转换为字符串
	public static String AToString(int i){
	    return Character.toString((char)i);
	}
	// 将字符串转换为ASCLL
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
		System.out.println("原始ASCLL: " + result + " " + result.length());
		return result;
	  }
	// 加密
	public static String Encrypt(String plainText) {
		System.out.println("\n--------加密-------");
		
		String XOR = "";				// 保存异或ASCLL累加值
		String cipherText = "";			// 保存密文
		StringToA(plainText);			// 获得密文的ASCLL值
		
		for (Integer integer : pass) {	// 真正ASCLL值与秘钥异或
			XOR += SecretNum ^ integer;
		}
		System.out.println("异或ASCLLXOR: " + XOR + " " + XOR.length());
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
		System.out.println("加密密文为: " + cipherText + " " + cipherText.length());
		
		return cipherText;
	}
	// 解密
	public static void Decrypt(String plain) {
		System.out.println("\n-------解密---------");
		
		String cipherText = Encrypt(plain);
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
		System.out.println("XOR: "+XOR+" "+XOR.length());
		char[] cXOR = XOR.toCharArray();		// 转化为字符串处理，异或后的值
		// 因为秘钥为3，故依次取3位与秘钥异或得到原ASCLL值
		for (int i = 0; i < cXOR.length; i += 3) {
			int plainInt = Integer.parseInt(cXOR[i] + "" + cXOR[i+1] + "" + cXOR[i+2]);
			// 获得明文
			plainText += AToString(SecretNum ^ plainInt);
		}
		System.out.println("A1解密 :" + plainText);
	}

	public static void main(String[] args) {
		System.out.println("this is a password");
		String str = "this is a password";
		
		Decrypt(str);
	}

}
