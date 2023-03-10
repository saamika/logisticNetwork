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
 *  logisticNetworkにおいて、荷物の配送先はこのMovementModelを利用。
 *  基本的にはStationaryRandomMovementと内容は変わらない。
 *  
 *  noted by matsutani
 */
public class StationaryRandomMovementForDest extends MovementModel {
	private Coord loc; /** The location of the nodes */
//	public DestinationList DList;
	
//	int j=0;//test用
	
	/**
	 * Creates a new movement model based on a Settings object's settings.
	 * @param s The Settings object where the settings are read from
	 */
	public StationaryRandomMovementForDest(Settings s) {
		super(s);
		
		
	//	this.DList = new DestinationList();
	//	System.out.println("initialize Destitnation List");
		//double coords[] = new double[2];
		
		//coords[0] = rng.nextDouble() * getMaxX();
		//coords[1] = rng.nextDouble() * getMaxY();
		//this.loc = new Coord(coords[0],coords[1]);
	}
	
	/**
	 * Copy constructor. 
	 * @param sm The StationaryMovement prototype
	 */
	public StationaryRandomMovementForDest(StationaryRandomMovementForDest sm) {
		super(sm);
		this.loc = sm.loc;
	//	this.DList = sm.DList;
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
			FileWriter fw = new FileWriter("point_list/DestinationList.txt",true);
			fw.write(c.getX()+"\n"+c.getY()+"\n");
			fw.close();
			System.out.println("Destination Write @ Stationary For Dest");
		}catch(IOException ex) {
			ex.printStackTrace();
			System.out.println("error @ StationaryRandomMovementForDest");
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
	public StationaryRandomMovementForDest replicate() {
		return new StationaryRandomMovementForDest(this);
	}
	
	protected Coord randomCoord() {
		return new Coord(rng.nextDouble() * getMaxX(),
				rng.nextDouble() * getMaxY());
	}

	/*
	public DestinationList getDList() {
		return this.DList;
	}
	*/
	
}
