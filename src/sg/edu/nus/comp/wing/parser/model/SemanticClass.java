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

import sg.edu.nus.comp.wing.parser.PdtbScorer;
import sg.edu.nus.comp.wing.parser.util.Util;

/**
 * <pre>
 * Col 11: 1st Semantic Class  corresponding to ConnHead, Conn1 or AltLex span (only for Explicit, Implicit and AltLex)
 * Col 12: 2nd Semantic Class  corresponding to ConnHead, Conn1 or AltLex span (only for Explicit, Implicit and AltLex)
 * Col 13: 1st Semantic Class corresponding to Conn2 (only for Implicit)
 * Col 14: 2nd Semantic Class corresponding to Conn2 (only for Implicit)
 * </pre>
 * 
 * @author ilija.ilievski@u.nus.edu
 * @since 0.5
 */
public class SemanticClass {

  private String semanticClass1;
  private String semanticClass2;
  private String semanticClass3;
  private String semanticClass4;

  private String[] nonEmptyClasses;

  public SemanticClass(String semanticClass1, String semanticClass2, String semanticClass3,
      String semanticClass4) {
    this.semanticClass1 = semanticClass1;
    this.semanticClass2 = semanticClass2;
    this.semanticClass3 = semanticClass3;
    this.semanticClass4 = semanticClass4;

    // to be lazy initialized
    this.nonEmptyClasses = null;
  }

  public String getSemanticClass1() {
    return semanticClass1;
  }

  public String getSemanticClass2() {
    return semanticClass2;
  }

  public String getSemanticClass3() {
    return semanticClass3;
  }

  public String getSemanticClass4() {
    return semanticClass4;
  }

  public String[] getNonEmptyClasses() {
    if (nonEmptyClasses == null) {
      nonEmptyClasses = Util.getNonEmptyClasses(this);
    }
    return nonEmptyClasses;
  }

  /**
   * At least one semantic class should match.
   * 
   * @param other
   * @return
   */
  public boolean match(SemanticClass other) {

    String[] thisSemantics = this.getNonEmptyClasses();
    String[] otherSemantics = other.getNonEmptyClasses();
    for (int i = 0; i < thisSemantics.length; ++i) {
      // TODO change how the semantic level is handled
      String thisSemantic = Util.extractSemantic(thisSemantics[i], PdtbScorer.semanticLevel);
      if (thisSemantic != null && thisSemantic.length() > 0) {
        for (int j = 0; j < otherSemantics.length; ++j) {
          String otherSemantic = Util.extractSemantic(otherSemantics[j], PdtbScorer.semanticLevel);
          if (otherSemantic != null && otherSemantic.length() > 0) {
            if (thisSemantic.equals(otherSemantic)) {
              return true;
            }
          }
        }
      }
    }

    return false;
  }

}
