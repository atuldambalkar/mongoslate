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

import com.entrib.mongoslate.type.Value;
import com.entrib.mongoslate.visitor.expr.ExpressionVisitable;
import com.entrib.mongoslate.visitor.expr.ExpressionVisitor;

import java.util.ArrayList;
import java.util.List;

/**
 * Class to represent the comma separated list of data values. The values in the list
 * cane be of any basic data type.

 * @author Atul M Dambalkar (atul@entrib.com)
 */
public class ValueListExpression<T extends Value> implements Expression, ExpressionVisitable {
    
    private List<T> values;

    public ValueListExpression() {
        this.values = new ArrayList<T>();
    }

    public ExpressionTypeEnum getExpressionTypeEnum() {
        return ExpressionTypeEnum.ValueList;
    }

    public Object accept(ExpressionVisitor visitor) {
        return visitor.visit(this);
    }

    public List<T> getValues() {
        return values;
    }

    public void setValues(List<T> values) {
        this.values = values;
    }

    public void add(T value) {
        this.values.add(value);
    }

    public boolean isLeaf() {
        return true;  
    }
}
