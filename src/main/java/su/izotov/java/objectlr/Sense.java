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

import java.lang.reflect.InvocationTargetException;
import java.util.logging.Logger;
import su.izotov.java.ddispatch.methods.MethodAmbiguouslyDefinedException;
import su.izotov.java.objectlr.print.Printable;
import su.izotov.java.objectlr.print.CellOf;
import su.izotov.java.objectlr.print.Cell;
import su.izotov.java.objectlr.token.Absence;
import su.izotov.java.objectlr.token.Extracted;
import su.izotov.java.objectlr.token.Failed;
import su.izotov.java.objectlr.token.Text;
import su.izotov.java.objectlr.token.Unrecognized;
import su.izotov.java.objectlr.tokens.Tokens;

/**
 * the recognized sense object
 * @author Vladimir Izotov
 */
public interface Sense
    extends Printable {
  /**
   * tokens of the language understood by this object
   * @return tokens
   */
  Tokens tokens();

  /**
   * Unrecognized text must be wrapped in a special class for further work with it. This method
   * should return an object of this class - a special "token" of the language that wraps the text.
   * @param text unrecognized text
   * @return the wrapped text
   */
  Sense textToken(String text);

  /**
   * interaction of senses
   * @param sense the second object
   * @return The result of interaction
   */
  default Sense concatDD(final Sense sense) {
    try {
      return new Concat(this, sense, Chain::new).invoke();
    } catch (final InvocationTargetException e) {
      throw new RuntimeException(e.getCause());
    } catch (final IllegalAccessException | MethodAmbiguouslyDefinedException e) {
      throw new RuntimeException(e);
    }
  }

  default Sense concat(final Absence absence) {
    return this;
  }

  default Sense concat(final Unrecognized text) {
    Extracted restPart = text;
    Cell log = new CellOf("------ Start recognition");
    log = log.addBottom(text.toVisual());
    log = log.addBottom("------------------------------------------------");
    Sense result = this;
    while (restPart.length() != 0) {
      // recognized element
      final Extracted leftMostParsed = result.tokens().leftMostParsed(restPart.toSource());
      // the text before recognized element
      final String precedingString = leftMostParsed.precedingIn(restPart);
      final Sense precedingText;
      if (precedingString.isEmpty()) {
        precedingText = new Absence();
      } else {
        precedingText = result.textToken(precedingString);
      }
      restPart = leftMostParsed.followingIn(restPart);
      log = log.addBottom(result.toVisual()
                                .addRight(" | ")
                                .addRight(precedingText.toVisual())
                                .addRight(" | ")
                                .addRight(leftMostParsed.toVisual())
                                .addRight(" | ")
                                .addRight(restPart.toVisual()));
      log = log.addBottom("------------------------------------------------");
      result = result.concatDD(precedingText);
      log = log.addBottom(result.toVisual()
                                .addRight(" | ")
                                .addRight(leftMostParsed.toVisual())
                                .addRight(" | ")
                                .addRight(restPart.toVisual()));
      log = log.addBottom("------------------------------------------------");
      result = result.concatDD(leftMostParsed);
      log = log.addBottom(result.toVisual().addRight(" | ").addRight(restPart.toVisual()));
      log = log.addBottom("------------------------------------------------");
    }
    log = log.addBottom(result.toVisual()).addBottom(new CellOf("------ End recognition"));
    final Cell finalLog = log;
    Logger.getGlobal().info(finalLog::toString);
    return result;
  }

  default Sense concat(final Failed failed) {
    Sense first = this.concatDD(textToken(failed.toSource()));
    return first.concatDD(new Text(failed.followingSource()));
  }
}
