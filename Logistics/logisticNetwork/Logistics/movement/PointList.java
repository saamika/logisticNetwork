package movement;
import java.util.LinkedList;

//matsutani original
//複数の座標を格納するリスト
//キューで実装
//DestinationListやDataPointListに継承させる。

public abstract class PointList {

	public static final String  isList = "isList";
	
	LinkedList<Point> pointsList;
	//コンストラクタ
	public PointList() {
		pointsList = new LinkedList<Point>();
	}
	
	public void addList(Point c) {
		pointsList.add(c);
	}
	
	public Point popList() {
		return pointsList.poll();
	}
	
}
