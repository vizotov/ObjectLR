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
import su.izotov.java.objectlr.print.TextCell;
import su.izotov.java.objectlr.tokens.EmptyTokensSet;
import su.izotov.java.objectlr.tokens.Tokens;

/**
 * the string for recognition
 * @author Vladimir Izotov
 */
public final class Text
    implements Unrecognized {
  private final String text;

  public Text(final String text) {
    this.text = text;
  }

  @Override public TextCell toVisual() {
    return Unrecognized.super.toVisual().addRight(this.text);
  }

  @Override public String precedingIn(final Extracted text) {
    return text.precedingThe(this.text);
  }

  @Override public Extracted followingIn(final Extracted text) {
    return text.followingThe(this.text);
  }

  @Override public int length() {
    return this.text.length();
  }

  @Override public int firstPositionIn(final String text) {
    return text.indexOf(this.text);
  }

  @Override public String toSource() {
    return this.text;
  }

  @Override public Tokens tokens() {
    return new EmptyTokensSet();
  }

  @Override public Sense textEnvelope(final String text) {
    return new Text(this.text + text);
  }
}
