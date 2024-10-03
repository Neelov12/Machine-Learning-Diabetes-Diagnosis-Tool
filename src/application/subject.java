package application;

public class subject {
	
	private double[] factor = new double[9]; 

	public subject(double p, double g, double bp, double st, double i, double b, double d, double a, double o) {
		
		/*
		 * 1D Double array factor stores factor information 
		 * 
		 * 0 - int pregnancy
		 * 1 - int glucose
		 * 2 - int blood pressure
		 * 3 - int skin thickness
		 * 4 - int insulin
		 * 5 - double bmi 
		 * 6 - double dpf
		 * 7 - int age 
		 * 8 - int outcome
		 */
		
		this.factor[0] = p; 
		this.factor[1] = g; 
		this.factor[2] = bp; 
		this.factor[3] = st; 
		this.factor[4] = i; 
		this.factor[5] = b; 
		this.factor[6] = d; 
		this.factor[7] = a; 
		this.factor[8] = o; 
	}

	public subject() {
		// TODO Auto-generated constructor stub
		this.factor[0] = 0.0; 
		this.factor[1] = 0.0;  
		this.factor[2] = 0.0; 
		this.factor[3] = 0.0; 
		this.factor[4] = 0.0; 
		this.factor[5] = 0.0;  
		this.factor[6] = 0.0; 
		this.factor[7] = 0.0;  
		this.factor[8] = 0.0; 
	}
	
	
	public void print() {
		
			 System.out.println("Pregnancies:" 		+ factor[0] +  "," + 
								" Glucose: " 		+ factor[1] + 
								" Blood Pressure: " + factor[2] +  "," + 
								" Skin Thickness: " + factor[3] +  "," + 
			  					" Insulin: " 		+ factor[4] +  "," + 
			  					" BMI: " 			+ factor[5] +  "," + 
			  					" DPF: " 			+ factor[6] +  "," + 
			  					" Age: " 			+ factor[7] + "\n" + 
			  					" Outcome: " 		+ factor[8]);  
	}
	
	public double getFactor(int index) {
		return factor[index];
	}
}
