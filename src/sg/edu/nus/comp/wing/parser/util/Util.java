/**
 * Copyright (C) 2014 WING, NUS and NUS NLP Group.
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

package sg.edu.nus.comp.wing.parser.util;

import static sg.edu.nus.comp.wing.parser.util.ArgumentChecker.checkIsNumber;
import static sg.edu.nus.comp.wing.parser.util.ArgumentChecker.checkTrue;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import sg.edu.nus.comp.wing.parser.model.Relation;
import sg.edu.nus.comp.wing.parser.model.SemanticClass;
import sg.edu.nus.comp.wing.parser.model.Type;

/**
 * 
 * Created: 2014-11-11
 * 
 * @author ilija.ilievski@u.nus.edu
 * @since 0.1
 */
public class Util {

  private static final Pattern PUNCTUATION = Pattern.compile("[\\:\\;\\-\\_\\!\\?`'`',\\.]",
      Pattern.CASE_INSENSITIVE);

  private static final FilenameFilter filter = new FilenameFilter() {
    @Override
    public boolean accept(File dir, String name) {
      return name.endsWith(".pipe");
    }
  };

  // It's a static class so hide the default constructor.
  private Util() {}

  /**
   * Check if two arrays have at least one common element.
   * 
   * @param array1 first object array
   * @param array2 second object array
   * @return Returns {@code true} if the arrays have one or more common elements
   */
  public static boolean arraysIntersect(Object[] array1, Object[] array2) {

    boolean intersect = false;
    for (int i = 0; i < array1.length && !intersect; ++i) {

      Object e1 = array1[i];
      if (e1 != null) {
        for (int j = 0; j < array2.length && !intersect; ++j) {
          Object e2 = array2[j];
          intersect = e1.equals(e2);
        }
      }
    }

    return intersect;
  }

  /**
   * Check if array contains only {@code null} elements.
   * 
   * @param array an object array
   * @return Returns {@code true} if the array has <b>only</b> {@code null} elements.
   */
  public static boolean isEmpty(Object[] array) {
    boolean isEmpty = true;

    for (int i = 0; i < array.length && isEmpty; i++) {
      if (array[i] != null) {
        isEmpty = false;
      }
    }

    return isEmpty;
  }

  /**
   * Prints the message:
   * 
   * <pre>
   * "{@code in}"is invalid semantic level, only 1, 2 or 3 are allowed.
   * 
   * </pre>
   * 
   * @param out the {@code PrintStream} to print the message to
   * @param in the invalid level string
   */
  public static void printInvalidLevel(PrintStream out, String in) {
    out.println("\"" + in + "\" is invalid semantic level, only 1, 2 or 3 are allowed.");
  }

  /**
   * Prints the message:
   * 
   * <pre>
   * Usage: java -jar scorer.jar predictedFileName expectedFileName semanticLevel [errorFileName outputFileName]
   *      predictedFileName - path to the file being scored
   *      expectedFileName - path to the file containing the gold standard annotations
   *      semanticLevel - semantic type level used to evaluate the annotations (1, 2 or 3)
   * Optional:
   *      errorFileName - path to a file to print error messages to. The default value is "errors_[timestamp].txt"
   *      outputFileName - path to a file to print results  to. The default value is to print to console.
   * </pre>
   * 
   * @param out the {@code PrintStream} to print the message to
   */
  public static void printHelpMessage(PrintStream out) {

    out.print("Usage: java -jar scorer.jar predictedFileName expectedFileName");
    out.println(" semanticLevel [errorFileName outputFileName]");

    out.println("\t predictedFileName - path to the file being scored");

    out.print("\t expectedFileName - ");
    out.println("path to the file containing the gold standard annotations");

    out.print("\t semanticLevel - ");
    out.println("semantic type level used to evaluate the annotations (1, 2 or 3)");

    out.println("Optional:");

    out.print("\t errorFileName - ");
    out.print("path to a file to print error messages to.");
    out.println(" The default value is \"errors_[timestamp].txt\"");

    out.print("\t outputFileName - ");
    out.print("path to a file to print results  to.");
    out.println(" The default value is to print to console.");
  }

  public static int[][] getSpanListRange(String spanList) {

    String[] spans = spanList.split(";");
    int[][] spanListRange = new int[spans.length][2];

    for (int i = 0; i < spans.length; ++i) {
      String span = spans[i];
      String[] range = span.split("\\.{2}");

      checkIsNumber(range[0], "SpanList");
      spanListRange[i][0] = Integer.parseInt(range[0]);

      checkIsNumber(range[1], "SpanList");
      spanListRange[i][1] = Integer.parseInt(range[1]);
    }

    return spanListRange;
  }

  public static int[][] getGornAddresses(String gornAddressList) {

    String[] addresses = gornAddressList.split(";");
    int[][] gornAddresses = new int[addresses.length][];

    for (int i = 0; i < addresses.length; ++i) {
      String[] address = addresses[i].split(",");
      gornAddresses[i] = new int[address.length];
      for (int j = 0; j < address.length; ++j) {
        checkIsNumber(address[j], "GornAddress");
        gornAddresses[i][j] = Integer.parseInt(address[j]);
      }
    }

    return gornAddresses;
  }

