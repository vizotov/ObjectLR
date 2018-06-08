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

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package su.izotov.java.objectlr.tokens;

import org.apache.commons.lang3.builder.EqualsBuilder;
import static org.junit.Assert.assertTrue;
import org.junit.Test;
import su.izotov.java.objectlr.MKLang;
import su.izotov.java.objectlr.MKSecondToken;
import su.izotov.java.objectlr.MKThirdToken;
import su.izotov.java.objectlr.token.EmptyToken;
import su.izotov.java.objectlr.token.Extracted;
import su.izotov.java.objectlr.token.IncompleteToken;

/**
 * @author Vladimir Izotov
 */
public class TokensSetIT {
  @Test public void testLeftMostParsed() {
    String text = " tokens second first in the certain order third";
    final MKLang mkLang = new MKLang() {
    };
    Tokens instance = (Tokens) mkLang.tokens();
    Extracted expResult = new MKSecondToken();
    Extracted result = instance.leftMostParsed(text);
    assertTrue(EqualsBuilder.reflectionEquals(expResult, result, false, null, true));
  }

  @Test public void testLeftMostParsed2() {
    String text = " there is only part of token thir";
    final MKLang mkLang = new MKLang() {
    };
    Tokens instance = (Tokens) mkLang.tokens();
    Extracted expResult = new IncompleteToken(new MKThirdToken(), 4);
    Extracted result = instance.leftMostParsed(text);
    assertTrue(EqualsBuilder.reflectionEquals(expResult, result, false, null, true));
  }

  @Test public void testLeftMostParsed3() {
    String text = " no token";
    final MKLang mkLang = new MKLang() {
    };
    Tokens instance = (Tokens) mkLang.tokens();
    Extracted expResult = new EmptyToken();
    Extracted result = instance.leftMostParsed(text);
    assertTrue(EqualsBuilder.reflectionEquals(expResult, result, false, null, true));
  }
}
