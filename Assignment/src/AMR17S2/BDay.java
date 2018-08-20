package AMR17S2;

import java.util.*;
import java.text.ParseException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class BDay {
	private static SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
	private String dateStr;
	private Date date;
	
	public BDay() {}
	
	public BDay(String b) {
		dateStr = b;
		String[] temp;
		if(dateStr.matches("\\d+\\D\\d+\\D\\d+")) {
			temp = dateStr.split("\\D");
			
			if(isLegalNumber(temp)) {
				if(temp.length == 3) {
					for(int i=0; i<2; ++i) {
						if(temp[i].length() < 2)
							temp[i] = "0" + temp[i];
					}
					dateStr = temp[0]+"-"+temp[1]+"-"+temp[2];
				}
				
				try {
					date = dateFormat.parse(dateStr);
				}catch(ParseException e) {
					date = null;
				}
			}else {
				date = null;
			}
		}
		
		
	}
	
	public boolean isLegalNumber(String[] temp){
		try {
			int[] check = new int[3];
			for(int i=0; i<3; i++) {
				check[i] = Integer.parseInt(temp[i]);
			}
			
			if(check[1]<=12 && check[1]>0) {
				if(check[1] == 1 || check[1] == 3 || check[1] ==5 || check[1] ==7 || check[1] ==8 || check[1] ==10 || check[1] ==12) {
					if(check[0]>0 && check[0]<=31) {
						return true;
					}else {
						return false;
					}
				}else if(check[1] == 2) {
					boolean leapYear;
					if(check[2]%4 == 0) {
						leapYear = true;
					}else {
						leapYear = false;
					}
					
					if(leapYear && check[0]>0 && check[0]<=29) {
						return true;
					}else if(!leapYear && check[0]>0 && check[0]<=28) {
						return true;
					}else {
						return false;
					}
				}else {
					if(check[0]>0 && check[0]<=30) {
						return true;
					}else {
						return false;
					}
				}
			}else {
				return false;
			}
		}catch(NumberFormatException e) {
			return false;
		}
	}
	
	public boolean isValid() {
		if (date != null)
			return true;
		else
			return false;
	}
	
	public String toString() {
		return dateFormat.format(date);
	}
	
	public Date getDate() {
		return date;
	}
}
