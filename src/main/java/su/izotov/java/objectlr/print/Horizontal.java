/*
 * The MIT License (MIT)
 *
 *  Copyright (c) 2018 Vladimir Izotov
 *
 *  Permission is hereby granted, free of charge, to any person obtaining a copy
 *  of this software and associated documentation files (the "Software"), to deal
 *  in the Software without restriction, including without limitation the rights
 *  to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 *  copies of the Software, and to permit persons to whom the Software is
 *  furnished to do so, subject to the following conditions:
 *
 *  The above copyright notice and this permission notice shall be included
 *  in all copies or substantial portions of the Software.
 *
 *  THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 *  IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 *  FITNESS FOR A PARTICULAR PURPOSE AND NON-INFRINGEMENT. IN NO EVENT SHALL THE
 *  AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 *  LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 *  OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 *  SOFTWARE.
 */
package su.izotov.java.objectlr.print;

/**
 * Two cells, concatenated left to right
 * @author Vladimir Izotov
 */
final class Horizontal
    implements Cell {

  private final Cell left;
  private final Cell right;

  public Horizontal(final String left,
                    final String right) {
    this(new CellOf(left),
         new CellOf(right));
  }

  public Horizontal(final Cell left,
                    final Cell right) {
    this.left = left;
    this.right = right;
  }

  @Override
  public String toSource() {
    final String leftCell = this.left.toSource();
    final String rightCell = this.right.toSource();
    String ret;
    if (leftCell.isEmpty()) {
      ret = rightCell;
    }
    else if (rightCell.isEmpty()) {
      ret = leftCell;
    }
    else {
      final String[] leftStrings = leftCell.split("\n",
                                          -1);
      final String[] rightStrings = rightCell.split("\n",
                                           -1);
      int maximumLength = 0;
      for (final String leftString : leftStrings) {
        maximumLength = Integer.max(maximumLength,
                                    leftString.length());
      }
      final int minCellHigh = Integer.min(leftStrings.length,
                                           rightStrings.length);
      final StringBuilder stringBuilder = new StringBuilder(2000);
      while (stringBuilder.length() < maximumLength) {
        stringBuilder.append("                                                                                               ");
      }
      final String spaces = stringBuilder.toString();
      ret = "";
      for (int leftStringNumber = 0;
           leftStringNumber < minCellHigh;
           leftStringNumber++) {
        if (leftStringNumber != 0 && leftStringNumber < minCellHigh) {
          ret += "\n";
        }
        ret += (leftStrings[leftStringNumber] + spaces).substring(0,
                                            maximumLength) + ' ' + rightStrings[leftStringNumber];
      }
      if (minCellHigh < rightStrings.length) {
        ret += "\n";
        for (int rightStringNumber = minCellHigh;
             rightStringNumber < rightStrings.length;
             rightStringNumber++) {
          if (rightStringNumber != minCellHigh && rightStringNumber < rightStrings.length) {
            ret += "\n";
          }
          ret += spaces.substring(0,
                                  maximumLength) + ' ' + rightStrings[rightStringNumber];
        }
      }
      else {
        for (int stringNumber = minCellHigh;
             stringNumber < leftStrings.length;
             stringNumber++) {
          if (stringNumber < leftStrings.length) {
            ret += "\n";
          }
          ret += leftStrings[stringNumber];
        }
      }
    }
    return ret;
  }
}
