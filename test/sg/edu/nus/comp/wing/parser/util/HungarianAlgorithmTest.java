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

import junit.framework.TestCase;

import org.junit.Test;

/**
 * 
 * @author ilija.ilievski@u.nus.edu
 *
 */
public class HungarianAlgorithmTest extends TestCase {

  @Test
  public void testOverallAlgorithm_case1() {
    int[][] matrix = { {3, 0, 3}, {1, 0, 3}, {0, 0, 1}};
    HungarianAlgorithm obj = new HungarianAlgorithm(matrix);
    int[] assigment = obj.execute();
    int sum = 0;
    for (int i = 0; i < assigment.length; ++i) {
      sum += matrix[i][assigment[i]];
    }

    int expected = 2;
    int actual = sum;

    assertEquals(expected, actual);
  }

  @Test
  public void testOverallAlgorithm_case2() {
    int[][] matrix = { {0, 0, 1}, {3, 0, 3}, {1, 0, 3}};
    HungarianAlgorithm obj = new HungarianAlgorithm(matrix);
    int[] assigment = obj.execute();
    int sum = 0;
    for (int i = 0; i < assigment.length; ++i) {
      sum += matrix[i][assigment[i]];
    }

    int expected = 2;
    int actual = sum;

    assertEquals(expected, actual);
  }
}
