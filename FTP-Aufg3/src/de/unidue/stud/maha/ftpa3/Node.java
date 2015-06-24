/**
 * 
 */
package de.unidue.stud.maha.ftpa3;

import java.util.HashMap;

import SoFTlib.SoFTException;

/** @author Hanno - Felix Wagner */
public class Node extends SoFTlib.Node {
	private Main experiment = null;
	private static final String CHAR_FAULTY = "-"; // Zeichen für fehlerhaften Knoten
	private static final String CHAR_TIE = "?"; // Zeichen für keine Mehrheit vorhanden/Unentschieden
	private static final String CHAR_GLUE = " "; // Zeichen für keine Mehrheit vorhanden/Unentschieden
	public static final String MESSAGE_SUPERFLUOUS = "I am superfluous, terminating.";
	public static final String MESSAGE_INVALID = "Input line invalid, terminating.";
	private String[] k = null; // Konsistenzvektor, k1...km

	private String initialWord = ""; // wj
	private String finalValue = CHAR_TIE;
	private Integer currentPhase = 0; // i
	private Integer senderIndex = 0; // von Rechner Re wird eine Nachricht empfangen

	/**
	 * 
	 */
	public Node(Main owner) {
		this.experiment = owner;
	}

	public String runNode(String input) throws SoFTException {
		String output = "";
		if (!experiment.parseInputLine(input)) {
			say(MESSAGE_INVALID);
			return MESSAGE_INVALID;
		}
		if (myIndex() > experiment.m - 1) { // terminate nodes which are not necessary
			say(MESSAGE_SUPERFLUOUS);
			return MESSAGE_SUPERFLUOUS;
		}

		k = new String[experiment.m];
		for (int x = 0; x < k.length; x++) { // initialize consistency vector
			k[x] = CHAR_TIE;
		}
		initialWord = experiment.initialWords[myIndex()];

		k[myIndex()] = initialWord;

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