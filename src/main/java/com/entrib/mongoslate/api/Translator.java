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
package  com.entrib.mongoslate.api;

import com.entrib.mongoslate.MongoslateLexer;
import com.entrib.mongoslate.MongoslateParser;
import com.entrib.mongoslate.api.mongo.Find;
import com.entrib.mongoslate.stmt.SelectStatement;
import com.entrib.mongoslate.stmt.Statement;
import com.entrib.mongoslate.stmt.StatementTreeBuilder;
import com.entrib.mongoslate.visitor.expr.evaluator.ArithExpressionEvaluatorTreeTraversor;
import com.entrib.mongoslate.visitor.expr.evaluator.ArithExpressionEvaluatorVisitor;
import com.entrib.mongoslate.visitor.expr.semantics.SemanticsCheckTreeTraversor;
import com.entrib.mongoslate.visitor.expr.semantics.SemanticsCheckVisitor;
import com.entrib.mongoslate.visitor.stmt.translator.StatementTranslatorVisitor;
import org.antlr.runtime.ANTLRStringStream;
import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.tree.CommonTree;
import org.antlr.runtime.tree.Tree;

/**
 * @author Atul M Dambalkar (atul@entrib.com)
 */
public class Translator {

    public Translator() {

    }

    public Find translate(String select) {
        MongoslateLexer lexer = new MongoslateLexer();
        lexer.setCharStream(new ANTLRStringStream(select));
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        MongoslateParser parser = new MongoslateParser(tokens);

        Find find = null;
        try {
            MongoslateParser.select_stmt_return select_stmt_return = parser.select_stmt();
            CommonTree tree = (CommonTree)select_stmt_return.getTree();
            if (tree != null ) {
                System.out.println(tree.toStringTree());
                tree.sanityCheckParentAndChildIndexes();

                System.out.println("Building AST...");
                Statement statement = StatementTreeBuilder.build(tree);

                System.out.println("Running pass 1 - semantics check...");
                SemanticsCheckVisitor semanticsCheckVisitor = new SemanticsCheckVisitor();
                SemanticsCheckTreeTraversor semanticsCheckTreeTraversor =
                        new SemanticsCheckTreeTraversor(semanticsCheckVisitor);
                Object result =
                        semanticsCheckTreeTraversor.visit(((SelectStatement)statement).getLogicalExpression());
                System.out.println("Semantic check result: " + result);

                System.out.println("Running pass 2 - arithmetic expression evaluation...");
                ArithExpressionEvaluatorVisitor arithExpressionEvaluatorVisitor =
                        new ArithExpressionEvaluatorVisitor();
                ArithExpressionEvaluatorTreeTraversor traversor =
                        new ArithExpressionEvaluatorTreeTraversor(arithExpressionEvaluatorVisitor);
                traversor.visit(((SelectStatement)statement).getLogicalExpression());

                System.out.println("Running pass 3 - translating to Mongo shell query...");
                StatementTranslatorVisitor stmtTranslatorVisitor = new StatementTranslatorVisitor();
                find = (Find)stmtTranslatorVisitor.visit((SelectStatement)statement);

                System.out.println("Printing the translated result...");
                System.out.println(find.toString());                
            }
        } catch (Exception exc) {
            exc.printStackTrace();
        }
        return find;
    }
}
