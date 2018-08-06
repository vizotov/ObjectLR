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
import su.izotov.java.objectlr.Sense;
import su.izotov.java.objectlr.text.Source;

/**
 * @author Vladimir Izotov
 */
public final class IncompleteIT {
  @Test public void testTrailingText() {
    Source source = new Source("before the piece of token sec");
    Incomplete instance = new Incomplete(new MKSecondToken(), 3);
    String expResult = "before the piece of token ";
    String result = instance.precedingIn(source);
    assertTrue(EqualsBuilder.reflectionEquals(expResult, result, false, null, true));
  }

  @Test public void testFollowingText() {
    Source source = new Source("simple text without pieces of tokens");
    Incomplete instance = new Incomplete(new MKFirstToken(), 2);
    Sense expResult = source;
    Sense result = instance.followingIn(source);
    assertTrue(EqualsBuilder.reflectionEquals(expResult, result, false, null, true));
  }

  @Test public void testToSource() {
    Incomplete instance = new Incomplete(new MKFirstToken(), 3);
    String expResult = "fir";
    String result = instance.toSource();
    assertTrue(EqualsBuilder.reflectionEquals(expResult, result, false, null, true));
  }
}
