import java.awt.geom.Point2D;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;

import org.apache.commons.math3.random.RandomDataImpl;


public class XDataGenerator {

	Properties prop = new Properties();
	
	
	public XDataGenerator(){    	
    	 
    	try {
    		prop.load(new FileInputStream("config.properties"));
 
    	} catch (IOException ex) {
    		ex.printStackTrace();
        }
	}
	
	public void generateSchedule(){
		String startTime = this.prop.getProperty("schedule.start_time");
		String endTime = this.prop.getProperty("schedule.end_time");
		String format = this.prop.getProperty("schedule.format");		
		DateFormat formatter = new SimpleDateFormat(format);
		
		try{
			Date start = formatter.parse(startTime);
			Date end = formatter.parse(endTime);
			Calendar startCal = Calendar.getInstance();
			startCal.setTime(start);
			Calendar endCal = Calendar.getInstance();
			endCal.setTime(end);
			Calendar temp = Calendar.getInstance();
			temp.setTime(start);
			System.out.println(startCal.toString());
			System.out.println(endCal.toString());
			int i =1;
			FileWriter output = new FileWriter("schedule.csv");
			while((temp.equals(startCal) || temp.after(startCal)) && temp.before(endCal)){
				int dayOfWeek = temp.get(Calendar.DAY_OF_WEEK);
				if(dayOfWeek != 2){
					temp.add(Calendar.DAY_OF_MONTH, 1);
				}else{					
					temp.add(Calendar.DAY_OF_MONTH, 7);
					int year = temp.get(Calendar.YEAR);
					int month = temp.get(Calendar.MONTH)+1;
					int day = temp.get(Calendar.DAY_OF_MONTH);
					String dateStr = year+"-"+(month<10?"0"+month:month)+"-"+(day<10?("0"+day):day);
					output.write((i++)+","+dateStr+"\n");					
				}				
				
			}
			output.close();
			
		}catch(Exception e){
			e.printStackTrace();
		}

		
		
	}
	
	
	public void generateLocation(){

		
		int num = Integer.valueOf(this.prop.getProperty("patient.num"));
		
		try{
			//gernerate the data for ontario
			double xmin = Double.valueOf(this.prop.getProperty("patient.x.min.on"));
			double xmax = Double.valueOf(this.prop.getProperty("patient.x.max.on"));
			double ymin = Double.valueOf(this.prop.getProperty("patient.y.min.on"));
			double ymax = Double.valueOf(this.prop.getProperty("patient.y.max.on"));
			String inflection = this.prop.getProperty("patient.inflection.point.on");
			String[] items = inflection.split(",");
			Point2D.Double point = new Point2D.Double(Double.valueOf(items[0]),Double.valueOf(items[1]));
						
			String onData = generate(num,xmin,xmax,ymin,ymax,point,"ontario-location.csv");
			System.out.println(onData + " is ready");
			
			//gernerate the data for ontario
			xmin = Double.valueOf(this.prop.getProperty("patient.x.min.bc"));
			xmax = Double.valueOf(this.prop.getProperty("patient.x.max.bc"));
			ymin = Double.valueOf(this.prop.getProperty("patient.y.min.bc"));
			ymax = Double.valueOf(this.prop.getProperty("patient.y.max.bc"));
			inflection = this.prop.getProperty("patient.inflection.point.bc");
			items = inflection.split(",");
			point = new Point2D.Double(Double.valueOf(items[0]),Double.valueOf(items[1]));
								
			String bcData = generate(num,xmin,xmax,ymin,ymax,point,"bc-location.csv");
			System.out.println(bcData + " is ready");
			 

		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	
	private String generate(int number,double xmin, double xmax, double ymin, double ymax,Point2D.Double inflection,String filename){	
		
		double x[] = new double[number];
		double y[] = new double[number];
					
		int total = 0;
	
		RandomDataImpl randomData1 = new RandomDataImpl(); 
		RandomDataImpl randomData2 = new RandomDataImpl();	
		randomData2.reSeed(10);
		randomData1.reSeed(1);
		for(int i=0;i<number;i++){										
			double xtmp = randomData1.nextUniform(xmin,xmax);
			double ytmp = randomData2.nextUniform(ymin,ymax);
			if(xtmp<inflection.x && ytmp < inflection.y)
				continue;
			x[total] = xtmp; 
			y[total] = ytmp;							
			
			total++;																
		}
		
		System.out.println(total);
		// normailze the value into the given value range
		System.out.println("===normalized==and start to write=");
		FileWriter fw = null;
		try{
			fw = new FileWriter(filename);
			for(int i=0;i<total;i++){
				fw.write(x[i] + ","+y[i]+"\n");
				
			}
			fw.close();
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return filename;
	}	
		
	
	
	
	public static void main(String[] args){
		XDataGenerator generator = new XDataGenerator();
		
		if(args.length < 1){
			System.out.println("0 => schedule; 1 => patient");
			System.exit(0);
		}
		if(args[0].equals("0")){
			generator.generateSchedule();
		}else if(args[0].equals("1")){
			generator.generateLocation();	
		}
		
		
	}
	
}
