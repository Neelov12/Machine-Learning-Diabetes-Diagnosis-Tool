package application;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class vector  {
	
	/*
	 * LIST HIERACHY 
	 * ---------------
	 * 
	 * List<List<Vector<Double>>> list:  
	 * 		- Has (8) elements
	 * 		- Each element stores list of (2) vectors representing factors with outcome 0 in one list and 1 in another 
	 * 
	 * -> List<Vector<Double>>:
	 * 		- Has (2) elements
	 * 		- Stores two vectors, one representing factor with outcome 0 and another outcome 1
	 * 
	 * -> Vector<Double>:
	 * 		- Size depends on which data point corresponds to which outcome
	 * 		- HOWEVER, both vectors in parent list must add up to 768 (number of subjects in study)
	 * 		- Stores factors corresponding to an outcome 
	 * 
	 * EXAMPLE:
	 *  
	 * List<Vector<Double>> listOfTwoVectors = list.get(0);
	 * Vector<Double> vectorsWithOutcome0 = listOfTwoVectors.get(0)
	 * 
	 */
	
	public List<List<Vector<Double>>> list = new ArrayList<>(8);
	
	public List<Vector<Double>> factor_data = new ArrayList<>(8); //Stores risk factor vectors 
	public Vector<Double> outcomes = new Vector<>(768); 
	
	public static double[][] coefs = new double[8][2];
	
	public void createVectors() {
		/*
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
		
		createOutcomeVector(); 
		
		for(int i = 0; i < 8; i++) {
			makeVector(i);
		}
		
		for(int i = 0; i < 8; i++) {
			makeSeperatedVectors(i);
		}
		//calculate(); 
		//printVector(0);

		
		logRegression log0 = new logRegression(factor_data.get(0), outcomes, 6.0, 10.0);
		coefs[0][0] =  log0.getCoefficients()[0];
		coefs[0][1] =  log0.getCoefficients()[1];
		System.out.println("0 Coefficients: " + coefs[0][0] + ", " + coefs[0][1]);
		
		
		logRegression log1 = new logRegression(factor_data.get(1), outcomes, 10.0, 6.0);
		coefs[1][0] =  log1.getCoefficients()[0];
		coefs[1][1] =  log1.getCoefficients()[1];
		System.out.println("1 Coefficients: " + coefs[1][0] + ", " + coefs[1][1]);
		
		

		logRegression log2 = new logRegression(factor_data.get(2), outcomes, 10.0, 6.0);
		coefs[2][0] =  log2.getCoefficients()[0];
		coefs[2][1] =  log2.getCoefficients()[1];
		System.out.println("2 Coefficients: " + coefs[2][0] + ", " + coefs[2][1]);
		
		
		
		logRegression log3 = new logRegression(factor_data.get(3), outcomes, 5.0, 10.0);
		coefs[3][0] =  log3.getCoefficients()[0];
		coefs[3][1] =  log3.getCoefficients()[1];
		System.out.println("3 Coefficients: " + coefs[3][0] + ", " + coefs[3][1]);
		
		

		logRegression log4 = new logRegression(factor_data.get(4), outcomes, 5.0, 10.0);
		coefs[4][0] =  log4.getCoefficients()[0];
		coefs[4][1] =  log4.getCoefficients()[1];
		System.out.println("4 Coefficients: " + coefs[4][0] + ", " + coefs[4][1]);
		
		
		
		logRegression log5 = new logRegression(factor_data.get(5), outcomes, 5.0, 10.0);
		coefs[5][0] =  log5.getCoefficients()[0];
		coefs[5][1] =  log5.getCoefficients()[1];
		System.out.println("5 Coefficients: " + coefs[5][0] + ", " + coefs[5][1]);
		
		
	
		logRegression log6 = new logRegression(factor_data.get(6), outcomes, 5.0, 10.0);
		coefs[6][0] =  log6.getCoefficients()[0];
		coefs[6][1] =  log6.getCoefficients()[1];
		System.out.println("6 Coefficients: " + coefs[6][0] + ", " + coefs[6][1]);
		
		
		
		logRegression log7 = new logRegression(factor_data.get(7), outcomes, 1.0, 5.0);
		coefs[7][0] =  log7.getCoefficients()[0];
		coefs[7][1] =  log7.getCoefficients()[1];
		System.out.println("7 Coefficients: " + coefs[7][0] + ", " + coefs[7][1]);
		
	
		
		
		
	}
	
	private void makeVector(int index) {
		
		factor_data.add(index, new Vector<Double>());
		
		for(int j = 0; j < parse.sbjs.length; j++) {
			try {				
					factor_data.get(index).add(j, parse.sbjs[j].getFactor(index));			

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	private void createOutcomeVector() {
		for(int j = 0; j < parse.sbjs.length; j++) {
			outcomes.add(j, parse.sbjs[j].getFactor(8));
		}
	}
	
	private void makeSeperatedVectors(int index) {
			/*
			 * Add 2 lists storing factor vectors into list
			 */
			list.add(index, new ArrayList<Vector<Double>>(2));
			
			/*
			 * Add vector with 768 elements into each List, one representing outcome 0 the other outcome 1
			 */
			list.get(index).add(0, new Vector<Double>(768)); 
			list.get(index).add(1, new Vector<Double>(768)); 
			
			for(int j = 0; j < parse.sbjs.length; j++) {
				try {
					
					/*
					 * Checks if outcome of factor from 0 - 8 of a subject at j is 0 
					 * 		-> if it is, it is added to to list of two vectors at index 0, else 1
					 */
					if(parse.sbjs[j].getFactor(8) == 0.0) 
						list.get(index).get(0).add(parse.sbjs[j].getFactor(index));			
					else
						list.get(index).get(1).add(parse.sbjs[j].getFactor(index));

				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	
	public void printVector(int index) {
		System.out.println("The corresponding values equate to the following risk factors: \n");
		System.out.print("0 - Pregnancy\n" + 
						 "1 - Glucose\n" + 
						 "2 - Blood pressure\n" + 
						 "3 - Skin thickness\n" +
						 "4 - Insulin\n" + 
						 "5 - BMI \n" + 
						 "6 - DPF\n" +
						 "7 - Age \n\n");
		
		System.out.print("Index: " + index + ", Size: " + factor_data.get(index).size() + " - ");
		for(int i = 0; i < factor_data.get(index).size(); i++) {
			System.out.print(factor_data.get(index).get(i) + ", ");
		}
	}
	
	public void printSeperatedVector(int index) {
		System.out.println("The corresponding values equate to the following risk factors: \n");
		System.out.print("0 - Pregnancy\n" + 
						 "1 - Glucose\n" + 
						 "2 - Blood pressure\n" + 
						 "3 - Skin thickness\n" +
						 "4 - Insulin\n" + 
						 "5 - BMI \n" + 
						 "6 - DPF\n" +
						 "7 - Age \n\n");
		
		System.out.print("Index: " + index + ", Outcome 0: Size: " + list.get(index).get(0).size() + " - ");
		for(int i = 0; i < list.get(index).get(0).size(); i++) {
			System.out.print(list.get(index).get(0).get(i) + ", ");
		}
		
		System.out.println(); 
		System.out.print("Index: " + index + ", Outcome 1: Size: " + list.get(index).get(0).size() + " - ");
		for(int i = 0; i < list.get(index).get(1).size(); i++) {
			System.out.print(list.get(index).get(1).get(i) + ", ");
		}
		System.out.println("\n");
	}
	
	/*
	 * RUN FOR DEBUGGING PURPOSES ONLY 
	 * -------------------------------
	 * Running time is very inefficient (O(n^2) worst case) and should not be run in final product 
	 */
	public void printAllSeperatedVectors() {
		System.out.println("The corresponding values equate to the following risk factors: \n");
		System.out.print("0 - Pregnancy\n" + 
						 "1 - Glucose\n" + 
						 "2 - Blood pressure\n" + 
						 "3 - Skin thickness\n" +
						 "4 - Insulin\n" + 
						 "5 - BMI \n" + 
						 "6 - DPF\n" +
						 "7 - Age \n\n");
		
		for(int index = 0; index < 8; index++) {
		
			System.out.print("Index: " + index + ", Outcome 0: Size: " + list.get(index).get(0).size() + " - ");
			for(int i = 0; i < list.get(index).get(0).size(); i++) {
				System.out.print(list.get(index).get(0).get(i) + ", ");
			}
			
			System.out.println(); 
			System.out.print("Index: " + index + ", Outcome 1: Size: " + list.get(index).get(0).size() + " - ");
			for(int i = 0; i < list.get(index).get(1).size(); i++) {
				System.out.print(list.get(index).get(1).get(i) + ", ");
			}
			System.out.println("\n");
		}
	}
}

