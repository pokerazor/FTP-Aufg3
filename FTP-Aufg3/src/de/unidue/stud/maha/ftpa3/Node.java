/**
 * 
 */
package de.unidue.stud.maha.ftpa3;


/**
 * @author Hanno - Felix Wagner
 *
 */
public class Node extends SoFTlib.Node {
	private Integer m=0; //Anzahl Knoten gesamt, von Eingabezeile gelesen
	private String[] k = new String[m]; //Konsistenzvektor

	/**
	 * 
	 */
	public Node(Integer m) {
		this.m=m;
		k=new String[m];
		// TODO Auto-generated constructor stub
	}

	private String Mehrheit(String[] k){
		return k[0];
	}
	
}
