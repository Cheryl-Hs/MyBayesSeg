package BayesSeg;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map.Entry;

public class trainingWords {
	
	Hashtable <String, String> trainingMaterial_datatable = new Hashtable<String, String>();
	HashMap <String, Integer> map = new HashMap<String, Integer>();
	
	public trainingWords(String trainingMaterialPath) {
		Init(trainingMaterialPath);
		System.out.println("初始化成功");
	}

	public double getCount(String s) {
		// TODO Auto-generated method stub
		double count = 1;
		 
	    for(Iterator it = trainingMaterial_datatable.keySet().iterator(); it.hasNext(); ) {   
            //从trainingMaterial_datatable中取  
            String key = (String) it.next();   
            Object value = trainingMaterial_datatable.get(key);   
            if (trainingMaterial_datatable.get(key).contains(s)) {
            	count = count + 1.00;
            	//System.out.println(trainingMaterial_datatable.get(key) + count);
	        } else {
	        	continue;
	        }
		    }
		return count;
		
	}
	    
	
 	private void Init(String trainingMaterialPath) {
		try {
			File trainingMaterial = new File(trainingMaterialPath);
			if (trainingMaterial.isFile() && trainingMaterial.exists()) {
				//按行读取trainingMaterial语料文件,存到trainingMaterial_datatable中
				InputStreamReader isr = new InputStreamReader(new FileInputStream(trainingMaterial),"UTF-8");
				BufferedReader br = new BufferedReader(isr);
				String str = br.readLine();
				while (str != null) {
					if (!trainingMaterial_datatable.containsKey(str))
						trainingMaterial_datatable.put(str, str);
					str = br.readLine();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
 	}
 	
// 	public static void main(String[] args) {
// 		String trainingMaterialPath = "Data/data38w.txt";
// 		//
// 		trainingWords wordCountSet = new trainingWords(trainingMaterialPath);
//// 		double i = wordCountSet.getCount("武汉市");
//// 		System.out.print(i);
// 		System.out.print(wordCountSet.getCount("流芳"));
// 	}

}

