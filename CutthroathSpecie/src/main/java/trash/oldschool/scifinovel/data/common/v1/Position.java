package trash.oldschool.scifinovel.data.common.v1;

import trash.oldschool.xml.XmlClass;
import trash.oldschool.xml.XmlField;

@XmlClass("position")
public class Position {

	public Position() {
	}

	public Position(int x, int y, int z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	@XmlField("x")
	public int getX() {
		return x;
	}

	@XmlField("x")
	public void setX(int x) {
		this.x = x;
	}

	@XmlField("y")
	public int getY() {
		return y;
	}

	@XmlField("y")
	public void setY(int y) {
		this.y = y;
	}

	@XmlField("z")
	public int getZ() {
		return z;
	}

	@XmlField("z")
	public void setZ(int z) {
		this.z = z;
	}

	private int x;
	private int y;
	private int z;

}
