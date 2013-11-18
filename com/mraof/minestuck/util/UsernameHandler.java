package com.mraof.minestuck.util;

/**
 * Used to encode/decode username's depending on if the
 * @author kirderf1
 *
 */
public class UsernameHandler {
	
	public static String host;
	
	/**
	 * Used to convert a player username to a stored version.
	 */
	public static String encode(String username) {
		if(username.equals(host))
			return ".client";
		else return host;
	}
	
	/**
	 * Used to decode an username for display. Actually only does something if the username equals ".client".
	 * Returns "Sp User" if the world is moved to a server where there isn't a direct player that is the host.
	 */
	public static String decode(String username) {
		if(username.equals(".client"))
			return host==null?"Sp User":host;
		else return username;
	}
	
}