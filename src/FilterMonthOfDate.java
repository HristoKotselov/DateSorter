import java.util.HashMap;
import java.util.Map;

public class FilterMonthOfDate {

	private Map<String, Integer> datemap;

	public FilterMonthOfDate() {
		datemap = new HashMap<String, Integer>();
		datemap.put("january", 1);
		datemap.put("february", 2);
		datemap.put("march", 3);
		datemap.put("april", 4);
		datemap.put("may", 5);
		datemap.put("june", 6);
		datemap.put("july", 7);
		datemap.put("august", 8);
		datemap.put("september", 9);
		datemap.put("october", 10);
		datemap.put("november", 11);
		datemap.put("december", 12);
		datemap.put("ianuari", 1);
		datemap.put("fevruari", 2);
		datemap.put("mart", 3);
		datemap.put("april", 4);
		datemap.put("mai", 5);
		datemap.put("iuni", 6);
		datemap.put("iuli", 7);
		datemap.put("avgust", 8);
		datemap.put("septemvri", 9);
		datemap.put("octomvri", 10);
		datemap.put("noemvri", 11);
		datemap.put("dekemvri", 12);
	}

	public int getMonthFromWord(String date) throws DateNotFoundException {
		for (String key : datemap.keySet()) {
			if (key.startsWith(date) || date.contains(key.substring(0, 2))) {
				return datemap.get(key);
			}
		}
		throw new DateNotFoundException("Cound not find date: " + date);
	}

}
