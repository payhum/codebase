package com.openhr.company;

import java.util.Calendar;
import java.util.Date;

import javax.xml.bind.DatatypeConverter;

public class LicenseValidator {
	
	public static String encrypt(String strToBeEncrypted) {
		strToBeEncrypted = strToBeEncrypted.replace(" ", "");
		strToBeEncrypted = strToBeEncrypted.replace("_", "");
		
		byte[] retBytes = DatatypeConverter.parseBase64Binary(strToBeEncrypted);
		StringBuilder retStrBuilder = new StringBuilder();
		
		for(int i = 0; i < retBytes.length; i++) {
			retStrBuilder.append(retBytes[i]);
		}
		
		return retStrBuilder.toString();
	}
	
	public static boolean encryptAndCompare(String strToBeEncrypted,
											String toCompare) {
		String retval = encrypt(strToBeEncrypted);
		
		return retval.equalsIgnoreCase(toCompare);
	}
	
	public static String formStringToEncrypt(String companyName, Date endDate) {
		companyName = companyName.replace(" ", "");
		companyName = companyName.replace("_", "");
		
		Calendar currDtCal = Calendar.getInstance();
		currDtCal.setTime(endDate);
		
	    // Zero out the hour, minute, second, and millisecond
	    currDtCal.set(Calendar.HOUR_OF_DAY, 0);
	    currDtCal.set(Calendar.MINUTE, 0);
	    currDtCal.set(Calendar.SECOND, 0);
	    currDtCal.set(Calendar.MILLISECOND, 0);
	    
	    int month = currDtCal.get(Calendar.MONTH);
        int year = currDtCal.get(Calendar.YEAR);

		String retStr = companyName + Integer.toString(month) + Integer.toString(year);
		
		return retStr;
	}
}
