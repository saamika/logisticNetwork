package movement;
import java.util.ArrayDeque;
import java.util.Queue;

import core.Coord;

//my original
//複数の座標を格納するリスト
//キューで実装
//DestinationListやDataPointListに継承させる。

public abstract class PointList {

	public static final String  isList = "isList";
	
	Queue<Coord> list;
	//コンストラクタ
	public PointList() {
		list = new ArrayDeque<>();
	}
	
	public void addList(Coord c) {
		list.add(c);
	}
	
	public Coord popList() {
		return list.poll();
	}
	
}
