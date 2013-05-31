package org.apache.sqoop.hbase;

import java.math.BigDecimal;

public class XUtil {

	public static String toHBaseString(Object val) {
		String valString;
		if (val instanceof BigDecimal) {
			valString = ((BigDecimal) val).toPlainString();
		} else {
			valString = val.toString();
		}
		return valString;
	}
}
