package entities;
import java.util.Scanner;
import java.io.File;
import entities.GetFiles;

public class Login {
	public String username;
	public String password;
	GetFiles getf = new GetFiles();
	
	public String LoginCheck(){
		String currentLine;
		String value = "0";
		try {
			File file = new File(getf.pathUsernames);
			Scanner scan = new Scanner(file);
			while(scan.hasNextLine()) {
				currentLine = scan.nextLine();
				currentLine = currentLine.replace("\"", "");
				String [] lineContent = currentLine.split("[,]", 0);
				String checkUsername = lineContent[0];
				String checkPassword = lineContent[5];
		
				if (username.equals(checkUsername)){
					if (password.equals(checkPassword)) {
						value = lineContent[6];
						scan.close();
						return value;
					}
				} 
			}
			scan.close();
		} catch (Exception e) {
			
		}
		
		return value;
	}
}