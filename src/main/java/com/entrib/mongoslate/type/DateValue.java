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

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.StringTokenizer;

/**
 * @author Atul M Dambalkar (atul@entrib.com)
 */
public class DateValue extends AbstractValue {

    private static final SimpleDateFormat dateFormatWithMillis = new SimpleDateFormat("dd-MM-yyyy-HH-mm-ss-SSS");
    private static final SimpleDateFormat dateFormatWithSeconds = new SimpleDateFormat("dd-MM-yyyy-HH-mm-ss");
    private static final SimpleDateFormat dateFormatWithMinutes = new SimpleDateFormat("dd-MM-yyyy-HH-mm");
    private static final SimpleDateFormat dateFormatWithHours = new SimpleDateFormat("dd-MM-yyyy-HH");
    private static final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");

    private Date value;

    public DateValue() {
        super.setValueType(ValueType.DATE);
    }

    public DateValue(String value) throws ValueParseException {
        super.setValueType(ValueType.DATE);
        parseAndSet(value);
    }

    public ExpressionTypeEnum getExpressionTypeEnum() {
        return ExpressionTypeEnum.DateValue;
    }

    public Date getValue() {
        return value;
    }

    public void setValue(Object value) {
        if (!(value instanceof Date)) {
            throw new IllegalArgumentException("Argument expected type is java.util.Date, passed of type: " + value.getClass());
        }
        this.value = (Date)value;
    }

	public void parseAndSet(String value) throws ValueParseException {
        try {
            StringTokenizer tokens = new StringTokenizer(value, "-");
            int numDelimeters = tokens.countTokens() - 1;
            DateFormat dateFormat = numDelimeters == 6? dateFormatWithMillis:
                    numDelimeters == 5? dateFormatWithSeconds:
                            numDelimeters == 4? dateFormatWithMinutes:
                                    numDelimeters == 3? dateFormatWithHours:
                                            simpleDateFormat;

		    this.value = dateFormat.parse(value);
        } catch (ParseException exc) {
            throw new ValueParseException("Exception while parsing the date: " + value, exc);
        }
	}

    public String getValueAsString() {
        return dateFormatWithMillis.format(value);
    }

    public Object getValueAsObject() {
        return value;
    }

    public Object accept(ExpressionVisitor visitor) {
        return visitor.visit(this);
    }
}
