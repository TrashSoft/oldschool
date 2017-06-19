package trash.oldschool.box.engine;

import trash.oldschool.box.model.PushTheBoxModel;
import trash.oldschool.engine.GameEngineCallback;
import trash.oldschool.engine.GameEngineFacade;

public class PushTheBoxDisposeStep implements GameEngineCallback {

	@Override
	public Object call(GameEngineFacade facade) {
		PushTheBoxModel model = (PushTheBoxModel) facade.model();
		model.map.clear();
		return null;
	}

}
