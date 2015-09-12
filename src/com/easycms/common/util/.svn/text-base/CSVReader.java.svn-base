package com.easycms.common.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
public class CSVReader {
	private BufferedReader reader;
	public final String head;
	public final String end;
	public final String regex;
	private boolean hasHead=false;
	private boolean hasEnd = false;
	private String currentLine;
	
	public static void main(String[] args) {
		try {
			CSVReader reader = new CSVReader("e:\\test.csv", "[head]", "[end]","[a-zA-Z]+,{1}\\+?[0-9]{11,13},{1}[a-zA-Z]+");
			String line = null;
			
			while((line=reader.readLine())!=null){
				System.out.println(line);//well-formatted data
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public CSVReader(String filename, String head, String end, String regex)
			throws IOException {
		this.head = head;
		this.end = end;
		this.regex = regex;
		File file = new File(filename);
		if (!file.exists()) {
			throw new FileNotFoundException("the file" + filename
					+ "is not exist.");
		}
		reader = getReader(file);
		
	}

	public String readLine() {
		boolean reachEnd = false;
		try {
			currentLine = reader.readLine();
			reachEnd = end.equalsIgnoreCase(currentLine);
			if(reachEnd){
				currentLine = null;
				hasEnd = true;
			}
		} catch (Exception e) {
			reachEnd = true;
			e.printStackTrace();
		}
		return currentLine;
	}

	private BufferedReader getReader(File file) throws IOException {
		BufferedReader reader = null;
		InputStream is = null;
		try {
			is = new FileInputStream(file);
			InputStreamReader isr = new InputStreamReader(is);
			reader = new BufferedReader(isr);
			if(reader!=null){
				readHeader(reader);
			}
			if(!hasHead){
				throw new IOException("the file does not have head.");
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		if (reader == null) {
			throw new IOException("get reader failed,reader==>" + reader);
		}
		return reader;
	}

	private boolean readHeader(BufferedReader reader) throws IOException {
		if(reader==null){
			throw new IOException("reader cannot be null");
		}
		String line = null;
		while (!hasHead) {
			line = reader.readLine();
			hasHead = this.head.equalsIgnoreCase(line);
		}
		return hasHead;
	}
	
	public void close(){
		if(reader!=null){
			try {
				reader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
