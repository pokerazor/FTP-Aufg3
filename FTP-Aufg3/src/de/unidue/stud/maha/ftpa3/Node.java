/**
 * 
 */
package de.unidue.stud.maha.ftpa3;

import SoFTlib.SoFTException;


/**
 * @author Hanno - Felix Wagner
 *
 */
public class Node extends SoFTlib.Node {
	private Integer m=0; //Anzahl Knoten gesamt, von Eingabezeile gelesen
	private String[] k = new String[m]; //Konsistenzvektor
	private Integer majorityShare=0; //m/2
	private static final String SIGN_FAULTY="-"; //Zeichen für fehlerhaften Knoten
	private static final String SIGN_TIE="?"; //Zeichen für keine Mehrheit vorhanden/Unentschieden
	
	/**
	 * 
	 */
	public Node(Integer m) {
		this.m=m;
		k=new String[m];
		// TODO Auto-generated constructor stub
	}
	
	public String runNode(String input) throws SoFTException {
		return input;
	}

	private String Mehrheit(String[] k){
		return k[0];
	}
	
}
