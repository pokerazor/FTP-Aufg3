/**
 * 
 */
package de.unidue.stud.maha.ftpa3;

import SoFTlib.SoFT;

/**
 * @author Marc Gesth�sen, Hanno - Felix Wagner
 *
 */
public class Main extends SoFT {
	
	public static Integer m=0; //Anzahl Knoten gesamt, von Eingabezeile gelesen
	public Integer F=0; //Anzahl fehlerhafte Knoten, von Eingabezeile gelesen
	public String[] initialWords = new String[m]; //W�rter als Anfangswerte der Knoten, von Eingabezeile gelesen
	public Integer d=0; //Dauer der Protokoll-Phase
	
	/**
	 * 
	 */
	public Main() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Node[] nodes = { new Node(m) };
		new Main().runSystem(nodes, "Signed Messages Protokoll", "Aufgabe 3", "Marc Gesth�sen, Hanno - Felix Wagner");
	}
	
	private static void parseInputLine(){
		
	}
}