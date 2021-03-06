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

import org.apache.commons.lang3.builder.EqualsBuilder;
import static org.junit.Assert.assertTrue;
import org.junit.Test;
import su.izotov.java.objectlr.MKFirstToken;
import su.izotov.java.objectlr.MKSecondToken;
import su.izotov.java.objectlr.MKThirdToken;

/**
 * Created with IntelliJ IDEA.
 * @author Vladimir Izotov
 * @version $Id$
 * @since 1.0
 */
public final class TokensOfTest {

  @Test
  public void testExclude() {
    Tokens instance = new TokensOf(new MKFirstToken(),
                                   new TokensOf(new MKSecondToken(),
                                                new MKThirdToken()));
    Tokens expResult = new TokensOf(new TokensOf(new MKSecondToken(),
                                                 new MKThirdToken()));
    Tokens result = instance.exclude(new MKFirstToken());
    assertTrue(EqualsBuilder.reflectionEquals(expResult,
                                              result,
                                              false,
                                              null,
                                              true));
  }

  @Test
  public void testExclude2() {
    Tokens instance = new TokensOf(new MKFirstToken(),
                                   new TokensOf(new MKSecondToken(),
                                                new MKThirdToken()));
    Tokens expResult = new TokensOf(new MKFirstToken(),
                                    new TokensOf(new MKThirdToken()));
    Tokens result = instance.exclude(new MKSecondToken());
    assertTrue(EqualsBuilder.reflectionEquals(expResult,
                                              result,
                                              false,
                                              null,
                                              true));
  }

  @Test
  public void testContains() {
    Tokens instance = new TokensOf(new MKFirstToken(),
                                   new TokensOf(new MKSecondToken(),
                                                new MKThirdToken()));
    assertTrue(instance.contains(new MKFirstToken()));
  }

  @Test
  public void testContains2() {
    Tokens instance = new TokensOf(new MKFirstToken(),
                                   new TokensOf(new MKSecondToken(),
                                                new MKThirdToken()));
    assertTrue(instance.contains(new MKSecondToken()));
  }

  @Test
  public void testContains3() {
    Tokens instance = new TokensOf(new MKFirstToken(),
                                   new TokensOf(new MKSecondToken()));
    assertTrue(!instance.contains(new MKThirdToken()));
  }
}