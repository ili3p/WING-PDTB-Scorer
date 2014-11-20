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

import static org.junit.Assert.assertArrayEquals;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import junit.framework.TestCase;
import org.junit.Test;
import sg.edu.nus.comp.wing.parser.model.Relation;
import sg.edu.nus.comp.wing.parser.model.SemanticClass;
import sg.edu.nus.comp.wing.parser.model.Type;

/**
 * @author ilija.ilievski@u.nus.edu
 *
 */
public class UtilTest extends TestCase {

  /**
   * Test method for {@link sg.edu.nus.comp.wing.parser.util.Util#getNonEmptyClasses(SemanticClass)}
   * .
   */
  @Test
  public final void testGetNonEmptyClasses_nonEmpty() {

    SemanticClass mock = new SemanticClass("", null, "Expansion", "");
    String[] expected = {"Expansion"};
    String[] actual = Util.getNonEmptyClasses(mock);

    assertArrayEquals(expected, actual);
  }

  /**
   * Test method for {@link sg.edu.nus.comp.wing.parser.util.Util#getNonEmptyClasses(SemanticClass)}
   * .
   */
  @Test
  public final void testGetNonEmptyClasses_empty() {

    SemanticClass mock = new SemanticClass("", null, "", null);
    String[] expected = new String[0];
    String[] actual = Util.getNonEmptyClasses(mock);

    assertArrayEquals(expected, actual);
  }

  /**
   * Test method for {@link sg.edu.nus.comp.wing.parser.util.Util#readRelations(File)}.
   * 
   * <p>
   * Test if all PDTB relations are read.
   * 
   * @throws IOException
   */
  @Test
  public final void testReadRelations() throws IOException {

    int expected = 40600;

    Map<Integer, List<Relation>> relations = Util.readAllTestRelations();
    int actual = Util.countRelations(relations);;

    assertEquals(expected, actual);
  }

  /**
   * Test method for {@link sg.edu.nus.comp.wing.parser.util.Util#readRelations(File)}.
   * 
   * <p>
   * Test if all relations with same key have same type.
   * 
   * @throws IOException
   */
  @Test
  public final void testMatchingRelationTypes() throws IOException {

    boolean isOK = true;

    Map<Integer, List<Relation>> relations = new HashMap<>();
    for (int i = 0; i < 25; ++i) {
      File directory = new File("test_resources/expected/" + (i < 10 ? "0" : "") + i);
      relations.putAll(Util.readRelations(directory));
    }

    Iterator<List<Relation>> ite = relations.values().iterator();
    while (isOK && ite.hasNext()) {
      List<Relation> list = ite.next();
      Type type = list.get(0).getType();

      for (Relation relation : list) {
        isOK = type.equals(relation.getType());
        if (!isOK) {
          break;
        }
      }
    }

    assertTrue(isOK);
  }

  /**
   * Test method for {@link sg.edu.nus.comp.wing.parser.util.Util#getSpanListRange(String)}.
   * 
   * <p>
   * Testing only one range.
   */
  @Test
  public final void testGetSpanListRange_single() {
    String mock = "260..265";
    int[][] expected = {{260, 265}};
    int[][] actual = Util.getSpanListRange(mock);

    assertArrayEquals(expected, actual);
  }

  /**
   * Test method for {@link sg.edu.nus.comp.wing.parser.util.Util#getSpanListRange(String)}.
   * 
   * <p>
   * Testing multiple ranges.
   */
  @Test
  public final void testGetSpanListRange_multiple() {
    String mock = "1840..1882;1953..1977";
    int[][] expected = { {1840, 1882}, {1953, 1977}};
    int[][] actual = Util.getSpanListRange(mock);

    assertArrayEquals(expected, actual);
  }

  /**
   * Test method for {@link sg.edu.nus.comp.wing.parser.util.Util#getGornAddresses(String)}.
   * 
   * <p>
   * Testing only one range.
   */
  @Test
  public final void testGetGornAddresses_single() {
    String mock = "0,3,2,1,0";
    int[][] expected = {{0, 3, 2, 1, 0}};
    int[][] actual = Util.getGornAddresses(mock);

    assertArrayEquals(expected, actual);
  }

  /**
   * Test method for {@link sg.edu.nus.comp.wing.parser.util.Util#getGornAddresses(String)}.
   * 
   * <p>
   * Testing multiple ranges.
   */
  @Test
  public final void testGetGornAddresses_multiple() {
    String mock = "4,0;4,2";
    int[][] expected = { {4, 0}, {4, 2}};
    int[][] actual = Util.getGornAddresses(mock);

    assertArrayEquals(expected, actual);
  }

  /**
   * Test method for {@link sg.edu.nus.comp.wing.parser.util.Util#getPunctuationOffset(String)}.
   */
  @Test
  public final void testGetPunctuationOffset_change() {

    String text = ".Bla.The,";

    String expected = "Bla.The";

    int[] offset = Util.getPunctuationOffset(text);
    String actual = text.substring(offset[0], text.length() - offset[1]);

    assertEquals(expected, actual);
  }

  /**
   * Test method for {@link sg.edu.nus.comp.wing.parser.util.Util#getPunctuationOffset(String)}.
   */
  @Test
  public final void testGetPunctuationOffset_noChange() {

    String text = "Bla.The";

    String expected = "Bla.The";

    int[] offset = Util.getPunctuationOffset(text);
    String actual = text.substring(offset[0], text.length() - offset[1]);

    assertEquals(expected, actual);
  }

  @Test
  public final void testExtractSemantic_level1() {
    int level = 1;
    String text = "Temporal.Asynchronous.Precedence";
    String expected = "Temporal";
    String actual = Util.extractSemantic(text, level);

    assertEquals(expected, actual);
  }

  @Test
  public final void testExtractSemantic_level2() {
    int level = 2;
    String text = "Temporal.Asynchronous.Precedence";
    String expected = "Asynchronous";
    String actual = Util.extractSemantic(text, level);

    assertEquals(expected, actual);
  }

  @Test
  public final void testExtractSemantic_level3() {
    int level = 3;
    String text = "Temporal.Asynchronous.Precedence";
    String expected = "Precedence";
    String actual = Util.extractSemantic(text, level);

    assertEquals(expected, actual);
  }

}
