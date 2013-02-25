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

import com.entrib.mongoslate.expr.ExistsExpression;
import com.entrib.mongoslate.expr.Expression;
import com.entrib.mongoslate.type.Value;
import com.entrib.mongoslate.type.ValueFactory;
import com.entrib.mongoslate.type.ValueParseException;
import com.entrib.mongoslate.type.ValueType;
import com.mongodb.BasicDBObject;
import com.mongodb.QueryOperators;
import org.antlr.runtime.*;

/**
 * The super class of the generated parser. It is extended by the generated
 * code because of the superClass optoin in the .g file.
 *
 * This class contains any helper functions used within the parser
 * grammar itself, as well as any overrides of the standard ANTLR Java
 * runtime methods, such as an implementation of a custom error reporting
 * method, symbol table populating methods and so on.
 *
 * @author Jim Idle - Temporal Wave LLC - jimi@temporal-wave.com
 */
public abstract class AbstractTParser

        extends Parser

{
    /**
     * Create a new parser instance, pre-supplying the input token stream.
     * 
     * @param input The stream of tokens that will be pulled from the lexer
     */
    protected AbstractTParser(TokenStream input) {
        super(input);
    }

    /**
     * Create a new parser instance, pre-supplying the input token stream
     * and the shared state.
     *
     * This is only used when a grammar is imported into another grammar, but
     * we must supply this constructor to satisfy the super class contract.
     *
     * @param input The stream of tokesn that will be pulled from the lexer
     * @param state The shared state object created by an interconnectd grammar
     */
    protected AbstractTParser(TokenStream input, RecognizerSharedState state) {
        super(input, state);
    }


    /**
     * Creates the error/warning message that we need to show users/IDEs when
     * ANTLR has found a parsing error, has recovered from it and is now
     * telling us that a parsing exception occurred.
     *
     * @param tokenNames token names as known by ANTLR (which we ignore)
     * @param e The exception that was thrown
     */
    @Override
    public void displayRecognitionError(String[] tokenNames, RecognitionException e) {

        // This is just a place holder that shows how to override this method
        //
        super.displayRecognitionError(tokenNames, e);
    }


}

