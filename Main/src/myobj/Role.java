package myobj;


public abstract class Role{
	protected int hp, atk;
	public Role(int hp, int atk) {
		this.hp = hp;
		this.atk = atk;
	}
	abstract public void attack(Role attacked);
	public boolean isDie() {
		return hp <= 0;
	}
	public int getHp() {
		return hp;
	}
}
class Hero extends Role{
	public Hero() {
		super(30, 0);
	}
	@Override
	public void attack(Role attacked) {}
}
class Attend extends Role{
	public Attend(int hp, int atk) {
		super(hp, atk);
	}
	@Override
	public void attack(Role attacked) {
		hp -= attacked.atk;
		attacked.hp -= atk;
	}
	
}
