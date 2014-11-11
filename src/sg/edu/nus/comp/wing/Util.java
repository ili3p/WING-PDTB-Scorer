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

import java.io.PrintStream;

/**
 * 
 * Created: 2014-11-11
 * 
 * @author ilija.ilievski@u.nus.edu
 * @since 0.1
 */
public class Util {

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

}
