package myobj;
import java.util.*;
/**
 * @author linxingde
 */


public class Main {
	public static void main(String[] args) {
		Scanner cin = new Scanner(System.in);
		int n = cin.nextInt();
		LinkedList<Integer> list = new LinkedList<Integer>();
		for(int i = 1; i <= n; ++i) {
			list.add(i);
		}
		int m = cin.nextInt();
		for(int i = 0; i < m; ++i) {
			int num = cin.nextInt();
			int move = cin.nextInt();
			int index = list.indexOf(num);
			list.remove(index);
			list.add(index + move, num);
		}
		for(int i = 0; i < n; ++i) {
			System.out.print(list.get(i) + " ");
		}
		cin.close();
	}
	
};


