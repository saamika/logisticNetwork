/* 
 * Copyright 2010 Aalto University, ComNet
 * Released under GPLv3. See LICENSE.txt for details. 
 */
package routing;

import core.CBRConnection;
import core.Settings;

/**
 * Epidemic message router with drop-oldest buffer and only single transferring
 * connections at a time.
 */
public class EpidemicRouterForBase extends ActiveRouter {
	
	/**
	 * Constructor. Creates a new message router based on the settings in
	 * the given Settings object.
	 * @param s The settings object
	 */
	int myAddress;
	public EpidemicRouterForBase(Settings s) {
		super(s);
		//TODO: read&use epidemic router specific settings (if any)
		
		myAddress = super.getHost().getAddress();
		
		CBRConnection Base = new CBRConnection(getHost(), null, host, null, myAddress);
	
		
		if(myAddress==0) {
			
		}else if(myAddress==1) {
			
		}else {
			System.out.println();
		}
	}
	
	/**
	 * Copy constructor.
	 * @param r The router prototype where setting values are copied from
	 */
	protected EpidemicRouterForBase(EpidemicRouterForBase r) {
		super(r);
		//TODO: copy epidemic settings here (if any)
	}
			
	@Override
	public void update() {
		super.update();
		if (isTransferring() || !canStartTransfer()) {
			return; // transferring, don't try other connections yet
		}
		
		// Try first the messages that can be delivered to final recipient
		if (exchangeDeliverableMessages() != null) {
			return; // started a transfer, don't try others (yet)
		}
		
		// then try any/all message to any/all connection
		this.tryAllMessagesToAllConnections();
	}
	
	
	@Override
	public EpidemicRouterForBase replicate() {
		return new EpidemicRouterForBase(this);
	}

}