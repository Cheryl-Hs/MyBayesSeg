package BayesSeg;
import java.util.ArrayList;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;

/**
 * 计算概率
 * @author Cheryl
 * @time 2016.1.14
 */
public class ComputePro implements GetSplitResult{
	//训练语料的路径
	private static String trainingMaterialPath = "Data/行政区划字典0.txt";
	//词汇库
	private static trainingWords wordCountSet = new trainingWords(trainingMaterialPath);
	//语料库的路径
//	private static String materialPath = "";
//	//词语库的路径
//	private static String wordsPath = "Data/";

//	public ComputePro() {
//		if (wordCountSet == null) {
//			wordCountSet =
//					new ChineseWordSetTwo(materialPath, wordsPath);
//		}
//	}
	
	/**
	 * 获取词语s的概率，也就是在词汇库中的出现次数
	 * @param s 要查询概率的词语
	 * @return 该词语的概率，也就是P(s)
	 */
	public double getProOfWord(String s) {
		return wordCountSet.getCount(s);
	}
	
	/**
	 * 计算概率P(w2|w1)也就是计算P(w1w2),直接返回w1w2在词汇库中的次数即可
	 * @param w1
	 * @param w2
	 * @return
	 */
	public double getProOfW1AndW2(String w1, String w2) {
		return getProOfWord(w1 + w2);
	}
	
	/**
	 * 计算条件概率P(w2|w1),根据概率公式有P(w2|w1) = P(w1w2)/P(w1)
	 * @param w1
	 * @param w2
	 * @return
	 */
	public double getProOfW2UnderW1(String w2, String w1) {
		return getProOfW1AndW2(w1, w2) / getProOfWord(w1);
	}
	
	/**
	 * 计算一个分割好的句子(分词方案)的概率，分词是在GetAllSplits.java中完成的,
	 * 使用的是'|'作为分隔符，所以再这里分割的时候要注意添加两个反斜杠"\\"
	 * 首先需要将该分词方案按分隔符切割成各个单词，然后根据公式：P(w1,w2,w3,w4...) =
	 * P(w1)*P(w2|w1)*P(w3|w2)*P(w4|w3)*...来计算出其概率
	 * 
	 * @param s
	 * @return
	 */
	private double getProOfASplitedSentence(String s) {
		//先对其按分词方案的分隔符进行分割，添加两个反斜杠"\\"
		String[] words = s.split("\\" + GetAllSplits.splitSymbol);
		int len = words.length;
//		for (int j=0;j<len;j++){
//			System.out.println(words[j] + getProOfWord(words[j]));
//		}
		double result = 0.0;
		if (len == 1) {
			result = getProOfWord(words[0]);
		} else {
			result = 1;
			//先计算P(w2|w1)*P(w3|w2)*P(w4|w3)*...部分
			for (int i = len -1; i>0; i--) {
				//System.out.println(words[i-1]+words[i]+getProOfW1AndW2(words[i-1], words[i]));
				//System.out.println(words[i]+"|"+words[i-1]+getProOfW2UnderW1(words[i], words[i-1]));
				result *= getProOfW2UnderW1(words[i], words[i-1]);
			}
			//乘上一项P(w1)
			result *= getProOfWord(words[0]);
		}
		return result;
	}
		
		/**
		 * 计算所有分词方案的概率大小
		 * @param sentences 是一个set,从GetAllSplits的splitSentence()方法传过来
		 * @return 返回保存所有分词方案及其概率信息的Map,以供进一步处理
		 */
		public Map<String, Double> 
				computeProOfAll (Set<String> sentences) {
			 TreeMap<String, Double> allSentence = new TreeMap<String, Double>();
			double pro = 0.0;
			if (sentences != null) {
				for (String string : sentences) {
					//对每个方案计算其概率
					System.out.print(string);
					pro = getProOfASplitedSentence(string);
					System.out.println(pro);
					//将方案及概率保存起来
					allSentence.put(string, pro);
				}
			}
			return allSentence; 
		}

		
		/**
		 * 获取概率最大的一个分词方案
		 * @param allSentence 所有的分词方案及其对应的概率
		 * @return 概率最大的一个分词方案
		 */
		public ArrayList<String> 
				getMaxProSentence (Map<String, Double> allSentence) {
			//保存概率最大的前三个方案
			
			ArrayList<String> resultList = new ArrayList<String>();
			String maxProSentence = "";
			double maxPro = 0.0;
			Set<Entry<String, Double>> allSet = allSentence.entrySet();
			for (Entry<String, Double> e : allSet) {
				if (e.getValue() > maxPro) {
					maxPro = e.getValue();
					maxProSentence = e.getKey();
				}
			}
			resultList.add(maxProSentence);
			//System.out.print(resultList);
			return resultList;
		}
		
		/**
		 * 对计算概率的方法做封装，以便于其他模块直接调用
		 * @param s 要进行分词的字符串
		 * @return 概率最大分词方案
		 */
		public ArrayList<String> splitSentence1(String s) {
			return getMaxProSentence(
					computeProOfAll(GetAllSplits.splitSentence(s)));
		}
		

	/* (non-Javadoc)
	 * 接口，实现获取GetAllSplits的结果
	 * @see BayesSeg.GetSplitResult#splitSentence(java.lang.String)
	 */
	@Override
	public Set<String> splitSentence(String s) {
		// TODO Auto-generated met stub
		return GetAllSplits.splitSentence(s);
	}
	
	public static void main(String[] args){
		ComputePro a = new ComputePro();
		//System.out.print();
		System.out.print(a.getProOfASplitedSentence("湖北省|武汉市|洪山区"));
	}

}
