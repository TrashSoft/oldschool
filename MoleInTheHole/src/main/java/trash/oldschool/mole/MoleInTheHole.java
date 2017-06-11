package trash.oldschool.mole;

import trash.oldschool.engine.GameEngineBuilder;
import trash.oldschool.engine.intf.GameEngineStep;
import trash.oldschool.mole.engine.MoleBuildStep;
import trash.oldschool.mole.engine.MoleControlStep;
import trash.oldschool.mole.engine.MoleDisposeStep;
import trash.oldschool.mole.engine.MoleInitStep;
import trash.oldschool.mole.engine.MoleModifyStep;
import trash.oldschool.mole.engine.MoleRenderStep;

public class MoleInTheHole {

	public static void main(String[] args) {

		GameEngineBuilder builder = new GameEngineBuilder();
		builder.create(
				"moleInTheHole",
				"Mole In The Hole",
				"v00.00.0002",
				"Clone of an old C64 / C16 game.");

		builder.step(GameEngineStep.INIT, new MoleInitStep());
		builder.step(GameEngineStep.BUILD, new MoleBuildStep());
		builder.step(GameEngineStep.CONTROL, new MoleControlStep());
		builder.step(GameEngineStep.MODIFY, new MoleModifyStep());
		builder.step(GameEngineStep.RENDER, new MoleRenderStep());
		builder.step(GameEngineStep.DISPOSE, new MoleDisposeStep());

		builder.startEngine();
	}

}
