package trash.oldschool.pet.engine;

import trash.oldschool.engine.GameEngineCallback;
import trash.oldschool.engine.GameEngineFacade;
import trash.oldschool.pet.model.WebPetModel;

public class WebPetDisposeStep implements GameEngineCallback {

	@Override
	public Object call(GameEngineFacade facade) {
		WebPetModel model = (WebPetModel) facade.model();
		return null;
	}

}
