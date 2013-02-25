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
package  com.entrib.mongoslate.visitor.expr;

import com.entrib.mongoslate.expr.*;
import com.entrib.mongoslate.type.DateValue;
import com.entrib.mongoslate.type.FloatValue;
import com.entrib.mongoslate.type.IdentifierValue;
import com.entrib.mongoslate.type.Value;

/**
 * The visitor interface
 *
 * @author Atul M Dambalkar (atul@entrib.com)
 */
public interface ExpressionVisitor {

    public Object visit(ArithExpression expression);
    public Object visit(ExistsExpression expression);
    public Object visit(LogicalExpression expression);
    public Object visit(MinusExpression expression);
    public Object visit(MultiplyingExpression expression);
    public Object visit(NegationExpression expression);
    public Object visit(RelationalExpression expression);
    public Object visit(ValueListExpression expression);
    public Object visit(TagListExpression expression);
    public Object visit(GroupByListExpression expression);
    public Object visit(OrderByListExpression expression);
    public Object visit(LimitExpression expression);
    public Object visit(Value expression);
    public Object getResult();
    public String getMessage();
   
}
