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
public final class Horizontal
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
  public final String toString() {
    final String strLeft = this.left.toString();
    final String strRight = this.right.toString();
    String ret;
    if (strLeft.isEmpty()) {
      ret = strRight;
    }
    else if (strRight.isEmpty()) {
      ret = strLeft;
    }
    else {
      final String[] arr1 = strLeft.split("\n",
                                          -1);
      final String[] arr2 = strRight.split("\n",
                                           -1);
      int maximumLength = 0;
      for (final String s1 : arr1) {
        maximumLength = Integer.max(maximumLength,
                                    s1.length());
      }
      final int minBlockHigh = Integer.min(arr1.length,
                                           arr2.length);
      final StringBuilder sb = new StringBuilder(2000);
      while (sb.length() < maximumLength) {
        sb.append("                                                                                               ");
      }
      final String spaces = sb.toString();
      ret = "";
      for (int i = 0;
           i < minBlockHigh;
           i++) {
        if (i != 0 && i < minBlockHigh) {
          ret += "\n";
        }
        ret += (arr1[i] + spaces).substring(0,
                                            maximumLength) + ' ' + arr2[i];
      }
      if (minBlockHigh < arr2.length) {
        ret += "\n";
        for (int i1 = minBlockHigh;
             i1 < arr2.length;
             i1++) {
          if (i1 != minBlockHigh && i1 < arr2.length) {
            ret += "\n";
          }
          ret += spaces.substring(0,
                                  maximumLength) + ' ' + arr2[i1];
        }
      }
      else {
        for (int i2 = minBlockHigh;
             i2 < arr1.length;
             i2++) {
          if (i2 < arr1.length) {
            ret += "\n";
          }
          ret += arr1[i2];
        }
      }
    }
    return ret;
  }
}
