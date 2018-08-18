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

import su.izotov.java.objectlr.Sense;
import su.izotov.java.objectlr.text.Incomplete;
import su.izotov.java.objectlr.text.Source;
import su.izotov.java.objectlr.text.Text;

/**
 * Empty token for using in situations of absence tokens in the string.
 * It is always recognized at the end of the string.
 * @author Vladimir Izotov
 */
public final class Absence
    implements Token,
               Text {

  public Text concat(Text text) {
    return text.asString()
               .isEmpty() ?
           this :
           text;
  }

  public Sense concat(Source text) {
    return text.asString()
               .isEmpty() ?
           this :
           text;
  }

  @Override
  public String asString() {
    return "";
  }

  public Sense concat(final Sense sense) {
    return sense;
  }

  @Override
  public Sense followingIn(final Source text) {
    return this;
  }

  @Override
  public int length() {
    return 0;
  }

  @Override
  public int firstPositionIn(final String text) {
    return text.length();
  }

  @Override
  public Text precedingIn(final Source text) {
    return text.asString()
               .isEmpty() ?
           this :
           new Incomplete(text.asString());
  }
}
