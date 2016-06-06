import java.io.FileWriter;
import java.io.IOException;

public class Log {
	static FileWriter f;
	public Log(){
		try {
			f=new FileWriter("D:\\log.txt");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	//הוספת שורה בקובץ
	public static void addLine(String line){
		try{
			f.write(line);
		}catch (Exception e) {}
	}
	
	//סגירת הקובץ
	public static void close(){
		try{
			f.close();
		}catch (Exception e) {
			System.out.println("ther is no such file.");
		}
	}
	
	
	/*
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try{
			File d = new File("C:/output.txt");
			d.createNewFile();
			FileWriter output = new FileWriter(d);
			output.write("hello !! \n");
			output.write("how u doing ? \n");
			addLine(output);
			output.write("asdqwdniqwdn");
			addLine(output);
			addLine(output);
			output.write("shimrit !! ");
			close(output);
			
			
		}catch (Exception e) {
			System.out.println("ther is no such file.");
		}

	}
	*/

}