  public static String[] getNonEmptyClasses(SemanticClass object) {

    String sem1 = object.getSemanticClass1();
    String sem2 = object.getSemanticClass2();
    String sem3 = object.getSemanticClass3();
    String sem4 = object.getSemanticClass4();

    List<String> temp = new ArrayList<>(4);

    if (sem1 != null && sem1.length() > 0) {
      temp.add(sem1);
    }
    if (sem2 != null && sem2.length() > 0) {
      temp.add(sem2);
    }
    if (sem3 != null && sem3.length() > 0) {
      temp.add(sem3);
    }
    if (sem4 != null && sem4.length() > 0) {
      temp.add(sem4);
    }

    return temp.toArray(new String[temp.size()]);
  }

  /**
   * It reads all .pipe files in the directory.
   * 
   * @param directory
   * @return
   * @throws IOException
   */
  public static Map<Integer, List<Relation>> readRelations(File directory) throws IOException {

    checkTrue(directory.isDirectory(), directory.getName() + " is not a directory.");
    File[] files = directory.listFiles(filter);
    Map<Integer, List<Relation>> relations = new HashMap<>();

    for (File file : files) {
      try (BufferedReader reader =
          new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF8"))) {

        String pipeLine;
        while ((pipeLine = reader.readLine()) != null) {

          Relation aRelation = new Relation(pipeLine);
          List<Relation> list = relations.get(aRelation.getKey());
          if (list == null) {
            list = new ArrayList<>();
          }
          list.add(aRelation);
          relations.put(aRelation.getKey(), list);
        }

      }
    }

    return relations;
  }

  /**
   * It returns the offset indices to remove leading and trailing punctuation symbols. To get the
   * trimmed string call {@code text.substring(offset[0], text.length() - offset[1])}.
   * <p>
   * It does <b>not</b> consider whitespace characters, so the text should be whitespace trimmed (
   * {@code text.trim()}) before calling this method.
   * 
   * <p>
   * Punctuation symbols considered:
   * 
   * <pre>
   * : ; - _ ! ? ` ' ` ' , .
   * </pre>
   * 
   * @param text a string to get offset indices for
   * @return an int array with two elements, the offset for the start and the end of the string
   *         {@code text}, {@code [start,end]}
   */
  public static int[] getPunctuationOffset(String text) {

    int meStartOffset = 0;
    int meEndOffset = 0;

    for (int i = 0; i < text.length() - 1; ++i) {
      CharSequence sequence = text.subSequence(i, i + 1);
      if (PUNCTUATION.matcher(sequence).matches()) {
        meStartOffset = i + 1;
      } else {
        break;
      }
    }

    for (int i = text.length(); i > 0; --i) {
      CharSequence sequence = text.subSequence(i - 1, i);
      if (PUNCTUATION.matcher(sequence).matches()) {
        meEndOffset = text.length() - i + 1;
      } else {
        break;
      }
    }

    return new int[] {meStartOffset, meEndOffset};
  }

  public static int[] offsetRange(int[][] range, int[] offset) {

    int start = range[0][0] + offset[0];
    int end = range[range.length - 1][1] - offset[1];

    return new int[] {start, end};
  }

  public static int getSizeForType(Type type, Map<Integer, List<Relation>> map) {

    int size = 0;

    for (List<Relation> relations : map.values()) {
      for (Relation relation : relations) {
        if (type.equals(relation.getType())) {
          ++size;
        }
      }
    }

    return size;
  }

  public static Map<Integer, List<Relation>> readAllTestRelations() throws IOException {

    Map<Integer, List<Relation>> rels = new HashMap<>();
    for (int i = 0; i < 25; ++i) {
      rels.putAll(readRelations(new File("test_resources/expected/" + (i < 10 ? "0" : "") + i)));
    }

    return rels;
  }

  public static int countRelations(Map<Integer, List<Relation>> map) {
    int count = 0;
    for (List<Relation> relations : map.values()) {
      count += relations.size();
    }
    return count;
  }

  public static Map<Integer, List<Relation>> filterByType(Type targetType,
      Map<Integer, List<Relation>> map) {

    Map<Integer, List<Relation>> filteredMap = new HashMap<>();

    for (List<Relation> rels : map.values()) {
      Integer key = rels.get(0).getKey();
      Type aType = rels.get(0).getType();
      if (aType.equals(targetType)) {
        filteredMap.put(key, rels);
      }
    }

    return filteredMap;
  }

  public static String newLine() {
    return System.getProperty("line.separator");
  }

  public static String extractSemantic(String semantic, int level) {
    String result = null;

    String[] semClass = semantic.split("\\.");

    if (semClass.length >= level) {
      result = semClass[level - 1];
    }

    return result;
  }

}
