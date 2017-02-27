package com.yazilimokulu.utils;

public class StringUtil {
	
	public static String clearTurkishChars(String str) {
		  String ret = str;
			char[] turkishChars = new char[] {0x131, 0x130, 0xFC, 0xDC, 0xF6, 0xD6, 0x15F, 0x15E, 0xE7, 0xC7, 0x11F, 0x11E};
			char[] englishChars = new char[] {'i', 'I', 'u', 'U', 'o', 'O', 's', 'S', 'c', 'C', 'g', 'G'};
			for (int i = 0; i < turkishChars.length; i++) {
				ret = ret.replaceAll(new String(new char[]{turkishChars[i]}), new String(new char[]{englishChars[i]}));
			}
			return ret;
		}

}
