package movement;

import java.awt.Rectangle;
import java.awt.geom.Line2D;
/* 
 * Copyright 2010 Aalto University, ComNet
 * Released under GPLv3. See LICENSE.txt for details. 
 */
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Random;

import core.Coord;
import core.Settings;
import org.jgrapht.alg.tour.*;


/**
 * 各基地に配置されたノードがアルゴリズムに従って移動。
 */

public class LogisticDroneMovement extends MovementModel {
// MovementModel を継承。
	
	
	/** Name space of the settings (append to group name space) */
	public static final String DRONE_MOVEMENT_NS = "DroneMovement.";
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
	private LinkedList<Coord> DList; // Destination:配送目的地のリスト

	private LinkedList<Coord> PList; //Point:経由地のリスト

<<<<<<< HEAD
	private  DestinationList BList; //Base:配送倉庫のリスト
=======
	private LinkedList<Coord> BList; //Base:配送倉庫のリスト
	
	private LinkedList<Coord> MyList; //自身の経由するポイントのリスト
	
	private LinkedList<Coord> DropList; //自身の後から経由するポイントのリスト
>>>>>>> refs/remotes/origin/master

	Comparator<Coord> compare;
	
	double startX;
	double startY;
	Coord myBase;
	Coord dest;
	
	double nofdrones[];
	double nofcells[];
<<<<<<< HEAD
	
=======
	double nofpoints[];

>>>>>>> refs/remotes/origin/master
	
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
	
	//直線で移動したときの直線
	Line2D.Double directLine;

	//Line：3x3のセルを想定
	//cellのグリッドを表すライン4本
	Line2D.Double gx1 = new Line2D.Double(0,3000,9000,3000);	
	Line2D.Double gx2 = new Line2D.Double(0,6000,9000,6000);	
	Line2D.Double gy1 = new Line2D.Double(3000,0,3000,9000);
	Line2D.Double gy2 = new Line2D.Double(6000,0,6000,9000);
	
	//セルの当たり判定を表す四角形9つ
	Rectangle c0 = new Rectangle(0,0,3000,3000);
	Rectangle c1 = new Rectangle(3000,0,3000,3000);
	Rectangle c2 = new Rectangle(6000,0,3000,3000);

	Rectangle c3 = new Rectangle(0,3000,3000,3000);
	Rectangle c4 = new Rectangle(3000,3000,3000,3000);
	Rectangle c5 = new Rectangle(6000,3000,3000,3000);

	Rectangle c6 = new Rectangle(0,6000,3000,3000);
	Rectangle c7 = new Rectangle(3000,6000,3000,3000);
	Rectangle c8 = new Rectangle(6000,6000,3000,3000);	
	
	//セルと直線のあたり判定を保存するためのリスト
	List<Boolean> lr = new ArrayList<Boolean>();


	public LogisticDroneMovement(Settings settings) {
		super(settings);
		
		nofdrones = settings.getCsvDoubles(NUMBER_OF_DRONES,1);
<<<<<<< HEAD
=======
		// System.out.println("nofnodes:"+nofdrones[0]); output : 3.0
>>>>>>> refs/remotes/origin/master
		nofcells = settings.getCsvDoubles(NUMBER_OF_CELLS,1);
<<<<<<< HEAD
		
		this.startLoc = new Coord(coords[0], coords[1]);
=======
		nofpoints = settings.getCsvDoubles(NUMBER_OF_POINTS,1);
>>>>>>> refs/remotes/origin/master

		this.DList = new LinkedList<Coord>();
		//System.out.println("initialize Destitnation List @ LogisticDroneMovement");

<<<<<<< HEAD
		this.PList = new DataPointList();
		System.out.println("initialize Data Point List@Movementmodel");

		
=======
		this.PList = new LinkedList<Coord>();
		//System.out.println("initialize Data Point List @ LogisticDroneMovement");
>>>>>>> refs/remotes/origin/master
		
<<<<<<< HEAD
		this.BList = new DestinationList();
		System.out.println("initialize Base List@Movementmodel");
=======
		this.BList = new LinkedList<Coord>();
		//System.out.println("initialize Base List @ LogisticDroneMovement");
		
		this.MyList = new LinkedList<Coord>();
		//System.out.println("initialize My Point List @ LogisticDroneMovement");
		
		this.DropList = new LinkedList<Coord>();
		//System.out.println("initialize Drop Point List @ LogisticDroneMovement");

>>>>>>> refs/remotes/origin/master
	}

