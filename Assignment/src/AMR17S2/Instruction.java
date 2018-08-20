package AMR17S2;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.Collections;

public class Instruction {
	ArrayList<Member> memberList;
	ArrayList<Member> reportMember;
	
	public Instruction() {
		memberList = new ArrayList<Member>();
		reportMember = new ArrayList<Member>();
	}
	
	public void addMember(String s) throws Exception{
		Member a = new Member(s);
		Member b = new Member();
		boolean addOrNot = true;
		
		if(a.isValidNumber())
			b.setNumber(a.getNumber());
		if(a.isValidName())
			b.setName(a.getName());
		if(a.isValidAddress())
			b.setAddress(a.getAddress());
		if(a.isValidTier() && !a.getTier().equalsIgnoreCase("Silver"))
			b.setTier(a.getTier());
		if(a.getMileage(). isValidMileage())
			b.setMileage(a.getMileage());
		if(!(a.getPoints()==0))
			b.setPoints(a.getPoints());
		if(a.getBirthday().isValid())
			b.setBirthday(a.getBirthday());


		for(Member m : memberList) {
			if(a.getNumber().equals(m.getNumber())) {
				if(a.isValidName())
					m.setName(a.getName());
				if(a.isValidAddress())
					m.setAddress(a.getAddress());
				if(a.isValidTier() && !a.getTier().equalsIgnoreCase("Silver"))
					m.setTier(a.getTier());
				if(!(a.getMileage().getMileageValue()==0))
					m.setMileage(a.getMileage());
				if(!(a.getPoints()==0))
					m.setPoints(a.getPoints());
				if(a.getBirthday().isValid())
					m.setBirthday(a.getBirthday());
				addOrNot = false;
			}
		}
		
		if(addOrNot && b.isValidNumber() && b.isValidName()) {
			memberList.add(b);
		}
	}
	
	public void deleteMember(String s) {	
		
		Scanner sc = new Scanner(s);
		String keyword, param;
		
		if(sc.hasNext()) {
			keyword = sc.next();
			if (sc.hasNextLine()){
				param = sc.nextLine().trim();
				param = param.replace(";","");
				if(keyword.equals("number")) {
					for(int i=memberList.size()-1;i>=0;i-- ) {
						if(memberList.get(i).getNumber().equals(param))
							memberList.remove(i);
					}
				}else if(keyword.equals("name")) {
					for(int i=memberList.size()-1;i>=0;i-- ){
						if(param.equals(memberList.get(i).getName()))
							memberList.remove(i);
					}
				}
			}
		}
		sc.close();
	}
	
	public void earn(String s) {
		
		Scanner sc = new Scanner(s);
		String param;
		
		if(sc.hasNext()) {
			if (sc.hasNextLine()){
				param = sc.nextLine().trim();
				param = param.replace(";","");
				
				String[] temp = param.trim().split("\\s");
				Mileage earnMileage = new Mileage(temp[3]);
				
				for(Member a :memberList) {
					if(a.getNumber().equals(temp[1]) && earnMileage.isValidMileage()) {
						a.getMileage().setMileageValue(a.getMileage().getMileageValue() + earnMileage.getMileageValue());
						if(a.getTier().equalsIgnoreCase("silver")) {
							a.setPoints(a.getPoints() + (0.25*earnMileage.getMileageValue()));
							a.updateTier();
						}else if(a.getTier().equalsIgnoreCase("gold")) {
							a.setPoints(a.getPoints() + (0.5*earnMileage.getMileageValue()));
							a.updateTier();
						}else if(a.getTier().equalsIgnoreCase("platinum")) {
							a.setPoints(a.getPoints() + earnMileage.getMileageValue());
							a.updateTier();
						}else {
							a.setPoints(a.getPoints() + 0.25*earnMileage.getMileageValue());
							a.updateTier();
						}
					}
				}
			}
		}
		
		sc.close();
	}
	
