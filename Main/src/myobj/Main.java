package myobj;
import java.util.*;
/**
 * @author linxingde
 */


public class Main {
	public final static char ISID = '#';
	public static void main(String[] args) {
		Scanner cin = new Scanner(System.in);
		int n = cin.nextInt();
		int m = cin.nextInt();
		cin.nextLine();
		ArrayList<Node> line = new ArrayList<Node>();
		addNode(line, n, cin);
		for(int i = 0; i < m; ++i) {
			String s = cin.nextLine();
			String[] operator = s.split(" ");
			if(operator.length == 1) {
				if(operator[0].charAt(0) == ISID) {
					ArrayList<Integer> a = idChooser(line, operator[0]);
					for(int j = 0; j < a.size(); ++j) {
						if(j == a.size() - 1) {
							System.out.println(a.get(j));
						}
						else {
							System.out.print(a.get(j) + " ");
						}
					}
				}
				else {
					ArrayList<Integer> a = labelChooser(line, operator[0].toLowerCase());
					for(int j = 0; j < a.size(); ++j) {
						if(j == a.size() - 1) {
							System.out.println(a.get(j));
						}
						else {
							System.out.print(a.get(j) + " ");
						}
					}
				}
			}
			else {
				ArrayList<Integer> a = unbornChooser(line, operator);
				for(int j = 0; j < a.size(); ++j) {
					if(j == a.size() - 1) {
						System.out.println(a.get(j));
					}
					else {
						System.out.print(a.get(j) + " ");
					}
				}
			}
		}
		cin.close();
	}
	static class Node{
		String label;
		String id;
		int level;
		Node(String label, String id, int level){
			this.label = label;
			this.id = id;
			this.level = level;
		}
	}
	static void addNode(ArrayList<Node> line, int n, Scanner cin) {
		for(int i = 0; i < n; ++i) {
			String s = cin.nextLine();
			int level = numOfPoint(s);
			s = deletePoint(s, 2 * level);
			String[] operator = s.split(" ");
			line.add(new Node(operator[0].toLowerCase(), 
					(operator.length > 1 ? operator[1] : ""), level));
			
		}
	}
	static ArrayList<Integer> labelChooser(ArrayList<Node> line, String toSearch) {
		ArrayList<Integer> out = new ArrayList<Integer>();
		out.add(0);
		for(int i = 0; i < line.size(); ++i) {
			if(toSearch.equals(line.get(i).label)) {
				out.add(i + 1);
				out.set(0, out.get(0) + 1);
			}
		}
		return out;
	}
	static ArrayList<Integer> idChooser(ArrayList<Node> line, String toSearch) {
		ArrayList<Integer> out = new ArrayList<Integer>();
		out.add(0);
		for(int i = 0; i < line.size(); ++i) {
			if(toSearch.equals(line.get(i).id)) {
				out.add(i + 1);
				out.set(0, out.get(0) + 1);
			}
		}
		return out;
	}
	static ArrayList<Integer> unbornChooser(ArrayList<Node> line, String[] operator) {
		ArrayList<Integer> appearLine;
		ArrayList<Integer> out = new ArrayList<Integer>();
		out.add(0);
		//是id
		if(operator[operator.length - 1].charAt(0) == ISID) {
			//得到尾元素所在行的集合
			appearLine = idChooser(line, operator[operator.length - 1]);
			//对每一个存在尾元素的行进行判断
			for(int i = 1; i <= appearLine.get(0); ++i) {
				int toJudgeLine = appearLine.get(i);
				int nowLine = appearLine.get(i) - 1;
				int nowLevel = line.get(nowLine).level;
				int j = operator.length - 2;
				//对该行进行判断
				while(j >= 0 && nowLine > 0) {
					if(line.get(nowLine - 1).level == nowLevel - 1) {
						if(line.get(nowLine - 1).id.equals(operator[j])
								|| line.get(nowLine - 1).label.equals(operator[j])) {
							--j;
						}
						--nowLevel;
					}
					--nowLine;
				}
				if(j < 0) {
					out.add(toJudgeLine);
					out.set(0, out.get(0) + 1);
				}
			}
		}
		//是标签
		else {
			for(int i = 0; i < operator.length; ++i) {
				if(operator[i].charAt(0) != '#') {
				operator[i] = operator[i].toLowerCase();
				}
			}
			//得到尾元素所在行的集合
			appearLine = labelChooser(line, operator[operator.length - 1]);
			//对每一个存在尾元素的行进行判断
			for(int i = 1; i <= appearLine.get(0); ++i) {
				int toJudgeLine = appearLine.get(i);
				int nowLine = appearLine.get(i) - 1;
				int nowLevel = line.get(nowLine).level;
				int j = operator.length - 2;
				//对该行进行判断
				while(j >= 0 && nowLine > 0) {
					if(line.get(nowLine - 1).level == nowLevel - 1) {
						if(line.get(nowLine - 1).label.equals(operator[j])
								|| line.get(nowLine - 1).id.equals(operator[j])) {
							--j;
						}
						--nowLevel;
					}
					--nowLine;
				}
				if(j < 0) {
					out.add(toJudgeLine);
					out.set(0, out.get(0) + 1);
				}
			}
		}
		return out;
	}
	static int numOfPoint(String s) {
		int sum = 0;
		for(int i = 0; i < s.length(); ++i) {
			if(s.charAt(i) == '.') {
				++sum;
			}
			else {
				return sum / 2;
			}
		}
		return sum / 2;
	}
	static String deletePoint(String s, int begin) {
		return s.substring(begin);
	}
	
};


