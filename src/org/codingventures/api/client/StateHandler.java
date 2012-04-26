package org.codingventures.api.client;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import com.google.gwt.core.client.GWT;

/**
 * This is the class which initiates changes to the History.  You should 
 * implement this class in such a way that Any BlogWidget's written should 
 * make changes (and update state) here!  The only method you are required to 
 * implement is the validateToken method, which we encourage you to write in 
 * such a way that expects bad input.  The default AddressBarHandler will 
 * call this method with the new token whenever a user has entered a new url 
 * into the address bar.
 * 
 * It is advisable that all calls to this should either return silently or 
 * call BlogHistoryHandler.update() [within an AsyncCallback if from a server] 
 * when finished.  It is advisable that you write your constructor such that 
 * any server-side variables can be updated here initially (if useful) from 
 * the EntryPoint, and that you call fireCurrentHistoyrState once when they 
 * finish.  Really, anything involving State at all should be centalized here.  
 * Also, you should NEVER call History.newItem() directly any longer because 
 * that will cause an infinite loop.
 * 
 * So, in short, you should <b>ONLY</b> call BlogHistoryHandler.update from 
 * within your StateHandler, and you should <b>NEVER</b> call History.newItem().
 * 
 * 
 * @author doubleagent
 */
public abstract class StateHandler {
	
	/**
	 * This method will be used to validate tokens.  It takes the token 
	 * in question and returns a valid token.
	 * 
	 * @param token
	 * @return
	 */
	public abstract String validateToken(String token);
	
	/**
	 * This method will be used to compare two tokens - it is used by 
	 * ClientHistoryHandler to compare the equality of the token given 
	 * to it and the token produced by validateToken
	 * 
	 * @param t1
	 * @param t2
	 * @return
	 */
	public abstract boolean compareTokens(String t1, String t2);

	/**
	 * This method quickly generates a token from a given map.  For 
	 * instance, if this is your map:
	 * 	{"base"=>"home", "logged-in"=>"false", "blog"=>"null"}
	 * 
	 * then the method will return:
	 * 	"base=home&logged-in=false&blog=null"
	 * 
	 * @param map
	 * @return
	 */
	public static final String mapToToken(Map<String, String> map) {
		Set<Entry<String, String>> keyVals = map.entrySet();
		String token = "";
		for(Entry<String, String> entry : keyVals)
			token+=entry.getKey() + "=" + entry.getValue() + "&";
		return token.substring(0, token.length() - 1);
	}

	/**
	 * The opposite of mapToToken.  It generates a map of key-value pairs 
	 * based on the String passed in.  For instance, if this is your 
	 * string:
	 * 	"base=home&logged-in=false&blog=null"
	 * 
	 * Then the method will return:
	 * 	{"base"=>"home", "logged-in"=>"false", "blog"=>"null"}
	 * 
	 * @param token
	 * @return
	 */
	public static final Map<String, String> tokenToMap(
			String token) {
		String params[] = token.split("&");
		Map<String, String> map = new HashMap<String, String>();
		for(String param : params) {
			String keyVal[] =  param.split("=");
			if(keyVal.length==2)
				map.put(keyVal[0], keyVal[1]);
		}
		return map;
	}
	
}
