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
		String chaineCas = "<li>43 le 29 févr.</li><li>30 le 1 mars</li><li>61 le 2 mars</li><li>21 le 3 mars</li><li>73 le 4 mars</li><li>138 le 5 mars</li><li>190 le 6 mars</li><li>336 le 7 mars</li><li>177 le 8 mars</li><li>286 le 9 mars</li><li>372 le 10 mars</li><li>497 le 11 mars</li><li>595 le 12 mars</li><li>785 le 13 mars</li><li>838 le 14 mars</li><li>924 le 15 mars</li><li>1 210 le 16 mars</li><li>1 097 le 17 mars</li><li>1 404 le 18 mars</li><li>1 861 le 19 mars</li><li>1 617 le 20 mars</li><li>1 847 le 21 mars</li><li>2 230 le 22 mars</li><li>3 167 le 23 mars</li><li>2 446 le 24 mars</li><li>2 931 le 25 mars</li><li>3 922 le 26 mars</li><li>3 809 le 27 mars</li><li>4 611 le 28 mars</li><li>2 599 le 29 mars</li><li>4 376 le 30 mars</li><li>7 578 le 31 mars</li><li>4 861 le 1 avr.</li><li>2 116 le 2 avr.</li><li>5 233 le 3 avr.</li><li>4 267 le 4 avr.</li><li>1 873 le 5 avr.</li><li>3 912 le 6 avr.</li><li>3 777 le 7 avr.</li><li>3 881 le 8 avr.</li><li>4 286 le 9 avr.</li><li>4 342 le 10 avr.</li><li>3 114 le 11 avr.</li><li>1 613 le 12 avr.</li><li>2 673 le 13 avr.</li><li>5 497 le 14 avr.</li><li>2 633 le 15 avr.</li><li>2 641 le 16 avr.</li><li>405 le 17 avr.</li><li>2 569 le 18 avr.</li><li>785 le 19 avr.</li><li>2 051 le 20 avr.</li><li>2 667 le 21 avr.</li><li>1 827 le 22 avr.</li><li>1 653 le 23 avr.</li><li>1 773 le 24 avr.</li><li>1 537 le 25 avr.</li><li>461 le 26 avr.</li><li>1 195 le 27 avr.</li><li>1 065 le 28 avr.</li><li>1 607 le 29 avr.</li><li>1 139 le 30 avr.</li><li>604 le 1 mai</li><li>794 le 2 mai</li><li>308 le 3 mai</li><li>576 le 4 mai</li><li>1 104 le 5 mai</li><li>4 183 le 6 mai</li><li>629 le 7 mai</li><li>642 le 8 mai</li><li>433 le 9 mai</li><li>209 le 10 mai</li><li>456 le 11 mai</li><li>708 le 12 mai</li><li>507 le 13 mai</li><li>622 le 14 mai</li><li>563 le 15 mai</li><li>372 le 16 mai</li><li>120 le 17 mai</li><li>492 le 18 mai</li><li>524 le 19 mai</li><li>418 le 20 mai</li><li>318 le 21 mai</li><li>403 le 22 mai</li><li>240 le 23 mai</li><li>115 le 24 mai</li><li>358 le 25 mai</li><li>276 le 26 mai</li><li>191 le 27 mai</li><li>597 le 29 mai</li><li>1 828 le 30 mai</li><li>257 le 31 mai</li><li>338 le 1 juin</li><li>352 le 3 juin</li><li>767 le 4 juin</li><li>611 le 5 juin</li><li>579 le 6 juin</li><li>343 le 7 juin</li><li>211 le 8 juin</li><li>403 le 9 juin</li><li>545 le 10 juin</li><li>425 le 11 juin</li><li>726 le 12 juin</li><li>526 le 13 juin</li><li>407 le 14 juin</li><li>152 le 15 juin</li><li>344 le 16 juin</li><li>458 le 17 juin</li><li>467 le 18 juin</li><li>811 le 19 juin</li><li>641 le 20 juin</li><li>284 le 21 juin</li><li>373 le 22 juin</li><li>517 le 23 juin</li><li>81 le 24 juin</li><li>0 le 25 juin</li><li>1 588 le 26 juin</li><li>541 le 30 juin</li><li>918 le 1 juil.</li><li>659 le 2 juil.</li><li>582 le 3 juil.</li><li>475 le 7 juil.</li><li>663 le 8 juil.</li><li>531 le 9 juil.</li><li>748 le 10 juil.</li><li>534 le 16 juil.</li><li>836 le 17 juil.</li><li>584 le 21 juil.</li><li>998 le 22 juil.</li><li>1 062 le 23 juil.</li><li>1 130 le 24 juil.</li><li>725 le 28 juil.</li><li>1 392 le 29 juil.</li><li>1 377 le 30 juil.</li><li>1 346 le 31 juil.</li><li>1 039 le 4 août</li><li>1 695 le 5 août</li><li>1 604 le 6 août</li><li>2 288 le 7 août</li><li>1 397 le 11 août</li><li>2 524 le 12 août</li><li>2 669 le 13 août</li><li>2 846 le 14 août</li><li>2 238 le 18 août</li><li>3 776 le 19 août</li><li>4 771 le 20 août</li><li>4 586 le 21 août</li><li>3 602 le 22 août</li><li>4 897 le 23 août</li><li>1 955 le 24 août</li><li>3 304 le 25 août</li><li>5 429 le 26 août</li><li>6 111 le 27 août</li><li>7 379 le 28 août</li><li>5 453 le 29 août</li><li>5 413 le 30 août</li><li>3 082 le 31 août</li><li>4 982 le 1 sept.</li><li>7 017 le 2 sept.</li><li>7 157 le 3 sept.</li><li>8 975 le 4 sept.</li><li>8 550 le 5 sept.</li><li>7 071 le 6 sept.</li><li>4 203 le 7 sept.</li><li>6 544 le 8 sept.</li><li>8 577 le 9 sept.</li><li>9 843 le 10 sept.</li><li>9 406 le 11 sept.</li><li>10 561 le 12 sept.</li><li>7 183 le 13 sept.</li>";
		System.out.println("Bonjour");
		
		HashMap<String, String> datesGoogle = chargerDatesGoogle();
		ArrayList<String> datesSouhaitees = chargerDates();
		ArrayList<String> datesManquantes = new ArrayList<String>();
		
		/*
		for(HashMap.Entry<String, String> date : datesGoogle.entrySet()) {
			System.out.println(date.getKey() + " " + date.getValue());
		}
		*/
		
		//ecrireFichier(datesGoogle, datesSouhaitees, datesManquantes);
		
		Document doc  = Jsoup.parseBodyFragment(chaineCas);
		Elements lis = doc.body().children();
		for(Element li : lis) {
			System.out.println(li.ownText());
		}
	}
	public static void garnirTableDates(Elements lis, HashMap<String, String> datesMortsGoogle) {
		String text;
		for( Element li: lis) {
			text = parseDate(li.ownText());
			
			String[] jetons = getJetons(text);
			String num = jetons[0];
			String date = jetons[1];

			if(date.length() < 8) {
				date = "0" + date;
			}
			datesMortsGoogle.put(date, num);
		}
	}
	public static void ecrireFichier(HashMap<String, String> datesGoogle,
			ArrayList<String> datesSouhaitees,
			ArrayList<String> datesManquantes) {
		File fout = new File("morts_france.csv");
		try {
			FileOutputStream fos = new FileOutputStream(fout);
 
			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));
 
			try {
				String ligne;
				for(String date: datesSouhaitees) {
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
		HashMap<String, String> datesMortsGoogle = new HashMap<String, String>(); 
		Document doc;
		Element ol;
		Elements lis;
		try {
			doc = Jsoup.connect("https://www.google.com/search?q=nombre+morts+coronavirus+france").get();
			ol = doc.select("div.ruktOc ol").first();
			lis = ol.children();
			
			garnirTableDates(lis, datesMortsGoogle);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return datesMortsGoogle;
	}
}
