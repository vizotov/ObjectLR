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
import su.izotov.java.objectlr.tokens.Empty;
import su.izotov.java.objectlr.tokens.Tokens;

/**
 * text construction related to a language
 * @author Vladimir Izotov
 */
public interface Token
    extends Extracted,
            Tokens {

  /**
   * the leftmost parsed element, related to this token in the string
   * @param text the string
   * @return token
   */
  @Override
  default Extracted leftMostParsed(final String text) {
    final Extracted ret;
    if (text.contains(this.toSource())) {
      ret = this;
    }
    else {
      ret = this.incompleteTokenAtEndOf(text);
    }
    return ret;
  }

  /**
   * the incomplete token, related to this token, at end of the string or
   * empty token, if the string do not contains it
   * @param text the string
   * @return the ParsedElement
   */
  default Extracted incompleteTokenAtEndOf(final String text) {
    for (int i = this.toSource()
                     .length() - 1;
         i > 0;
         i--) {
      if (text.endsWith(this.toSource()
                            .substring(0,
                                       i))) {
        return new Incomplete(this,
                              i);
      }
    }
    return new Absence();
  }

  @Override
  default Tokens exclude(final Tokens tokens) {
    return tokens.contains(this) ?
           new Empty() :
           this;
  }

  @Override
  default boolean contains(final Token token) {
    return this.getClass()
               .equals(token.getClass()) && this.toSource()
                                                .equals(token.toSource());
  }

  @Override
  default Cell toVisual() {
    return Extracted.super.toVisual()
                          .addRight('\'' + this.toSource() + '\'');
  }

  @Override
  default String precedingIn(final Extracted text) {
    return text.precedingThe(this);
  }

  @Override
  default Extracted followingIn(final Extracted text) {
    return text.followingThe(this);
  }

  @Override
  default int length() {
    return this.toSource()
               .length();
  }

  /**
   * check if this token exists in the string
   * @param text the string
   * @return result
   */
  default boolean existsIn(final String text) {
    return this.firstPositionIn(text) != -1;
  }

  @Override
  default int firstPositionIn(final String text) {
    return text.indexOf(this.toSource());
  }

  /**
   * text after first occurrence of this token in the string
   * @param text the string
   * @return the text
   */
  default String afterFirstOccurrenceIn(final String text) {
    if (this.existsIn(text)) {
      return text.substring(this.firstPositionIn(text) + this.toSource()
                                                             .length());
    }
    else {
      return text;
    }
  }

  /**
   * by default recognizing Text is a failed recognition
   * @param text following text
   * @return failed
   */
  default Sense concat(final Text text) {
    return new Failed(this,
                      text.toSource());
  }
}
