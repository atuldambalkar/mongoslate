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
package  com.entrib.mongoslate.expr;

import com.entrib.mongoslate.visitor.expr.ExpressionVisitor;
import com.entrib.mongoslate.visitor.expr.ExpressionVisitable;

/**
 * Class to represent logical expression. In addtion to usual 'and' and 'or' operators
 * it also captures Mongo operator 'nor'. 
 *
 * @author Atul M Dambalkar (atul@entrib.com)
 */
public class LogicalExpression implements BinaryExpression, ExpressionVisitable {

    public enum LogicalOperatorEnum { AND, NOR, OR };

    private LogicalOperatorEnum logicalOperatorEnum;
    private Expression left;
    private Expression right;

    public LogicalExpression() {
    }

    public LogicalExpression(LogicalOperatorEnum logicalOperatorEnum,
                             Expression left,
                             Expression right) {
        this.logicalOperatorEnum = logicalOperatorEnum;
        this.left = left;
        this.right = right;
    }

    public ExpressionTypeEnum getExpressionTypeEnum() {
        return ExpressionTypeEnum.Logical;
    }

    public LogicalOperatorEnum getLogicalOperatorEnum() {
        return logicalOperatorEnum;
    }

    public void setLogicalOperatorEnum(LogicalOperatorEnum logicalOperatorEnum) {
        this.logicalOperatorEnum = logicalOperatorEnum;
    }

    public Expression getLeft() {
        return left;
    }

    public void setLeft(Expression left) {
        this.left = left;
    }

    public Expression getRight() {
        return right;
    }

    public void setRight(Expression right) {
        this.right = right;
    }

    public Object accept(ExpressionVisitor visitor) {
        return visitor.visit(this);
    }

    public boolean isLeaf() {
        return false;
    }

}
