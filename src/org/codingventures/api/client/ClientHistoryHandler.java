package org.codingventures.api.client;

import java.util.LinkedList;

import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.History;

/**
 * This class is a ValueChangeHandler which you can use with the History 
 * class.  It allows you to assign different ClientWidget's under it's domain, 
 * and when onValueChange is called, it will cycle through and activate the 
 * related widgets.
 * 
 * It interacts with StateHandler to ensure that only valid tokens are acted 
 * upon.  If the StateHandler's validateToken method returns a different String 
 * than the one input to it, then the onValueChange method will call 
 * ClientHistoryHandler.back(), which calls History.back(), and notifies the 
 * ClientHistoryHandler's not to handle the call.  Then it calls newItem with 
 * the validated token and exits.
 * 
 * Throughout all of this there are booleans used to ensure that procedures 
 * which don't need to be duplicated are not duplicated.
 * 
 * 
 * @author doubleagent
 *
 */
public class ClientHistoryHandler implements ValueChangeHandler<String> {
	// This is used to ensure that calls to ClientHistoryHandler.back 
	// don't trigger changes to the page
	private static boolean back = false;
	// This is our StateHandler, which we use to validate tokens
	private static StateHandler stateHandler;
	// This is the list of ClientWidgets which this ClientHistoryHandler 
	// has domain over
	private LinkedList<ClientWidget> list = 
		new LinkedList<ClientWidget>();
	/**
	 * This is used to make the ClientHistoryHandler's aware of your 
	 * StateHandler, so that they can use it.
	 * 
	 * @param stateHandler
	 */
	public static void setup(StateHandler stateHandler) {
		ClientHistoryHandler.stateHandler = stateHandler;
	}
	
	/**
	 * This calls History.back in such a way that it won't affect the 
	 * contents on the page.
	 */
	public static void back() {
		back = true;
		History.back();
		back = false;
	}
	
	/**
	 * This puts a ClientWidget under the domain of this 
	 * ClientHistoryHandler
	 * 
	 * @param clientWidget
	 */
	public void addClientWidget(ClientWidget clientWidget) {
		list.add(clientWidget);
	}
	
	/**
	 * This will return immediately if the token passed in the event 
	 * object isn't the token currently on the History stack, or if the 
	 * static back variable is true.  If those aren't the case it calls 
	 * stateHandler.validateToken(token) and checks for equality with the 
	 * token passed in.  If they are unequal it calls back() and adds a 
	 * new item immediately after, then returns.  If everything checks out 
	 * then the method cycles through the list of ClientWidget's and adds 
	 * those which are related to the page, by calling addWidget().
	 */
	public void onValueChange(ValueChangeEvent<String> event) {
		String token = event.getValue();
		if(back || !token.equals(History.getToken()))
			return;
		String validatedToken = stateHandler.validateToken(token);
		if(!stateHandler.compareTokens(token, validatedToken)) {
			//back(); /* Actually works in Chrome, so I can't use this trick
			History.newItem(validatedToken);
			return;
		}
		for(ClientWidget w : list)
			if(w.isTokenRelated(validatedToken))
				w.addWidgetToRootPanel();
	}
}
