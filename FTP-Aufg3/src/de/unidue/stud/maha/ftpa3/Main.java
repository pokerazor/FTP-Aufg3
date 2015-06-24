/**
 * 
 */
package de.unidue.stud.maha.ftpa3;

import static SoFTlib.Helper.*;
import SoFTlib.SoFT;

/** @author Marc Gesthüsen, Hanno - Felix Wagner */
public class Main extends SoFT {

	// TEST INPUT LINE 4 1 rot orange gruen orange

	public Integer m = 0; // Anzahl Knoten gesamt, von Eingabezeile gelesen
	public Integer majorityShare = 0; // m/2
	public Integer F = 0; // Anzahl fehlerhafte Knoten, von Eingabezeile gelesen
	public String[] initialWords = new String[m]; // Wörter als Anfangswerte der Knoten, von Eingabezeile gelesen
	public Integer d = 0; // Dauer der Protokoll-Phase
	public String inputLine = "";
	public static Boolean initialized = false;
	public static final Integer NUMBER_OF_RUNS = 1000;
	static Node[] nodes = new Node[10];
	private static final Integer NUMBER_OF_NOTIFICATIONS = 20;

	public Main() {
	}

	/** @param args */
	public static void main(String[] args) {
		Main expriment = new Main();
		for (int i = 0; i < nodes.length; i++) {
			nodes[i] = new Node(expriment); // create maximum number of nodes, because we can't read the input line before running the experiment. WHY IS THIS SUCH A pita?!
		}

		expriment.runSystem(nodes, "Signed Messages Protokoll", "Aufgabe 3", "Marc Gesthüsen, Hanno - Felix Wagner");
	}

	// IC1: Alle fehlerfreien Knoten terminieren mit dem gleichen Konsistenzvektor
	// IC2: Wenn der Erzeuger fehlerfrei ist, dann wird sein Anfangswert von jedem fehlerfreien Knoten in den Konsistenzvektor übernommen
	public int result(String input, String[] output) {
		initialized = false;
		Integer resultVal = -1;

		String summary = " ";

		for (int i = 0; i < output.length; i++) {
			String curOutput = output[i];
			summary += i + ":\"" + curOutput + "\" ";

		}

		setSummary(fault(0) + " m =" + m + " F =" + F + " majorityShare =" + majorityShare + "\n" + summary);

		String[] foo = explodeWords(output[0]);

		setSummary(foo[0] + "");

		if (false) {
			resultVal = 0; // wenn IC1 und IC2 erfüllt sind (d.h. Übereinstimmung erreicht worden ist)
		} else if (false) {
			resultVal = 1; // wenn nur IC1 erfüllt ist,
		} else if (false) {
			resultVal = 2; // wenn nur IC2 erfüllt ist,
		} else if (false) {
			resultVal = 3; // wenn IC1 und IC2 verletzt sind
		} else {
			resultVal = 4; // else, in komischen fällen
		}

		if (exec() > NUMBER_OF_RUNS || output[0].equals(Node.MESSAGE_INVALID)) {
			resultVal = 5; // NUMBER_OF_RUNS reached, terminating
			notifyOfEnd();
		}
		return resultVal;
	}

	public Integer[] getFaultVector() {
		Integer[] faultVector = new Integer[m];
		for (int i = 0; i < m; i++) {
			faultVector[i] = fault(i);
		}
		return faultVector;
	}

	public Boolean[] getIntegrityVector() {
		Integer[] faultVector = getFaultVector();

		Boolean[] integrityVector = new Boolean[faultVector.length];
		for (int i = 0; i < m; i++) {
			integrityVector[i] = faultVector[i]==0; //if node is faultless, return true integrity
		}
		return integrityVector;
	}
	
	public String getIntegrityGroupString(){
		Integer[] faultVector = getFaultVector();
		String integrityGroupString="";
		for (int i = 0; i < m; i++) {
			if(faultVector[i]==0){
				or(integrityGroupString,nodeChr (i)+""); //add node char to list of faultless noodes
			}
		}
		return integrityGroupString;
	}

	public synchronized Boolean parseInputLine(String input) {
		if (!initialized) {
			inputLine = input;
			Integer wordCount = getWordCount(inputLine, 1);

			m = number(word(inputLine, 1));
			F = number(word(inputLine, 2));

			if (wordCount < 4 || m < 2 || wordCount - 2 != m || F > m) { // no sense in less than 2 nodes, check if number of inputs matches number of words, not too many faulty nodes
				return false;
			}

			majorityShare = (m / 2);
			initialWords = explodeWords(inputLine, 3);
			/*
			initialWords = new String[m];
			for (int i = 0; i < initialWords.length; i++) {
				initialWords[i] = word(inputLine, 3 + i); 
			}
			*/

			initialized = true;
		}
		return initialized;
	}

	public static String[] explodeWords(String inputLine, Integer offset) {
		Integer numOfElements = getWordCount(inputLine, 1);
		String[] elements = new String[numOfElements];
		for (int i = 0; i < elements.length; i++) {
			elements[i] = word(inputLine, offset + i);
		}
		return elements;
	}

	public static String[] explodeWords(String inputLine) {
		return explodeWords(inputLine, 1);
	}

	public void notifyOfEnd() {
		new NotifyOfEnd().start();
	}

	private class NotifyOfEnd extends Thread {
		public void run() {
			for (Integer i = 0; i < NUMBER_OF_NOTIFICATIONS; i++) {
				java.awt.Toolkit.getDefaultToolkit().beep();
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
}