	protected LogisticDroneMovement(LogisticDroneMovement dm) {
		super(dm);

		this.DList = dm.DList;
		this.PList = dm.PList;
		this.BList = dm.BList;
<<<<<<< HEAD
=======
		this.MyList = dm.MyList;
		this.DropList = dm.DropList;
>>>>>>> refs/remotes/origin/master
		this.startLoc = dm.startLoc;	
		this.nofdrones = dm.nofdrones;
		this.nofcells = dm.nofcells;
<<<<<<< HEAD
		
		startX = startLoc.getX();
		startY = startLoc.getY();
=======
		this.nofpoints = dm.nofpoints;
>>>>>>> refs/remotes/origin/master
	}
	
	/**
	 * 自身の配送基地を決定し、基地の場所を自身のスタート地点にする。
	 * また、自身の経由する予定の経由地を持つリストも生成する。
	 * @return 自身のスタート地点
	 * added by matsutani
	 */
	@Override
	public Coord getInitialLocation() {
		assert rng != null : "MovementModel not initialized!";
		
        long startTime = System.currentTimeMillis();

		//自身の基地を初期配置にする。
<<<<<<< HEAD
=======
		System.out.println("Initialize @ Nodes"+super.getHost().getAddress());
>>>>>>> refs/remotes/origin/master
		Coord c = getMyBase();
		myBase = new Coord(c.getX(),c.getY());
		this.lastWaypoint = c;
<<<<<<< HEAD
		System.out.println("test1:");
		System.out.println("test2:");
=======

>>>>>>> refs/remotes/origin/master
		
<<<<<<< HEAD
		readListDest();
		readListDataPoint();
		System.out.println("test3:");
		System.out.println("test4:");
=======
		//経由地と配送目的地を決定。
		readListDest();	//配送目的地の決定
		readListDataPoint();//経由地の決定とMyListへの追加
		MyList.add(dest);//配送目的地のMyListへの追加
		readListDrop();
		

		
		
		//System.out.println("自身が経由しないセル内にある経由地の数:"+DropList.size() + "@ Node"+super.getHost().getAddress());
		/**
		 * 補足 by matsutani
		 *
		 * MyListの最後に配送目的地を入れたい
		 * でもDataPointの中で、配送目的を利用したい（配送目的地までの直線をひくため）
		 * そのためにreadListDest()とMyList.add(dest)を分けた。
		 * 
		 */
	     long endTime = System.currentTimeMillis();
	     System.out.println("経路計算時間：" + (endTime - startTime) + " ms");
	     
		System.out.println("------------------------------------");
>>>>>>> refs/remotes/origin/master
		return c;
	}

	//ファイル読み込みで配送目的地、データポイントのリストを取得。
	//ファイルはpoint_listの中にtxtファイルとして格納。txt書き出しダサいから本当は直したい。
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
					System.out.println("error:DestinationListに奇数個の数字が読み込まれています@LogisticDroneMobement");
				}else {
					y=Double.parseDouble(data);
					destp = new Coord(x,y);
					System.out.println(destp);
					
					DList.add(destp);
				}
			}

			//DListから一つだけ取り出し、そこを現在のノードの目的地にする。
			Coord keep = DList.poll();

			//DListに残っている配送目的地をファイルに書き出す。
			Coord tmp;
			try {
				FileWriter fw = new FileWriter("point_list/DestinationList.txt");
				while((tmp = DList.poll())!=null) {
				//	System.out.println(tmp);
					fw.write(tmp.getX()+"\n"+tmp.getY()+"\n");
				}
				fw.close();
			//	System.out.println("Destination ReWrite");
			}catch(IOException ex) {
				ex.printStackTrace();
				System.out.println("error");
			}

			destp = new Coord(keep.getX(),keep.getY());
<<<<<<< HEAD
			DList.addList(destp);
			System.out.println("finally Destination List : " + destp);

=======

			DList.add(destp);
			System.out.println("最終目的地決定: " + destp+"@"+super.getHost().getAddress());
			
			dest = new Coord(destp.getX(), destp.getY());
			