	public void redeem(String s) {
		
		Scanner sc = new Scanner(s);
		String param;
		
		if(sc.hasNext()) {
			if (sc.hasNextLine()){
				param = sc.nextLine().trim();
				param = param.replace(";","");
				String[] temp = param.trim().split("\\s");
				double point = Double.parseDouble(temp[3]);
				
				for(Member a :memberList) {
					if(a.getNumber().equals(temp[1])) {
						if(a.getPoints()>point) {
							a.setPoints(a.getPoints()-point);
							a.updateTier();
						}
					}
				}
			}			
		}	
		sc.close();
	}
	
	public void query(String s) {
		Scanner sc = new Scanner(s);
		String keyword, param;
		if(sc.hasNext()) {
			keyword = sc.next();
			if (sc.hasNextLine()) {
				param = sc.nextLine().trim();
				param = param.replace(";","");
				
				if(keyword.equalsIgnoreCase("tier")) {
					for(Member a: memberList) {
						if(a.getTier().equalsIgnoreCase(param)) {
							reportMember.add(a);
						}
					}
				}
			}
		}
		sortReport();
		sc.close();
	}
	
	public String queryBasedAge() {
		
		double count08 = 0;
		double count818 = 0;
		double count1865 = 0;
		double count65 = 0;
		double unknown = 0;
		
		for (Member a: memberList) {
			if(a.getAgeByBirth()>0 && a.getAgeByBirth() <= 8) {
				count08 += a.getMileage().getMileageValue();
			}else if(a.getAgeByBirth()>8 && a.getAgeByBirth() <= 18) {
				count818 += a.getMileage().getMileageValue();
			}else if(a.getAgeByBirth()>18 && a.getAgeByBirth() <= 65) {
				count1865 += a.getMileage().getMileageValue();
			}else if(a.getAgeByBirth()>65) {
				count65 += a.getMileage().getMileageValue();
			}else {
				unknown += a.getMileage().getMileageValue();
			}
		}
		
		StringBuilder sb = new StringBuilder();
		sb.append("Total Airline Members:" + memberList.size());
		sb.append("\n");
		sb.append("Age based mileage distribution");
		sb.append("\n");
		sb.append("(0, 8]: "+count08);
		sb.append("\n");
		sb.append("(8, 18]: "+count818);
		sb.append("\n");
		sb.append("(18, 65]: "+count1865);
		sb.append("\n");
		sb.append("(65, -): "+count65);
		sb.append("\n");
		sb.append("Unknown: " + unknown);
		sb.append("\n");
		return sb.toString();
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for(Member a: memberList) {
			sb.append(a.toString());
			sb.append("\n\n");
		}
		return sb.toString();
	}
	
	public String toStringReport() {
		StringBuilder sb = new StringBuilder();
		for(Member a: reportMember) {
			sb.append(a.toString());
			sb.append("\n\n");
		}
		reportMember = new ArrayList<Member>();
		return sb.toString();
	}
	
	public void sortReport() {
		
		Member temp;
		
		for(int i=0; i<reportMember.size(); i++)
        {
            for(int j=1; j<reportMember.size(); j++)
            {
                if(reportMember.get(j-1).getName().compareTo(reportMember.get(j).getName())>0)
                {
                    temp=reportMember.get(j-1);
                    reportMember.set(j-1, reportMember.get(j));
                    reportMember.set(j, temp);
                }else if(reportMember.get(j-1).getName().compareTo(reportMember.get(j).getName())==0) {
                		if(reportMember.get(j-1).getNumber().compareTo(reportMember.get(j).getNumber())>0) {
                			temp=reportMember.get(j-1);
                         reportMember.set(j-1, reportMember.get(j));
                         reportMember.set(j, temp);
                		}
                }
            }
        }
	}
	
	public ArrayList<Member> getMemberList(){
		return memberList;
	}
	
	public void setMemberList(ArrayList<Member> memberList) {
		this.memberList = memberList;
	}
}
