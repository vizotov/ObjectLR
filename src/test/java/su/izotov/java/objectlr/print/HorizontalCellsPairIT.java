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
package su.izotov.java.objectlr.print;

import org.apache.commons.lang3.builder.EqualsBuilder;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

/**
 * @author Vladimir Izotov
 */
public class HorizontalCellsPairIT {
  private static final String THREE_LINES_TEXT = "the first line\n" + "second\n" + "third";
  private static final String FIVE_LINES_TEXT  = "the line number one\n"
                                                 + "very long line number two\n"
                                                 + "the third line\n"
                                                 + "4\n"
                                                 + "the last line";

  @Test public void testToString() {
    HorizontalCellsPair instance = new HorizontalCellsPair(new StringCell("left"),
                                                           (new StringCell("right")));
    String expResult = "left right";
    String result = instance.toString();
    assertTrue(EqualsBuilder.reflectionEquals(expResult, result, false, null, true));
  }

  @Test public void testToString2() {
    HorizontalCellsPair instance = new HorizontalCellsPair(new StringCell(THREE_LINES_TEXT),
                                                           (new StringCell(FIVE_LINES_TEXT)));
    String expResult = "the first line the line number one\n"
                       + "second         very long line number two\n"
                       + "third          the third line\n"
                       + "               4\n"
                       + "               the last line";
    String result = instance.toString();
    assertTrue(EqualsBuilder.reflectionEquals(expResult, result, false, null, true));
  }

  @Test public void testToString3() {
    HorizontalCellsPair instance = new HorizontalCellsPair(new StringCell(FIVE_LINES_TEXT),
                                                           (new StringCell(THREE_LINES_TEXT)));
    String expResult = "the line number one       the first line\n"
                       + "very long line number two second\n"
                       + "the third line            third\n"
                       + "4\n"
                       + "the last line";
    String result = instance.toString();
    assertTrue(EqualsBuilder.reflectionEquals(expResult, result, false, null, true));
  }
}
