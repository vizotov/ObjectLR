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

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.logging.Logger;
import org.cactoos.collection.CollectionOf;
import org.cactoos.collection.Filtered;
import org.cactoos.collection.Joined;
import org.cactoos.collection.Mapped;
import su.izotov.java.ddispatch.methods.MethodAmbiguouslyDefinedException;
import su.izotov.java.ddispatch.methods.ResultFunction;
import su.izotov.java.objectlr.print.Cell;
import su.izotov.java.objectlr.print.CellOf;
import su.izotov.java.objectlr.print.Printable;
import su.izotov.java.objectlr.print.Spaces;
import su.izotov.java.objectlr.text.Source;
import su.izotov.java.objectlr.text.Unrecognized;
import su.izotov.java.objectlr.token.Absence;
import su.izotov.java.objectlr.token.Failed;
import su.izotov.java.objectlr.token.Token;
import su.izotov.java.objectlr.tokens.Tokens;
import su.izotov.java.objectlr.tokens.TokensOf;

/**
 * the recognized sense object
 * @author Vladimir Izotov
 */
public interface Sense
    extends Printable {

  int[] logLevel = { 0 };
  Cell[] logBuffer = { new CellOf(""),
                       new CellOf("") };

  default Sense concat(final Source source) {
    if (source.toSource()
              .isEmpty()) {
      return this;
    }
    // recognized element
    final Token leftMostParsed = this.tokens()
                                     .leftMostParsed(source.toSource());
    // the text before recognized element
    final Sense precedingText = new Absence().concatDD(textToken(source.precedingThe(leftMostParsed))); // leftMostParsed.precedingIn(restPart);
    Sense restPart = new Absence().concatDD(leftMostParsed.followingIn(source));
    Sense one = this.concatDD(precedingText);
    Sense two = one.concatDD(leftMostParsed);
    Sense three = two.concatDD(restPart);
    return three;
  }

  default Sense concat(final Absence absence) {
    return this;
  }

  /**
   * Tokens of the language understood by this object. By default, the list of tokens is composed as follows:
   * <p> - the list of parameters classes of the 'concat' methods of this object, its superclasses
   * and interfaces is used as the source data.</p>
   * <p> - this list recursively adds the first parameter classes of their constructors that do
   * not implement the Token class.
   * </p>
   * <p> - only classes that have a constructor with the first parameter implementing the Token
   * interface are left in this list.</p>
   * <p> - if the first parameter of the constructor is of a String, the second parameter is used in the previous steps.</p>
   * <p> - The following is a list of the classes of these first parameters that implement the
   * Token interface.</p>
   * <p> - only classes that have a default constructor are left in this list.</p>
   * <p> - their instances are added to the list of understood tokens.</p>
   * @return tokens
   */
  default Tokens tokens() {
    final Collection<Method> allMethods = new CollectionOf<>(this.getClass()
                                                                 .getMethods());
    final Collection<Method> concatMethods = new Filtered<>(method -> "concat".equals(method.getName()) && method.getParameterTypes().length == 1,
                                                            allMethods);
    Collection<Class> tokenClasses = new LinkedList<>();
    Collection<Class> parameterClasses = new Mapped<>(method -> method.getParameterTypes()[0],
                                                      concatMethods);
    final Collection<Class> usedClasses = new HashSet<>();
    do {
      final Collection<Constructor> constructors = new Joined<>(new Mapped<>(clazz1 -> new CollectionOf<>(clazz1.getConstructors()),
                                                                             parameterClasses));
      Collection<Class> firstParamCandidates = new Mapped<>(constructor -> constructor.getParameterTypes()[0],
                                                            new Filtered<>(constructor1 -> constructor1.getParameterTypes().length > 0 && !String.class.isAssignableFrom(constructor1.getParameterTypes()[0]),
                                                                           constructors));
      Collection<Class> secondParamCandidates = new Mapped<>(constructor -> constructor.getParameterTypes()[1],
                                                             new Filtered<>(constructor1 -> constructor1.getParameterTypes().length > 1 && String.class.isAssignableFrom(constructor1.getParameterTypes()[0]),
                                                                            constructors));
      final Joined<Class> candidates = new Joined<Class>(firstParamCandidates,
                                                         secondParamCandidates);
      tokenClasses = new Joined<Class>(tokenClasses,
                                       new Filtered<>(clazz -> Token.class.isAssignableFrom(clazz),
                                                      candidates));
      parameterClasses = new Filtered<>(clazz -> !usedClasses.contains(clazz),
                                        new Filtered<>(clazz -> !Token.class.isAssignableFrom(clazz),
                                                       candidates));
      usedClasses.addAll(parameterClasses);
    } while (!parameterClasses.isEmpty())
        ;
    final Iterable<Iterable<Constructor>> tokenConstructorSets = new Mapped<>(clazz -> new CollectionOf<>(clazz.getConstructors()),
                                                                              tokenClasses);
    final Collection<Constructor> tokenConstructors = new Joined<>(tokenConstructorSets);
    final Collection<Constructor> tokenDefaultConstructors = new Filtered<>(constructor -> constructor.getParameterTypes().length == 0,
                                                                            tokenConstructors);
    final Collection<Tokens> tokens = new Mapped<>(constructor -> (Tokens) constructor.newInstance(),
                                                   tokenDefaultConstructors);
    return new TokensOf(tokens);
  }

  /**
   * Unrecognized text must be wrapped in a special class for further work with it. This method
   * should return an object of this class - a special "token" of the language that wraps the text.
   * @param text unrecognized text
   * @return the wrapped text
   */
  default Sense textToken(final String text) {
    return Unrecognized.create(text);
  }

  /**
   * interaction of senses
   * @param sense the second object
   * @return The result of interaction
   */
  default Sense concatDD(final Sense sense) {
    if (logLevel[0] == 0) {
      logBuffer[0] = new CellOf("");
    }
    logBottom(this.toVisual()
                  .addRight(" | ")
                  .addRight(sense.toVisual()));
    Sense ret;
    try {
      final ResultFunction<Sense, Sense, Sense> resultFunction = new Concat(this,
                                                                            sense,
                                                                            Chain::new).resultFunction();
      logRight(new CellOf(" - " + resultFunction.toString()));
      logLevel[0]++;
      ret = resultFunction.apply(this,
                                 sense);
    } catch (final MethodAmbiguouslyDefinedException e) {
      throw new RuntimeException(e);
    }
    logLevel[0]--;
    logBottom(new CellOf("VVVVV").addBottom(ret.toVisual())
                                 .addBottom(new CellOf("-----")));
    if (logLevel[0] == 0) {
      flushLog();
    }
    return ret;
  }

  static void logBottom(Cell content) {
    logBuffer[0] = logBuffer[0].addBottom(logBuffer[1]);
    logBuffer[1] = new Spaces(logLevel[0]).addRight(content);
  }

  static void logRight(Cell content) {
    logBuffer[1] = logBuffer[1].addRight(content);
  }

  static void flushLog() {
    logBuffer[0] = logBuffer[0].addBottom(logBuffer[1]);
    Logger.getGlobal()
          .info(logBuffer[0]::toString);
    logBuffer[0] = new CellOf("");
    logBuffer[1] = new CellOf("");
  }

  /**
   * re-recognizing following token as text
   * @param failed representation of the following token and following text
   * @return recognition result
   */
  default Sense concat(final Failed failed) {
    return new Excluded(failed.token(),
                        this).concatDD(new Source(failed.toSource()));
  }

  /**
   * @return Source text of this
   */
  String toSource();

  @Override
  default Cell toVisual() {
    return Printable.super.toVisual()
                          .addRight(toSource().length() == 0 ?
                                    "" :
                                    " \'" + toSource() + "\'");
  }
}
