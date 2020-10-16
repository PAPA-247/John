/**
 * Coordinate class is used to store a physical location via geographical coordinates.
 * These coordinates can then later be used to calculate distances and so on.
 * 
 * @author cnewb
 *
 */

package com.papa247.john.Support;

public class Coordinate {
	public String degrees;
	public String minutes;
	public String seconds;
	
	public Coordinate(String deg, String min, String sec) {
		degrees = deg;
		minutes = min;
		seconds = sec;
	}
}
