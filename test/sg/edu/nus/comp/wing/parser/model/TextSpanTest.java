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

package sg.edu.nus.comp.wing.parser.model;

import junit.framework.TestCase;

import org.junit.Test;

import sg.edu.nus.comp.wing.parser.model.TextSpan;

/**
 * 
 * @author ilija.ilievski@u.nus.edu
 *
 */
public class TextSpanTest extends TestCase {

  @Test
  public final void testMatch_endDifferes() {
    TextSpan textSpan = new TextSpan("Bla.", "1..5", "1,1");
    TextSpan other = new TextSpan("Bla", "1..4", "2,2");

    assertTrue(textSpan.match(other) && other.match(textSpan));
  }

  @Test
  public final void testMatch_startDifferes() {
    TextSpan textSpan = new TextSpan("Bla", "2..5", "1,1");
    TextSpan other = new TextSpan("!Bla", "1..5", "2,2");

    assertTrue(textSpan.match(other) && other.match(textSpan));
  }

  @Test
  public final void testMatch_noDifference() {
    TextSpan textSpan = new TextSpan("Bla", "1..4", "1,1");
    TextSpan other = new TextSpan("Bla", "1..4", "2,2");

    assertTrue(textSpan.match(other) && other.match(textSpan));
  }
}
