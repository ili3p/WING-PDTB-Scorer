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
package sg.edu.nus.comp.wing.parser;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import junit.framework.TestCase;
import org.junit.Test;
import sg.edu.nus.comp.wing.parser.model.Relation;
import sg.edu.nus.comp.wing.parser.model.Type;
import sg.edu.nus.comp.wing.parser.util.Util;

/**
 * @author ilija.ilievski@u.nus.edu
 *
 */
public class PdtbScorerTest extends TestCase {

  /**
   * 
   * The corpus annotates 40600 discourse relations, distributed into the following five types:
   * 
   * <pre>
   * 18459 Explicit Relations
   * 16053 Implicit Relations
   *   624 Alternative Lexicalizations
   *  5210 Entity Relations
   *   254 No Relations
   * </pre>
   * 
   * @throws IOException
   */
  @Test
  public final void testFilterByType_explicit() throws IOException {

    Map<Integer, List<Relation>> allRelations = Util.readAllTestRelations();
    Map<Integer, List<Relation>> filtered = Util.filterByType(Type.EXPLICIT, allRelations);

    int expected = 18459;
    int actual = Util.countRelations(filtered);

    assertEquals(expected, actual);
  }

  /**
   * 
   * The corpus annotates 40600 discourse relations, distributed into the following five types:
   * 
   * <pre>
   * 18459 Explicit Relations
   * 16053 Implicit Relations
   *   624 Alternative Lexicalizations
   *  5210 Entity Relations
   *   254 No Relations
   * </pre>
   * 
   * @throws IOException
   */
  @Test
  public final void testFilterByType_implicit() throws IOException {

    Map<Integer, List<Relation>> allRelations = Util.readAllTestRelations();
    Map<Integer, List<Relation>> filtered = Util.filterByType(Type.IMPLICIT, allRelations);

    int expected = 16053;
    int actual = Util.countRelations(filtered);

    assertEquals(expected, actual);
  }

  /**
   * 
   * The corpus annotates 40600 discourse relations, distributed into the following five types:
   * 
   * <pre>
   * 18459 Explicit Relations
   * 16053 Implicit Relations
   *   624 Alternative Lexicalizations
   *  5210 Entity Relations
   *   254 No Relations
   * </pre>
   * 
   * @throws IOException
   */
  @Test
  public final void testFilterBy_altLex() throws IOException {

    Map<Integer, List<Relation>> allRelations = Util.readAllTestRelations();
    Map<Integer, List<Relation>> filtered = Util.filterByType(Type.ALT_LEX, allRelations);

    int expected = 624;
    int actual = Util.countRelations(filtered);

    assertEquals(expected, actual);
  }

  /**
   * 
   * The corpus annotates 40600 discourse relations, distributed into the following five types:
   * 
   * <pre>
   * 18459 Explicit Relations
   * 16053 Implicit Relations
   *   624 Alternative Lexicalizations
   *  5210 Entity Relations
   *   254 No Relations
   * </pre>
   * 
   * @throws IOException
   */
  @Test
  public final void testFilterBy_entRel() throws IOException {

    Map<Integer, List<Relation>> allRelations = Util.readAllTestRelations();
    Map<Integer, List<Relation>> filtered = Util.filterByType(Type.ENT_REL, allRelations);

    int expected = 5210;
    int actual = Util.countRelations(filtered);

    assertEquals(expected, actual);
  }

  /**
   * 
   * The corpus annotates 40600 discourse relations, distributed into the following five types:
   * 
   * <pre>
   * 18459 Explicit Relations
   * 16053 Implicit Relations
   *   624 Alternative Lexicalizations
   *  5210 Entity Relations
   *   254 No Relations
   * </pre>
   * 
   * @throws IOException
   */
  @Test
  public final void testFilterBy_noRel() throws IOException {

    Map<Integer, List<Relation>> allRelations = Util.readAllTestRelations();
    Map<Integer, List<Relation>> filtered = Util.filterByType(Type.NO_REL, allRelations);

    int expected = 254;
    int actual = Util.countRelations(filtered);

    assertEquals(expected, actual);
  }

}
