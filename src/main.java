import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class main {
	
	static List<String> tokens;

	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new FileReader("Dates.lst"));
		List<Date> assembledDates = new ArrayList<>();
		FilterMonthOfDate filter = new FilterMonthOfDate();
		String line = reader.readLine();
		int dateCounter = 1;
		while(line != null) {
			Date date = new Date();
			line = line.replaceAll("[^A-Za-z0-9]"," ");
			tokens = new ArrayList<>(Arrays.asList(line.split("[ ]+")));
			filterIrrelevantTokens();
			removeDayExtensions();
			separateMonthAndYear();
			getMonthFromWord(date, filter);
			assignDateValues(date);
			System.out.println(dateCounter + " " + date.getDay() + "/" + date.getMonth() + "/" + date.getYear());
			if (date.isValid()){
				assembledDates.add(date);
			}
			line = reader.readLine();
			dateCounter++;
		}
		reader.close();
		Collections.sort(assembledDates);
		Collections.reverse(assembledDates);
		System.out.println();
		System.out.println("Sorted list of Dates:");
		assembledDates.forEach(date -> System.out.println(date.getDay() + "/" + date.getMonth() + "/" + date.getYear()));
		System.out.println(assembledDates.size() + " dates have been sorted");
	}
	
	private static int tryToFilter(FilterMonthOfDate filter, String date){
		try{
			int month = filter.getMonthFromWord(date);
			return month;
		}catch(DateNotFoundException e){
			System.out.println("Failed to retrive information. " + e.getMessage());
			return -1;
		}
	}
	
	private static void evaluateDateAndYear(List<String> tokens, Date date){
		int first = Integer.valueOf(tokens.get(0));
		int second = Integer.valueOf(tokens.get(1));
		if (first < 32){
			date.setDay(first).setYear(second);
		}
		else if (first > 31){
			date.setYear(first).setDay(second);
		}
		else {
			date.setDay(first).setYear(second);
		}
	}
	
	private static void evaluateMonthAndYear(List<String> tokens, Date date){
		int first = Integer.valueOf(tokens.get(0));
		int second = Integer.valueOf(tokens.get(1));
		if (first <= 12){
			date.setMonth(first).setYear(second);
		}
		else if (first > 12){
			date.setMonth(second).setYear(first);
		}
		else date.setMonth(first).setYear(second);
		if (date.getDay() == 0) {
			date.setDay(1);
		}
	}
	
	private static void evaluateDayAndMonthAndYear(List<String> tokens, Date date){
		List<String> temporary = new ArrayList<>(tokens);
		for (int i = 0; i < tokens.size(); i++){
			if (Integer.valueOf(tokens.get(i)) > 31){
				date.setYear(Integer.valueOf(tokens.get(i)));
				temporary.remove(i);
			}
		}
		for (int i = 0; i < temporary.size(); i++){
			if (Integer.valueOf(temporary.get(i)) > 12){
				date.setDay(Integer.valueOf(temporary.get(i)));
				temporary.remove(i);
				break;
			}
		}
		if (temporary.size() == 1){
			date.setMonth(Integer.valueOf(temporary.get(0)));
			return;
		}
		if (temporary.size() == 3){
			date.setDay(Integer.valueOf(temporary.get(0))).setMonth(Integer.valueOf(temporary.get(1))).setYear(Integer.valueOf(temporary.get(2)));
			return;
		}
			if (date.getDay() != 0){
				evaluateMonthAndYear(temporary,date);
			}
			else {
				date.setDay(Integer.valueOf(temporary.get(0))).setMonth(Integer.valueOf(temporary.get(1)));
			}
	}
	
	private static void filterIrrelevantTokens(){
		for (int i = 0; i < tokens.size(); i++){
			if (tokens.get(i).matches("[A-Za-z]{1,2}")){
				tokens.remove(i);
			}
		}
	}
	
	private static void removeDayExtensions(){
		for (int i = 0; i < tokens.size(); i++){
			if (tokens.get(i).matches("([0-9]{1,2})([A-Za-z]{1,2})")){
				String odd = tokens.get(i).replaceAll("[A-Za-z]", "");
				tokens.remove(i);
				tokens.add(i, odd);
			}
		}
	}
	
	private static void separateMonthAndYear(){
		for (int i = 0; i < tokens.size(); i++){
			if (tokens.get(i).matches("([A-Za-z]+)([0-9]+)")){
				Pattern p = Pattern.compile("[a-z]+|\\d+");
				Matcher m = p.matcher(tokens.get(i));
				while (m.find()) {
					tokens.add(m.group());
				}
				tokens.remove(0);
			}
		}
	}
	
	private static void getMonthFromWord(Date date, FilterMonthOfDate filter){
		for (int j = 0; j < tokens.size(); j++){
			if (tokens.get(j).toLowerCase().matches("[a-z]+")){
				date.setMonth(tryToFilter(filter,tokens.get(j)));
				tokens.remove(j);
			}
		}
	}
	
	private static void assignDateValues(Date date){
		if (tokens.size() == 1){
			date.setYear(Integer.valueOf(tokens.get(0))).setDay(1);
			if (date.getMonth() == 0) {
				date.setMonth(1);
			}
		}
		else if (tokens.size() == 2 && date.getMonth() != 0){
			evaluateDateAndYear(tokens,date);
		}
		else if (tokens.size() == 2){
			evaluateMonthAndYear(tokens,date);
		}
		else {
			evaluateDayAndMonthAndYear(tokens,date);
		}
	}
}
