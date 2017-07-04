package trash.oldschool.scifinovel.model;

public class SciFiNovelPlayer extends SciFiNovelMoveable {

	public static final String ANIMATION_DIRECTION_DOWN = "down";
	public static final String ANIMATION_DIRECTION_UP = "up";
	public static final String ANIMATION_DIRECTION_LEFT = "left";
	public static final String ANIMATION_DIRECTION_RIGHT = "right";

	public String animationDirection = ANIMATION_DIRECTION_DOWN;

	public SciFiNovelPlayer(int x, int y) {
		super(x, y);
	}

}
