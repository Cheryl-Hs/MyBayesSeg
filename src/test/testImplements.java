package test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import BayesSeg.GetAllSplits;
import BayesSeg.GetSplitResult;


public class testImplements implements GetSplitResult{

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
//		testImplements result = new testImplements();
//		
//		System.out.print(result.splitSentence("湖北省武汉市洪山区"));
		
		List<String> v=new ArrayList();
		v.add("one");
		v.add("two");
		for(String s:v){
		System.out.println(s);
		}

	}

	@Override
	public Set<String> splitSentence(String s) {
		// TODO Auto-generated method stub
		return GetAllSplits.splitSentence(s);
	}
	
	public Map<String, Double> ss(Set<String> aa) {
		return null;
		
	}

}