>>>>>>> refs/remotes/origin/master
			fd.close();
			br.close();

		}catch(IOException ex) {
			ex.printStackTrace();
			System.out.println("error");
		}

	}

	//ファイル読み込みで配送目的地、データポイントのリストを取得。
	//ファイルはpoint_listの中にtxtファイルとして格納。txt書き出しダサいから本当は直したい。
	public void readListDataPoint() {
		try {
			FileReader fd = new FileReader("point_list/DataPointList.txt");
			BufferedReader br =  new BufferedReader(fd);

			String data;
			Coord datap; //Data Point
			double x;
			double y;
<<<<<<< HEAD
=======
			
			//int j=0;
>>>>>>> refs/remotes/origin/master
			while ((data = br.readLine()) != null) {
				x = Double.parseDouble(data); 
				if((data=br.readLine())==null) {
					System.out.println("error:DatapointListに奇数個の数字が読み込まれています");
				}else {
					y=Double.parseDouble(data);
					datap = new Coord(x,y);
<<<<<<< HEAD
					PList.addList(datap);
					//				System.out.println("DataPoint Read");
=======
					PList.add(datap);
				//	j++;
>>>>>>> refs/remotes/origin/master
				}
<<<<<<< HEAD
=======
			//	System.out.println("Size of PList:"+j);
>>>>>>> refs/remotes/origin/master
			}
<<<<<<< HEAD

=======
				
				//要変更：ここがアルゴリズムの本質です。
				//現在は単にリストの先頭から順番に取り出しているだけ。
				//セルを考慮して選び方を決定する。
				//ここから
			
				directLine = new Line2D.Double(myBase.getX(),myBase.getY(),dest.getX(),dest.getY());	
				
				//nodeの直線移動経路が各rectangleと交差するかを判定
				lr.add(directLine.intersects(c0));
				lr.add(directLine.intersects(c1));
				lr.add(directLine.intersects(c2));
				lr.add(directLine.intersects(c3));
				lr.add(directLine.intersects(c4));
				lr.add(directLine.intersects(c5));
				lr.add(directLine.intersects(c6));
				lr.add(directLine.intersects(c7));
				lr.add(directLine.intersects(c8));
							
				int sizePL = PList.size();
				int nofmypoint;
				
				int res =  (int) (nofpoints[0] % nofdrones[0]);
				nofmypoint = (int) (nofpoints[0]/nofdrones[0]);
				//System.out.println("res:"+res);
				
				if(sizePL==nofpoints[0] || nofpoints[0]-res*(nofmypoint+1)<sizePL){
					nofmypoint++;
				}

			//	
				//直線からの距離をセット。
				for(Coord c: PList) {
					double tmp = c.distanceFromLine(directLine);
					c.setDFromLine(tmp);
				}	
				
				
				Comparator<Coord> comparator1 = new Comparator<Coord>() {
					@Override
					public int compare(Coord c1, Coord c2) {
						double diff = c1.getDFromLine() - c2.getDFromLine();
						
						return (int) diff;
					}

				};
				
				Collections.sort(PList,comparator1);
				
				for(int i=0; i< nofmypoint; i++){
					Coord tmp = PList.poll();
					
					if(tmp!=null) {
						int e = calarea3x3(tmp);
						if(lr.get(e)==true) {
							MyList.add(tmp);
						}else {
							DropList.add(tmp);
							System.out.println("Drone"+super.getHost().getAddress()+"は,cell"+e+"を経由しない");
						}
					}
				}
				//ここまで
								
				Coord tmp;
				try {
					FileWriter fw = new FileWriter("point_list/DataPointList.txt");
					//System.out.println("残りのノードに行ってもらう経由地の数"+PList.size()+"@ Node "+super.getHost().getAddress());
					while((tmp = PList.poll())!=null) {
					//	System.out.println(tmp);
						fw.write(tmp.getX()+"\n"+tmp.getY()+"\n");
					}

					fw.close();
				//	System.out.println("Datapoint ReWrite@"+super.getHost().getAddress());
					
					}catch(IOException ex) {
					ex.printStackTrace();
					System.out.println("error");
				}
				
>>>>>>> refs/remotes/origin/master
			fd.close();
			br.close();
			//	File f = new File("point_list/DataPointList.txt");
			//	f.delete();
		}catch(IOException ex) {
			ex.printStackTrace();
			System.out.println("error");
		}		
		
		/**
		 * 要修正：現状は基地からの距離のみで図っているため、逆方向にも行ってしまう？　これがなんか変なので修正する。
		 * →修正済み、但し未だに少し遠回りしすぎている場合がある気がする。どうにかならんのか。
		 */
		
		//基地からの距離をセット。
		for(Coord x: MyList) {
			double tmp = x.distance(myBase);
			x.setDFromStart(tmp);
		}	
		
		/*
		System.out.println("---未ソート---");
		int i=0;
		System.out.println("Size:"+MyList.size());
		for(Coord x: MyList) {
			System.out.println("getDFromStart @ Point"+i+":"+x.getDFromStart());
			i++;
		}
		*/

		/**数時間かけてやっとうまくいったソート。
		 * 要修正：現段階では配送基地からの距離によって経由地をソートし、距離の小さい順に訪れる。
		 * →ここが逆方向に行ってしまう原因か、上記のCoord.setD()を修正する。→修正済み
		 */
		
		//comparator2はスタートの基地からの距離によってソート
		Comparator<Coord> comparator2 = new Comparator<Coord>() {
			@Override
			public int compare(Coord c1, Coord c2) {
				double diff = c1.getDFromStart() - c2.getDFromStart();
				
				return (int) diff;
			}

		};
		
		
		Collections.sort(MyList,comparator2);

		//System.out.println("---ソート後---");
		int i=0;
		for(Coord x: MyList) {
			System.out.println("getDFromStart @ Point"+i+x+":"+x.getDFromStart());
			i++;
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
<<<<<<< HEAD
					destp = new Coord(x,y);
					BList.addList(destp);
=======
					basep = new Coord(x,y);
					BList.add(basep);
>>>>>>> refs/remotes/origin/master
					i++;
				}

			}
