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

/**
 * a parsed part of the string
 * @author Vladimir Izotov
 */
public interface Extracted
    extends Sense {

  /**
   * if this text is contained in the parameter, then
   * @param text parameter
   * @return text is preceding to first occurrence
   */
  String precedingIn(Extracted text);

  /**
   * @param text parameter
   * if this text is contained in the parameter, then
   * @return text is following to first occurrence
   */
  Extracted followingIn(Extracted text);

  int length();

  /**
   * first position of this element in the string
   * @param text parameter
   * @return position
   */
  int firstPositionIn(String text);

  /**
   * leftmost element in the string, either this, or parameter. if both
   * positions are equals, returns shorter one. if neither element is exist,
   * then returns Empty token
   * @param parsed parameter
   * @param text where to search elements
   * @return left most element
   */
  default Extracted leftMost(final Extracted parsed,
                             final String text) {
    final int thisPosition = this.firstPositionIn(text);
    final int tokenPosition = parsed.firstPositionIn(text);
    if (thisPosition == -1 && tokenPosition == -1) {
      return new Absence();
    }
    else if (thisPosition == -1) {
      return parsed;
    }
    else if (tokenPosition == -1) {
      return this;
    }
    else if (thisPosition > tokenPosition) {
      return parsed;
    }
    else if (tokenPosition > thisPosition) {
      return this;
    }
    else if (this.length() > parsed.length()) {
      return this;
    }
    else {
      return parsed;
    }
  }

  /**
   * check if this element longer than other
   * @param parsed other element
   * @return result
   */
  default boolean longerThan(final Extracted parsed) {
    return parsed.lengthLessThan(this.length());
  }

  /**
   * check if length of this element is less than parameter value
   * @param length parameter
   * @return result
   */
  default boolean lengthLessThan(final int length) {
    return this.length() < length;
  }

  /**
   * text, preceding the other text
   * @param text other text
   * @return preceding text
   */
  default String precedingThe(final String text) {
    if (this.toSource()
            .contains(text)) {
      return this.toSource()
                 .substring(0,
                            this.toSource()
                                .indexOf(text));
    }
    else {
      return "";
    }
  }

  /**
   * The text that follows the first occurrence of the other text in the string
   * @param text other text
   * @return following text
   */
  default Extracted followingThe(final String text) {
    if (this.toSource()
            .contains(text)) {
      return new Unrecognized(this.toSource()
                                  .substring(this.toSource()
                                                 .indexOf(text) + text.length()));
    }
    else {
      return new Absence();
    }
  }

  /**
   * The text that follows the first occurrence of the token in the string
   * @param token the token
   * @return following text
   */
  default Extracted followingThe(final Token token) {
    final String ret = token.afterFirstOccurrenceIn(this.toSource());
    if (ret.isEmpty()) {
      return new Absence();
    }
    else {
      return new Unrecognized(ret);
    }
  }

  /**
   * The text that follows the incomplete token in the string
   * @param incomplete token
   * @return text
   */
  default Extracted followingThe(final Incomplete incomplete) {
    if (this.toSource()
            .endsWith(incomplete.toSource())) {
      return new Absence();
    }
    else {
      return this;
    }
  }

  /**
   * The text preceding the incomplete token in the string
   * @param incomplete token
   * @return text
   */
  default String precedingThe(final Incomplete incomplete) {
    if (this.toSource()
            .endsWith(incomplete.toSource())) {
      return this.toSource()
                 .substring(0,
                            this.toSource()
                                .length() - incomplete.length());
    }
    else {
      return "";
    }
  }

  /**
   * The text preceding the first occurrence of the token in the string
   * @param token the token
   * @return the text
   */
  default String precedingThe(final Token token) {
    return this.precedingThe(token.toSource());
  }
}
