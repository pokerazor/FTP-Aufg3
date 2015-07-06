/**
 * 
 */
package de.unidue.stud.maha.ftpa3;

import static SoFTlib.Helper.*;
import SoFTlib.SoFT;

/** @author Marc Gesth�sen, Hanno - Felix Wagner */
public class Main extends SoFT {

	// TEST INPUT LINE 4 1 rot orange gruen orange

	public Integer m = 0; // Anzahl Knoten gesamt, von Eingabezeile gelesen
	public Integer majorityShare = 0; // m/2
	public Integer F = 0; // Anzahl fehlerhafte Knoten, von Eingabezeile gelesen
	public String[] initialWords = new String[m]; // W�rter als Anfangswerte der
													// Knoten, von Eingabezeile
													// gelesen
	public Integer d = 50; // Dauer der Protokoll-Phase
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
			nodes[i] = new Node(expriment); // create maximum number of
											// nodes, because we can't read
											// the input line before running
											// the experiment. WHY IS THIS
											// SUCH A pita?!
		}

		expriment.runSystem(nodes, "Signed Messages Protokoll", "Aufgabe 3", "Marc Gesth�sen, Hanno - Felix Wagner");
	}

	// IC1: Alle fehlerfreien Knoten terminieren mit dem gleichen
	// Konsistenzvektor
	// IC2: Wenn der Erzeuger fehlerfrei ist, dann wird sein Anfangswert von
	// jedem fehlerfreien Knoten in den Konsistenzvektor �bernommen
	public int result(String input, String[] output) {
		initialized = false;
		Integer resultVal = -1;

		String summary = " ";

		for (int i = 0; i < output.length; i++) {
			String curOutput = output[i];
			summary += i + ":\"" + curOutput + "\" ";

		}

		summary += fault(0) + " m =" + m + " F =" + F + " majorityShare =" + majorityShare;

		Boolean IC1 = checkIC1(getIntegrityGroup(), output);
		Boolean IC2 = checkIC2(getIntegrityGroup(), output);

		summary += IC1 + " " + IC2;

		setSummary(summary);

		if (IC1 && IC2) {
			resultVal = 0; // wenn IC1 und IC2 erf�llt sind (d.h.
							// �bereinstimmung erreicht worden ist)
		} else if (IC1) {
			resultVal = 1; // wenn nur IC1 erf�llt ist,
		} else if (IC2) {
			resultVal = 2; // wenn nur IC2 erf�llt ist,
		} else if (!IC1 && !IC2) {
			resultVal = 3; // wenn IC1 und IC2 verletzt sind
		} else {
			resultVal = 4; // else, in komischen f�llen
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

	public Boolean checkIC1(String integrityGroup, String outputs[]) {
		String sameVector = null;
		Boolean result = false;
		for (int i = 0; i < outputs.length; i++) {
			String curOutput = outputs[i];

			char checkNode = nodeChr(i);

			if (curOutput.equals(Node.MESSAGE_SUPERFLUOUS)) { // if the node
																// feels
																// superfluous,
																// don't give a
																// shit an his
																// result
				continue;
			}

			if (and(checkNode + "", integrityGroup).equals(checkNode + "")) { // currently
																				// probed
																				// node
																				// is
																				// part
																				// of
																				// the
																				// integrity
																				// group
				if (sameVector == null) {
					sameVector = curOutput;
					result = true;
				} else {
					if (curOutput.equals(sameVector)) {
						result = true;
					} else {
						result = false;
					}
				}
				if (result == false) {
					return result;
				}
			}
		}
		return result;
	}

	public Boolean checkIC2(String integrityGroup, String outputs[]) {
		Boolean result = true;

		for (int i = 0; i < outputs.length; i++) {  //loop all nodes
			String curOutput = outputs[i];

			char checkNode = nodeChr(i);

			if (curOutput.equals(Node.MESSAGE_SUPERFLUOUS)) { // if the node feels superfluous, don't give a shit an his result
				continue;
			}

			if (and(checkNode + "", integrityGroup).equals(checkNode + "")) { // currently probed node is part of the integrity group
				for (int j = 0; j < outputs.length; j++) { // loop all nodes again
					String curInput = initialWords[j]; //look at it's input

					if (outputs[j].equals(Node.MESSAGE_SUPERFLUOUS)) { // if the node feels superfluous, don't compare his input
						continue;
					}
					if (!word(curOutput, j).equals(curInput)) { //if the input of the inner looped node doesn't match the outer's output at the corresponding position
						result = false;
						return result;
					}

				}
			}

		}
		return result;
	}

	public Boolean[] getIntegrityVector() {
		Integer[] faultVector = getFaultVector();

		Boolean[] integrityVector = new Boolean[faultVector.length];
		for (int i = 0; i < m; i++) {
			integrityVector[i] = faultVector[i] == 0; // if node is faultless,
														// return true integrity
		}
		return integrityVector;
	}

/*
 * return list (of chars) of all nodes, wich are faultless (as configured in the GUI)
 */
	public String getIntegrityGroup() {
		Integer[] faultVector = getFaultVector();
		String integrityGroup = "";
		for (int i = 0; i < m; i++) {
			if (faultVector[i] == 0) {
				integrityGroup = or(integrityGroup, nodeChr(i) + ""); // add
																		// node
																		// char
																		// to
																		// list
																		// of
																		// faultless
																		// noodes
			}
		}
		return integrityGroup;
	}

	public synchronized Boolean parseInputLine(String input) {
		if (!initialized) {
			inputLine = input;
			Integer wordCount = getWordCount(inputLine, 1);

			if (wordCount < 4) { // no sense in less than 2 nodes
				return false;
			}

			m = number(word(inputLine, 1));
			F = number(word(inputLine, 2));

			if (m < 2 || wordCount - 2 != m || F > m) { // check if number of
														// inputs matches number
														// of words, not too
														// many faulty nodes
				return false;
			}

			majorityShare = (m / 2);
			initialWords = explodeWords(inputLine, 3);
			/*
			 * initialWords = new String[m]; for (int i = 0; i <
			 * initialWords.length; i++) { initialWords[i] = word(inputLine, 3 +
			 * i); }
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