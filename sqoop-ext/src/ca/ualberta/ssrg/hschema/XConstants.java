package ca.ualberta.ssrg.hschema;

public class XConstants {
	
	public static String DELIMETER_ROW_KEY = "-";
	
	public static String FIELD_OBJECTID="ID";
	public static String FIELD_LATTITUDE="lattd";
	public static String FIELD_LONGITUDE="longtd";
	
	
	public static String EXT_ROW_KEY_REGION="region";
	public static String EXT_ROW_KEY_IDENTIFIER="identifier";
	public static String EXT_ROW_KEY_COMBINED="combined";
	public static String EXT_ROW_KEY_TIMESTAMP="timestamp";
	
	public static String EXT_ROW_KEY_SPATIAL="spatial";
	public static String EXT_ROW_KEY_SPATIAL_FIELDS="fields";	
	public static String EXT_ROW_KEY_SPATIAL_SCHEMA = "schema";
	public static String EXT_ROW_KEY_SPATIAL_SPACE = "space";
	public static String EXT_ROW_KEY_SPATIAL_INDEXING = "indexing";
	public static String EXT_ROW_KEY_SPATIAL_ENCODING = "encoding";
	public static String EXT_ROW_KEY_SPATIAL_SUBSPACE = "subspace";
	public static String EXT_ROW_KEY_SPATIAL_TILE = "tile";
	public static String EXT_ROW_KEY_SPATIAL_OFFSET = "offset";
	
	// for hbase-column-family
	public static String EXT_FAMILY="family";
	public static String EXT_COLUMNS="columns";	
	public static String EXT_COLUMNS_PREFIX="prefix";
	public static String EXT_VERSION="version";
	public static String EXT_VERSION_INTERVAL="interval";
	public static String EXT_VERSION_UNIT="unit";	
	public static String EXT_VERSION_UNIT_MINUTE="min";
	public static String EXT_VERSION_UNIT_SECOND="sec";
	
	public static String EXT_TIMESTAMP_PERIOD="period";
	public static String EXT_TIMESTAMP_PERIOD_BLOCK = "block"; // four block: 08-10,10-12,13-15,15-17
	public static String EXT_TIMESTAMP_PERIOD_HOUR = "hour";
	public static String EXT_TIMESTAMP_FORMAT="format";
	public static String EXT_KEY_FIELD="field";
}