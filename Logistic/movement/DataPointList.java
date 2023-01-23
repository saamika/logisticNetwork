package movement;

import core.Coord;

//my original
//データポイントの一覧を格納するリスト

public class DataPointList extends PointList{

	//コンストラクタ
	public DataPointList() {
		super();
	}
	
	public void addList(Coord c) {
		list.add(c);
	}
	
	public Coord popList() {
		return list.poll();
	}
	
}
