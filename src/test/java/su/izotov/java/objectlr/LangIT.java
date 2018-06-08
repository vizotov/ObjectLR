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

import org.apache.commons.lang3.builder.EqualsBuilder;
import static org.junit.Assert.assertTrue;
import org.junit.Test;
import su.izotov.java.objectlr.token.IncompleteToken;
import su.izotov.java.objectlr.token.Text;

/**
 * @author Vladimir Izotov
 */
public class LangIT {
  @Test public void testConcat()
      throws Exception {
    String text = "string before token second string after token";
    MKLang instance = new MKLangImpl();
    String expResult = "MKTextSense string before token \n"
                       + "'second'\n"
                       + "MKTextSense  string after token";
    String result = instance.concat(new Text(text)).toVisual().toString();
    assertTrue(EqualsBuilder.reflectionEquals(expResult, result, false, null, true));
  }

  @Test public void testConcat2() {
    String text = "second string after token";
    MKLang instance = new MKLangImpl();
    Sense expResult = new BufferedChain(
        new MKSecondToken(),//
        new MKTextSense(" string after token"));
    Sense result = instance.concat(new Text(text));
    assertTrue(EqualsBuilder.reflectionEquals(expResult, result, false, null, true));
  }

  @Test public void testConcat3() {
    String text = "string before token second";
    MKLang instance = new MKLangImpl();
    Sense expResult = new BufferedChain(
        new MKTextSense("string before token ")//
        , new MKSecondToken());
    Sense result = instance.concat(new Text(text));
    assertTrue(EqualsBuilder.reflectionEquals(expResult, result, false, null, true));
  }

  @Test public void testConcat4() {
    String text = "second";
    MKLang instance = new MKLangImpl();
    Sense expResult = new MKSecondToken();
    Sense result = instance.concat(new Text(text));
    assertTrue(EqualsBuilder.reflectionEquals(expResult, result, false, null, true));
  }

  @Test public void testConcat5() {
    String text = "it is not token";
    Lang instance = new MKLangImpl();
    Sense expResult = new MKTextSense(text);
    Sense result = instance.concat(new Text(text));
    assertTrue(EqualsBuilder.reflectionEquals(expResult, result, false, null, true));
  }

  @Test public void testConcat6() {
    String text = "thi";
    MKLang instance = new MKLangImpl();
    Sense expResult = new BufferedChain(new MKLangImpl(),
                                        new IncompleteToken(new MKThirdToken(), 3));
    Sense result = instance.concat(new Text(text));
    assertTrue(EqualsBuilder.reflectionEquals(expResult, result, false, null, true));
  }

  @Test public void testConcat7() {
    String text = "firstsecond textik";
    MKLang instance = new MKLangImpl();
    String expResult = "'first'\n" + "'second'\n" + "MKTextSense  textik";
    String result = instance.concat(new Text(text)).toVisual().toString();
    assertTrue(EqualsBuilder.reflectionEquals(expResult, result, false, null, true));
  }

  @Test public void testConcat8() {
    String text = "start text firstsecond text third first ttt thi";
    MKLang instance = new MKLangImpl();
    String expResult = "MKTextSense start text \n"
                       + "'first'\n"
                       + "'second'\n"
                       + "MKTextSense  text \n"
                       + "'third'\n"
                       + "MKTextSense  \n"
                       + "'first'\n"
                       + "MKTextSense  ttt \n"
                       + "IncompleteToken thi";
    String result = instance.concat(new Text(text)).toVisual().toString();
    assertTrue(EqualsBuilder.reflectionEquals(expResult, result, false, null, true));
  }

  @Test public void testConcat9() {
    String text = "";
    MKLang instance = new MKLangImpl();
    Sense expResult = new MKLangImpl();
    Sense result = instance.concat(new Text(text));
    assertTrue(EqualsBuilder.reflectionEquals(expResult, result, false, null, true));
  }
}
