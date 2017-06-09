package trash.oldschool.engine;

public interface GameControl {

	boolean isUpOn();
	boolean isDownOn();
	boolean isLeftOn();
	boolean isRightOn();
	boolean isControlOn();
	boolean isAltOn();
	boolean isShiftOn();

	void registerKeyListener(GameKeyListener listener);
}
