package com.pj.functions;

import java.net.*;
import java.io.*;

public class nameGenerator {
	private static String getRandomName(boolean maleName) throws Exception {
		URL website = new URL("http://www.behindthename.com/random/random.php?number=1&gender=m&surname=&randomsurname=yes&all=no&usage_eng=1");
		if (!maleName){
			website = new URL("http://www.behindthename.com/random/random.php?number=1&gender=f&surname=&randomsurname=yes&all=no&usage_eng=1");
		}
		
		URLConnection connection = website.openConnection();
		BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));

		StringBuilder response = new StringBuilder();
		String inputLine;

		boolean doNext = false;
		while ((inputLine = in.readLine()) != null) {
			if (doNext)
				response.append(inputLine);
			if (inputLine.contains("<p><span class=\"heavyhuge\">"))
				doNext = true;
		}
		in.close();

		String nameLine = response.toString();
		nameLine = nameLine.replace(" <a class=\"plain\" href=\"/name/", "");
		
		String firstname = nameLine.subSequence(0, 1).toString().toUpperCase();
		nameLine = nameLine.substring(1);
		while (!nameLine.subSequence(0, 1).equals("\"")){
			firstname = firstname + nameLine.subSequence(0, 1);
			nameLine = nameLine.substring(1);
		}
		nameLine = nameLine.substring(2+firstname.length() + 68);
		
		String surname = nameLine.subSequence(0, 1).toString().toUpperCase();
		nameLine = nameLine.substring(1);
		while (!nameLine.subSequence(0, 1).equals("\"")){
			surname = surname + nameLine.subSequence(0, 1);
			nameLine = nameLine.substring(1);
		}
		
		return firstname + " " + surname;
	}
	
	public static String getRandomFirstname(boolean maleName){
		try {
			return getRandomName(maleName).split(" ")[0];
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "";
		}
	}
	
	public static String getRandomSurname(boolean maleName){
		try {
			return getRandomName(maleName).split(" ")[1];
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "";
		}
	}

	// public static void main(String[] args) throws Exception {
	// String content = URLConnectionReader.getText(args[0]);
	// System.out.println(content);
	// }
}