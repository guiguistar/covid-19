package dataCatcher;

import java.io.IOException;

import org.jsoup.*;
import org.jsoup.nodes.*;
import org.jsoup.select.Elements;

public class Main {

	public static void main(String[] args) {
		System.out.println("Bonjour");
		
		//doit();
		testit();
	}
	public static String parseDate(String date) {
		String[] mois = {"févr.", "mars", "avr.", "mai", "juin", "juil.", "août"};
		String[] codes = {"022020", "032020", "042020", "052020", "062020", "072020", "082020"};

		for(int i = 0; i < 7; i++ ) {
				date = date.replace(mois[i], codes[i]);
		}
		return date;
	}
	public static void testit() {
		String date_str = "1 438 le 15 avr.";
		date_str = date_str.replaceAll("\\s", "");
		String[] jetons = date_str.split("le");
			
		String num = jetons[0];
		String date = jetons[1];
		
		System.out.println(parseDate(date));
		System.out.println(num);
	}
	public static void doit() {
		Document doc;
		Element ol;
		Elements lis;
		try {
			doc = Jsoup.connect("https://www.google.com/search?q=nombre+morts+coronavirus+france").get();
			ol = doc.select("div.ruktOc ol").first();
			lis = ol.children();
			
			String text;
			for( Element li: lis) {
				text = li.ownText();
				System.out.println(text);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
