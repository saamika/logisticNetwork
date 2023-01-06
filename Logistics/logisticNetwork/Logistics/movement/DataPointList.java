package movement;

//matsutani original PointListを継承
//データポイントの一覧を格納するリスト

public class DataPointList extends PointList{

	//コンストラクタ
	public DataPointList() {
		super();
	}
	
	public void addList(Point c) {
		pointsList.add(c);
	}
	
	public Point popList() {
		return pointsList.poll();
	}
	
}
