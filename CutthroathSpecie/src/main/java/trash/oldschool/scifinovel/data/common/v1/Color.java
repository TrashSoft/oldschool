package trash.oldschool.scifinovel.data.common.v1;

import trash.oldschool.xml.XmlClass;
import trash.oldschool.xml.XmlField;

@XmlClass("color")
public class Color {

	public Color() {
	}

	public Color(int r, int g, int b) {
		this.r = r;
		this.g = g;
		this.b = b;
	}

	@XmlField("r")
	public int getR() {
		return r;
	}

	@XmlField("r")
	public void setR(int r) {
		this.r = r;
	}

	@XmlField("g")
	public int getG() {
		return g;
	}

	@XmlField("g")
	public void setG(int g) {
		this.g = g;
	}

	@XmlField("b")
	public int getB() {
		return b;
	}

	@XmlField("b")
	public void setB(int b) {
		this.b = b;
	}

	private int r;
	private int g;
	private int b;

}
