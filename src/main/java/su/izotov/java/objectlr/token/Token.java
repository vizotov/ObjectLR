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
import su.izotov.java.objectlr.text.Source;
import su.izotov.java.objectlr.text.Text;
import su.izotov.java.objectlr.text.Unrecognized;
import su.izotov.java.objectlr.tokens.Empty;
import su.izotov.java.objectlr.tokens.Tokens;

/**
 * text construction related to a language
 * @author Vladimir Izotov
 */
public interface Token
    extends Text,
            Tokens {

  /**
   * the leftmost parsed element, related to this token in the string
   * @param text the string
   * @return token
   */
  @Override
  default Token leftMostIn(final String text) {
    final Token ret;
    if (text.contains(this.asString())) {
      ret = this;
    } else {
      ret = new Absence();
    }
    return ret;
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
               .equals(token.getClass()) && this.asString()
                                                .equals(token.asString());
  }

  /**
   * if this text is contained in the parameter, then
   * @param text parameter
   * @return text is preceding to first occurrence
   */
  default String precedingIn(final Source text) {
    return text.precedingThe(this.asString());
  }

  /**
   * @param text parameter
   * if this text is contained in the parameter, then
   * @return text is following to first occurrence
   */
  default Sense followingIn(final Source text) {
    return text.followingThe(this.asString());
  }

  default int length() {
    return this.asString()
               .length();
  }

  /**
   * first position of this element in the string
   * @param text parameter
   * @return position
   */
  default int firstPositionIn(final String text) {
    return text.indexOf(this.asString());
  }

  /**
   * by default recognizing Text is a failed recognition
   * @param unrecognized following text
   * @return failed
   */
  default Sense concat(final Unrecognized unrecognized) {
    return new Failed(this,
                      new Source(unrecognized.asString()));
  }

  /**
   * leftmost element in the string, either this, or parameter. if both
   * positions are equals, returns longer one. if neither element is exist,
   * then returns Empty token
   * @param parsed parameter
   * @param text where to search elements
   * @return left most element
   */
  default Token leftMost(final Token parsed,
                         final String text) {
    final int thisPosition = this.firstPositionIn(text);
    final int parsedPosition = parsed.firstPositionIn(text);
    if (thisPosition == -1 && parsedPosition == -1) {
      return new Absence();
    } else if (thisPosition == -1) {
      return parsed;
    } else if (parsedPosition == -1) {
      return this;
    } else if (thisPosition > parsedPosition) {
      return parsed;
    } else if (parsedPosition > thisPosition) {
      return this;
    } else if (this.length() > parsed.length()) {
      return this;
    } else {
      return parsed;
    }
  }
}
