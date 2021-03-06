package trash.oldschool.scifinovel.engine;

import trash.oldschool.engine.GameEngineCallback;
import trash.oldschool.engine.GameEngineFacade;
import trash.oldschool.scifinovel.model.SciFiNovelModel;

public class SciFiNovelInitStep implements GameEngineCallback {

	@Override
	public Object call(GameEngineFacade facade) {
		facade.setModel(new SciFiNovelModel());

		facade.control().registerKeyListener(new SciFiNovelSpecialKeys());

		return null;
	}

}
