package myobj;
import java.util.*;
/**
 * @author linxingde
 */

public class Main {
	public static void main(String[] args) {
		Scanner cin = new Scanner(System.in);
		int winner = 0, n = cin.nextInt();
		LinkedList<Role> player1 = new LinkedList<Role>();
		LinkedList<Role> player2 = new LinkedList<Role>();
		player1.add(new Hero());
		player2.add(new Hero());
		end : for(int i = 0; true;) {
			while(true) {
				if(i == n) {
					break end;
				}
				String operation = cin.next();
				if("summon".equals(operation)) {
					int position = cin.nextInt(), atk = cin.nextInt(), hp = cin.nextInt();
					player1.add(position, new Attend(hp, atk));
				}
				else if("attack".equals(operation)) {
					int attacker = cin.nextInt(), defender = cin.nextInt();
					player1.get(attacker).attack(player2.get(defender));
					if(player1.get(attacker).isDie()) {
						player1.remove(attacker);
					}
					if(defender != 0 && player2.get(defender).isDie()) {
						player2.remove(defender);
					}
				}
				else if("end".equals(operation)) {
					++i;
					break;
				}
				++i;
				if(player2.get(0).isDie()) {
					winner = 1;
					break end;
				}
			}
			while(true) {
				if(i == n) {
					break end;
				}
				String operation = cin.next();
				if("summon".equals(operation)) {
					int position = cin.nextInt(), atk = cin.nextInt(), hp = cin.nextInt();
					player2.add(position, new Attend(hp, atk));
				}
				else if("attack".equals(operation)) {
					int attacker = cin.nextInt(), defender = cin.nextInt();
					player2.get(attacker).attack(player1.get(defender));
					if(player2.get(attacker).isDie()) {
						player2.remove(attacker);
					}
					if(defender != 0 && player1.get(defender).isDie()) {
						player1.remove(defender);
					}
				}
				else if("end".equals(operation)) {
					++i;
					break;
				}
				++i;
				if(player1.get(0).isDie()) {
					winner = -1;
					break end;
				}
			}
		}
		System.out.println(winner);
		print(player1, player2);
		cin.close();
	}
	static void print(LinkedList<Role> player1, LinkedList<Role> player2) {
		int aliveAttend1 = player1.size() - 1;
		int aliveAttend2 = player2.size() - 1;
		System.out.println(player1.get(0).getHp());
		System.out.print(aliveAttend1 + " ");
		for(int i = 0; i < aliveAttend1; ++i) {
			System.out.print(player1.get(i + 1).getHp() + " ");
		}
		System.out.println();
		System.out.println(player2.get(0).getHp());
		System.out.print(aliveAttend2 + " ");
		for(int i = 0; i < aliveAttend2; ++i) {
			System.out.print(player2.get(i + 1).getHp() + " ");
		}
	}
};

