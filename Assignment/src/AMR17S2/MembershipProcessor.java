package AMR17S2;

import java.util.Scanner;
import java.io.*;

public class MembershipProcessor {
	private File inputMembersFile;
	private File inputInstructionFile;
	private File outputResultFile;
	private File outputReportFile;
	private Instruction instruction;
	
	public MembershipProcessor(String[] s) {
		inputMembersFile = new File(s[0]);
		inputInstructionFile = new File(s[1]);
		outputResultFile = new File(s[2]);
		outputReportFile = new File(s[3]);
		instruction = new Instruction();
	}
	
	public void saveResult() {
		try {
			PrintWriter out = new PrintWriter(new FileOutputStream(outputResultFile));
			out.println(instruction.toString());
			out.close();
		}catch(FileNotFoundException e){
			e.printStackTrace();
		}
	}
	
	public void readMember() throws Exception{
		try {
			Scanner scan = new Scanner(inputMembersFile);
			
			String s = "";
			while(scan.hasNextLine()) {
				String information = scan.nextLine();
				information = information.trim();
				if(!information.equals("")) {
					if(information.charAt(information.length()-1) == ',') {
						s=s+information + " ";
					}else {
						s=s+information+";";
					}
				}else{
					if(!s.equals("")) {
						instruction.addMember(s);
						s="";
					}
				}
				
			} 
			if(!s.equals(""))
				instruction.addMember(s);
		}catch(FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public void readInstruction() throws Exception{
		try {
			Scanner scan = new Scanner(inputInstructionFile);
			while(scan.hasNextLine()) {
				String behaviour = scan.nextLine();
				Scanner sc = new Scanner(behaviour);
				String keyword, param;
				if(sc.hasNext()) {
					keyword = sc.next();
					if(sc.hasNextLine()) {
						param = sc.nextLine();
						if(keyword.equalsIgnoreCase("add")) {
							instruction.addMember(param);
						}else if(keyword.equalsIgnoreCase("delete")) {
							instruction.deleteMember(param);
						}else if(keyword.equalsIgnoreCase("redeem")) {
							instruction.redeem(param);
						}else if(keyword.equalsIgnoreCase("earn")) {
							instruction.earn(param);
						}else if(keyword.equalsIgnoreCase("query")) {
							param = param.trim();
							if(param.equalsIgnoreCase("age mileage")) {
								instruction.queryBasedAge();
								try {
									PrintWriter out = new PrintWriter(new FileWriter(outputReportFile, true));
									out.println("-----------" + keyword + " " + param + "-----------");
									out.println(instruction.queryBasedAge());
									out.println("-------------------------------------");
									out.close();
								}catch(IOException e) {
									e.printStackTrace();
								}
							}else {
								instruction.query(param);
								try {
									PrintWriter out = new PrintWriter(new FileWriter(outputReportFile, true));
									out.println("-----------" + keyword + " " + param + "-----------");
									out.println(instruction.toStringReport());
									out.println("-------------------------------");
									out.close();
								}catch(IOException e) {
									e.printStackTrace();
								}
							}
						}
					}else {
						continue;
					}
					sc.close();
				}else {
					continue;
				}
			}
			scan.close();
		}catch(FileNotFoundException e) {
			e.printStackTrace();
		}
	}
}
