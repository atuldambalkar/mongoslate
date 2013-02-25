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

import com.entrib.mongoslate.expr.*;
import com.entrib.mongoslate.type.FloatValue;
import com.entrib.mongoslate.type.IntValue;
import com.entrib.mongoslate.type.Value;
import com.entrib.mongoslate.type.ValueType;
import com.entrib.mongoslate.visitor.expr.ExpressionVisitor;

/**
 * Visitor to evaluate the arithmetic expression.
 * 
 * @author Atul M Dambalkar (atul@entrib.com)
 */
public class ArithExpressionEvaluatorVisitor implements ExpressionVisitor {

    private Object result;

    public ArithExpressionEvaluatorVisitor() {
    }

    public Object visit(ArithExpression expression) {
        Value operand1 = (Value)expression.getLeft().accept(this);
        Value operand2 = (Value)expression.getRight().accept(this);
        ValueType resultType =
                operand1.getValueType() == ValueType.FLOAT? ValueType.FLOAT:
                    (operand2.getValueType() == ValueType.FLOAT? ValueType.FLOAT:
                            ValueType.INT);

        switch (expression.getArithOperatorEnum()) {
            case MINUS:
                switch (resultType) {
                    case FLOAT:
                        return new FloatValue(Float.parseFloat(operand1.getValueAsString())
                                - Float.parseFloat(operand2.getValueAsString()));
                    case INT:
                        return new IntValue(Integer.parseInt(operand1.getValueAsString())
                                - Integer.parseInt(operand2.getValueAsString()));
                }
                break;
            case PLUS:
                switch (resultType) {
                    case FLOAT:
                        return new FloatValue(Float.parseFloat(operand1.getValueAsString())
                                + Float.parseFloat(operand2.getValueAsString()));
                    case INT:
                        return new IntValue(Integer.parseInt(operand1.getValueAsString())
                                + Integer.parseInt(operand2.getValueAsString()));
                }
        }
        throw new IllegalArgumentException("Unsupported arith operator");
    }

    public Object visit(ExistsExpression expression) {
        return null;
    }

    public Object visit(LogicalExpression expression) {
        return null;
    }

    public Object visit(MinusExpression expression) {
        Value operand = (Value)expression.getChild().accept(this);
        ValueType resultType = operand.getValueType();

        switch (resultType) {
            case FLOAT:
                return new FloatValue((-Float.parseFloat(operand.getValueAsString())));
            case INT:
                return new IntValue((-Integer.parseInt(operand.getValueAsString())));
        }
        throw new IllegalArgumentException("Unsupported data type");
    }

    public Object visit(MultiplyingExpression expression) {
        Value operand1 = (Value)expression.getLeft().accept(this);
        Value operand2 = (Value)expression.getRight().accept(this);
        ValueType resultType =
                operand1.getValueType() == ValueType.FLOAT? ValueType.FLOAT:
                    (operand2.getValueType() == ValueType.FLOAT? ValueType.FLOAT:
                            ValueType.INT);

        switch (expression.getMultiplyingOperatorEnum()) {
            case DIV:
                switch (resultType) {
                    case FLOAT:
                        return new FloatValue(Float.parseFloat(operand1.getValueAsString())
                                / Float.parseFloat(operand2.getValueAsString()));
                    case INT:
                        return new IntValue(Integer.parseInt(operand1.getValueAsString())
                                / Integer.parseInt(operand2.getValueAsString()));
                }
                break;
            case MOD:
                switch (resultType) {
                    case FLOAT:
                        return new FloatValue(Float.parseFloat(operand1.getValueAsString())
                                % Float.parseFloat(operand2.getValueAsString()));
                    case INT:
                        return new IntValue(Integer.parseInt(operand1.getValueAsString())
                                % Integer.parseInt(operand2.getValueAsString()));
                }
                break;
            case MULTI:
                switch (resultType) {
                    case FLOAT:
                        return new FloatValue(Float.parseFloat(operand1.getValueAsString())
                                * Float.parseFloat(operand2.getValueAsString()));
                    case INT:
                        return new IntValue(Integer.parseInt(operand1.getValueAsString())
                                * Integer.parseInt(operand2.getValueAsString()));
                }
        }
        return null;
    }

    public Object visit(NegationExpression expression) {
        return null;
    }

    public Object visit(RelationalExpression expression) {
        return null;
    }

    public Object visit(ValueListExpression expression) {
        return null;
    }

    public Object visit(TagListExpression expression) {
        return null;  
    }

    public Object visit(GroupByListExpression expression) {
        return null;
    }

    public Object visit(OrderByListExpression expression) {
        return null;  
    }

    public Object visit(LimitExpression expression) {
        return null;  
    }

    public Object visit(Value expression) {
        switch (expression.getValueType()) {
            case FLOAT:
            case INT:
                return expression;
        }
        throw new IllegalArgumentException("Unsupported data type in arith expression");
    }

    public Object getResult() {
        return result;
    }

    public String getMessage() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
