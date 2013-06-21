import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;


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
		String startTime = this.prop.getProperty("start_time");
		String endTime = this.prop.getProperty("end_time");
		String format = this.prop.getProperty("format");		
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
	
	
	public static void main(String[] args){
		XDataGenerator generator = new XDataGenerator();
		generator.generateSchedule();
		
	}
	
}
