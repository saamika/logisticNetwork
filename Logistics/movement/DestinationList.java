package movement;

import core.Coord;

//my original
//配送目的地の一覧を格納するリスト

public class DestinationList extends PointList implements Cloneable{

	//コンストラクタ
	public DestinationList() {
		super();
	}
	
	public void addList(Coord c) {
		list.add(c);
	}
	
	public Coord popList() {
		return list.poll();
	}
	
}
