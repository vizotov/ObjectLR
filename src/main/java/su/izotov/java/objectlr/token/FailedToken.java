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
import su.izotov.java.objectlr.tokens.EmptyTokensSet;
import su.izotov.java.objectlr.tokens.Tokens;

/**
 * If a token recognizes the following sequence of tokens as unsuitable for creating the desired
 * object, it may be necessary to recognize it not as a token, but as a text. For this purpose,
 * it should return as a result of its interaction with subsequent tokens an object of type
 * FailedToken containing both its text and the text following it. In this case, it will be
 * recognized as text, and recognition of the following text will continue.
 * Created with IntelliJ IDEA.
 * @author Vladimir Izotov
 * @version $Id$
 * @since 1.0
 */
public class FailedToken
    implements Token {
  private final String selfSource;
  private final String followingSource;

  /**
   * @param selfSource the source of token to become text
   * @param followingSource text to re-recognize
   */
  public FailedToken(final String selfSource, final String followingSource) {
    this.selfSource = selfSource;
    this.followingSource = followingSource;
  }

  @Override public Tokens tokens() {
    return new EmptyTokensSet();
  }

  @Override public Sense textToken(final String text) {
    return new Text(text);
  }

  @Override public String toSource() {
    return selfSource;
  }

  public String followingSource() {
    return followingSource;
  }
}
