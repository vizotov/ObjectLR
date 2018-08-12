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

import org.apache.commons.lang3.builder.EqualsBuilder;
import static org.junit.Assert.assertTrue;
import org.junit.Test;
import su.izotov.java.objectlr.MKFirstToken;
import su.izotov.java.objectlr.MKSecondToken;
import su.izotov.java.objectlr.MKThirdToken;

/**
 * @author Vladimir Izotov
 */
public final class TokenIT {
  @Test public void testTextAfterFirstOccupence() {
    String text = "before second after second";
    Token instance = new MKSecondToken();
    String expResult = " after second";
    String result = instance.afterFirstOccurrenceIn(text);
    assertTrue(EqualsBuilder.reflectionEquals(expResult, result, false, null, true));
  }

  @Test public void testTextAfterFirstOccupence2() {
    String text = "before second after second";
    Token instance = new MKFirstToken();
    String expResult = text;
    String result = instance.afterFirstOccurrenceIn(text);
    assertTrue(EqualsBuilder.reflectionEquals(expResult, result, false, null, true));
  }

  @Test public void testLeftMostParsed() {
    String text = "the text with second token";
    Token instance = new MKSecondToken();
    Token expResult = new MKSecondToken();
    Token result = instance.leftMostParsed(text);
    assertTrue(EqualsBuilder.reflectionEquals(expResult, result, false, null, true));
  }

  @Test public void testLeftMostParsed3() {
    String text = "the text without token";
    Token instance = new MKSecondToken();
    Token expResult = new Absence();
    Token result = instance.leftMostParsed(text);
    assertTrue(EqualsBuilder.reflectionEquals(expResult, result, false, null, true));
  }

  @Test
  public void testLeftMost() {
    Token token = new MKFirstToken();
    String text = " no tokens exists";
    Token instance = new MKSecondToken();
    Token expResult = new Absence();
    Token result = instance.leftMost(token,
                                     text);
    assertTrue(EqualsBuilder.reflectionEquals(expResult,
                                              result,
                                              false,
                                              null,
                                              true));
  }

  @Test
  public void testLeftMost2() {
    Token token = new MKFirstToken();
    String text = " exists only second";
    Token instance = new MKSecondToken();
    Token expResult = new MKSecondToken();
    Token result = instance.leftMost(token,
                                     text);
    assertTrue(EqualsBuilder.reflectionEquals(expResult,
                                              result,
                                              false,
                                              null,
                                              true));
  }

  @Test
  public void testLeftMost3() {
    Token token = new MKFirstToken();
    String text = " exists only first";
    Token instance = new MKSecondToken();
    Token expResult = new MKFirstToken();
    Token result = instance.leftMost(token,
                                     text);
    assertTrue(EqualsBuilder.reflectionEquals(expResult,
                                              result,
                                              false,
                                              null,
                                              true));
  }

  @Test
  public void testLeftMost4() {
    Token token = new MKFirstToken();
    String text = " exists only first and second";
    Token instance = new MKSecondToken();
    Token expResult = new MKFirstToken();
    Token result = instance.leftMost(token,
                                     text);
    assertTrue(EqualsBuilder.reflectionEquals(expResult,
                                              result,
                                              false,
                                              null,
                                              true));
  }

  @Test
  public void testLeftMost5() {
    Token token = new MKFirstToken();
    String text = " exists only second and first";
    Token instance = new MKSecondToken();
    Token expResult = new MKSecondToken();
    Token result = instance.leftMost(token,
                                     text);
    assertTrue(EqualsBuilder.reflectionEquals(expResult,
                                              result,
                                              false,
                                              null,
                                              true));
  }

  @Test
  public void testLeftMost6() {
    Token token = new MKThirdToken();
    String text = " exists only second and first";
    Token instance = new MKSecondToken();
    Token expResult = new MKSecondToken();
    Token result = instance.leftMost(token,
                                     text);
    assertTrue(EqualsBuilder.reflectionEquals(expResult,
                                              result,
                                              false,
                                              null,
                                              true));
  }
}
