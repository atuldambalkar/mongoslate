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
 * @author Atul M Dambalkar (atul@entrib.com)
 */
public class FloatValue extends AbstractValue {

    private float value;

    public FloatValue() {
        super.setValueType(ValueType.FLOAT);
    }

    public FloatValue(float value) {
        super.setValueType(ValueType.FLOAT);
        this.value = value;
    }

    public FloatValue(String value) throws ValueParseException {
        super.setValueType(ValueType.FLOAT);
        parseAndSet(value);
    }

    public ExpressionTypeEnum getExpressionTypeEnum() {
        return ExpressionTypeEnum.FloatValue;
    }
    
    public Object getValueAsObject() {
        return value;
    }

    public String getValueAsString() {
        return "" + value;
    }

    public void setValue(Object object) {
        if (!(object instanceof Float)) {
            throw new IllegalArgumentException("Float expected, found: " + object.getClass());
        }
        this.value = (Float)object;
    }

    public void parseAndSet(String value) throws ValueParseException {
        try {
            this.value = Float.parseFloat(value);
        } catch (NumberFormatException exc) {
            throw new ValueParseException("Exceptin while parsing the value string.", exc);
        }
    }

    public Object accept(ExpressionVisitor visitor) {
        return visitor.visit(this);
    }    
}
