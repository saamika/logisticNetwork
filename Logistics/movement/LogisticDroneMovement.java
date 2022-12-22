package movement;

import java.awt.geom.Line2D;
/* 
 * Copyright 2010 Aalto University, ComNet
 * Released under GPLv3. See LICENSE.txt for details. 
 */
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

import core.Coord;
import core.Settings;



/**
 * 各基地に配置されたノードがアルゴリズムに従って移動。
 */
public class LogisticDroneMovement extends MovementModel {

	/** Name space of the settings (append to group name space) */
	public static final String DRONE_MOVEMENT_NS = "DroneMovement.";
	/** Per node group setting for defining the start coordinates of 
	 * the line ({@value}) */
	public static final String START_LOCATION_S = "startLocation";
	/** Per node group setting for defining the end coordinates of 
	 * the line ({@value}) */
	public static final String END_LOCATION_S = "endLocation";	

	public static final String NUMBER_OF_DRONES = "nofdrones";	
	
	public static final String NUMBER_OF_CELLS = "nofdrones";	
	
	public int  flag =0;
	/** how many waypoints should there be per path */
	//private static final int PATH_LENGTH = 1;

	private Coord startLoc; /** The start location of the line */

	private Coord lastWaypoint; //1つ前の経由地
	private  DestinationList DList; // Destination:配送目的地のリスト

	private  DataPointList PList; //Point:経由地のリスト

	private  DestinationList BList; //Base:配送倉庫のリスト

	double startX;
	double startY;
	
	double nofdrones[];
	double nofcells[];
	
	
	//変数宣言
	double m_d_direct;
	double m_d_sum1;
	double m_ratio1;
	double m_d_direct_1;
	double m_d_direct_2;
	double m_d_sum_1;
	double m_d_sum_2;
	int m_exchange;
	double m_d_sum_ex1;
	double m_d_sum_ex2;

	
	//Line：3x3のセルを想定
	Line2D.Double gx1 = new Line2D.Double(0,3000,9000,3000);	
	Line2D.Double gx2 = new Line2D.Double(0,6000,9000,6000);	
	Line2D.Double gy1 = new Line2D.Double(3000,0,3000,9000);
	Line2D.Double gy2 = new Line2D.Double(6000,0,6000,9000);


	public LogisticDroneMovement(Settings settings) {
		super(settings);


		double coords[];

		coords = settings.getCsvDoubles(START_LOCATION_S, 2);
		nofdrones = settings.getCsvDoubles(NUMBER_OF_DRONES,1);
		nofcells = settings.getCsvDoubles(NUMBER_OF_CELLS,1);
		
		this.startLoc = new Coord(coords[0], coords[1]);

		this.DList = new DestinationList();
		System.out.println("initialize Destitnation List@Movementmodel");

		this.PList = new DataPointList();
		System.out.println("initialize Data Point List@Movementmodel");

		
		
		this.BList = new DestinationList();
		System.out.println("initialize Base List@Movementmodel");
	}

	protected LogisticDroneMovement(LogisticDroneMovement dm) {
		super(dm);
		this.DList = dm.DList;
		this.PList = dm.PList;
		this.BList = dm.BList;
		this.startLoc = dm.startLoc;	
		this.nofdrones = dm.nofdrones;
		this.nofcells = dm.nofcells;
		
		startX = startLoc.getX();
		startY = startLoc.getY();
	}
	/**
	 * Returns a possible (random) placement for a host
	 * @return Random position on the map
	 */
	@Override
	public Coord getInitialLocation() {
		assert rng != null : "MovementModel not initialized!";
		//自身の基地を初期配置にする。
		Coord c = getMyBase();
		this.lastWaypoint = c;
		System.out.println("test1:");
		System.out.println("test2:");
		
		readListDest();
		readListDataPoint();
		System.out.println("test3:");
		System.out.println("test4:");
		return c;
	}


