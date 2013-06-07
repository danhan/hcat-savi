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

import com.util.hybrid.XHybridIndex;

import ca.ualberta.ssrg.hschema.XConstants;
import ca.ualberta.ssrg.hschema.XHGridSchema;
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

	public static final Log LOG = LogFactory.getLog(GeospatialPutTransformer.class.getName());				
	
	@Override
	public List<Put> getPutCommand(Map<String, Object> fields)
			throws IOException {
		System.out.println("GeospatialPutTransformer: getPutCommand()"+fields.toString());
		String colFamily = getColumnFamily();
		byte [] colFamilyBytes = Bytes.toBytes(colFamily);
		System.out.println("family name: "+colFamily);

		String indicator[] = this.buildHGridModel(fields);		
				
		if (null == indicator) {
			// If the row-key column is null, we don't insert this row.
			System.out.println("indicator is null! could not get the HGrid index");
		}else{
			System.out.println("indicator: "+indicator[0]+";"+indicator[1]);
		}
		
		String rowkey = indicator[0];
		String qualifer = indicator[1];		
		Put put = new Put(Bytes.toBytes(rowkey));	
					
		System.out.println("rowkey=>"+rowkey+";column=>"+qualifer);
		
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
	
	
	/**
	 *  
	 * @return the identifer of the cell
	 */
	private String[] buildHGridModel(Map<String, Object> fields){
		System.out.println("build HGrid model: "+fields);
		XHybridIndex hybrid = null;
		
		String indicator[] = null;
		try{
			XHGridSchema schema = this.getSpatialSchema();	
			System.out.println("get the table schema successfully");
			if(schema != null){
				Rectangle2D.Double space = schema.getEntireSpace();
				double min_size_of_subspace = schema.getSubSpace();
				int encoding = schema.getEncoding();
				Point2D.Double offset = schema.getOffset();
				System.out.println("prepare all configuration items: space=>"+space.toString()+
						";subspace=>"+min_size_of_subspace+";encoding=>"+encoding+";offset=>"+offset);	
				hybrid = new XHybridIndex(space,schema.getTileSize(),offset,min_size_of_subspace);
				hybrid.buildZone(encoding);	
				String region = this.getRegionInfo(fields);
				System.out.println("get region: "+region);
				//get the current location
				double[] location = this.getSpatialLocationValue(fields);
				if(location == null){
					LOG.error("the location is null");
					return null;
				}
				System.out.println("get location: latitude=>"+location[0]+";longitude=>"+location[1]);
				// get object id
				String objectId = this.getObjectIdentifier(fields);
				System.out.println("get object Id : "+objectId);		
				if(objectId == null){
					LOG.error("the object id is null");
					return null;
				}
				
				if(hybrid != null){				
					String[] keys = hybrid.locate(location[0],location[1]);
					indicator = new String[2]; // 1st: rowIndex; 2nd: columnIndex
					indicator[0] = (null != region)?(region+XConstants.DELIMETER_ROW_KEY+keys[0]):keys[0];
					indicator[1] = keys[1]+"-"+objectId;
				}							
		
			}else{
				System.out.println("schema is wrong!");
			}			
		}catch(Exception e){
			e.printStackTrace();
		}

	
	
		return indicator;		
	}

}
