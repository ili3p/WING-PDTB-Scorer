/**
 * Copyright (C) 2014 WING, NUS.
 * 
 * This program is free software: you can redistribute it and/or modify it under the terms of the
 * GNU General Public License as published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without
 * even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License along with this program. If
 * not, see http://www.gnu.org/licenses/.
 */
package sg.edu.nus.comp.wing;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Date;
import java.util.Scanner;

/**
 * <pre>
 * The columns definition:
 * Col  0: Relation type (Explicit/Implicit/AltLex/EntRel/NoRel)
 * Col  1: Section number (0-24)
 * Col  2: File number (0-99)
 * Col  3: Connective/AltLex SpanList (only for Explicit and AltLex)
 * Col  4: Connective/AltLex GornAddressList (only for Explicit and AltLex)
 * Col  5: Connective/AltLex RawText (only for Explicit and AltLex)
 * Col  6: String position (only for Implicit, EntRel and NoRel) 
 * Col  7: Sentence number (only for Implicit, EntRel and NoRel)
 * Col  8: ConnHead (only for Explicit)
 * Col  9: Conn1 (only for Implicit)
 * Col 10: Conn2 (only for Implicit)
 * Col 11: 1st Semantic Class  corresponding to ConnHead, Conn1 or AltLex span (only for Explicit, Implicit and AltLex)
 * Col 12: 2nd Semantic Class  corresponding to ConnHead, Conn1 or AltLex span (only for Explicit, Implicit and AltLex)
 * Col 13: 1st Semantic Class corresponding to Conn2 (only for Implicit)
 * Col 14: 2nd Semantic Class corresponding to Conn2 (only for Implicit)
 * Col 15: Relation-level attribution: Source (only for Explicit, Implicit and AltLex)
 * Col 16: Relation-level attribution: Type (only for Explicit, Implicit and AltLex)
 * Col 17: Relation-level attribution: Polarity (only for Explicit, Implicit and AltLex)
 * Col 18: Relation-level attribution: Determinacy (only for Explicit, Implicit and AltLex)
 * Col 19: Relation-level attribution: SpanList (only for Explicit, Implicit and AltLex)
 * Col 20: Relation-level attribution: GornAddressList (only for Explicit, Implicit and AltLex)
 * Col 21: Relation-level attribution: RawText (only for Explicit, Implicit and AltLex)
 * Col 22: Arg1 SpanList
 * Col 23: Arg1 GornAddress
 * Col 24: Arg1 RawText
 * Col 25: Arg1 attribution: Source (only for Explicit, Implicit and AltLex)
 * Col 26: Arg1 attribution: Type (only for Explicit, Implicit and AltLex)
 * Col 27: Arg1 attribution: Polarity (only for Explicit, Implicit and AltLex)
 * Col 28: Arg1 attribution: Determinacy (only for Explicit, Implicit and AltLex)
 * Col 29: Arg1 attribution: SpanList (only for Explicit, Implicit and AltLex)
 * Col 30: Arg1 attribution: GornAddressList (only for Explicit, Implicit and AltLex)
 * Col 31: Arg1 attribution: RawText (only for Explicit, Implicit and AltLex)
 * Col 32: Arg2 SpanList
 * Col 33: Arg2 GornAddress
 * Col 34: Arg2 RawText
 * Col 35: Arg2 attribution: Source (only for Explicit, Implicit and AltLex)
 * Col 36: Arg2 attribution: Type (only for Explicit, Implicit and AltLex)
 * Col 37: Arg2 attribution: Polarity (only for Explicit, Implicit and AltLex)
 * Col 38: Arg2 attribution: Determinacy (only for Explicit, Implicit and AltLex)
 * Col 39: Arg2 attribution: SpanList (only for Explicit, Implicit and AltLex)
 * Col 40: Arg2 attribution: GornAddressList (only for Explicit, Implicit and AltLex)
 * Col 41: Arg2 attribution: RawText (only for Explicit, Implicit and AltLex)
 * Col 42: Sup1 SpanList (only for Explicit, Implicit and AltLex)
 * Col 43: Sup1 GornAddress (only for Explicit, Implicit and AltLex)
 * Col 44: Sup1 RawText (only for Explicit, Implicit and AltLex)
 * Col 45: Sup2 SpanList (only for Explicit, Implicit and AltLex)
 * Col 46: Sup2 GornAddress (only for Explicit, Implicit and AltLex)
 * Col 47: Sup2 RawText (only for Explicit, Implicit and AltLex)
 * </pre>
 * 
 * Example:
 * 
 * <pre>
 * Explicit|18|70|262..265|1,0|But|||but|||Comparison.Contrast||||Wr|Comm|Null|Null||||9..258|0|From a helicopter a thousand feet above Oakland after the second-deadliest earthquake in U.S. history, a scene of devastation emerges: a freeway crumbled into a concrete sandwich, hoses pumping water into once-fashionable apartments, abandoned autos|Inh|Null|Null|Null||||266..354|1,1;1,2;1,3|this quake wasn't the big one, the replay of 1906 that has been feared for so many years|Inh|Null|Null|Null|||||||||
 * </pre>
 *
 * created: 2014-11-11
 * 
 * @author ilija.ilievski@u.nus.edu
 * @since 0.1
 */
