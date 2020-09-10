package dataCatcher;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.IOException;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;

import org.jsoup.*;
import org.jsoup.nodes.*;
import org.jsoup.select.Elements;

public class Main {

	public static void main(String[] args) {
		System.out.println("Bonjour");
		
		HashMap<String, String> datesGoogle = chargerDatesGoogle();
		ArrayList<String> dates = chargerDates();
		ArrayList<String> datesManquantes = new ArrayList<String>();
		
		/*
		for(HashMap.Entry<String, String> date : datesGoogle.entrySet()) {
			System.out.println(date.getKey() + " " + date.getValue());
		}
		*/
				
		File fout = new File("morts_france.csv");
		try {
			FileOutputStream fos = new FileOutputStream(fout);
	 
			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));
	 
			try {
				String ligne;
				for(String date: dates) {
					ligne = date + ",";
					if(datesGoogle.containsKey(date)) {
						ligne = ligne + datesGoogle.get(date);
					}
					else {
						datesManquantes.add(date);
						ligne = ligne + "0000";
					}
					System.out.println(ligne);
					bw.write(ligne);
					bw.newLine();
				}
				bw.close();
			}
			catch(IOException e) {
				e.printStackTrace();
			}
		}
		catch(FileNotFoundException e) {
			e.printStackTrace();
		}
		
		for(String dateManquante: datesManquantes) {
			System.out.println(dateManquante);
		}
	}
	public static String parseDate(String date) {
		String[] mois = {"févr.", "mars", "avr.", "mai", "juin", "juil.", "août", "sept."};
		String[] codes = {"022020", "032020", "042020", "052020", "062020", "072020", "082020", "092020"};

		for(int i = 0; i < mois.length; i++ ) {
				date = date.replace(mois[i], codes[i]);
		}

		return date;
	}
	public static ArrayList<String> chargerDates() {
		ArrayList<String> dates = new ArrayList<String>();
		
		LocalDate dateDebut = LocalDate.of(2020, Month.FEBRUARY, 29);
		LocalDate dateFin = LocalDate.now();
		
		for(LocalDate date = dateDebut; date.isBefore(dateFin); date = date.plusDays(1)) {
			DateTimeFormatter formateur = DateTimeFormatter.ofPattern("ddMMyyyy");
			dates.add(date.format(formateur));
		}
		
		return dates;
	}
	public static String[] getJetons(String date_str) {
		date_str = date_str.replaceAll("\\s", "");
		String[] jetons = date_str.split("le");
		
		return jetons;
	}
	public static void testit() {

	}
	public static HashMap<String, String> chargerDatesGoogle() {
		HashMap<String, String> dates = new HashMap<String, String>(); 
		Document doc;
		Element ol;
		Elements lis;
		try {
			doc = Jsoup.connect("https://www.google.com/search?q=nombre+morts+coronavirus+france").get();
			ol = doc.select("div.ruktOc ol").first();
			lis = ol.children();
			
			String text;
			for( Element li: lis) {
				text = parseDate(li.ownText());
				
				String[] jetons = getJetons(text);
				String num = jetons[0];
				String date = jetons[1];

				if(date.length() < 8) {
					date = "0" + date;
				}
				
				dates.put(date, num);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return dates;
	}
}
