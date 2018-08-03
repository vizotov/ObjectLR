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

import java.util.logging.Logger;
import su.izotov.java.objectlr.token.Source;

/**
 * the sentence on the certain master, containing string representation of the recognized object
 * <p>Created with IntelliJ IDEA.</p>
 * @param <T> The type of the master object that will understand the sentence text
 * @param <R> the type of recognizable object
 * @author Vladimir Izotov
 * @version $Id$
 * @since 1.0
 */
public final class Sentence<T extends Sense, R extends Sense> {

  private final String text;
  private final T master;

  protected Sentence(final String text,
                     final T master) {
    this.text = text;
    this.master = master;
  }

  /**
   * recognize the text and convert it into Object
   * @return an object
   * @throws RecognitionException if recognition process fail
   */
  @SuppressWarnings("unchecked")
  public final R toObject() throws
                            RecognitionException {
    final Sense ret = this.master.concat(new Source(this.text));
    try {
      return (R) ret;
    } catch (final RuntimeException ignored) {
      Logger.getGlobal()
            .info("Unrecognizable text!");
      Logger.getGlobal()
            .info(ret.toVisual()
                     .toString());
      throw new RecognitionException(ret);
    }
  }
}
