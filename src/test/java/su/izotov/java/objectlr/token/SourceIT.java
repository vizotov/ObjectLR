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
import su.izotov.java.objectlr.Sense;
import su.izotov.java.objectlr.text.Source;

/**
 * @author Vladimir Izotov
 */
public final class SourceIT {
  @Test public void testPreceding() {
    Source source = new Source("before second after");
    Token instance = new MKSecondToken();
    String expResult = "before ";
    String result = instance.precedingIn(source)
                            .toSource();
    assertTrue(EqualsBuilder.reflectionEquals(expResult, result, false, null, true));
  }

  @Test public void testPreceding2() {
    Source source = new Source("before second after");
    Token instance = new MKFirstToken();
    String expResult = "";
    String result = instance.precedingIn(source)
                            .toSource();
    assertTrue(EqualsBuilder.reflectionEquals(expResult, result, false, null, true));
  }

  @Test public void testPreceding3() {
    Source source = new Source("before seco");
    Token instance = new MKSecondToken();
    String expResult = "";
    String result = instance.precedingIn(source)
                            .toSource();
    assertTrue(EqualsBuilder.reflectionEquals(expResult, result, false, null, true));
  }

  @Test public void testFollowingText() {
    Source source = new Source("before second after");
    Token instance = new MKSecondToken();
    Sense expResult = new Source(" after");
    Sense result = instance.followingIn(source);
    assertTrue(EqualsBuilder.reflectionEquals(expResult, result, false, null, true));
  }

  @Test public void testFollowingText2() {
    Source source = new Source("before second after");
    Token instance = new MKFirstToken();
    Sense expResult = source;
    Sense result = instance.followingIn(source);
    assertTrue(EqualsBuilder.reflectionEquals(expResult, result, false, null, true));
  }

  @Test public void testLeftMost() {
    Token token = new MKFirstToken();
    String text = " no tokens exists";
    Token instance = new MKSecondToken();
    Token expResult = new Absence();
    Token result = instance.leftMost(token,
                                     text);
    assertTrue(EqualsBuilder.reflectionEquals(expResult, result, false, null, true));
  }

  @Test public void testLeftMost2() {
    Token token = new MKFirstToken();
    String text = " exists only second";
    Token instance = new MKSecondToken();
    Token expResult = new MKSecondToken();
    Token result = instance.leftMost(token,
                                     text);
    assertTrue(EqualsBuilder.reflectionEquals(expResult, result, false, null, true));
  }

  @Test public void testLeftMost3() {
    Token token = new MKFirstToken();
    String text = " exists only first";
    Token instance = new MKSecondToken();
    Token expResult = new MKFirstToken();
    Token result = instance.leftMost(token,
                                     text);
    assertTrue(EqualsBuilder.reflectionEquals(expResult, result, false, null, true));
  }

  @Test public void testLeftMost4() {
    Token token = new MKFirstToken();
    String text = " exists only first and second";
    Token instance = new MKSecondToken();
    Token expResult = new MKFirstToken();
    Token result = instance.leftMost(token,
                                     text);
    assertTrue(EqualsBuilder.reflectionEquals(expResult, result, false, null, true));
  }

  @Test public void testLeftMost5() {
    Token token = new MKFirstToken();
    String text = " exists only second and first";
    Token instance = new MKSecondToken();
    Token expResult = new MKSecondToken();
    Token result = instance.leftMost(token,
                                     text);
    assertTrue(EqualsBuilder.reflectionEquals(expResult, result, false, null, true));
  }

  @Test public void testLeftMost6() {
    Token token = new MKThirdToken();
    String text = " exists only second and first";
    Token instance = new MKSecondToken();
    Token expResult = new MKSecondToken();
    Token result = instance.leftMost(token,
                                     text);
    assertTrue(EqualsBuilder.reflectionEquals(expResult, result, false, null, true));
  }

  @Test public void testPreceding_Token() {
    Token token = new MKFirstToken();
    Source instance = new Source("the text is containing first in the middle");
    String expResult = "the text is containing ";
    String result = instance.precedingThe(token);
    assertTrue(EqualsBuilder.reflectionEquals(expResult, result, false, null, true));
  }

  /**
   * ${CLASS} can work.
   * @throws Exception If fails
   */
  @Test public void testPrecedingThe()
      throws Exception {
    Source instance = new Source("preceding text second following text");
    String expResult = "preceding text ";
    String result = instance.precedingThe(new MKSecondToken());
    assertTrue(EqualsBuilder.reflectionEquals(expResult, result, false, null, true));
  }

  /**
   * ${CLASS} can work.
   * @throws Exception If fails
   */
  @Test public void testFollowingThe()
      throws Exception {
    Source instance = new Source("preceding text second following text");
    String expResult = " following text";
    Source result = instance.followingThe(new MKSecondToken());
    assertTrue(EqualsBuilder.reflectionEquals(expResult, result.toSource(), false, null, true));
  }
}