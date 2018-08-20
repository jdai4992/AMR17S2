package AMR17S2;

public class Mileage {
	
	private double mileage;
	
	public Mileage() {}

	public Mileage(String p) {
			
		p = p.trim();
		if (!p.endsWith("km")) {
			mileage = -1;
			
		}else {
			try {
				mileage = Double.parseDouble(p.substring(0,p.length()-2));
			} catch (Exception e) {
				mileage = -1;
			}
		}
	}
	
	
	public double getMileageValue() {
		return mileage;
	}
	
	public void setMileageValue(double mileage) {
		this.mileage = mileage;
	}
	
	public boolean isValidMileage() {
		return mileage>=0;
	}
	
	public String toString() {
		if(isValidMileage()) {
			return String.format("%.2fkm",mileage);
		}else {
			return null;
		}
	}
}
