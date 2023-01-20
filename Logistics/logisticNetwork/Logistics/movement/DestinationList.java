package movement;

//matsutni original PointListを継承
//配送目的地の一覧を格納するリスト

public class DestinationList extends PointList implements Cloneable{

	//コンストラクタ
	public DestinationList() {
		super();
	}
	
	public void addList(Point c) {
		pointsList.add(c);
	}
	
	public Point popList() {
		return pointsList.poll();
	}
	
}
