/* 
 * Copyright 2010 Aalto University, ComNet
 * Released under GPLv3. See LICENSE.txt for details. 
 */
package routing;

import java.util.List;

import core.Connection;
import core.Settings;



/**
 * EpidemicRouterを改良した、LogisticNetwork向けのRouter。
 * 
 */

public class LogisticRouter extends ActiveRouter {


	/**
	 * Constructor. Creates a new message router based on the settings in
	 * the given Settings object.
	 * @param s The settings object
	 */

	List<Connection> myConnection;

	public LogisticRouter(Settings s) {
		super(s);
		//TODO: read&use epidemic router specific settings (if any)

	}

	/**
	 * Copy constructor.
	 * @param r The router prototype where setting values are copied from
	 */
	protected LogisticRouter(LogisticRouter r) {
		super(r);
		//TODO: copy epidemic settings here (if any)
	}

	@Override
	public void update() {
		super.update();

		myConnection = getConnections();
	//	int num = myConnection.size();
	//	System.out.println("コネクションの数 @ LogisticRouter："+num+"@ Node"+super.getHost().getAddress());		

		for(Connection c : myConnection){

			//ここアドレスの範囲をハードコーディング。辞めたい。
			//if(c.getToNode().getAddress()>=0 && c.getToNode().getAddress()<=10) {
				
				if (isTransferring() || !canStartTransfer()) {
					return; // transferring, don't try other connections yet：転送中ならまだコネクションを試さない
				}

				// Try first the messages that can be delivered to final recipient：宛先ならメッセージを渡す。
				if (exchangeDeliverableMessages() != null) {
					return; // started a transfer, don't try others (yet)
				}

				// then try any/all message to any/all connection
				this.tryAllMessagesToAllConnections();
			//}
		}
	}


	@Override
	public LogisticRouter replicate() {
		return new LogisticRouter(this);
	}

}