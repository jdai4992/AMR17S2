package AMR17S2;

import java.util.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class Member {
	
	private String number;
	private String name;
	private BDay birthday;
	private String tier;
	private Mileage mileage;
	private double points;
	private String address;
	
	public Member () {
		birthday = new BDay();
		mileage = new Mileage();
		tier = "Silver";
	}
	
	
	public Member (String s) throws Exception{
		
		birthday = new BDay("");
		mileage = new Mileage("0.00km");
		tier = "Silver";
		
		String[] temp = s.trim().split("\\;");
		for(int j=0;j<temp.length;j++) {
			Scanner sc = new Scanner(temp[j]);
			String keyword, param;
			if(sc.hasNext()) {
				keyword = sc.next();
				if (sc.hasNextLine()){
					param = sc.nextLine().trim();
					if(keyword.equals("number")) {
						number = param;
					}else if(keyword.equals("name")) {
						name = param;
					}else if(keyword.equals("tier")) {
						tier = param;
					}else if(keyword.equals("mileage")) {
						mileage = new Mileage(param);
					}else if(keyword.equals("points")) {
						points = Double.parseDouble(param);
					}else if(keyword.equals("address")) {
						address = param;
					}else if(keyword.equals("birthday")){
						birthday = new BDay(param);
					}
				}else {
					continue;
				}
				sc.close();
			}else {
				continue;
			}
		}
	}
	
	public void setNumber(String number) {
		this.number = number;
	}
	public String getNumber() {
		return number;
	}	
	public void setName(String name) {
		this.name = name;
	}
	public String getName() {
		return name;
	}
	public BDay getBirthday() {
		return birthday;
	}
	public void setBirthday(BDay birthday) {
		this.birthday = birthday;
	}
	public String getTier() {
		return tier;
	}
	public void setTier(String tier) {
		this.tier = tier;
	}
	public Mileage getMileage() {
		return mileage;
	}
	public void setMileage(Mileage mileage) {
		this.mileage = mileage;
	}
	public double getPoints() {
		return points;
	}
	public void setPoints(double points) {
		this.points = points;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	
	public boolean isValidNumber() {
		if(number == null) {
			return false;
		}else {
			if(number.length() == 5 && number.matches("[0-9]+")) {
				return true;
			}else {
				return false;
			}
		}
	}
	
	public boolean isValidName() {
		if(name != null && name.matches("[a-zA-Z\\s]+")){
			return true;
		}else {
			return false;
		}
	}
	
	public boolean isValidTier() {
		if(tier == null) {
			return false;
		}else {
			if (tier.equalsIgnoreCase("Silver") || tier.equalsIgnoreCase("Gold") || tier.equalsIgnoreCase("Platinum")) {
				return true;
			}else {
				return false;
			}
		}
	}
	
	public boolean isValidAddress() {
		if(address == null) {
			return false;
		}else {
			String test = address.substring(address.length()-4, address.length());
			if(test.matches("[0-9]+"))
				return true;
			else
				return false;
		}
	}
	
	public void updateTier() {
		if(points < 5000 || tier.isEmpty()) {
			tier = "Silver";
		}else if(points < 10000 && points >= 5000) {
			tier = "Gold";
		}else if (points >= 10000) {
			tier = "Platinum";
		}
	}
	
	public int getAgeByBirth() {
		int age = 0;
		try {
			Calendar now = Calendar.getInstance();
			now.setTime(new Date());
	
		    Calendar birth = Calendar.getInstance();
		    birth.setTime(birthday.getDate());
	
		    if (birth.after(now) || !(birthday.isValid())) {
		    		age = 0;
		    } else {
		    		age = now.get(Calendar.YEAR) - birth.get(Calendar.YEAR);
		    		if (now.get(Calendar.DAY_OF_YEAR) > birth.get(Calendar.DAY_OF_YEAR)) {
		    			age += 1;
		    		}
		    	}
		    return age;
		} catch (Exception e) {
			return 0;
		}
	}
	
	public String toString() {
		
		StringBuilder sb = new StringBuilder();
		sb.append("number: " + number + "\n");
		sb.append("name: " + name + "\n");
		if(!(tier == ""))
			sb.append("tier: " + tier + "\n");
		if(!(mileage.getMileageValue() <= 0))
			sb.append("mileage: " + mileage.toString() + "\n");
		if(!(points==0))
			sb.append("points: " + points + "\n");
		if(isValidAddress())
			sb.append("address: " + address + "\n");
		if(birthday.isValid())
			sb.append("birthday: " + birthday.toString());
		return sb.toString();
	}
}

