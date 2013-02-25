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
package  com.entrib.mongoslate.visitor.stmt.semantics.select;

import com.entrib.mongoslate.expr.*;
import com.entrib.mongoslate.type.TagValue;
import com.entrib.mongoslate.type.Value;
import com.entrib.mongoslate.visitor.expr.ExpressionVisitor;

import java.util.List;

/**
 * This checker implements following check.
 *
 * Having can only use the tag names used as the identifiers defined in the alias clause (in the select clause).
 * 
 * @author Atul M Dambalkar (atul@entrib.com)
 */
public class SelectAndHavingCheckVisitor implements ExpressionVisitor {

    private List<TagValue> tags;

    public SelectAndHavingCheckVisitor(List<TagValue> tags) {
        this.tags = tags;
    }

    public Object visit(ArithExpression expression) {
        return true;
    }

    public Object visit(ExistsExpression expression) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public Object visit(LogicalExpression expression) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public Object visit(MinusExpression expression) {
        return true;
    }

    public Object visit(MultiplyingExpression expression) {
        return true;
    }

    public Object visit(NegationExpression expression) {
        return true;
    }

    public Object visit(RelationalExpression expression) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public Object visit(ValueListExpression expression) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public Object visit(TagListExpression expression) {
        return true;
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
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public Object getResult() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public String getMessage() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
