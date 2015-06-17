/**
 * 
 */
package de.unidue.stud.maha.ftpa3;

import static SoFTlib.Helper.*;
import SoFTlib.SoFT;

/** @author Marc Gesthüsen, Hanno - Felix Wagner */
public class Main extends SoFT {

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
// java.awt.Toolkit.getDefaultToolkit().beep();

		Main expriment = new Main();
		for (int i = 0; i < nodes.length; i++) {
			nodes[i] = new Node(expriment); // create maximum number of nodes, because we can't read the input line before running the experiment. WHY IS THIS SUCH A pita?!
		}

		expriment.runSystem(nodes, "Signed Messages Protokoll", "Aufgabe 3", "Marc Gesthüsen, Hanno - Felix Wagner");
	}

	public int result(String input, String[] output) {
		initialized=false;
		Integer resultVal = -1;

		this.getSummary();

		if (false) {
			resultVal = 0; // wenn IC1 und IC2 erfüllt sind (d.h. Übereinstimmung erreicht worden ist)
		} else if (false) {
			resultVal = 1; // wenn nur IC1 erfüllt ist,
		} else if (false) {
			resultVal = 2; // wenn nur IC1 erfüllt ist,
		} else if (false) {
			resultVal = 3; // wenn nur IC1 erfüllt ist,
		} else {
			resultVal = 4; // else
		}

		if (exec() > NUMBER_OF_RUNS || output[0].equals(Node.MESSAGE_INVALID)) {
			resultVal = 5; // NUMBER_OF_RUNS reached, terminating
			notifyOfEnd();
		}
		return resultVal;
	}

	public synchronized Boolean parseInputLine(String input) {
		if (!initialized) {
			inputLine = input;
			Integer wordCount = getWordCount(inputLine, 1);

			m = number(word(inputLine, 1));
			F = number(word(inputLine, 2));
			
			if(wordCount<4 || m<2 || wordCount-2!=m || F>m){ // no sense in less than 2 nodes, check if number of inputs matches number of words, not too many faulty nodes
				return false;
			}
			
			majorityShare = (m / 2);
			initialWords = new String[m];
			for (int i = 0; i < initialWords.length; i++) {
				initialWords[i] = word(inputLine, 3 + i); // create maximum number of nodes, because we can't read the input line before running the experiment. WHY IS THIS SUCH A pita?!
			}

			initialized = true;
		}
		return initialized;
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