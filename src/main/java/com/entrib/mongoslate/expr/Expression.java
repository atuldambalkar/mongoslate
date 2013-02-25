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

import com.entrib.mongoslate.type.LocationValue;
import com.entrib.mongoslate.type.PolygonValue;
import com.entrib.mongoslate.visitor.expr.ExpressionVisitor;

/**
 * Interface to capture expression. The expression can be of type arithmetic, multiplicative,
 * logical, relational or simple data value.
 *
 * @author Atul M Dambalkar (atul@entrib.com)
 */
public interface Expression {

    public enum ExpressionTypeEnum {
        IntValue,
        FloatValue,
        DateValue,
        StringValue,
        IdentifierValue,
        GroupByValue,
        OrderByValue,
        TagValue,
        TagAliasValue,
        ValueList,
        OrderByList,
        Limit,
        TagList,
        Arithmetic,
        Multiplying,
        Relational,
        Logical,
        Minus,
        Negation,
        Exists,
        Near,
        Within,
        GeoCordinateValue,
        LocationValue,
        BoxValue,
        PolygonValue,
        CircleValue
    };

    public ExpressionTypeEnum getExpressionTypeEnum();
    public Object accept(ExpressionVisitor visitor);
    public boolean isLeaf();
}
