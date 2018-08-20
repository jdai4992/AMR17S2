package AMR17S2;

public class AMR {

	public static void main(String[] args) throws Exception{
		// TODO Auto-generated method stub
		if(args.length == 4) {
			MembershipProcessor mp = new MembershipProcessor(args);
			mp.readMember();
			mp.readInstruction();
			mp.saveResult();
		}
	}

}
