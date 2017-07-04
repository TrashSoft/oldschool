package trash.oldschool.scifinovel.engine;

import trash.oldschool.engine.GameEngineCallback;
import trash.oldschool.engine.GameEngineFacade;
import trash.oldschool.scifinovel.model.SciFiNovelModel;

public class SciFiNovelDisposeStep implements GameEngineCallback {

	@Override
	public Object call(GameEngineFacade facade) {
		SciFiNovelModel model = (SciFiNovelModel) facade.model();
		model.map.clear();
		return null;
	}

}
