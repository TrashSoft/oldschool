package trash.oldschool.scifinovel.data.starmap.v1;

import trash.oldschool.scifinovel.data.common.v1.Color;
import trash.oldschool.scifinovel.data.common.v1.Position;
import trash.oldschool.xml.XmlClass;
import trash.oldschool.xml.XmlField;

@XmlClass("star")
public class Star {

	public Star() {
	}

	public Star(String name, Position position, Position orbitCenter, Position direction, Color color, int size) {
		this.position = position;
		this.orbitCenter = orbitCenter;
		this.direction = direction;
		this.color = color;
		this.name = name;
		this.size = size;
	}

	@XmlField("position")
	public Position getPosition() {
		return position;
	}

	@XmlField("position")
	public void setPosition(Position position) {
		this.position = position;
	}

	@XmlField("orbitCenter")
	public Position getOrbitCenter() {
		return orbitCenter;
	}

	@XmlField("orbitCenter")
	public void setOrbitCenter(Position orbitCenter) {
		this.orbitCenter = orbitCenter;
	}

	@XmlField("direction")
	public Position getDirection() {
		return direction;
	}

	@XmlField("direction")
	public void setDirection(Position direction) {
		this.direction = direction;
	}

	@XmlField("color")
	public Color getColor() {
		return color;
	}

	@XmlField("color")
	public void setColor(Color color) {
		this.color = color;
	}

	@XmlField("name")
	public String getName() {
		return name;
	}

	@XmlField("name")
	public void setName(String name) {
		this.name = name;
	}

	@XmlField("size")
	public int getSize() {
		return size;
	}

	@XmlField("size")
	public void setSize(int size) {
		this.size = size;
	}

	private String name;

	private Color color;
	private int size;

	private Position position;
	private Position orbitCenter;
	private Position direction;

}
