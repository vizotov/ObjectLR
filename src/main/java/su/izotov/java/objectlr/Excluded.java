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
import su.izotov.java.objectlr.token.Token;
import su.izotov.java.objectlr.tokens.Tokens;

/**
 * Do not use the specified token in the subsequent recognition of Source text. The
 * recognition occurs as if the following text was recognized by the given sense, but without the
 * use of this token.
 * Created with IntelliJ IDEA.
 * @author Vladimir Izotov
 * @version $Id$
 * @since 1.0
 */
public final class Excluded
    implements Sense {

  private final Token token;
  private final Sense sense;

  /**
   * @param token the excluded token
   * @param sense the recognizer
   */
  public Excluded(final Token token,
                  final Sense sense) {
    this.token = token;
    this.sense = sense;
  }

  @Override
  public final Tokens tokens() {
    return this.sense.tokens()
                     .exclude(this.token);
  }

  @Override
  public final Sense textToken(final String text) {
    return this.sense.textToken(text);
  }

  @Override
  public final String toSource() {
    return this.sense.toSource();
  }

  public final Sense concat(final Sense sense) {
    return this.sense.concatDD(sense);
  }

  @Override
  public Cell toVisual() {
    return sense.toVisual();
  }
}
