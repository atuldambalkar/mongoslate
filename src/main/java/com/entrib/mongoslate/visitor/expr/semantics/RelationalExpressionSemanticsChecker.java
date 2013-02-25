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

import com.entrib.mongoslate.expr.Expression;
import com.entrib.mongoslate.expr.RelationalExpression;

/**
 * 
 * @author Atul M Dambalkar (atul@entrib.com)
 */
public class RelationalExpressionSemanticsChecker {

    public static boolean checkSemantics(RelationalExpression expression) {
        Expression.ExpressionTypeEnum expression1Type = expression.getLeft().getExpressionTypeEnum();
        Expression.ExpressionTypeEnum expression2Type = expression.getRight().getExpressionTypeEnum();
        switch (expression.getRelationalOperatorEnum()) {
            case ALL:
            case IN:
            case NIN:    
                return expression1Type == Expression.ExpressionTypeEnum.IdentifierValue
                        && expression2Type == Expression.ExpressionTypeEnum.ValueList;

            case STARTS:
            case ENDS:
            case CONTAINS:
                return expression1Type == Expression.ExpressionTypeEnum.IdentifierValue
                        && expression2Type == Expression.ExpressionTypeEnum.StringValue;

            case EQ:
            case NEQ:
            case GT:
            case GTE:
            case LT:
            case LTE:
                return checkSemantics(expression1Type, expression2Type);

            case NEAR:
                return expression1Type == Expression.ExpressionTypeEnum.IdentifierValue
                        && expression2Type == Expression.ExpressionTypeEnum.LocationValue;
            case WITHIN:
                return expression1Type == Expression.ExpressionTypeEnum.IdentifierValue
                        && (expression2Type == Expression.ExpressionTypeEnum.BoxValue
                            || expression2Type == Expression.ExpressionTypeEnum.PolygonValue
                            || expression2Type == Expression.ExpressionTypeEnum.CircleValue);
        }
        return false;
    }

    private static boolean checkSemantics(Expression.ExpressionTypeEnum expression1Type,
                                          Expression.ExpressionTypeEnum expression2Type) {
        if (expression1Type == Expression.ExpressionTypeEnum.IdentifierValue) {
            return expression2Type != Expression.ExpressionTypeEnum.IdentifierValue;
        }
        if (expression1Type == Expression.ExpressionTypeEnum.Arithmetic
                || expression1Type == Expression.ExpressionTypeEnum.Multiplying
                || expression1Type == Expression.ExpressionTypeEnum.FloatValue
                || expression1Type == Expression.ExpressionTypeEnum.IntValue
                || expression1Type == Expression.ExpressionTypeEnum.StringValue
                || expression1Type == Expression.ExpressionTypeEnum.DateValue) {
            if (expression1Type == expression2Type) {
                return true;
            }
        }
        if (expression1Type == Expression.ExpressionTypeEnum.Arithmetic) {
            return expression2Type == Expression.ExpressionTypeEnum.Multiplying
                    || expression2Type == Expression.ExpressionTypeEnum.IntValue
                    || expression2Type == Expression.ExpressionTypeEnum.FloatValue
                    || expression2Type == Expression.ExpressionTypeEnum.IdentifierValue;
        }
        if (expression1Type == Expression.ExpressionTypeEnum.Multiplying) {
            return expression2Type == Expression.ExpressionTypeEnum.Arithmetic
                    || expression2Type == Expression.ExpressionTypeEnum.IntValue
                    || expression2Type == Expression.ExpressionTypeEnum.FloatValue
                    || expression2Type == Expression.ExpressionTypeEnum.IdentifierValue;
        }
        if (expression1Type == Expression.ExpressionTypeEnum.IntValue) {
            return expression2Type == Expression.ExpressionTypeEnum.Arithmetic
                    || expression2Type == Expression.ExpressionTypeEnum.Multiplying
                    || expression2Type == Expression.ExpressionTypeEnum.FloatValue
                    || expression2Type == Expression.ExpressionTypeEnum.IdentifierValue;
        }
        if (expression1Type == Expression.ExpressionTypeEnum.FloatValue) {
            return expression2Type == Expression.ExpressionTypeEnum.Arithmetic
                    || expression2Type == Expression.ExpressionTypeEnum.Multiplying
                    || expression2Type == Expression.ExpressionTypeEnum.IntValue
                    || expression2Type == Expression.ExpressionTypeEnum.IdentifierValue;
        }
        if (expression1Type == Expression.ExpressionTypeEnum.StringValue) {
            return expression2Type == Expression.ExpressionTypeEnum.StringValue
                    || expression2Type == Expression.ExpressionTypeEnum.IdentifierValue;
        }
        if (expression1Type == Expression.ExpressionTypeEnum.DateValue) {
            return expression2Type == Expression.ExpressionTypeEnum.DateValue
                    || expression2Type == Expression.ExpressionTypeEnum.IdentifierValue;
        }
        return false;
    }

}
