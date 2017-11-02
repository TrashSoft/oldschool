package trash.oldschool.scifinovel.engine;

import trash.oldschool.engine.GameEngineFacade;
import trash.oldschool.engine.intf.GameKeyListener;
import trash.oldschool.scifinovel.model.SciFiNovelModel;

public class SciFiNovelSpecialKeys implements GameKeyListener {

	@Override
	public void hit(GameEngineFacade facade, char key) {
		SciFiNovelModel model = (SciFiNovelModel) facade.model();

		if (27 == key) { // ESC
			if (model != null) {
				// model.restartLevel();
			}
		}

		if ('n' == key || 'N' == key) {
			if (model != null) {
				// model.nextLevel();
			}
		}
	}

}
