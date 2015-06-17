/**
 * 
 */
package de.unidue.stud.maha.ftpa3;

import static SoFTlib.Helper.number;
import static SoFTlib.Helper.words;
import SoFTlib.SoFT;

/** @author Marc Gesthüsen, Hanno - Felix Wagner */
public class Main extends SoFT {

	public static Integer m = 0; // Anzahl Knoten gesamt, von Eingabezeile gelesen
	public Integer F = 0; // Anzahl fehlerhafte Knoten, von Eingabezeile gelesen
	public String[] initialWords = new String[m]; // Wörter als Anfangswerte der Knoten, von Eingabezeile gelesen
	public Integer d = 0; // Dauer der Protokoll-Phase
	public String inputLine="";

	/**
	 * 
	 */
	public Main() {
		// TODO Auto-generated constructor stub
	}

	/** @param args */
	public static void main(String[] args) {
		Node[] nodes = { new Node(m) };
		new Main().runSystem(nodes, "Signed Messages Protokoll", "Aufgabe 3", "Marc Gesthüsen, Hanno - Felix Wagner");
	}

	public int result(String input, String[] output) {
		Integer resultVal = -1;
		if (false) {
			resultVal = 0; // wenn IC1 und IC2 erfüllt sind (d.h. Übereinstimmung erreicht worden ist)
		} else if (false) {
			resultVal = 1; // wenn nur IC1 erfüllt ist,
		} else if (false) {
			resultVal = 2; // wenn nur IC1 erfüllt ist,
		} else if (false) {
			resultVal = 3; // wenn nur IC1 erfüllt ist,
		} else {
			resultVal = 4;
		}
		return resultVal;
	}

	private void parseInputLine() {
		String input="";
		number(words(input, 1, 1, 1));
	}
}