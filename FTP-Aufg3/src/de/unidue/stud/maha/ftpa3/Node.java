/**
 * 
 */
package de.unidue.stud.maha.ftpa3;

import java.util.HashMap;

import SoFTlib.Msg;
import SoFTlib.SoFTException;

/** @author Hanno - Felix Wagner */
public class Node extends SoFTlib.Node {
	private Main experiment = null;
	private static final String CHAR_FAULTY = "-"; // Zeichen f�r fehlerhaften
													// Knoten
	private static final String CHAR_TIE = "?"; // Zeichen f�r keine Mehrheit
												// vorhanden/Unentschieden
	private static final String CHAR_GLUE = " "; // Zeichen f�r keine Mehrheit
													// vorhanden/Unentschieden
	public static final String MESSAGE_SUPERFLUOUS = "I am superfluous, terminating.";
	public static final String MESSAGE_INVALID = "Input line invalid, terminating.";

	private String[] k = null; // Konsistenzvektor, k1...km
	private String initialWord = ""; // wj
	private String finalValue = CHAR_TIE;
	private Integer currentPhase = 0; // i
	private Integer e = myIndex(); // von Rechner Re wird eine Nachricht
									// empfangen

	private String possible_nodes = "ABCDEFGHIJ";

	/**
	 * 
	 */
	public Node(Main owner) {
		this.experiment = owner;
	}

	/**
	 * returns neighbor nodes as String
	 * 
	 * @return
	 */
	private String getNeighborNodes() {
		String targets = possible_nodes.substring(0, experiment.m - 1);
		targets = targets.replace("" + myChar(), "");
		return targets;
	}

	private String getSignatur(String msg) {
		String[] array = msg.split(":");
		String signaturen = "";
		for (int i = 1; i < msg.length(); i++) {
			signaturen += array[i];
		}
		return signaturen;
	}

	private String getMessage(String msg) {
		String[] array = msg.split(":");
		return array[0];
	}

	private String addSignatur(String msg) {
		return msg + ":" + e;
	}

	public String runNode(String input) throws SoFTException {
		String output = "";
		if (!experiment.parseInputLine(input)) {
			say(MESSAGE_INVALID);
			return MESSAGE_INVALID;
		}
		if (myIndex() > experiment.m - 1) { // terminate nodes which are not
											// necessary
			say(MESSAGE_SUPERFLUOUS);
			return MESSAGE_SUPERFLUOUS;
		}

		k = new String[experiment.m];
		for (int x = 0; x < k.length; x++) { // initialize consistency vector
			k[x] = CHAR_TIE;
		}
		initialWord = experiment.initialWords[e];

		k[e] = initialWord;

		String neighbors = getNeighborNodes();

		if (e == 1) {
			form('n', initialWord).send(neighbors);
		}

		currentPhase = 1;

		for (int i = currentPhase; i <= experiment.F + 1; i++) {
			Msg receive = receive(neighbors, 'n', experiment.d * currentPhase);
			if (receive != null) {

			}
		}

		finalValue = Mehrheit(k);

		output = buildOutput();
		return output;
	}

	private String Mehrheit(String[] k) {
		Integer maxMatches = 0;
		String maxMatchesValue = CHAR_TIE;
		HashMap<String, Integer> occurences = new HashMap<String, Integer>();
		for (int i = 0; i < k.length; i++) {
			String curElement = k[i];
			if (curElement.equals(CHAR_FAULTY)) {
				continue; // skip entries of faulty nodes
			}
			Integer curCount = occurences.get(curElement);
			if (curCount == null) { // value has no count yet, start from 0
				curCount = 0;
			}
			curCount++;
			occurences.put(curElement, curCount);
			maxMatches = Math.max(maxMatches, curCount);

			if (maxMatches > experiment.majorityShare) {
				maxMatchesValue = curElement;
				break; // already found the majority, don't continue
			}
		}
		return maxMatchesValue;
	}

	private String buildOutput() { // https://stackoverflow.com/questions/1515437/java-function-for-arrays-like-phps-join
		String[] s = k;
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