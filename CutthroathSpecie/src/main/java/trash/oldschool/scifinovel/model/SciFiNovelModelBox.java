package trash.oldschool.scifinovel.model;

public class SciFiNovelModelBox extends SciFiNovelMoveable {

	public int type = 0;
	public boolean merging;

	public SciFiNovelModelBox(int x, int y, int type) {
		super(x, y);
		this.type = type;
	}

}
