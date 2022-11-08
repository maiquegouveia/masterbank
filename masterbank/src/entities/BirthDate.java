package entities;

public class BirthDate {
	public String day;
	public String month;
	public String year;
	
	public String Date() {
		String date = null;
		int nDay, nMonth, nYear;
		try {
			nDay = Integer.parseInt(day);
			nMonth = Integer.parseInt(month);
			nYear = Integer.parseInt(year);
		} catch(NumberFormatException ex) {
			System.out.println("ERRO. Digite apenas números.");
			return date;
		}
		
		if (nDay > 31 || nDay < 1 || nMonth > 12 || nMonth < 1 || nYear > 2022) {
			System.out.println("Data de nascimento inválida.");
		} else {
			date = day + "/" + month + "/" + year;
		}
		
		return date;
	}

}
