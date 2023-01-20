package movement;

import core.Coord;

//my original
//配送目的地の一覧を格納するリスト

public class MyList extends PointList implements Cloneable{

	double distanceFromStart;
	//コンストラクタ
	public MyList() {
		super();
		
	}
	
	public void addList(Coord c) {
		list.add(c);
	}
	
	public Coord popList() {
		return list.poll();
	}
	
}
