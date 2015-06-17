/**
 * 
 */
package de.unidue.stud.maha.ftpa3;

import SoFTlib.SoFTException;

/** @author Hanno - Felix Wagner */
public class Node extends SoFTlib.Node {
	private Integer m = 0; // Anzahl Knoten gesamt, von Eingabezeile gelesen
	private String[] k = new String[m]; // Konsistenzvektor
	private Integer majorityShare = 0; // m/2
	private static final String CHAR_FAULTY = "-"; // Zeichen für fehlerhaften Knoten
	private static final String CHAR_TIE = "?"; // Zeichen für keine Mehrheit vorhanden/Unentschieden
	private static final String CHAR_GLUE = " "; // Zeichen für keine Mehrheit vorhanden/Unentschieden
	private String finalValue="";

	/**
	 * 
	 */
	public Node(Integer m) {
		this.m = m;
		k = new String[m];
		// TODO Auto-generated constructor stub
	}

	public String runNode(String input) throws SoFTException {
		String output="";
		output=buildOutput();
		return output;
	}

	private String Mehrheit(String[] k) {
		return k[0];
	}

	private String buildOutput() { // https://stackoverflow.com/questions/1515437/java-function-for-arrays-like-phps-join
		String[] s=k;
		int k = s.length;
		if (k == 0) {
			return null;
		}
		StringBuilder out = new StringBuilder();
		out.append(s[0]);
		for (int x = 1; x < k; ++x) {
			out.append(CHAR_GLUE).append(s[x]);
		}
		out.append(CHAR_GLUE).append(finalValue);

		return out.toString();
	}
}