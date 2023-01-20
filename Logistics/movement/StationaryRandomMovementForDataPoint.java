/* 
 * Copyright 2010 Aalto University, ComNet
 * Released under GPLv3. See LICENSE.txt for details. 
 */
package movement;

import java.io.FileWriter;
import java.io.IOException;

import core.Coord;
import core.Settings;

/**
 * A dummy stationary "movement" model where nodes do not move.
 * Might be useful for simulations with only external connection events.
 * 
 *  stationaryMovementを基本として、各ノードの位置を最初にランダムに決定する。
 *  logisticNetworkにおいて、メッセージの経由地はこのMovementModelを利用。
 *  基本的にはStationaryRandomMovementと内容は変わらない。
 *  
 *  noted by matsutani
 */
public class StationaryRandomMovementForDataPoint extends MovementModel {
	private Coord loc; /** The location of the nodes */
//	public DataPointList PList;
	
	int i=0; //test用
	/**
	 * Creates a new movement model based on a Settings object's settings.
	 * @param s The Settings object where the settings are read from
	 */
	public StationaryRandomMovementForDataPoint(Settings s) {
		super(s);
		
//		PList = new DataPointList();
//		System.out.println("initialize DataPoint List");
		//double coords[] = new double[2];
		
		//coords[0] = rng.nextDouble() * getMaxX();
		//coords[1] = rng.nextDouble() * getMaxY();
		//this.loc = new Coord(coords[0],coords[1]);
	}
	
	/**
	 * Copy constructor. 
	 * @param sm The StationaryMovement prototype
	 */
	public StationaryRandomMovementForDataPoint(StationaryRandomMovementForDataPoint sm) {
		super(sm);
		this.loc = sm.loc;
//		this.PList = sm.PList;
	
	}
	
	/**
	 * Returns the only location of this movement model
	 * @return the only location of this movement model
	 */
	@Override
	/**
	 * 初期配置を決定する。
	 * RandomWaypointの実装方法をコピペして実現
	 */
	public Coord getInitialLocation() {
		assert rng != null : "MovementModel not initialized!";
		Coord c = randomCoord();

		this.loc = c;
		
		try {
			FileWriter fw = new FileWriter("point_list/DataPointList.txt",true);
			fw.write(c.getX()+"\n"+c.getY()+"\n");
			fw.close();
		//	System.out.println("DataPoint Write @ StationaryMovement For Data");			
		}catch(IOException ex) {
			ex.printStackTrace();
			System.out.println("error @ StationaryRandomMovementForDataPoint");
		}

		
		return c;
	}
	
	/**
	 * Returns a single coordinate path (using the only possible coordinate)
	 * @return a single coordinate path
	 */
	@Override
	public Path getPath() {
		Path p = new Path(0);
		p.addWaypoint(loc);
		return p;
	}
	
	@Override
	public double nextPathAvailable() {
		return Double.MAX_VALUE;	// no new paths available
	}
	
	@Override
	public StationaryRandomMovementForDataPoint replicate() {
		return new StationaryRandomMovementForDataPoint(this);
	}
	
	protected Coord randomCoord() {
		return new Coord(rng.nextDouble() * getMaxX(),
				rng.nextDouble() * getMaxY());
	}
/*
	public DataPointList getPList() {
		return this.PList;
	}
	
	*/
	
}