public class PdtbScorer {

  /**
   * Column index for relation type (Explicit/Implicit/AltLex/EntRel/NoRel).
   */
  private static final int TYPE_COL = 0;

  /**
   * Column index for connective SpanList.
   */
  private static final int CONN_COL = 3;

  /**
   * Column start index for semantic class.
   */
  private static final int SEM_CLASS_START_COL = 11;

  /**
   * Column index for Arg1 SpanList.
   */
  private static final int ARG1_COL = 22;

  /**
   * Column index for Arg2 SpanList.
   */
  private static final int ARG2_COL = 32;

  /**
   * Predicted file path.
   */
  private static String predFileName;

  /**
   * Expected file path.
   */
  private static String expFileName;

  /**
   * Semantic class level - 1, 2 or 3.
   */
  private static int semanticLevel;

  /**
   * Error file name or errors_[timestamp] if not set,
   */
  private static String errFileName;

  /**
   * Output file name or System.out if not set.
   */
  private static String outFileName;

  private static Scanner predSc = null;
  private static Scanner expSc = null;
  private static PrintWriter err = null;
  private static PrintStream out = null;

  /**
   * Parse the arguments and start the scorer.
   * 
   * @param args [predictedFileName, expectedFileName, semanticLevel, errorFileName, outputFileName]
   */
  public static void main(String[] args) {

    if (args.length < 3) {
      Util.printHelpMessage(System.out);
      System.exit(1);
    }
    if (!args[2].matches("[1-3]")) {
      Util.printInvalidLevel(System.out, args[2]);
      Util.printHelpMessage(System.out);
      System.exit(1);
    }

    predFileName = args[0];
    expFileName = args[1];
    semanticLevel = Integer.parseInt(args[2]);
    errFileName = (args.length > 3) ? args[3] : "errors_" + (new Date().getTime()) + ".txt";
    outFileName = (args.length > 4) ? args[4] : null;

    try {

      runScorer();

    } catch (FileNotFoundException ex) {
      ex.printStackTrace(System.out);
    } finally {
      if (predSc != null) {
        predSc.close();
      }
      if (expSc != null) {
        expSc.close();
      }
      if (err != null) {
        err.close();
      }
      if (out != null) {
        out.close();
      }
    }
  }

