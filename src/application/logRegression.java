package application;

import java.util.Vector;

public class logRegression {
	
	/*
	 * Stores two coefficients for each risk factor
	 */
	private double[] coefficients = new double[2];
	private Vector<Double> factor; 
	private Vector<Double> outcome; 
	
	private double estCoef1, estCoef2; 
		
	public logRegression(Vector<Double> fac, Vector<Double> out, double estC1, double estC2) {
		this.factor = fac; 
		this.outcome = out; 
		
		this.estCoef1 = estC1; this.estCoef2 = estC2; 
	}
	
	public double[] getCoefficients() {
		return newtonsMethod(factor, outcome);
	}
	
	/*
	 * Returns regression equation 
	 */
	private Vector<Double> sigmoid(Vector<Double> risk, double c1, double c2) {
		/*
		 * 1st argument - risk factor vector
		 * 2nd argument - coefficient 1
		 * 3rd argument - coefficient 2 
		 */
		Vector<Double> z = new Vector<>(768);
		Vector<Double> sigmoidVector = new Vector<>(768);

		
		for(int i = 0; i < 768; i++) {
			z.add(i, c1 * risk.get(i) + c2);
		}
		for(int i = 0; i < 768; i++) {
			sigmoidVector.add(i, 1.0 / (1.0 + Math.exp((-1 * z.get(i)))));
		}
		return sigmoidVector; 
	}
	
	private double logLikelihood(Vector<Double> risk, Vector<Double> outcome, double c1, double c2) {
		/*
		 * 1st argument - risk factor vector
		 * 2nd argument - outcome vector
		 * 3rd argument - coefficient 1
		 * 4th argument - coefficient 2 
		 */
		double sum = 0.0; 
		Vector<Double> sigmoidProbs = sigmoid(risk, c1, c2); 
		double sig = 0.0; 
		double sig_1 = 0.0;
		
		for(int i = 0; i < 768; i++) {
			if(sigmoidProbs.get(i) != 0.0) {
				sig = Math.log(sigmoidProbs.get(i));
				sig_1 = Math.log(1.0 - sig);
				
				sum+= (outcome.get(i) * sig) + ((1.0 - outcome.get(i)) * sig_1);
			}
		}
		return sum;
	}
	
	private double[][] gradient(Vector<Double> risk, Vector<Double> outcome, double c1, double c2) {	
		double sumLHS, sumRHS; 
		sumLHS = 0.0; sumRHS = 0.0;
		
		double[][] gradients = new double[1][2];
		
		Vector<Double> sigmoidProbs = sigmoid(risk, c1, c2); 
		
		for(int i = 0; i < 768; i++) {
			if(sigmoidProbs.get(i) != 0.0) {
			sumLHS += (risk.get(i) * (outcome.get(i) - sigmoidProbs.get(i)));
			sumRHS += (1 * (outcome.get(i) - sigmoidProbs.get(i)));
			}
		}
		
		gradients[0][0] = sumLHS;
		gradients[0][1] = sumRHS;
		
		return gradients; 
	}
	
	private double[][] hessian(Vector<Double> risk, Vector<Double> outcome, double c1, double c2) {
		double d1, d2, d3;
		d1 = 0.0; d2 = 0.0; d3 = 0.0; 
		
		double[][] Hessian = new double[2][2];
		Vector<Double> sigmoidProbs = sigmoid(risk, c1, c2); 
		
		
		for(int i = 0; i < sigmoidProbs.size(); i++) {
			d1 += ((sigmoidProbs.get(i) * (1 - sigmoidProbs.get(i))) * c1 * c2);  
			d2 += ((sigmoidProbs.get(i) * (1 - sigmoidProbs.get(i))) * c1 * 1); 
			d3 += ((sigmoidProbs.get(i) * (1 - sigmoidProbs.get(i))) * 1 * 1); 
		}
		
		/*
		 * Hessian
		 * -------
		 * 
		 *    Col 0  1
		 * Row 
		 *  0     d1 d2
		 *  1     d2 d3
		 */
		
		// Row 1
		Hessian[0][0] = d1; Hessian[0][1] = d2; 
		// Row 2
		Hessian[1][0] = d2; Hessian[1][1] = d3; 
		
		
		return Hessian; 
	}
	
