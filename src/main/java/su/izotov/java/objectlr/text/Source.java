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
package su.izotov.java.objectlr.text;

import su.izotov.java.objectlr.Sense;
import su.izotov.java.objectlr.token.Token;

/**
 * Source text for the recognition
 * @author Vladimir Izotov
 */
public final class Source
    implements Sense {

  private final String text;

  public Source(final String text) {
    this.text = text;
  }

  @Override
  public Source concat(final Source source) {
    return new Source(this.text + source.toSource());
  }

  @Override
  public String toSource() {
    return this.text;
  }

  @Override
  public String toString() {
    return this.toSource();
  }

  /**
   * text, preceding the other text
   * @param text other text
   * @return preceding text
   */
  public String precedingThe(final String text) {
    if (this.toSource()
            .contains(text)) {
      return this.toSource()
                 .substring(0,
                            this.toSource()
                                .indexOf(text));
    } else {
      return "";
    }
  }

  /**
   * The text that follows the first occurrence of the token in the string
   * @param token the token
   * @return following text
   */
  public Sense followingThe(final Token token) {
    return token.followingIn(this);
  }

  /**
   * The text that follows the first occurrence of the other text in the string
   * @param text other text
   * @return following text
   */
  public Sense followingThe(final String text) {
    if (this.toSource()
            .contains(text)) {
      return new Source(this.toSource()
                            .substring(this.toSource()
                                           .indexOf(text) + text.length()));
    } else {
      return this;
    }
  }
}
