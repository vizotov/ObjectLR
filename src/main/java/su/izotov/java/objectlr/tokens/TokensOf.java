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
package su.izotov.java.objectlr.tokens;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import org.cactoos.collection.Mapped;
import org.cactoos.scalar.Or;
import org.cactoos.scalar.UncheckedScalar;
import su.izotov.java.objectlr.token.Absence;
import su.izotov.java.objectlr.token.Extracted;
import su.izotov.java.objectlr.token.Token;

/**
 * set of tokens
 * @author Vladimir Izotov
 */
public class TokensOf
    implements Tokens {
  private final Set<Tokens> tokens = new HashSet<>(10);

  public TokensOf(final Tokens... tokens) {
    this(Arrays.asList(tokens));
  }

  public TokensOf(final Collection<Tokens> tokens) {
    this.tokens.addAll(tokens);
  }

  @Override public final Extracted leftMostParsed(
      final String text) {
    if (this.tokens.isEmpty()) {
      return new Absence();
    }
    final Iterator<Tokens> iterator = this.tokens.iterator();
    Extracted ret = iterator.next().leftMostParsed(text);
    while (iterator.hasNext()) {
      ret = ret.leftMost(iterator.next().leftMostParsed(text), text);
    }
    return ret;
  }

  @Override public Tokens exclude(final Tokens tokens) {
    return new TokensOf(new Mapped<>(tokens1 -> tokens1.exclude(tokens), this.tokens));
  }

  @Override public boolean contains(final Token token) {
    return new UncheckedScalar<>(new Or(new Mapped<Tokens, Boolean>(tokens1 -> tokens1.contains(
        token), this.tokens))).value();
  }
}
