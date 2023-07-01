package tp1.p2.record;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import tp1.p2.control.exceptions.RecordException;

public class Record {
	private Map<String, Integer> record = new HashMap<String, Integer>();
	
	public Record() {
		record.put("easy", 0);
		record.put("hard", 0);
		record.put("insane", 0);
	}
	
	public void readFromRecord() throws IOException {
		// System.out.println("Reading from record");
		try (FileReader input = new FileReader("record.txt")) {
			BufferedReader bufferedInput = new BufferedReader(input);
			String data = bufferedInput.readLine();
			while (data != null) 
			{ 
				String[] words = data.split(":");
				record.put(words[0], Integer.parseInt(words[1]));
				data = bufferedInput.readLine();
			}
		} 
	}
	
	private void writeToRecord() throws IOException {
		// System.out.println("Writing to record");
		try (FileWriter output = new FileWriter("record.txt")) {
			BufferedWriter bufferedOutput = new BufferedWriter(output);
			for (String level : record.keySet()) {
				bufferedOutput.write(level + ":" + record.get(level));
				bufferedOutput.newLine();
			}
			bufferedOutput.flush();
		}
	}
	
	public void updateRecord(String level, int currScore) throws IOException {
		level = level.toLowerCase();
		int currHighScore = record.get(level);
		if (currHighScore < currScore) {
			record.put(level, currScore);
			writeToRecord();
		}
	}
	
	public int getCurrHighScore(String level) {
		return record.get(level);
	}
	
}
