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

import com.entrib.mongoslate.expr.BinaryExpression;
import com.entrib.mongoslate.expr.Expression;

/**
 * Class to check the semantics of the given arithmetic expression.
 *
 * This will only allow following types of operands in an arithmetic expression,
 * <ul>
 *   <li>Arithmetic</li>
 *   <li>Multiplying</li>
 *   <li>Minus</li>
 *   <li>Float</li>
 *   <li>Int</li>
 * </ul>
 * 
 * @author Atul M Dambalkar (atul@entrib.com)
 */
public class ArithExpressionSemanticsChecker {

    /**
     * Date, String, Identifier and Tag will not be allowed 
     * @param expression
     * @return
     */
    public static boolean checkSemantics(Expression expression) {
        Expression.ExpressionTypeEnum expression1Type = ((BinaryExpression)expression).getLeft().getExpressionTypeEnum();
        Expression.ExpressionTypeEnum expression2Type = ((BinaryExpression)expression).getRight().getExpressionTypeEnum();
        if (expression1Type == Expression.ExpressionTypeEnum.Arithmetic
                || expression1Type == Expression.ExpressionTypeEnum.Multiplying
                || expression1Type == Expression.ExpressionTypeEnum.Minus
                || expression1Type == Expression.ExpressionTypeEnum.FloatValue
                || expression1Type == Expression.ExpressionTypeEnum.IntValue) {
            if (expression1Type == expression2Type) {
                return true;
            }
        }
        if (expression1Type == Expression.ExpressionTypeEnum.Arithmetic) {
            return expression2Type == Expression.ExpressionTypeEnum.Multiplying
                    || expression2Type == Expression.ExpressionTypeEnum.Minus
                    || expression2Type == Expression.ExpressionTypeEnum.IntValue
                    || expression2Type == Expression.ExpressionTypeEnum.FloatValue;
        }
        if (expression1Type == Expression.ExpressionTypeEnum.Multiplying) {
            return expression2Type == Expression.ExpressionTypeEnum.Arithmetic
                    || expression2Type == Expression.ExpressionTypeEnum.Minus
                    || expression2Type == Expression.ExpressionTypeEnum.IntValue
                    || expression2Type == Expression.ExpressionTypeEnum.FloatValue;
        }
        if (expression1Type == Expression.ExpressionTypeEnum.Minus) {
            return expression2Type == Expression.ExpressionTypeEnum.Arithmetic
                    || expression2Type == Expression.ExpressionTypeEnum.Multiplying
                    || expression2Type == Expression.ExpressionTypeEnum.IntValue
                    || expression2Type == Expression.ExpressionTypeEnum.FloatValue;
        }
        if (expression1Type == Expression.ExpressionTypeEnum.IntValue) {
            return expression2Type == Expression.ExpressionTypeEnum.Arithmetic
                    || expression2Type == Expression.ExpressionTypeEnum.Multiplying
                    || expression2Type == Expression.ExpressionTypeEnum.Minus
                    || expression2Type == Expression.ExpressionTypeEnum.FloatValue;
        }
        if (expression1Type == Expression.ExpressionTypeEnum.FloatValue) {
            return expression2Type == Expression.ExpressionTypeEnum.Arithmetic
                    || expression2Type == Expression.ExpressionTypeEnum.Multiplying
                    || expression2Type == Expression.ExpressionTypeEnum.Minus
                    || expression2Type == Expression.ExpressionTypeEnum.IntValue;
        }
        return false;
    }

}
