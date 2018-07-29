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
 * A cell containing the text for creating formatted text output
 * @author Vladimir Izotov
 */
public interface Cell {

  @Override
  String toString();

  /**
   * add new cell to bottom
   * @param text the string
   * @return big cell
   */
  default Cell addBottom(final String text) {
    return this.addBottom(new CellOf(text));
  }

  /**
   * add new cell to bottom
   * @param otherCell param
   * @return big cell
   */
  default Cell addBottom(final Cell otherCell) {
    return new Vertical(this,
                        otherCell);
  }

  /**
   * add new cell to right
   * @param text param
   * @return big cell
   */
  default Cell addRight(final String text) {
    return this.addRight(new CellOf(text));
  }

  /**
   * add new cell to right
   * @param otherCell param
   * @return big cell
   */
  default Cell addRight(final Cell otherCell) {
    return new Horizontal(this,
                          otherCell);
  }
}
