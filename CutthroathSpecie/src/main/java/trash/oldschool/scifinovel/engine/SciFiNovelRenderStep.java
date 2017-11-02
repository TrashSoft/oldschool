package trash.oldschool.scifinovel.engine;

import trash.oldschool.engine.GameEngineCallback;
import trash.oldschool.engine.GameEngineFacade;
import trash.oldschool.engine.g2d.GameColor;
import trash.oldschool.engine.intf.GameGraphics;
import trash.oldschool.scifinovel.data.common.v1.Color;
import trash.oldschool.scifinovel.data.common.v1.Position;
import trash.oldschool.scifinovel.data.starmap.v1.Star;
import trash.oldschool.scifinovel.model.SciFiNovelModel;

public class SciFiNovelRenderStep implements GameEngineCallback {

	private static final int TILE_WIDTH_AND_HEIGHT = 64;

	@Override
	public Object call(GameEngineFacade facade) {
		SciFiNovelModel model = (SciFiNovelModel) facade.model();
		GameGraphics g = facade.graphics();
		int screenWidth = g.screenWidth();
		int screenHeight = g.screenHeight();

		double centerX = screenWidth / 2.0;
		double centerY = screenHeight / 2.0;

		g.translate(centerX, centerY);
		g.scale(0.25);

		for(Star star : model.getStars()) {
			Position p = star.getPosition();
			double r = star.getSize() / 2.0;
			Color c = star.getColor();
			GameColor gc = new GameColor(c.getR(), c.getG(), c.getB());
			g.fillOval(p.getX(), p.getY(), r, r, gc);
		}


		g.resetTransformations();
		return null;
	}

}
