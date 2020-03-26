package it.polito.tdp.spellchecker;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import it.polito.tdp.spellchecker.model.Dictionary;
import it.polito.tdp.spellchecker.model.RichWord;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;

/**
 * Correttore ortografico, dopo aver inserito un testo, vengono stampate le
 * parole errate, il numero di errori, ed il tempo impiegato per effettuare il
 * controllo
 * 
 * @author D2435
 *
 */
public class FXMLController {

	Dictionary dizionario = new Dictionary();
	
	@FXML
	private ResourceBundle resources;

	@FXML
	private URL location;

	@FXML
	private RadioButton radioIta;

	@FXML
	private RadioButton radioEng;

	@FXML
	private TextArea txtAreaUp;

	@FXML
	private Button btnSpell;

	@FXML
	private TextArea txtAreaDown;

	@FXML
	private Label txtCount;

	@FXML
	private Button btnClear;

	@FXML
	private Label txtTime;

	@FXML
	void doSpell(ActionEvent event) {
		
		double inizio = System.nanoTime();
		
		String[] txtInserito = txtAreaUp.getText().replaceAll("[?.,\\/#!$%\\^&\\*;:{}=\\-_'()\\[\\]\"]", "").split(" ");
		List<String> txtLista = new ArrayList<>();

		for (String s : txtInserito) {
			txtLista.add(s);
		}

		List<RichWord> risultato = dizionario.spellCheckText(txtLista);

		for (RichWord s : risultato) {
			System.out.print(s.parola + " -> ");
			System.out.println(s.corretta);
		}
		
		int erroriNumero = 0;
		String erroriParole = "";
		
		for (RichWord s : risultato) {
			if(!s.corretta) {
				erroriNumero++;
				erroriParole += s.parola + "\n";
			}
		}
    	double fine = System.nanoTime() - inizio;
    	txtTime.setText("Time: " + fine);
		
		txtAreaDown.setText(erroriParole);
		txtCount.setText("Error: " + erroriNumero);
	}

	@FXML
	void doClear(ActionEvent event) {
		
		txtAreaUp.clear();
		txtAreaDown.clear();
		
		txtCount.setText("Error: 0");
		txtTime.setText("Time: 0");
	}

	@FXML
	void doEng(ActionEvent event) {
		if (radioEng.isSelected()) {
			radioIta.setSelected(false);
			dizionario.loadDictionary("English");
			doClear(event);
		} else {
			radioIta.setSelected(true);
			dizionario.loadDictionary("Italian");
			doClear(event);
		}
	}

	@FXML
	void doIta(ActionEvent event) {
		if (radioIta.isSelected()) {
			radioEng.setSelected(false);
			dizionario.loadDictionary("Italian");
		} else {
			radioEng.setSelected(true);
			dizionario.loadDictionary("English");
		}
	}

	@FXML
	void initialize() {
		assert radioIta != null : "fx:id=\"radioIta\" was not injected: check your FXML file 'Scene.fxml'.";
		assert radioEng != null : "fx:id=\"radioEng\" was not injected: check your FXML file 'Scene.fxml'.";
		assert txtAreaUp != null : "fx:id=\"txtAreaUp\" was not injected: check your FXML file 'Scene.fxml'.";
		assert btnSpell != null : "fx:id=\"btnSpell\" was not injected: check your FXML file 'Scene.fxml'.";
		assert txtAreaDown != null : "fx:id=\"txtAreaDown\" was not injected: check your FXML file 'Scene.fxml'.";
		assert txtCount != null : "fx:id=\"txtCount\" was not injected: check your FXML file 'Scene.fxml'.";
		assert btnClear != null : "fx:id=\"btnClear\" was not injected: check your FXML file 'Scene.fxml'.";
		assert txtTime != null : "fx:id=\"txtTime\" was not injected: check your FXML file 'Scene.fxml'.";
		dizionario.loadDictionary("Italian");
		txtCount.setText("Error: 0");
		txtTime.setText("Time: 0");
	}
}
