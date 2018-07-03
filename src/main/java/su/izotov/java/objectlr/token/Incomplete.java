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
package su.izotov.java.objectlr.token;

import su.izotov.java.objectlr.Sense;
import su.izotov.java.objectlr.print.Cell;

/**
 * text, which corresponds to beginning of a token
 * @author Vladimir Izotov
 */
public final class Incomplete
    implements Extracted {
  private final Token token;
  private final int   length;

  public Incomplete(final Token token, final int length) {
    this.token = token;
    this.length = length;
  }

  public Sense concat(final Incomplete incomplete) {
    return new Unrecognized(this.toSource() + incomplete.toSource());
  }

  public Sense concat(final Token token) {
    return new Unrecognized(this.toSource() + token.toSource());
  }

  @Override public int firstPositionIn(final String text) {
    if (text.endsWith(this.toSource())) {
      return text.length() - this.length;
    } else {
      return -1;
    }
  }

  @Override public String toSource() {
    return this.token.toSource().substring(0, this.length);
  }

  @Override public String precedingIn(final Extracted text) {
    return text.precedingThe(this);
  }

  @Override public Extracted followingIn(final Extracted text) {
    return text.followingThe(this);
  }

  @Override public int length() {
    return this.length;
  }

  @Override public Cell toVisual() {
    return Extracted.super.toVisual().addRight(this.toSource());
  }

  @Override public Sense textToken(final String text) {
    return new Unrecognized(this.toSource() + text);
  }
}
