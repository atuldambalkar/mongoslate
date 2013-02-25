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
package  com.entrib.mongoslate.type;

import com.entrib.mongoslate.visitor.expr.ExpressionVisitor;

import java.util.List;

/**
 * Class to represent Polygon value.
 *
 * @author Atul M Dambalkar (atul@entrib.com)
 */
public class PolygonValue extends AbstractValue {

    public static final class Polygon {
        public List<GeoCoordinateValue> coordinates;
    }

    private Polygon polygon;

    public PolygonValue() {
        super();
        super.valueType = ValueType.POLYGON;;
    }

    @Override
    public ValueType getValueType() {
        return ValueType.POLYGON;
    }

    @Override
    public void setValueType(ValueType valueType) {
        this.valueType = ValueType.POLYGON;
    }

    public Object getValueAsObject() {
        return polygon;
    }

    public String getValueAsString() {
        return polygon.toString();
    }

    public void setValue(Object object) {
        this.polygon = (Polygon)object;
    }

    public void parseAndSet(String value) throws ValueParseException {
        //no-op
    }

    public ExpressionTypeEnum getExpressionTypeEnum() {
        return ExpressionTypeEnum.PolygonValue;
    }

    public Object accept(ExpressionVisitor visitor) {
        return visitor.visit(this);
    }
}
