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
package  com.entrib.mongoslate.visitor.expr.evaluator;

import com.entrib.mongoslate.expr.BinaryExpression;
import com.entrib.mongoslate.expr.Expression;
import com.entrib.mongoslate.expr.NumberExpression;
import com.entrib.mongoslate.expr.UnaryExpression;
import com.entrib.mongoslate.type.Value;

/**
 * Traverse the expression tree. If it's a number expression node
 * evaluate the number expression value and update the underlying number expression
 * object with the computed result. 
 *
 * @author Atul M Dambalkar (atul@entrib.com)
 */
public class ArithExpressionEvaluatorTreeTraversor {

    private ArithExpressionEvaluatorVisitor visitor;

    public ArithExpressionEvaluatorTreeTraversor(ArithExpressionEvaluatorVisitor visitor) {
        this.visitor = visitor;
    }

    public void visit(Expression expression) {
        Object result = null;
        switch (expression.getExpressionTypeEnum()) {
            case Relational:
            case Logical:
                visit(((BinaryExpression)expression).getLeft());
                visit(((BinaryExpression)expression).getRight());
                break;

            case Negation:
                visit(((UnaryExpression)expression).getChild());
                break;

            case Arithmetic:
            case Multiplying:
                result = expression.accept(visitor);
                ((NumberExpression)expression).setResult((Value)result);
                break;

            case Minus:
                result = expression.accept(visitor);
                ((NumberExpression)expression).setResult((Value)result);
        }
    }
}
