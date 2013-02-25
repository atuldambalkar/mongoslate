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

/**
 * Class to capture circle value.
 *
 * @author Atul M Dambalkar (atul@entrib.com)
 */
public class CircleValue extends AbstractValue {

    public static final class Circle {
        public GeoCoordinateValue geoCoordinateValue;
        public float radius;
    }

    private Circle circle;

    public CircleValue() {
        super.valueType = ValueType.CIRCLE; 
    }

    @Override
    public ValueType getValueType() {
        return ValueType.CIRCLE;
    }

    @Override
    public void setValueType(ValueType valueType) {
        super.setValueType(valueType);
    }

    public Object getValueAsObject() {
        return circle;
    }

    public String getValueAsString() {
        if (this.circle == null) {
            throw new IllegalStateException("Query criterios not set!");
        }
        return "Circle "
                + circle.geoCoordinateValue
                + "," + circle.radius;
    }

    public void setValue(Object object) {
        this.circle = (Circle)object;
    }

    public void parseAndSet(String value) throws ValueParseException {
        // no-op
    }

    public ExpressionTypeEnum getExpressionTypeEnum() {
        return ExpressionTypeEnum.CircleValue;
    }

    public Object accept(ExpressionVisitor visitor) {
        return visitor.visit(this);
    }

}
