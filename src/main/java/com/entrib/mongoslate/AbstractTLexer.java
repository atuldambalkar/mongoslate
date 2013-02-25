/*
   Copyright [2013] [Entrib Technologies]

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
*/
package  com.entrib.mongoslate;

import org.antlr.runtime.CharStream;
import org.antlr.runtime.Lexer;
import org.antlr.runtime.RecognizerSharedState;

/**

 * This is the super class for the lexer. It is extended by the lexer class
 * generated from TLexer.g.
 *
 * Do not place code and declarations in the lexer .g files, use
 * a superclass like this and place all the support methods and
 * error overrides etc in the super class. This way you will keep
 * the lexer grammar clean and hunky dory.
 *
 * @author Jim Idle - Temporal Wave LLC (jimi@idle.ws)
 */
public abstract class AbstractTLexer
    extends Lexer

{
    /**
     * Default constructor for the lexer, when you do not yet know what
     * the character stream to be provided is.
     */
    public AbstractTLexer() {
    }

    /**
     * Create a new instance of the lexer using the given character stream as
     * the input to lex into tokens.
     *
     * @param input A valid character stream that contains the ruleSrc code you
     *              wish to compile (or lex at least)
     */
    public AbstractTLexer(CharStream input) {
        this(input, new RecognizerSharedState());
    }

    /**
     * Internal constructor for ANTLR - do not use.
     *
     * @param input The character stream we are going to lex
     * @param state The shared state object, shared between all lexer comonents
     */
    public AbstractTLexer(CharStream input, RecognizerSharedState state) {
        super(input,state);
    }

}

