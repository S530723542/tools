package com.key.tools;

import java.text.SimpleDateFormat;
import java.util.Date;



public class Test
{
	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
	
	public static void main(String[] args) throws Exception
	{
		Date date=sdf.parse("2015/12/11");
		
		System.out.println(date);
	}
}
