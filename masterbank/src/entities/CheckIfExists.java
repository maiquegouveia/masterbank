package entities;
import java.io.File;
import java.util.Scanner;
import entities.GetFiles;

public class CheckIfExists {
	public String info;
	public int position;
	GetFiles getf = new GetFiles();
	
	public int Check() {
		int valid = 0;
		try {
			File file = new File(getf.pathUsernames);
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
