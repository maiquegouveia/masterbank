package entities;
import java.io.File;
import java.util.Scanner;

public class CheckIfExists {
	public String info;
	public int position;
	
	public int Check() {
		String filePath = "C:\\Users\\Maique\\Desktop\\usernames.csv";
		int valid = 0;
		
		try {
			File file = new File(filePath);
			Scanner scan = new Scanner(file);
			
			while(scan.hasNextLine()) {
				String currentLine = scan.nextLine();
				currentLine = currentLine.replace("\"", "");
				String [] lineContent = currentLine.split("[,]", 0);
				String checkInfo = lineContent[position];
				
				if (info.equals(checkInfo)) {
					valid = 1;
					scan.close();
					return valid;
				}
			}
			scan.close();
		} catch (Exception e) {
			System.out.println(e);
		}
		return valid;
	}

}
