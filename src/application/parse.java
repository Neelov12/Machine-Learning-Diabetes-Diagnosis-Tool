package application;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.StringTokenizer;

import javax.swing.text.Utilities;

public class parse {
	
		public static subject[] sbjs;
		
		private vector v = new vector(); 
		private int SIZE_OF_DATA = 768;

	public void parseFile() throws IOException {
	    File file = new File("diabetes.csv");
	    FileReader fileReader = new FileReader(file);
	    BufferedReader bufferedReader = new BufferedReader(fileReader);
	    String line;
	    StringTokenizer str; 
	    sbjs = new subject[SIZE_OF_DATA]; 
	    
	    int i = 0; 
	    String[] stor_arr = new String[9]; 
	    
	    
	    while ((line = bufferedReader.readLine()) != null) {
	        //tokenizedLine = parse(line);
	    	str = new StringTokenizer(line, "\",\"");
	    	//System.out.print(i);
	    	//i++;
	        // do stuff with your array
	    	int j = 1; 
	    	String object = ""; 
	    	while (str.hasMoreElements()) {
	    		object = (String) str.nextElement(); 
		    	//System.out.println("StringTokenizer Output: " + j + i+  object);
		    	
		    	if(i != 0) {
		    		
		    	
		    	switch(j) {
		    		case 1: 
		    			stor_arr[0] = object; 
		    			break; 
		    		case 2: 
		    			stor_arr[1] = object; 
		    			break; 
		    		case 3: 
		    			stor_arr[2] = object; 
		    			break; 
		    		case 4: 
		    			stor_arr[3] = object; 
		    			break; 
		    		case 5: 
		    			stor_arr[4] = object; 
		    			break; 
		    		case 6: 
		    			stor_arr[5] = object; 
		    			break; 
		    		case 7: 
		    			stor_arr[6] = object; 
		    			break; 
		    		case 8: 
		    			stor_arr[7] = object; 
		    			break; 
		    		case 9: 
		    			stor_arr[8] = object; 
		    			break; 
		    	}
		    	}
		    	if (j % 9 == 0) {
		    		
		    		if (i == 0) {
		    			j = 1; 
			    		i++; 
		    		}
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
		
		    		else {
		    			sbjs[i - 1] = new subject(Double.parseDouble(stor_arr[0]),
		    									  Double.parseDouble(stor_arr[1]),
		    									  Double.parseDouble(stor_arr[2]),
		    									  Double.parseDouble(stor_arr[3]),
		    									  Double.parseDouble(stor_arr[4]),
		    									  Double.parseDouble(stor_arr[5]),
		    									  Double.parseDouble(stor_arr[6]),
		    									  Double.parseDouble(stor_arr[7]),
		    									  Double.parseDouble(stor_arr[8]));

		    		
		    			stor_arr[0] = "";
		    			stor_arr[1] = "";
		    			stor_arr[2] = ""; 
		    			stor_arr[3] = "";
		    			stor_arr[4] = "";
		    			stor_arr[5] = "";
		    			stor_arr[6] = "";
		    			stor_arr[7] = "";
		    			stor_arr[8] = "";
		    		
		    		j = 1; 
		    		i++; 
		    		}
		    	}
	    		j++; 
		    }	
	    }
	    
	    bufferedReader.close(); 
	    
	    v.createVectors();		
	    }
		
	}


/*
{
    "coordinates": [
        {
            "latitude": "29.35344",
            "longitude": "-88.05267"
        }
    ],
    "timestamp": "01/20/2023, 07:35:44"
}
*/