	/*
	 * NOTE: 
	 * This function can only invert a 2x2 matrix 
	 */
	private double[][] invert(double[][] matrix) {
		/*
		 * INVERSE EQUATION
		 * ---------------- 
		 * 
		 * ([a b], [c, d])^-1 = 1/ad - bc * ([d, -b], [-c, a])
		 * 
		 * FOR REFERENCE	
		 * -------------
		 * 
		 * a = matrix[0][0] 
		 * b = matrix[0][1]
		 * c = matrix[1][0]
		 * d = matrix[1][1]
		 */
		
		double[][] inv = new double[2][2]; 
		
		double determinant = (matrix[0][0] * matrix[1][1]) - (matrix[0][1] * matrix[1][0]);
		double oneOverDet = 1.0/determinant;  
		
		// Reorder matrix into inv 
		inv[0][0] = oneOverDet * matrix[1][1];
		inv[0][1] = oneOverDet * -1 * matrix[0][1];
		inv[1][0] = oneOverDet * -1 * matrix[1][0];
		inv[1][1] = oneOverDet * matrix[0][0];
		
		return inv; 
	}
	
	public double[][] transpose(double[][] matrix, int rowSize, int colSize) {
		double[][] t = new double[rowSize][colSize]; 
		
		for(int i = 0; i < rowSize; i++) {
			for(int j = 0; j < colSize; j++) {
				t[i][j] = matrix[j][i];
			}
		}
		return t; 
	}
	
	/*
	 * Meant for h.inv * g.T only 
	 */
	private double[] modifiedDotProduct(double[][] h, double[][] g) {
		double[] returnMatrix = new double[2];
		
		returnMatrix[0] = (h[0][0]*g[0][0] + h[0][1]*g[1][0]);
		returnMatrix[1] = (h[1][0]*g[0][0] + h[1][1]*g[1][0]);
		
		return returnMatrix; 
	}
	
	private double[] newtonsMethod(Vector<Double> risk, Vector<Double> outcome) {
		/*
		 * 1st argument - Vector representation of desired risk factor 
		 * 2nd argument - Vector representation of outcomes
		 */
		
		double[] coef = new double[2]; 
		double[][] g = new double[1][2];
		//double[][] h = new double[2][2];
		
		double[][] g_t = new double[2][1];
		double[] dot = new double[2];
		
		double coef1 = estCoef1; double coef2 = estCoef2; 
		
		double updateC1 = 0.0; double updateC2 = 0.05; 
		
		double delta_l = 1.0; 
		double conv = .01;
		int maxIterations = 25; 
		
		double lh = logLikelihood(risk, outcome, coef1, coef2);
		double lh_updated = 0.0; 
		
		int i = 0; 
		while(Math.abs(delta_l) > conv && i < maxIterations) {
			//delta_l = 0.0; 
			i ++; 
			g = gradient(risk, outcome, coef1, coef2);
			double[][] h = hessian(risk, outcome, coef1, coef2); 
			double[][] h_inv = invert(h);
			
			
			g_t = transpose(g, 2, 1);
			dot[0] = modifiedDotProduct(h_inv, g_t)[0];
			dot[1] = modifiedDotProduct(h_inv, g_t)[1];
			
			updateC1 = dot[0];
			updateC2 = dot[1];
			
			coef1 += updateC1;
			coef2 += updateC2; 
			
			lh_updated = logLikelihood(risk, outcome, coef1, coef2);
			delta_l = lh - lh_updated; 
			/*
			System.out.println(lh);
			System.out.println(lh_updated);
			System.out.println(delta_l);
			*/
			lh = lh_updated; 
		}
		coef[0] = coef1; coef[1] = coef2; 
		
		
		return coef; 
	}

}
