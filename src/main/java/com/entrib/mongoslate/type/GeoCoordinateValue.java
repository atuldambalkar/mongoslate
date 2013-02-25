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
 *
 * @author Atul M Dambalkar (atul@entrib.com)
 */
public class GeoCoordinateValue extends AbstractValue {

    private GeoCoordinate geoCoordinate;

    public GeoCoordinateValue() {
        super();
        super.valueType = ValueType.GEOCOORDINATE;
    }

    @Override
    public ValueType getValueType() {
        return ValueType.GEOCOORDINATE;
    }

    public Object getValueAsObject() {
        return geoCoordinate;
    }

    public String getValueAsString() {
        return geoCoordinate.toString();
    }

    public void setValue(Object object) {
        this.geoCoordinate = (GeoCoordinate)object;
    }

    public void parseAndSet(String value) throws ValueParseException {
        // no-op
    }

    @Override
    public void setValueType(ValueType valueType) {
        super.setValueType(valueType);
    }

    public ExpressionTypeEnum getExpressionTypeEnum() {
        return ExpressionTypeEnum.GeoCordinateValue;
    }

    public Object accept(ExpressionVisitor visitor) {
        return visitor.visit(this);
    }

}
