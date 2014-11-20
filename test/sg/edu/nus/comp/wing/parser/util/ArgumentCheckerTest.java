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

import sg.edu.nus.comp.wing.parser.util.ArgumentChecker;

/**
 * 
 * @author ilija.ilievski@u.nus.edu
 *
 */
public class ArgumentCheckerTest extends TestCase {

  private static final String MOCK_STRING = "mock";

  /**
   * Test method for {@link sg.edu.nus.comp.wing.parser.util.ArgumentChecker#checkRelType(String)}.
   * <p>
   * Should throw an exception.
   */
  @Test
  public final void testCheckRelType_failure() {
    String type = "mock";

    try {
      ArgumentChecker.checkRelType(type);
      fail("No exception thrown");
    } catch (IllegalArgumentException ex) {
    }

  }

  /**
   * Test method for {@link sg.edu.nus.comp.wing.parser.util.ArgumentChecker#checkRelType(String)}.
   */
  @Test
  public final void testCheckRelType_success() {

    boolean result = ArgumentChecker.checkRelType("Explicit");
    assertTrue(result);

  }

  /**
   * Test method for {@link sg.edu.nus.comp.wing.parser.util.ArgumentChecker#checkNotNull(Object)}.
   * <p>
   * Should throw an exception.
   */
  @Test
  public final void testCheckNotNull_failure() {
    try {
      ArgumentChecker.checkNotNull(null);
      fail("No exception thrown");
    } catch (NullPointerException expected) {
    }
  }

  /**
   * Test method for {@link sg.edu.nus.comp.wing.parser.util.ArgumentChecker#checkNotNull(Object)}.
   */
  @Test
  public final void testCheckNotNull_success() {

    boolean result = ArgumentChecker.checkNotNull(MOCK_STRING);
    assertTrue(result);
  }

  /**
   * Test method for
   * {@link sg.edu.nus.comp.wing.parser.util.ArgumentChecker#checkTrue(boolean, String)}.
   * <p>
   * Should throw an exception.
   */
  @Test
  public final void testCheckTrue_failure() {
    try {
      ArgumentChecker.checkTrue(false, MOCK_STRING);
      fail("No exception thrown");
    } catch (IllegalArgumentException e) {
      assertEquals(MOCK_STRING, e.getMessage());
    }
  }

  /**
   * Test method for
   * {@link sg.edu.nus.comp.wing.parser.util.ArgumentChecker#checkTrue(boolean, String)}.
   */
  @Test
  public final void testCheckTrue_success() {

    boolean result = ArgumentChecker.checkTrue(true, MOCK_STRING);
    assertTrue(result);
  }

  /**
   * Test method for
   * {@link sg.edu.nus.comp.wing.parser.util.ArgumentChecker#checkIsNumber(String, String)}.
   * <p>
   * Should throw an exception.
   */
  @Test
  public final void testCheckIsNumber_failure() {
    String notNumber = "";
    try {
      ArgumentChecker.checkIsNumber(notNumber, MOCK_STRING);
      fail("No exception thrown");
    } catch (IllegalArgumentException e) {
      String expected = "For " + MOCK_STRING + ", it was: " + notNumber;
      assertEquals(expected, e.getMessage());
    }
  }

  /**
   * Test method for
   * {@link sg.edu.nus.comp.wing.parser.util.ArgumentChecker.ArgumentChecker#checkIsNumber(String, String)}
   * .
   */
  @Test
  public final void testCheckIsNumber_success() {

    boolean result = ArgumentChecker.checkIsNumber("3", MOCK_STRING);
    assertTrue(result);
  }

}
