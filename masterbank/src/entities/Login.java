package entities;
import java.util.Scanner;
import java.io.File;

public class Login {
	public String username;
	public String password;
	String filePath = "C:\\Users\\Aluno\\eclipse-workspace\\masterbank_final\\src\\data\\usernames.csv";
	
	public int LoginCheck(){
		String currentLine;
		int valid = 0;
		try {
			File file = new File(filePath);
			Scanner scan = new Scanner(file);
			while(scan.hasNextLine()) {
				currentLine = scan.nextLine();
				currentLine = currentLine.replace("\"", "");
				String [] lineContent = currentLine.split("[,]", 0);
				String checkUsername = lineContent[0];
				String checkPassword = lineContent[5];
		
				if (username.equals(checkUsername)){
					if (password.equals(checkPassword)) {
						valid = 1;
						scan.close();
						return valid;
					}
				} 
			}
			scan.close();
		} catch (Exception e) {
			
		}
		
		return valid;
	}
}