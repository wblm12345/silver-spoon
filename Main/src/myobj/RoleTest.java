package myobj;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.logging.*;

public class RoleTest {
	private Role r1, r2;
	private Logger logger;
	@Before
	public void init() throws SecurityException, IOException{
		r1 = new Attend(10 ,10);
		r2 = new Attend(5, 5);
		logger = Logger.getLogger("test.RoleTest");
		logger.setUseParentHandlers(false);
		FileHandler handler = new FileHandler("C:\\Users\\wblm\\Desktop\\新建文件夹2\\myfirstobj\\RoleTest.log");
		SimpleFormatter formatter = new SimpleFormatter();
		handler.setFormatter(formatter);
		logger.addHandler(handler);
		logger.info("r1、r2攻击力分别为10、5，血量为10、5");
	}
	@Test
	public void testAttack() {
		r1.attack(r2);
		logger.info("r1攻击r2后r1血量为" + r1.hp);
		assertEquals(r1.hp, 5);
	}
	@Test
	public void testIsDie() {
		r1.attack(r2);
		logger.info("r1攻击r2后r2" + (r2.isDie() ? "活着" : "死了"));
		assertTrue(r2.isDie());
	}
	@Test
	public void testGetHp() {
		logger.info("r1血量为" + r1.getHp());
		assertEquals(10, r1.getHp());
	}
}
