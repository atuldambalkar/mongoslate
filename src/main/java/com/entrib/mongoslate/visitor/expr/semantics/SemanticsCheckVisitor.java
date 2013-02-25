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
package  com.entrib.mongoslate.visitor.expr.semantics;

import com.entrib.mongoslate.expr.*;
import com.entrib.mongoslate.type.Value;
import com.entrib.mongoslate.type.ValueType;
import com.entrib.mongoslate.visitor.expr.ExpressionVisitor;

/**
 * Semantics checking visitor
 * 
 * @author Atul M Dambalkar (atul@entrib.com)
 */
public class SemanticsCheckVisitor implements ExpressionVisitor {

    public SemanticsCheckVisitor() {        
    }

    public Object visit(ArithExpression expression) {
        if (ArithExpressionSemanticsChecker.checkSemantics(expression)) {
            boolean leftResult = true;
            boolean rightResult = true;
            if (!(expression.getLeft() instanceof Value)) {
                leftResult = (Boolean)expression.getLeft().accept(this);
                }
            if (!(expression.getRight() instanceof Value)) {
                rightResult = (Boolean)expression.getRight().accept(this);
            }
            return leftResult && rightResult;
        }
        return false;
    }

    public Object visit(ExistsExpression expression) {
        Value value = (Value)expression.accept(this);
        return value.getValueType() == ValueType.TAG;
    }

    public Object visit(LogicalExpression expression) {
        if (LogicalExpressionSemanticsChecker.checkSemantics(expression)) {
            boolean leftResult = true;
            boolean rightResult = true;
            if (!(expression.getLeft() instanceof Value)) {
                leftResult = (Boolean)expression.getLeft().accept(this);
            }
            if (!(expression.getRight() instanceof Value)) {
                rightResult = (Boolean)expression.getRight().accept(this);
            }
            return leftResult && rightResult;
        }
        return false;
    }

    public Object visit(MinusExpression expression) {
        if (MinusExpressionSemanticsChecker.checkSemantics(expression)) {
            if (!(expression.getChild() instanceof Value)) {
                return expression.getChild().accept(this);
            }
            return true;
        }
        return false;
    }

    public Object visit(MultiplyingExpression expression) {
        if (MultiplyingExpressionSemanticsChecker.checkSemantics(expression)) {
            boolean leftResult = true;
            boolean rightResult = true;
            if (!(expression.getLeft() instanceof Value)) {
                leftResult = (Boolean)expression.getLeft().accept(this);
            }
            if (!(expression.getRight() instanceof Value)) {
                rightResult = (Boolean)expression.getRight().accept(this);
            }
            return leftResult && rightResult;
        }
        return false;
    }

    public Object visit(NegationExpression expression) {
        if (NegationExpressionSemanticsChecker.checkSemantics(expression)) {
            if (!(expression.getChild() instanceof Value)) {
                return expression.getChild().accept(this);
            }
            return true;
        }
        return false;
    }

    public Object visit(RelationalExpression expression) {
        if (RelationalExpressionSemanticsChecker.checkSemantics(expression)) {
            boolean leftResult = true;
            boolean rightResult = true;
            if (!(expression.getLeft() instanceof Value)) {
                leftResult = (Boolean)expression.getLeft().accept(this);
            }
            if (!(expression.getRight() instanceof Value)) {
                rightResult = (Boolean)expression.getRight().accept(this);
            }
            return leftResult && rightResult;
        }
        return false;
    }

    public Object visit(ValueListExpression expression) {
        return ValueListSemanticsChecker.checkSemantics(expression);
    }

    public Object visit(TagListExpression expression) {
        return TagListSemanticsChecker.checkSemantics(expression);
    }

    public Object visit(GroupByListExpression expression) {
        return true;
    }
    
    public Object visit(OrderByListExpression expression) {
        return true;  
    }

    public Object visit(LimitExpression expression) {
        return true;  
    }

    public Object visit(Value expression) {
        return expression;  
    }

    public Object getResult() {
        return null;
    }

    public String getMessage() {
        return null;
    }
}
