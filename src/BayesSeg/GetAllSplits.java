package BayesSeg;
import java.util.HashSet;
import java.util.Set;

/**
 * 获取一个句子的所有分词方案
 * @author Cheryl
 * @time 2016.1.14
 */
public class GetAllSplits {
	//用作句子分隔符的符号，暂时设定为竖杠
	public static char splitSymbol = '|';
	
	/**
	 * 分割句子
	 * @param s 要分割的句子
	 * @return 该句子所有的分词方案，保存在一个Set中
	 */
	public static Set<String> splitSentence(String s) {
		HashSet<String> allSplits = new HashSet<String>();
		//调用一次即可，传入句子的长度。因为计算过程是从1开始计算的
		//在计算过程中可以得到所有数量分隔符的分词方案
		allSplits.addAll(insertSplit(s,s.length()));
		return allSplits;		
	}
	
	/**
	 * 给句子插入指定数量的分隔符，暂时以"|"作为分隔符
	 * @param s 要分割的句子
	 * @param count 要插入的分隔符的数量
	 * @return
	 */
	private static HashSet<String> insertSplit(String s, int count) {
		//作为临时保存的容器
		HashSet<String> tempSet = new HashSet<String>();
		//保存所有的分词方案
		HashSet<String> resultSet = new HashSet<String>();
		String tempString;
		//先插入一个分隔符，作为后续插入的基础
		for (int i = 0; i<s.length(); i++) {
			tempString = insertSplitAt(i,s);
			if (tempString != null) {
				resultSet.add(tempString);
			}
		}
		int times = count - 1;
		//如果count为1则直接返回；否则进入循环进行后续计算
		while (times > 0) {
			tempSet.addAll(resultSet);
			for (String string : tempSet) {
				for (int i =0; i < string.length(); i++) {
					tempString = insertSplitAt(i, string);
					
					//如果返回的分词是有效的，即不为空，则添加到resultSet中
					if (tempString != null) {
						resultSet.add(tempString);
					}
				}
			}
			times--;
		}
		return resultSet;
		
	}

	/**
	 * 执行具体插入分隔符的函数，在指定位置给句子插入分隔符
	 * @param index 要插入的位置
	 * @param s 要插入分隔符的句子
	 * @return
	 */
	private static String insertSplitAt(int index, String s) {
		//在句子最后面不插入分隔符
		if (index == s.length() - 1) {
			return s;
		}
		//如果index小于0或者要插入的位置前后已经有分隔符则不插入，返回null
		else if (index < 0 || (s.charAt(index) == splitSymbol)
				|| (s.charAt(index + 1) == splitSymbol)) {
			return null;
		}
		//得到前半部分句子
		String frontString = s.substring(0, index + 1);
		//得打后半部分句子
		String backString = s.substring(index + 1, s.length());
		frontString = frontString + splitSymbol;
		return frontString + backString;
	}
	
	public static void main(String[] args) {
		GetAllSplits allSplits = new GetAllSplits();
		String s = "上海市虹口区场中路628号场";
		allSplits.splitSentence(s);
		System.out.println(allSplits.splitSentence(s));
		System.out.print(allSplits.splitSentence(s).size());
		ComputePro computePro = new ComputePro();
		computePro.splitSentence1(s);
		System.out.print(computePro.splitSentence1(s));
		
	}
}
