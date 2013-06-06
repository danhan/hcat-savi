package org.apache.sqoop.hbase;

import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.util.Bytes;
import org.json.JSONException;
import org.json.JSONObject;

import com.util.XTableSchema;
import com.util.hybrid.XHybridIndex;

import ca.ualberta.ssrg.hschema.XConstants;
import ca.ualberta.ssrg.hschema.XUtil;
/**
 * 
 * source table: key, lattd, longtd, attributes
 * hbase table: hgrid-index: columnindex-key: json{}
 * e.g.
 * --hbase-row-key (key1,key2,lattd,longtd)
 * --column-family d
 * @author dan
 *
 */

public class GeospatialPutTransformer extends HSchemaPutTransformer{

	public static final Log LOG = LogFactory.getLog(GeospatialPutTransformer.class);				
	
	@Override
	public List<Put> getPutCommand(Map<String, Object> fields)
			throws IOException {
		
		String colFamily = getColumnFamily();
		byte [] colFamilyBytes = Bytes.toBytes(colFamily);
		

		String indicator[] = this.buildHGridModel(fields);				
		
		if (null == indicator) {
			// If the row-key column is null, we don't insert this row.
			LOG.error("could not get the HGrid index");
			return null;
		}
		
		String rowkey = indicator[0];
		String qualifer = indicator[1];		
		Put put = new Put(Bytes.toBytes(rowkey));	
			
		LOG.debug("rowkey=>"+rowkey+";column=>"+qualifer);
		
		JSONObject object = new JSONObject();
		
		// all the remaining columns are wrapped into a JSON object
		for (Map.Entry<String, Object> fieldEntry : fields.entrySet()) {
			// This is a regular field, not the row key.
			// Add it if it's not null.
			Object val = fieldEntry.getValue();
			if (null != val) {
				try {
					object.put(XUtil.toHBaseString(fieldEntry.getKey()), XUtil.toHBaseString(val));
				} catch (JSONException e) {					
					e.printStackTrace();
				}
			}
		}

		put.add(colFamilyBytes, Bytes.toBytes(qualifer),Bytes.toBytes(object.toString()));		
		
		return Collections.singletonList(put);
	}
	

	
/*	private String getObjectID(Map<String,Object> fields){
		Object val = fields.get(objectid);
		if(null != val)
			return val.toString();
		return null;
	}*/
	
	/**
	 *  
	 * @return the identifer of the cell
	 */
	private String[] buildHGridModel(Map<String, Object> fields){
		LOG.debug("build HGrid model: "+fields);
		XTableSchema tableSchema = new XTableSchema("hschema/spatial.schema");		
		LOG.debug("get the table schema successfully");
		Rectangle2D.Double space = tableSchema.getEntireSpace();
		double min_size_of_subspace = tableSchema.getSubSpace();
		int encoding = tableSchema.getEncoding();
		Point2D.Double offset = tableSchema.getOffset();
		LOG.debug("prepare all configuration items: space=>"+space.toString()+
				";subspace=>"+min_size_of_subspace+";encoding=>"+encoding+";offset=>"+offset);
		
		XHybridIndex hybrid = new XHybridIndex(space,tableSchema.getTileSize(),offset,min_size_of_subspace);
		hybrid.buildZone(encoding);
		
		String region = this.getRegionInfo(fields);
		LOG.debug("get region: "+region);
		//get the current location
		double[] location = this.getSpatialRaw(fields);
		if(location == null){
			LOG.error("the location is null");
			return null;
		}
		LOG.debug("get location: latitude=>"+location[0]+";longitude=>"+location[1]);
		// get object id
		String objectId = this.getObjectIdentifier(fields);
		LOG.debug("get object Id : "+objectId);		
		if(objectId == null){
			LOG.error("the object id is null");
			return null;
		}
		
		String indicator[] = new String[2]; // 1st: rowIndex; 2nd: columnIndex		
		if(hybrid != null){
			
			String[] keys = hybrid.locate(location[0],location[1]);
			indicator[0] = (null != region)?(region+XConstants.DELIMETER_ROW_KEY+keys[0]):keys[0];
			indicator[1] = keys[1]+"-"+objectId;
		}					
		return indicator;		
	}
	
	/**
	 * TODO
	 * create the spatial index based on latitude and longitude in the record
	 * @param lattd
	 * @param longtd
	 * @return
	 */
	private String[] createSpatailIndex(Object lattd,Object longtd){
		String[] index = new String[2];
		if(null != lattd && null != longtd){
			index[0] = "01-01";
			index[1] = "01";
			return index;
		}
		return null;
	}

}
