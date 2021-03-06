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
import su.izotov.java.objectlr.text.Source;
import su.izotov.java.objectlr.text.Text;
import su.izotov.java.objectlr.token.Failed;
import su.izotov.java.objectlr.tokens.Tokens;

/**
 * the buffer contains the chain of senses
 * <p>Created with IntelliJ IDEA.</p>
 * @author Vladimir Izotov
 * @version $Id$
 * @since 1.0
 */
public final class Chain
    implements Buffer {

  private final Buffer parent;
  private final Sense current;

  Chain(final Sense sense1,
        final Sense sense2) {
    this(new One(sense1),
         sense2);
  }

  Chain(final Buffer parent,
        final Sense current) {
    if (current instanceof Buffer) {
      throw new RuntimeException("Can not use buffer inside the buffer!");
    }
    this.parent = parent;
    this.current = current;
  }

  @Override
  public final Sense concat(final Sense sense) throws
                                               Exception {
    final Sense res = this.current.concatDD(sense);
    if (res instanceof Buffer) {
      return new Chain(this,
                       sense);
    }
    else {
      return this.parent.concatDD(res);
    }
  }

  /**
   * re-recognizing following token as text
   * @param failed representation of the following token and following text
   * @return recognition result
   */
  public Sense concat(final Failed failed) {
    return new Chain(this.parent,
                     new Excluded(failed.token(),
                                  this.current)).concatDD(new Source(failed.toSource()));
  }

  @Override
  public final Cell toVisual() {
    return this.parent.toVisual()
                      .addBottom(this.current.toVisual());
  }

  @Override
  public final Tokens tokens() {
    return this.current.tokens();
  }

  @Override
  public final Sense textToken(final String text) {
    return this.current.textToken(text);
  }

  @Override
  public final String toSource() {
    return this.parent.toSource() + this.current.toSource();
  }
}
