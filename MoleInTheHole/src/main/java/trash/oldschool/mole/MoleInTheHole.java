package trash.oldschool.mole;

import trash.oldschool.engine.GameAdapter;
import trash.oldschool.engine.GameDescriptor;
import trash.oldschool.engine.GameEngine;
import trash.oldschool.swing.SwingGameAdapter;

public class MoleInTheHole {

	public static void main(String[] args) {

		GameDescriptor descriptor = new GameDescriptor();
		descriptor.setId("moleInTheHole");
		descriptor.setTitle("Mole In The Hole");
		descriptor.setVersion("v00.00.0001");
		descriptor.setDescription("Clone of an old C64 game.");

		GameAdapter adapter = new SwingGameAdapter();

		GameEngine engine = GameEngine.create(descriptor, adapter);
		engine.startInNewWindow();
	}

}