	//ファイル読み込みで配送目的地、データポイントのリストを取得。
	public void readListDest() {

		try {
			//全ての配送目的地をtxtファイルから読み出し、DListに格納。
			FileReader fd = new FileReader("point_list/DestinationList.txt");
			BufferedReader br =  new BufferedReader(fd);

			String data;
			Coord destp; //Destination Point
			double x;
			double y;
			while ((data = br.readLine()) != null) {
				x = Double.parseDouble(data); 
				if((data=br.readLine())==null) {
					System.out.println("error:DestinationListに奇数個の数字が読み込まれています");
				}else {
					y=Double.parseDouble(data);
					destp = new Coord(x,y);
					DList.addList(destp);

				}
			}

			//DListから一つだけ取り出し、そこを現在のノードの目的地にする。
			Coord keep = DList.popList();


			System.out.println("------------------------------------");

			//DListに残っている配送目的地をファイルに書き出す。
			Coord tmp;
			try {
				FileWriter fw = new FileWriter("point_list/DestinationList.txt");
				while((tmp = DList.popList())!=null) {
					System.out.println(tmp);
					fw.write(tmp.getX()+"\n"+tmp.getY()+"\n");
				}
				fw.close();
				System.out.println("Destination ReWrite");
			}catch(IOException ex) {
				ex.printStackTrace();
				System.out.println("error");
			}


			System.out.println("------------------------------------");

			destp = new Coord(keep.getX(),keep.getY());
			DList.addList(destp);
			System.out.println("finally Destination List : " + destp);

			fd.close();
			br.close();

			//		System.out.println("Destination Read");

			//		File f = new File("point_list/DestinationList.txt");
			//		f.delete();

		}catch(IOException ex) {
			ex.printStackTrace();
			System.out.println("error");
		}


	}


	//ファイル読み込みで配送目的地、データポイントのリストを取得。
	public void readListDataPoint() {
		try {
			FileReader fd = new FileReader("point_list/DataPointList.txt");
			BufferedReader br =  new BufferedReader(fd);

			String data;
			Coord datap; //Data Point
			double x;
			double y;
			while ((data = br.readLine()) != null) {
				x = Double.parseDouble(data); 
				if((data=br.readLine())==null) {
					System.out.println("error:DatapointListに奇数個の数字が読み込まれています");
				}else {
					y=Double.parseDouble(data);
					datap = new Coord(x,y);
					PList.addList(datap);
					//				System.out.println("DataPoint Read");
				}
			}

			fd.close();
			br.close();
			//	File f = new File("point_list/DataPointList.txt");
			//	f.delete();
		}catch(IOException ex) {
			ex.printStackTrace();
			System.out.println("error");
		}


	}


	public Coord getMyBase() {
		Coord keep = null;
		try {
			FileReader fd = new FileReader("point_list/BaseList.txt");
			BufferedReader br =  new BufferedReader(fd);

			String data;
			Coord destp; //Destination Point
			double x;
			double y;
			int i=0;
			while ((data = br.readLine()) != null) {
				x = Double.parseDouble(data); 
				if((data=br.readLine())==null) {
					System.out.println("error:DestinationListに奇数個の数字が読み込まれています");
				}else {
					y=Double.parseDouble(data);
					destp = new Coord(x,y);
					BList.addList(destp);
					i++;
				}

			}
 
			System.out.println("i:"+i);


			Random random = new Random();
			int r = random.nextInt(100)%i + 1;
			System.out.println("rand:"+r);		

			
			for (int j=0;j<r;j++) {
				keep = BList.popList();
			}
			
			if(keep == null) {
				System.out.println("自身の基地決定でエラー");
			}
			
			startX = keep.getX();
			startY = keep.getY();
			
			fd.close();
			br.close();


			return keep;
			//		f.delete();

		}catch(IOException ex) {
			ex.printStackTrace();
			System.out.println("error");
		}

		return keep;


	}



	@Override
	public Path getPath() {
		Path p;
		p = new Path(generateSpeed());
		p.addWaypoint(lastWaypoint.clone());
		Coord c = lastWaypoint;

		c = DList.popList();
		if(c==null) {
			c = new Coord(startX,startY);
		}
		p.addWaypoint(c);	
		this.lastWaypoint = c;
		System.out.println(p);
		
		return p;
	}

	@Override
	public LogisticDroneMovement replicate() {
		return new LogisticDroneMovement(this);
	}

	protected Coord randomCoord() {
		return new Coord(rng.nextDouble() * getMaxX(),
				rng.nextDouble() * getMaxY());
	}


	public DestinationList getDList() {
		return this.DList;
	}


	public DataPointList getPList() {
		return this.PList;
	}

}

