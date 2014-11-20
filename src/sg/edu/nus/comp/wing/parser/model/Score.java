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

import static sg.edu.nus.comp.wing.parser.util.Util.newLine;

/**
 * 
 * @author ilija.ilievski@u.nus.edu
 * @since 0.5
 */
public class Score {

  private boolean hasConnectives = false;
  private boolean hasSemanticClass = false;
  private int connectivesCorrect = 0;
  private int arg1Correct = 0;
  private int arg2Correct = 0;
  private int semanticClassCorrect = 0;
  private int expectedTotal = 0;
  private int predictedTotal = 0;

  @Override
  public String toString() {
    return printEvalMetrics();
  }

  private String printEvalMetrics() {
    StringBuilder sb = new StringBuilder();
    if (hasConnectives) {
      sb.append("Connective:" + newLine());
      sb.append("================================");
      sb.append(newLine());
      sb.append(printMetric(connectivesCorrect));
      sb.append(newLine());
    }
    if (hasSemanticClass) {
      sb.append("Semantic class:" + newLine());
      sb.append("================================");
      sb.append(newLine());
      sb.append(printMetric(semanticClassCorrect));
      sb.append(newLine());
    }
    sb.append("Arg1:" + newLine());
    sb.append("================================");
    sb.append(newLine());

    sb.append(printMetric(arg1Correct));
    sb.append(newLine());

    sb.append("Arg2:" + newLine());
    sb.append("================================");
    sb.append(newLine());
    sb.append(printMetric(arg2Correct));
    sb.append(newLine());

    return sb.toString();
  }

  private String printMetric(int metricCorrect) {

    double recall = (100.0 * metricCorrect / expectedTotal);
    double precision = (100.0 * metricCorrect / predictedTotal);
    double f1 = (2 * recall * precision) / (recall + precision);

    StringBuilder sb = new StringBuilder();
    sb.append("Predicted total: " + predictedTotal + newLine());
    sb.append(" Expected total: " + expectedTotal + newLine());
    sb.append("        Correct: " + metricCorrect + newLine());
    sb.append("      Incorrect: " + (predictedTotal - metricCorrect) + newLine());
    sb.append("         Recall: " + String.format("%.2f", recall) + "%" + newLine());
    sb.append("      Precision: " + String.format("%.2f", precision) + "%" + newLine());
    sb.append("             F1: " + String.format("%.2f", f1) + "%" + newLine());

    return sb.toString();
  }

  public Score(int expectedTotal, int predictedTotal) {
    this.expectedTotal = expectedTotal;
    this.predictedTotal = predictedTotal;
  }

  public void incSemanticClass() {
    if (!hasSemanticClass) {
      throw new IllegalStateException("The scorer should not count semantic class. ");
    }
    ++semanticClassCorrect;
  }

  public void incArg2() {
    ++arg2Correct;
  }

  public void incArg1() {
    ++arg1Correct;
  }

  public void incConnective() throws IllegalStateException {
    if (!hasConnectives) {
      throw new IllegalStateException("The scorer should not count connectives.");
    }
    ++connectivesCorrect;
  }

  public int getConnectivesCorrect() {
    return connectivesCorrect;
  }

  public int getArg1Correct() {
    return arg1Correct;
  }

  public int getArg2Correct() {
    return arg2Correct;
  }

  public int getSemanticClassCorrect() {
    return semanticClassCorrect;
  }

  public int getExpectedTotal() {
    return expectedTotal;
  }

  public int getPredictedTotal() {
    return predictedTotal;
  }

  public void setHasConnectives(boolean hasConnectives) {
    this.hasConnectives = hasConnectives;
  }

  public void setHasSemanticClass(boolean hasSemanticClass) {
    this.hasSemanticClass = hasSemanticClass;
  }
}
