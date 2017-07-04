package trash.oldschool.box;

import trash.oldschool.box.engine.PushTheBoxBuildStep;
import trash.oldschool.box.engine.PushTheBoxControlStep;
import trash.oldschool.box.engine.PushTheBoxDisposeStep;
import trash.oldschool.box.engine.PushTheBoxInitStep;
import trash.oldschool.box.engine.PushTheBoxModifyStep;
import trash.oldschool.box.engine.PushTheBoxRenderStep;
import trash.oldschool.engine.GameEngineBuilder;
import trash.oldschool.engine.intf.GameEngineStep;

public class PushTheBox {

	public static void main(String[] args) {

		GameEngineBuilder builder = new GameEngineBuilder();
		builder.create(
				"pushTheBox",
				"Push The Box",
				"v00.00.0001",
				"Clone of some old C64 / C16 games.");

		builder.step(GameEngineStep.INIT, new PushTheBoxInitStep());
		builder.step(GameEngineStep.BUILD, new PushTheBoxBuildStep());
		builder.step(GameEngineStep.CONTROL, new PushTheBoxControlStep());
		builder.step(GameEngineStep.MODIFY, new PushTheBoxModifyStep());
		builder.step(GameEngineStep.RENDER, new PushTheBoxRenderStep());
		builder.step(GameEngineStep.DISPOSE, new PushTheBoxDisposeStep());

		builder.startEngine();
	}

}
