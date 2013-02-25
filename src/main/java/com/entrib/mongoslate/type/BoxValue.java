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
 * Class to represent Rectangular Box value.
 *
 * @author Atul M Dambalkar (atul@entrib.com)
 */
public class BoxValue extends AbstractValue {

    public static final class Box {
        public GeoCoordinateValue bottomLeft;
        public GeoCoordinateValue topRight;
    }

    private Box box;

    public BoxValue() {
        super();
        super.valueType = ValueType.BOX;;
    }

    @Override
    public ValueType getValueType() {
        return ValueType.BOX;
    }

    @Override
    public void setValueType(ValueType valueType) {
        this.valueType = ValueType.BOX;
    }

    public Object getValueAsObject() {
        return box;
    }

    public String getValueAsString() {
        if (this.box == null) {
            throw new IllegalStateException("No box value set!");
        }
        return "Box ("
                + box.bottomLeft
                + "," + box.topRight
                + ")";

    }

    public void setValue(Object object) {
        this.box = (Box)object;
    }

    public void parseAndSet(String value) throws ValueParseException {
        //no-op
    }

    public ExpressionTypeEnum getExpressionTypeEnum() {
        return ExpressionTypeEnum.BoxValue;
    }

    public Object accept(ExpressionVisitor visitor) {
        return visitor.visit(this);
    }
}
