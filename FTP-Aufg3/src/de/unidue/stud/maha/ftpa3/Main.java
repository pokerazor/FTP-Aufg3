/**
 * 
 */
package de.unidue.stud.maha.ftpa3;

import SoFTlib.Node;
import SoFTlib.SoFT;

/**
 * @author Marc Gesthüsen, Hanno - Felix Wagner
 *
 */
public class Main extends SoFT {

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
		Node[] nodes = { new Node() };
		new Main().runSystem(nodes, "Signed Messages Protokoll", "Aufgabe 3", "Marc Gesthüsen, Hanno - Felix Wagner");
	}
}