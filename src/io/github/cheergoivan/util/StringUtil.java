package io.github.cheergoivan.util;

import java.io.InputStream;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class StringUtil {
	public static boolean isEmpty(String s){
		return s==null||"".equals(s);
	}
	
	public static String convertStreamToString(InputStream in){
		Scanner scanner =new Scanner(in);
		scanner.useDelimiter("^");
		String content=scanner.hasNext()?scanner.next():"";
		scanner.close();
		return content;
	}
	
	public static String[] splitCmdArgs(String input){
		List<String> args=new LinkedList<>();
		if(!input.contains("\""))
			return input.split(" ");
		else{
			char[] arr=input.toCharArray();
			boolean quotaStart=false;
			StringBuilder sb=new StringBuilder();
			for(char c:arr){
				if((c==' '&&!quotaStart)||(quotaStart&&c=='"')){
					args.add(sb.toString());
					quotaStart=false;
					sb=new StringBuilder();
				}else{
					if(c=='"')
						quotaStart=true;
					else
						sb.append(c);
				}
			}
		}
		return args.toArray(new String[0]);
	}
	
	public static void printError(String msg){
		System.err.println( "error:"+msg);
	}
}
