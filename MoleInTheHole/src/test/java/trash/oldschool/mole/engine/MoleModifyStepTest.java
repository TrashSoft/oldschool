package trash.oldschool.mole.engine;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import trash.oldschool.mole.model.MoleMonster;
import trash.oldschool.mole.model.MolePlayer;

public class MoleModifyStepTest {

	@Test
	public void testAreTouching1() {

		MoleModifyStep modify = new MoleModifyStep();

		MolePlayer player = new MolePlayer(0, 0);
		MoleMonster monster = new MoleMonster(0, 0);
		assertTrue(modify.areTouching(player, monster));
		assertTrue(modify.areTouching(monster, player));

		player.position.x = player.target.x = 2;
		player.position.y = player.target.y = 2;
		assertFalse(modify.areTouching(player, monster));
		assertFalse(modify.areTouching(monster, player));

		player.position.x = 0;
		player.position.y = 0;
		player.target.x = 1;
		player.target.y = 0;
		player.delta = 0.9;
		assertTrue(modify.areTouching(player, monster));
		assertTrue(modify.areTouching(monster, player));

		player.position.x = 0;
		player.position.y = 0;
		player.target.x = 1;
		player.target.y = 0;
		player.delta = 0.1;
		assertTrue(modify.areTouching(player, monster));
		assertTrue(modify.areTouching(monster, player));

		player.delta = 0.3;
		monster.position.x = 2;
		monster.position.y = 0;
		monster.target.x = 1;
		monster.target.y = 0;
		monster.delta = 0.9;
		assertFalse(modify.areTouching(player, monster));
		assertFalse(modify.areTouching(monster, player));
	}

}