<<<<<<< HEAD
 
			System.out.println("i:"+i);


=======
			System.out.println("getMyBase@ Node"+super.getHost().getAddress());
			
>>>>>>> refs/remotes/origin/master
			Random random = new Random();
			int r = random.nextInt(100)%i + 1;
			System.out.println("rand:"+r);		

			
			for (int j=0;j<r;j++) {
				keep = BList.poll();
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


	public void readListDrop() {
		Coord tmp;
		while((tmp = DropList.poll())!=null) {
			MyList.add(tmp);
		}
	}

	@Override
	public Path getPath() {
		clearPointList();
		
		Path p;
		p = new Path(generateSpeed());
		p.addWaypoint(lastWaypoint.clone());
		Coord c = lastWaypoint;

<<<<<<< HEAD
		c = DList.popList();
=======
		c = MyList.poll();
>>>>>>> refs/remotes/origin/master
		if(c==null) {
		//	c = new Coord(startX,startY);
			c = new Coord(myBase.getX(),myBase.getY());
		}
		p.addWaypoint(c);	
		this.lastWaypoint = c;
		
		//シミュレーション実行中、常に現在経由しているパスを表示させる。
		//System.out.println(p+"@"+super.getHost().getAddress());
		
		return p;
	}
	
	public void clearPointList(){
		
		try (RandomAccessFile raf = new RandomAccessFile("point_list/DataPointList.txt", "rw")) {
            raf.setLength(0);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
		
		try (RandomAccessFile raf = new RandomAccessFile("point_list/BaseList.txt", "rw")) {
            raf.setLength(0);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
		
		try (RandomAccessFile raf = new RandomAccessFile("point_list/DestinationList.txt", "rw")) {
            raf.setLength(0);
        }
        catch (IOException e) {
            e.printStackTrace();
        }

		
	}

	@Override
	public LogisticDroneMovement replicate() {
		PList.clear();
		DList.clear();
		DropList.clear();
		return new LogisticDroneMovement(this);
	}

	protected Coord randomCoord() {
		return new Coord(rng.nextDouble() * getMaxX(),
				rng.nextDouble() * getMaxY());
	}


	public Queue<Coord> getDList() {
		return this.DList;
	}


	public Queue<Coord> getPList() {
		return this.PList;
	}
	
	public int calarea3x3(Coord c) {
		if(c.getX()<3000) {
			if(c.getY()<3000) {
				return  0;
			}else if(c.getY()<6000) {
				return 3;
			}else {
				return 6;
			}
		}else if(c.getX()<6000) {
			if(c.getY()<3000) {
				return 1;
			}else if(c.getY()<6000) {
				return 4;
			}else {
				return 7;
			}	
		}else {
			if(c.getY()<3000) {
				return  2;
			}else if(c.getY()<6000) {
				return 5;
			}else {
				return 8;
			}
		}
	
	}

}