  /**
   * Reads from predicted and expected files and compares their Connective SpanList, Semantic Class,
   * Arg1 SpanList and Arg2 SpanList columns.
   * <p>
   * The result is printed to {@code outFileName} and errors are printed to {@code errFileName}.
   * 
   * @throws FileNotFoundException if predicted, expected or error file is not found
   */
  private static void runScorer() throws FileNotFoundException {


    predSc = new Scanner(new File(predFileName));
    expSc = new Scanner(new File(expFileName));
    out = (outFileName != null) ? new PrintStream(new File(outFileName)) : System.out;

    int total = 0;
    int correct = 0;
    int wrong = 0;

    while (predSc.hasNext()) {

      StringBuilder errTypes = new StringBuilder();

      String predLine = predSc.nextLine();
      String expLine = expSc.nextLine();

      String[] predicted = predLine.split("\\|");
      String[] expected = expLine.split("\\|");

      String pType = predicted[TYPE_COL];
      String eType = expected[TYPE_COL];


      // get connectives span list
      boolean isConnCorrect = compareSpans(predicted, expected, CONN_COL);

      if (!isConnCorrect) {
        errTypes.append("Connective, ");
      }

      // compare semantic classes only if connectives are correct
      if (isConnCorrect) {

        // no semantic class for EntRel relations
        if (!("EntRel".equals(pType) || "EntRel".equals(eType))) {

          String[] pSemClass = getSemanticClasses(predicted, semanticLevel);
          String[] eSemClass = getSemanticClasses(expected, semanticLevel);

          // the arrays can be empty if there is no semantic class at the current level (2 or 3)
          boolean arraysAreEmpty = Util.isEmpty(pSemClass) && Util.isEmpty(pSemClass);

          if (!arraysAreEmpty) {

            boolean isSemanticCorrect = Util.arraysIntersect(pSemClass, eSemClass);

            if (!isSemanticCorrect) {
              errTypes.append("Semantic Class, ");
            }
          }
        }
      }

      // check Arg1 span list
      boolean isArg1Correct = compareSpans(predicted, expected, ARG1_COL);

      if (!isArg1Correct) {
        errTypes.append("Arg1, ");
      }

      // check Arg2 span List
      boolean isArg2Correct = compareSpans(predicted, expected, ARG2_COL);

      if (!isArg2Correct) {
        errTypes.append("Arg2, ");
      }

      // print out errors, if any
      if (errTypes.length() > 0) {
        if (err == null) {
          err = new PrintWriter(new File(errFileName));
        }
        String types = errTypes.substring(0, errTypes.length() - 2);
        err.println(total + " " + types);
        err.println(predLine);
        err.println(expLine);

        ++wrong;
      } else {
        ++correct;
      }
      ++total;
    }

    out.println("Correct: " + correct);
    out.println("Wrong: " + wrong);
    out.println("Total: " + total);
    out.println("Accuracy: " + String.format("%.2f", 100.0 * correct / total) + "%");
  }

  /**
   * Compares two SpanList strings at the specified column index.
   * 
   * @param predicted the predicted string
   * @param expected the expected string
   * @param columnIndex the column index of the spans used for comparison
   * @return {@code true} if the SpanList are equal
   */
  private static boolean compareSpans(String[] predicted, String[] expected, int columnIndex) {
    int[] pSpan = getSpan(predicted[3]);
    int[] eSpan = getSpan(expected[3]);
    boolean areSame = Arrays.equals(pSpan, eSpan);

    return areSame;
  }

  /**
   * Extracts the semantic class of the relation from the {@code line} at {@code level} semantic
   * level. It checks columns 11, 12, 13 and 14 from the annotation line.
   * 
   * @param line the whole annotation line
   * @param level the semantic level
   * @return a four element String array.
   */
  private static String[] getSemanticClasses(String[] line, int level) {

    String[] semanticClasses = new String[4];
    int start = SEM_CLASS_START_COL;
    int end = start + 4;

    if (line != null && line.length > end) {
      for (int i = start; i < end; ++i) {
        String elem = line[i];
        if (elem != null && elem.length() > 0) {
          String[] semClass = elem.split("\\.");
          if (semClass.length >= level) {
            semanticClasses[i - start] = semClass[level - 1];
          }
        }
      }
    }

    return semanticClasses;
  }

  /**
   * Transforms a span list string with format startCharacterIndex..endCharacterIndex to an int
   * array {@code [startCharacterIndex, endCharacterIndex]} or to an empty int array {@code [0, 0]}
   * if {@code column} is an empty or invalid string.
   * 
   * @param column the span list string
   * @return an int array with two elements.
   */
  private static int[] getSpan(String column) {

    int start = 0;
    int end = 0;

    try {
      if (column != null) {
        String[] tmp = column.split("\\.\\.");
        if (tmp.length > 1) {
          start = Integer.parseInt(tmp[0]);
          end = Integer.parseInt(tmp[1]);
        }
      }
    } catch (NumberFormatException ex) {
      // empty or invalid span list so just return an empty array.
    }

    return new int[] {start, end};
  }
}
