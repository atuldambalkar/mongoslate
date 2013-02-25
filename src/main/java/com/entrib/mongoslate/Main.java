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

import com.entrib.mongoslate.api.mongo.Find;
import com.entrib.mongoslate.expr.Expression;
import com.entrib.mongoslate.stmt.SelectStatement;
import com.entrib.mongoslate.stmt.Statement;
import com.entrib.mongoslate.stmt.StatementTreeBuilder;
import com.entrib.mongoslate.visitor.expr.evaluator.ArithExpressionEvaluatorTreeTraversor;
import com.entrib.mongoslate.visitor.expr.evaluator.ArithExpressionEvaluatorVisitor;
import com.entrib.mongoslate.visitor.expr.semantics.SemanticsCheckTreeTraversor;
import com.entrib.mongoslate.visitor.expr.semantics.SemanticsCheckVisitor;
import com.entrib.mongoslate.visitor.stmt.translator.StatementTranslatorVisitor;
import org.antlr.runtime.*;
import org.antlr.runtime.tree.*;

import java.io.*;


/**
 * Test driver program for the ANTLR3 Maven Architype demo
 *
 * @author Jim Idle (jimi@temporal-wave.com)
 */
class Main {

    static MongoslateLexer lexer;

    /**
     * Just a simple test driver for the ASP parser
     * to show how to call it.
     */

    public static void main(String[] args) {
        try {
            // Create the lexer, which we can keep reusing if we like
            lexer = new MongoslateLexer();

            if (args.length > 0) {
                parse(new File(args[0]));
            } else {
                System.err.println("Usage: java -jar AntlrPOC-1.0-SNAPSHOT-jar-with-dependencies.jar <directory | filename.expr>");
            }
        }
        catch (Exception ex) {
            System.err.println("ANTLR demo parser threw exception:");
            ex.printStackTrace();
        }
    }

    public static void parse(File source) throws Exception {

        // Open the supplied file or directory
        try {

            // From here, any exceptions are just thrown back up the chain
            if (source.isDirectory()) {
                System.out.println("Directory: " + source.getAbsolutePath());
                String files[] = source.list();

                for (int i = 0; i < files.length; i++) {
                    if (files[i].endsWith(".expr")) {
                        parse(new File(source, files[i]));
                    }
                }
            } else { // its a file
                parseSource(source.getAbsolutePath());
            }
        }
        catch (Exception ex) {
            System.err.println("ANTLR demo parser caught error on file open:");
            ex.printStackTrace();
        }

    }

    public static void parseSource(String source) throws Exception {
        // Parse an ANTLR demo file
        //
        try {
            // First create a file stream using the povided file/path
            // and tell the lexer that that is the character source.
            // You can also use text that you have already read of course
            // by using the string stream.
            //
            lexer.setCharStream(new ANTLRFileStream(source, "UTF8"));

            // Using the lexer as the token source, we create a token
            // stream to be consumed by the parser
            //
            CommonTokenStream tokens = new CommonTokenStream(lexer);

            // Now we need an instance of our parser
            //
            MongoslateParser parser = new MongoslateParser(tokens);

            System.out.println("file: " + source);

            // Provide some user feedback
            //
            System.out.println("    Lexer Start");
            long start = System.currentTimeMillis();

            // Force token load and lex (don't do this normally,
            // it is just for timing the lexer)
            //
            tokens.LT(1);
            long lexerStop = System.currentTimeMillis();
            System.out.println("      lexed in " + (lexerStop - start) + "ms.");

            // And now we merely invoke the start rule for the parser
            //
            System.out.println("    Parser Start");
            long pStart = System.currentTimeMillis();

            long stop = System.currentTimeMillis();
            System.out.println("      Parsed in " + (stop - pStart) + "ms.");

            printSelectTree(parser);
//            Expression expr = parser.expr().expression;
//            List<Expression> exprs = parser.
            System.out.println("Done");

//            NOw walk it with the generic tree walker, which does nothing but
//            verify the tree really.
            
//            try
//            {
//                if (parser.getNumberOfSyntaxErrors() == 0) {
//                    TTree walker = new TTree(new CommonTreeNodeStream(t));
//                    System.out.println("    AST Walk Start\n");
//                    pStart = System.currentTimeMillis();
//                    walker.expression();
//                    stop = System.currentTimeMillis();
//                    System.out.println("\n      AST Walked in " + (stop - pStart) + "ms.");
//                 }
//            }
//            catch(Exception w)
//            {
//                System.out.println("AST walk caused exception.");
//                w.printStackTrace();
//            }
        }
        catch (FileNotFoundException ex) {
            // The file we tried to parse does not exist
            //
            System.err.println("\n  !!The file " + source + " does not exist!!\n");
        }
        catch (Exception ex) {
            // Something went wrong in the parser, report this
            //
            System.err.println("Parser threw an exception:\n\n");
            ex.printStackTrace();
        }
    }

    private static void printSelectTree(MongoslateParser parser) throws Exception {
        MongoslateParser.select_stmt_return select = parser.select_stmt();
        if (select.tree!=null ) {
            System.out.println(((Tree)select.tree).toStringTree());
            ((CommonTree)select.tree).sanityCheckParentAndChildIndexes();
            Statement statement = StatementTreeBuilder.build((CommonTree)select.tree);
            SemanticsCheckVisitor semanticsCheckVisitor = new SemanticsCheckVisitor();
            //ExpressionTreeVisitor expressionTreeVisitor = new ExpressionTreeVisitor(semanticsCheckVisitor);
            SemanticsCheckTreeTraversor semanticsCheckTreeTraversor =
                    new SemanticsCheckTreeTraversor(semanticsCheckVisitor);
            Object result =
                    semanticsCheckTreeTraversor.visit(((SelectStatement)statement).getLogicalExpression());
            System.out.println(result);
            ArithExpressionEvaluatorVisitor arithExpressionEvaluatorVisitor =
                    new ArithExpressionEvaluatorVisitor();
            ArithExpressionEvaluatorTreeTraversor traversor =
                    new ArithExpressionEvaluatorTreeTraversor(arithExpressionEvaluatorVisitor);
            traversor.visit(((SelectStatement)statement).getLogicalExpression());
            StatementTranslatorVisitor stmtTranslatorVisitor = new StatementTranslatorVisitor();
            Find find = (Find)stmtTranslatorVisitor.visit((SelectStatement)statement);
            System.out.println(find.toString());
            //expressionTreeVisitor.visit(((SelectStatement)statement).getTagListExpression());
//            System.out.println(semanticsCheckVisitor.getResult());
            //expressionTreeVisitor.visit(((SelectStatement)statement).getLogicalExpression());
//            System.out.println(((Value)result).getValueAsObject());
            Expression expression = ((SelectStatement) statement).getLogicalExpression();
        }
    }
}
