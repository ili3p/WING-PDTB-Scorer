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

import static sg.edu.nus.comp.wing.parser.util.ArgumentChecker.checkIsNumber;
import static sg.edu.nus.comp.wing.parser.util.ArgumentChecker.checkNotNull;
import static sg.edu.nus.comp.wing.parser.util.ArgumentChecker.checkRelType;
import static sg.edu.nus.comp.wing.parser.util.ArgumentChecker.checkTrue;

import java.util.regex.Pattern;

/**
 * <pre>
 * Col 0: Relation type (Explicit/Implicit/AltLex/EntRel/NoRel)
 * Col 1: Section number (0-24)
 * Col 2: File number (0-99)
 * Col 7: Sentence number (only for Implicit, EntRel and NoRel)
 * </pre>
 * 
 * @author ilija.ilievski@u.nus.edu
 * @since 0.5
 */
public class Relation implements Comparable<Relation> {

  public static final Type[] types = {Type.ALT_LEX, Type.ENT_REL, Type.EXPLICIT, Type.IMPLICIT,
      Type.NO_REL};

  private static final Pattern SPLITTER = Pattern.compile("\\|");

  private String pipeLine;

  private Type type;

  /**
   * <p>
   * Range: [000-999]
   */
  private int section;

  /**
   * <p>
   * Range: [000-999]
   */
  private int fileNumber;

  /**
   * It's column 7 for Implicit, EntRel and NoRel relation types and Arg2 sentence number of first
   * tree for Explicit and AltLex.
   * <p>
   * Range: [0000-9999]
   * 
   */
  private int sentenceNumber;

  /**
   * It is <b>not</b> unique.
   * <p>
   * Check {@link Relation#generateKey()} to see how is calculated.
   */
  private int key;

  private Connective connective;
  private SemanticClass semanticClass;
  private Argument arg1;
  private Argument arg2;

  public Relation(String pipeLine) {

    checkNotNull(pipeLine);

    String[] columns = SPLITTER.split(pipeLine, -1);
    checkTrue(columns.length == 48, "Invalid columns length " + columns.length + ". " + pipeLine);

    this.pipeLine = pipeLine;

    checkRelType(columns[0]);
    this.type = Type.getType(columns[0]);

    checkIsNumber(columns[1], "section");
    this.section = Integer.parseInt(columns[1]);

    checkIsNumber(columns[2], "file number");
    this.fileNumber = Integer.parseInt(columns[2]);

    if (type.hasActualConnective()) {
      connective = new Connective(columns[5], columns[3], columns[4]);
    } else if (type.equals(Type.IMPLICIT)) {
      connective = new Connective(columns[9]);
    } else {
      connective = null;
    }

    semanticClass = new SemanticClass(columns[11], columns[12], columns[13], columns[14]);

    arg1 = new Argument(columns[24], columns[22], columns[23]);
    arg2 = new Argument(columns[34], columns[32], columns[33]);

    if (columns[7].isEmpty()) {

      checkTrue(type.hasActualConnective(),
          "Column 7 should be empty only for Explicit and AltLex.");
      this.sentenceNumber = arg2.getGornAddresses()[0][0];
    } else {
      checkIsNumber(columns[7], "sentence number");
      this.sentenceNumber = Integer.parseInt(columns[7]);
    }

    this.key = generateKey();
  }

  private int generateKey() {

    int textPos;
    if (type.hasActualConnective()) {
      textPos = connective.getSpanListRange()[0][0];
    } else {
      textPos = sentenceNumber;
    }

    long key = type.getId() * 1_00_00_0000 + section * 1_00_0000 + fileNumber * 1_0000 + textPos;

    checkTrue(key < Integer.MAX_VALUE, "Integer overflow for relation key: " + pipeLine);

    return (int) key;

  }

  @Override
  public int compareTo(Relation o) {
    return Integer.compare(this.key, o.key);
  }

  @Override
  public boolean equals(Object anObject) {
    if (this == anObject) {
      return true;
    }
    if (anObject instanceof Relation) {
      Relation anotherRelation = (Relation) anObject;
      return pipeLine.equals(anotherRelation.pipeLine);
    }
    return false;
  }

  @Override
  public String toString() {
    return pipeLine;
  }

  @Override
  public int hashCode() {
    return key;
  }

  public Type getType() {
    return type;
  }

  public int getSection() {
    return section;
  }

  public int getFileNumber() {
    return fileNumber;
  }

  public int getSentence() {
    return sentenceNumber;
  }

  public Connective getConnective() {
    return connective;
  }

  public SemanticClass getSemanticClass() {
    return semanticClass;
  }

  public Argument getArg1() {
    return arg1;
  }

  public Argument getArg2() {
    return arg2;
  }

  public String getPipeLine() {
    return pipeLine;
  }

  public int getKey() {
    return key;
  }
}
