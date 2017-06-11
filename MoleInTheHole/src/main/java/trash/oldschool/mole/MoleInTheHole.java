package trash.oldschool.mole;

import trash.oldschool.engine.GameAdapter;
import trash.oldschool.engine.GameDescriptor;
import trash.oldschool.engine.GameEngine;
import trash.oldschool.engine.GameEngineStep;
import trash.oldschool.mole.engine.MoleBuildStep;
import trash.oldschool.mole.engine.MoleControlStep;
import trash.oldschool.mole.engine.MoleDisposeStep;
import trash.oldschool.mole.engine.MoleInitStep;
import trash.oldschool.mole.engine.MoleModifyStep;
import trash.oldschool.mole.engine.MoleRenderStep;
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
		
		engine.step(GameEngineStep.INIT, new MoleInitStep());
		engine.step(GameEngineStep.BUILD, new MoleBuildStep());
		engine.step(GameEngineStep.CONTROL, new MoleControlStep());
		engine.step(GameEngineStep.MODIFY, new MoleModifyStep());
		engine.step(GameEngineStep.RENDER, new MoleRenderStep());
		engine.step(GameEngineStep.DISPOSE, new MoleDisposeStep());
		
		engine.startInNewWindow();
	}

}
