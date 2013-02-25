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
 * Class to capture Geographic location value.
 *
 * @author Atul M Dambalkar (atul@entrib.com)
 */
public class LocationValue extends AbstractValue {

    public static final class LocationQuery {
        public GeoCoordinateValue geoCoordinateValue;
        public float distance;
    }

    private LocationQuery locationQuery;

    public LocationValue() {
        super();
        super.valueType = ValueType.LOCATION; 
    }

    @Override
    public ValueType getValueType() {
        return ValueType.LOCATION;
    }

    @Override
    public void setValueType(ValueType valueType) {
        super.setValueType(valueType);
    }

    public Object getValueAsObject() {
        return locationQuery;
    }

    public String getValueAsString() {
        if (this.locationQuery == null) {
            throw new IllegalStateException("Query criterios not set!");
        }
        return "Location "
                + locationQuery.geoCoordinateValue
                + "," + locationQuery.distance;
    }

    public void setValue(Object object) {
        this.locationQuery = (LocationQuery)object;
    }

    public void parseAndSet(String value) throws ValueParseException {
        // no-op
    }

    public ExpressionTypeEnum getExpressionTypeEnum() {
        return ExpressionTypeEnum.LocationValue;
    }

    public Object accept(ExpressionVisitor visitor) {
        return visitor.visit(this);
    }
}
