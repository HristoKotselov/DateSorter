
public class Date implements Comparable<Date> {
	
	private int day;
	private int month;
	private int year;
	
	public Date() {
		this.day = 0;
		this.month = 0;
		this.year = 0;
	}

	public Date setDay(int day) {
		this.day = day;
		return this;
	}
	
	public Date setMonth(int month) {
		this.month = month;
		return this;
	}
	
	public Date setYear(int year) {
		if (year >= 0 && year <18) {
			this.year = 2000 + year;
		}
		else if (year < 100){
			this.year = 1900 + year;
		}
		else {
			this.year = year;
		}
		return this;
	}
	
	public int getDay(){
		return day;
	}
	
	public int getMonth(){
		return month;
	}
	
	public int getYear(){
		return year;
	}
	
	@Override
	public int compareTo(Date date) {
		if (year > date.getYear()){
			return 1;
		}
		else if (year < date.getYear()){
			return -1;
		}
		else if (month > date.getMonth()){
			return 1;
		}
		else if (month < date.getMonth()){
			return -1;
		}
		else if (day > date.getDay()){
			return 1;
		}
		else if (day < date.getDay()){
			return -1;
		}
		return 0;
	}
	
	public boolean isValid(){
		if ((day > 0 && day < 32) && (month > 0 && month < 13) && (year > 0 && year < 2018)){
			return true;
		}
		return false;
	}
	
}
