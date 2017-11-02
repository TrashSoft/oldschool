package trash.oldschool.scifinovel.data.common.v1;

import trash.oldschool.xml.XmlClass;
import trash.oldschool.xml.XmlField;

@XmlClass("position")
public class Position {

	public Position() {
	}

	public Position(double x, double y, double z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	@XmlField("x")
	public double getX() {
		return x;
	}

	@XmlField("x")
	public void setX(double x) {
		this.x = x;
	}

	@XmlField("y")
	public double getY() {
		return y;
	}

	@XmlField("y")
	public void setY(double y) {
		this.y = y;
	}

	@XmlField("z")
	public double getZ() {
		return z;
	}

	@XmlField("z")
	public void setZ(double z) {
		this.z = z;
	}

	private double x;
	private double y;
	private double z;

}
