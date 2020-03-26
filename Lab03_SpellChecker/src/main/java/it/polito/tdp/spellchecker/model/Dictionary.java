package it.polito.tdp.spellchecker.model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Dictionary {

	private Set<String> dictionary;

	public void loadDictionary(String language) {
		dictionary = new HashSet<>();

		try {
			FileReader fr = new FileReader("src/main/resources/" + language + ".txt");
			BufferedReader bReader = new BufferedReader(fr);
			String word;
			while ((word = bReader.readLine()) != null) {
				// System.out.println(word);
				dictionary.add(word.toLowerCase());
			}
			System.out.println("Dizionario di " + language + " caricato");
		} catch (IOException e) {
			System.out.println("Errore nella lettura del file");
		}
	}

	public List<RichWord> spellCheckText(List<String> inputListTextList) {

		List<RichWord> output = new ArrayList<>();

		for (String s : inputListTextList) {
			try {
				String t = s.toLowerCase();
				boolean esiste = dictionary.contains(t);
				if (esiste) {
					output.add(new RichWord(t, true));
				} else {
					output.add(new RichWord(t, false));
				}
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
		return output;
	}
}
