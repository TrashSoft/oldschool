package trash.oldschool.pet.engine;

import trash.oldschool.engine.GameEngineCallback;
import trash.oldschool.engine.GameEngineFacade;
import trash.oldschool.pet.model.WebPetModel;

public class WebPetModifyStep implements GameEngineCallback {

	private static final double GAME_SPEED = 5.0; // seconds
	// private static final Random RANDOM = new Random(System.currentTimeMillis());

	@Override
	public Object call(GameEngineFacade facade) {
		WebPetModel model = (WebPetModel) facade.model();
		return facade;
	}

}
