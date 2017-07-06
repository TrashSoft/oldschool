package trash.oldschool.pet;

import trash.oldschool.engine.GameEngineBuilder;
import trash.oldschool.engine.intf.GameEngineStep;
import trash.oldschool.pet.engine.WebPetBuildStep;
import trash.oldschool.pet.engine.WebPetControlStep;
import trash.oldschool.pet.engine.WebPetDisposeStep;
import trash.oldschool.pet.engine.WebPetInitStep;
import trash.oldschool.pet.engine.WebPetModifyStep;
import trash.oldschool.pet.engine.WebPetRenderStep;

public class WebPet {

	public static void main(String[] args) {

		GameEngineBuilder builder = new GameEngineBuilder();
		builder.create(
				"pet",
				"Web Pet",
				"v00.00.0001",
				"Digital pet with state stored on the web.");

		builder.step(GameEngineStep.INIT, new WebPetInitStep());
		builder.step(GameEngineStep.BUILD, new WebPetBuildStep());
		builder.step(GameEngineStep.CONTROL, new WebPetControlStep());
		builder.step(GameEngineStep.MODIFY, new WebPetModifyStep());
		builder.step(GameEngineStep.RENDER, new WebPetRenderStep());
		builder.step(GameEngineStep.DISPOSE, new WebPetDisposeStep());

		builder.startEngine();
	}

}
