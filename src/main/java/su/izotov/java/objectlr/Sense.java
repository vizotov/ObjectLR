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
import java.lang.reflect.InvocationTargetException;
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
import su.izotov.java.objectlr.print.Cell;
import su.izotov.java.objectlr.print.CellOf;
import su.izotov.java.objectlr.print.Printable;
import su.izotov.java.objectlr.token.Absence;
import su.izotov.java.objectlr.token.Extracted;
import su.izotov.java.objectlr.token.Failed;
import su.izotov.java.objectlr.token.Text;
import su.izotov.java.objectlr.token.Token;
import su.izotov.java.objectlr.token.Unrecognized;
import su.izotov.java.objectlr.tokens.Tokens;
import su.izotov.java.objectlr.tokens.TokensOf;

/**
 * the recognized sense object
 * @author Vladimir Izotov
 */
public interface Sense
    extends Printable {

  default Sense concat(final Unrecognized unrecognized) {
    Extracted restPart = unrecognized;
    Cell log = new CellOf("------ Start recognition");
    log = log.addBottom(unrecognized.toVisual());
    log = log.addBottom("------------------------------------------------");
    Sense result = this;
    while (restPart.length() != 0) {
      // recognized element
      final Extracted leftMostParsed = result.tokens()
                                             .leftMostParsed(restPart.toSource());
      // the text before recognized element
      final String precedingString = leftMostParsed.precedingIn(restPart);
      final Sense precedingText;
      if (precedingString.isEmpty()) {
        precedingText = new Absence();
      }
      else {
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
      log = log.addBottom(result.toVisual()
                                .addRight(" | ")
                                .addRight(restPart.toVisual()));
      log = log.addBottom("------------------------------------------------");
    }
    log = log.addBottom(result.toVisual())
             .addBottom(new CellOf("------ End recognition"));
    final Cell finalLog = log;
    Logger.getGlobal()
          .info(finalLog::toString);
    return result;
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
      tokenClasses = new Joined<>(tokenClasses,
                                  new Filtered<>(clazz -> Token.class.isAssignableFrom(clazz),
                                                 new Joined<>(firstParamCandidates,
                                                              secondParamCandidates)));
      parameterClasses = new Filtered<>(clazz -> !usedClasses.contains(clazz),
                                        new Filtered<>(clazz -> !Token.class.isAssignableFrom(clazz),
                                                       new Joined<>(firstParamCandidates,
                                                                    secondParamCandidates)));
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
    return new Text(text);
  }

  default Sense concat(final Absence absence) {
    return this;
  }

  /**
   * interaction of senses
   * @param sense the second object
   * @return The result of interaction
   */
  default Sense concatDD(final Sense sense) {
    try {
      return new Concat(this,
                        sense,
                        Chain::new).invoke();
    } catch (final InvocationTargetException e) {
      throw new RuntimeException(e.getCause());
    } catch (final IllegalAccessException | MethodAmbiguouslyDefinedException e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * re-recognizing following token as text
   * @param failed representation of the following token and following text
   * @return recognition result
   */
  default Sense concat(final Failed failed) {
    return new Excluded(failed.token(),
                        this).concatDD(new Unrecognized(failed.toSource()));
  }

  /**
   * @return Source text of this
   */
  String toSource();
}
