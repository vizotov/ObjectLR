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

import su.izotov.java.objectlr.text.Incomplete;
import su.izotov.java.objectlr.text.Text;
import su.izotov.java.objectlr.text.Unrecognized;
import su.izotov.java.objectlr.token.Token;

/**
 * <p>Created with IntelliJ IDEA.</p>
 * @author Vladimir Izotov
 * @version $Id$
 * @since 1.0
 */
public final class MKLangImpl
    implements MKLang {

  public Incomplete concat(Incomplete sense) {
    return sense;
  }

  public Unrecognized concat(Unrecognized sense) {
    return sense;
  }

  public Token concat(Token token) {
    return token;
  }

  public Text concat(Text text) {
    return text;
  }

  @Override
  public String toSource() {
    return "";
  }

  @Override
  public Text textToken(final String text) {
    return new MKText(text);
  }
}
