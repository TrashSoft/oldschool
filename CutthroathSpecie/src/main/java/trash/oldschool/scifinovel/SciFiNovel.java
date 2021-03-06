package trash.oldschool.scifinovel;

import trash.oldschool.engine.GameEngineBuilder;
import trash.oldschool.engine.intf.GameEngineStep;
import trash.oldschool.scifinovel.data.common.v1.Color;
import trash.oldschool.scifinovel.data.common.v1.Position;
import trash.oldschool.scifinovel.data.starmap.v1.Star;
import trash.oldschool.scifinovel.engine.SciFiNovelBuildStep;
import trash.oldschool.scifinovel.engine.SciFiNovelControlStep;
import trash.oldschool.scifinovel.engine.SciFiNovelDisposeStep;
import trash.oldschool.scifinovel.engine.SciFiNovelInitStep;
import trash.oldschool.scifinovel.engine.SciFiNovelModifyStep;
import trash.oldschool.scifinovel.engine.SciFiNovelRenderStep;
import trash.oldschool.xml.XmlBinding;

public class SciFiNovel {

	public static void main(String[] args) {

		XmlBinding.registerClass(Position.class);
		XmlBinding.registerClass(Color.class);

		XmlBinding.registerClass(Star.class);

		GameEngineBuilder builder = new GameEngineBuilder();
		builder.create(
				"cutthroatSpecie",
				"Cutthroat Specie",
				"v00.00.0001",
				"Game to support my motivation in building up a novel story.");

		builder.step(GameEngineStep.INIT, new SciFiNovelInitStep());
		builder.step(GameEngineStep.BUILD, new SciFiNovelBuildStep());
		builder.step(GameEngineStep.CONTROL, new SciFiNovelControlStep());
		builder.step(GameEngineStep.MODIFY, new SciFiNovelModifyStep());
		builder.step(GameEngineStep.RENDER, new SciFiNovelRenderStep());
		builder.step(GameEngineStep.DISPOSE, new SciFiNovelDisposeStep());

		builder.startEngine();
	}

}
