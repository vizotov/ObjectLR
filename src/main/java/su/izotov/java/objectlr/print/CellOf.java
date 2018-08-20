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
 * The cell representation based on string
 * @author Vladimir Izotov
 */
public final class CellOf
    implements Cell {

  private final int margin;
  private final String text;

  /**
   * Constructor with no right margin restrictions
   * @param text the string
   */
  public CellOf(final String text) {
    this(text,
         0);
  }

  /**
   * @param text content of the cell
   * @param margin the margin to wrap lines
   */
  public CellOf(final String text,
                final int margin) {
    this.text = text;
    this.margin = margin;
  }

  @Override
  public final String toSource() {
    final String ret;
    if (this.margin == 0) {
      ret = this.text;
    }
    else {
      final StringBuilder builder = new StringBuilder(100);
      final String[] lines = this.text.split("\n",
                                             -1);
      for (final String line : lines) {
        String rest = line;
        while (true) {
          if (!(builder.length() == 0)) {
            builder.append("\n");
          }
          if (rest.length() > this.margin) {
            builder.append(rest.substring(0,
                                          this.margin));
            rest = rest.substring(this.margin);
          }
          else {
            builder.append(rest);
            break;
          }
        }
      }
      ret = builder.toString();
    }
    return ret;
  }
}
