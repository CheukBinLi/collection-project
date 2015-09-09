package org.code.demo.x;

import java.util.ArrayList;
import java.util.List;

public class sort {

	public String x(String in, int next) {
		StringBuffer sb = new StringBuffer(in.substring(0, in.length() - 1));
		char value = in.charAt(in.length() - 1);
		System.out.println(sb.toString());
		if ((value >= 0 && value < 9) || (value >= 65 && value < 90))
			sb.append((char) (((int) value) + next));
		else
			sb.append(value).append(0);
		System.out.println(sb.toString());
		List<Character> AZ = new ArrayList<Character>();
		for (int i = 65; i < 91; i++)
			AZ.add((char) i);
		System.out.println(AZ);

		return null;
	}

	public static void main(String[] args) {
		System.out.println((int) 'A' + "  " + (int) 'Z');
//		new sort().x("a123Z9");
	}

	public String x2(String str, int next) {
		//分析next的倍数+余数 10%
		//分析倍数进位 （前一位ABC/0-9）
		
		
		return null;
	}

}
