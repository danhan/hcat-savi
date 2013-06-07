package ca.ualberta.ssrg.hschema;

import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

public class XHGridSchema {
	
	JSONObject schema = null; 
	
	public XHGridSchema(String schema_str){
		try{
			System.out.println("schema string: "+schema_str);
			JSONTokener tokener = new JSONTokener(schema_str);
			this.schema = new JSONObject(tokener);
			System.out.println("the json schema is ready:"+this.schema.toString());
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	/**
	 * used in indexing.
	 * @return
	 */
	public double getSubSpace(){		
		if(schema.has(XConstants.EXT_ROW_KEY_SPATIAL_SUBSPACE)){
			try {
				return java.lang.Double.valueOf((String)schema.get(XConstants.EXT_ROW_KEY_SPATIAL_SUBSPACE));
			} catch (NumberFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return -1;
	
	}
	
	/**
	 * Used in Hybrid indexing
	 * @return
	 */
	public double getTileSize(){
	
		if(schema.has(XConstants.EXT_ROW_KEY_SPATIAL_TILE)){
			try {
				return java.lang.Double.valueOf((String)schema.get(XConstants.EXT_ROW_KEY_SPATIAL_TILE));
			} catch (NumberFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return -1;
	}	
	/**
	 * used in indexing.
	 * @return
	 */
	public int getIndexing(){		
		if(schema.has(XConstants.EXT_ROW_KEY_SPATIAL_INDEXING)){
			try {
				return Integer.valueOf((String)schema.get(XConstants.EXT_ROW_KEY_SPATIAL_INDEXING));
			} catch (NumberFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return -1;
	}	
	
	/**
	 * used in indexing.
	 * @return
	 */
	public int getEncoding(){		
		if(schema.has(XConstants.EXT_ROW_KEY_SPATIAL_ENCODING)){
			try {
				return Integer.valueOf((String)schema.get(XConstants.EXT_ROW_KEY_SPATIAL_ENCODING));
			} catch (NumberFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return -1;	
	}	
	
	
	
	/**
	 * The entire space, the format is (x-topleft,y-topleft,x-bottomright,y-bottomright)
	 * @return
	 */
	public Rectangle2D.Double getEntireSpace(){		
		if(schema.has(XConstants.EXT_ROW_KEY_SPATIAL_SPACE)){
			String space;
			try {
				space = (String)schema.get(XConstants.EXT_ROW_KEY_SPATIAL_SPACE);
				String[] items = space.split(",");
				return new Rectangle2D.Double(java.lang.Double.valueOf(items[0]).doubleValue(),
												java.lang.Double.valueOf(items[1]).doubleValue(),
												java.lang.Double.valueOf(items[2]).doubleValue(),
												java.lang.Double.valueOf(items[3]).doubleValue());				
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			
		}
		return null;		
	}	
	
	/**
	 * The offset point to normalize the space to the first quadrant, 
	 * the format is (x,y)
	 * @return
	 */
	public Point2D.Double getOffset(){		
		if(schema.has(XConstants.EXT_ROW_KEY_SPATIAL_OFFSET)){
			String space;
			try {
				space = (String)schema.get(XConstants.EXT_ROW_KEY_SPATIAL_OFFSET);
				String[] items = space.split(",");
				if(items != null || items.length == 2){
					return new Point2D.Double(java.lang.Double.valueOf(items[0]).doubleValue(),
							java.lang.Double.valueOf(items[1]).doubleValue());
				}				
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		return null;
	}			

}
