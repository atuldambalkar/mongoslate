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

import com.entrib.mongoslate.type.OrderByValue;
import com.entrib.mongoslate.visitor.expr.ExpressionVisitable;
import com.entrib.mongoslate.visitor.expr.ExpressionVisitor;

import java.util.ArrayList;
import java.util.List;

/**
 * Class to represent the comma separated list of tags. The values in the list
 * are actually identifiers that represent the tag names.
 *  
 * @author Atul M Dambalkar (atul@entrib.com)
 */
public class OrderByListExpression implements Expression, ExpressionVisitable {

    private List<OrderByValue> values;

    public OrderByListExpression() {
        this.values = new ArrayList<OrderByValue>();
    }

    public ExpressionTypeEnum getExpressionTypeEnum() {
        return ExpressionTypeEnum.OrderByList;
    }

    public Object accept(ExpressionVisitor visitor) {
        return visitor.visit(this);
    }

    public void add(OrderByValue value) {
        this.values.add(value);
    }
    
    public List<OrderByValue> getValues() {
        return values;
    }

    public void setValues(List<OrderByValue> values) {
        this.values = values;
    }

    public boolean isLeaf() {
        return true;  
    }
}
