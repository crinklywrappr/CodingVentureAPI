package org.codingventures.api.client;

import java.util.Map;

/**
 * This class is used by BlogHistory handler to determine if a given token 
 * is going to alter the page and how.  isTokenRelated() answers the first 
 * question, and addWidgetToRootPanel answers the second.  These classes 
 * are therefore self-aware in that they know what can trigger them and they 
 * know what to do when they're triggered.
 * 
 * @author doubleagent
 */
public abstract class ClientWidget {
	
	public abstract void addWidgetToRootPanel();
	
	public abstract boolean isTokenRelated(String token);

}
