package test;

import java.util.Hashtable;

import savi.hcat.common.util.XTimestamp;

public class TestXTimestamp {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String start = "2010-01-01 01:00:00";
		String end = "2011-03-20 01:00:00";
		Hashtable<String,String> slots = XTimestamp.splitTime(start, end, 4);
		System.out.println(slots.toString());

	}

}
