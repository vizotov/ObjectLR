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
package su.izotov.java.objectlr;

import su.izotov.java.objectlr.print.Cell;
import su.izotov.java.objectlr.token.Absence;

/**
 * text sense, which do not need to recognize
 * @author Vladimir Izotov
 */
class MKTextSense
    implements MKLang {
  private final String text;

  public MKTextSense(String text) {
    this.text = text;
  }

  @Override public Cell toVisual() {
    return MKLang.super.toVisual().addRight(text);
  }

  public String toStr() {
    return text;
  }

  public Sense concat(MKTextSense other) {
    return new MKTextSense(text + other.toStr()).orEmpty();
  }

  /**
   * if this text is empty, returns empty sense
   */
  public Sense orEmpty() {
    return text.isEmpty() ? new Absence() : this;
  }

  public Sense parse(String text) {
    if (text.isEmpty()) {
      return new Absence();
    }
    return new MKTextSense(text);
  }
}
