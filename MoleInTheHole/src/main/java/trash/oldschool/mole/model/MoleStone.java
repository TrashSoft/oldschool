package trash.oldschool.mole.model;

import trash.oldschool.mole.engine.MoleStoneFall;

public class MoleStone extends MoleMoveable {

	private MoleStoneFall fallType;

	public MoleStone(int x, int y) {
		super(x, y);
	}

	////////////////////////////////////////////////////////////////////////////
	// The special case I need to handle about stones is when they are falling
	// off other stones. It should be slower, that the free fall, and the
	// path is different.
	////////////////////////////////////////////////////////////////////////////

	public void move(int targetX, int targetY, MoleStoneFall fallType) {
		super.move(targetX, targetY);
		this.fallType = fallType;
	}

	@Override
	public void reduceDelta(double time) {
		if(fallType == MoleStoneFall.SIDE_FALL) {
			super.reduceDelta(time / 2.0);
		} else {
			super.reduceDelta(time);
		}

		if(isAllowedToMove()) {
			fallType = MoleStoneFall.NONE;
		}
	}

	@Override
	public double currentX() {
		double x;

		if(isAllowedToMove()) {
			x = position.x;
		} else {
			double t = (fallType == MoleStoneFall.SIDE_FALL ? delta * delta * delta : delta);
			x = (position.x * t + target.x * (1.0 - t));
		}

		return x;
	}

	@Override
	public double currentY() {
		double y;

		if(isAllowedToMove()) {
			y = position.y;
		} else {
			double t = (fallType == MoleStoneFall.SIDE_FALL ? 1- (1-delta) * (1-delta) * (1-delta) : delta);
			y = (position.y * t + target.y * (1.0 - t));
		}

		return y;
	}
}
