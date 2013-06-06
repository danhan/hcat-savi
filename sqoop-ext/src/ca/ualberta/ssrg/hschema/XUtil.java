package ca.ualberta.ssrg.hschema;

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
