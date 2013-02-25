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

/**
 * Operators that can be used on data items in aggregation queries.
 * 
 * @author Atul M Dambalkar (atul@entrib.com)
 */
public class DataOperatorValue extends TagValue {

    private DataOperatorEnum dataOperatorEnum;

    public DataOperatorValue() {
        super.setValueType(ValueType.DATA_OPERATOR);
    }

    public DataOperatorValue(DataOperatorEnum dataOperatorEnum, String value) {
        super(value);
        super.setValueType(ValueType.DATA_OPERATOR);
        this.dataOperatorEnum = dataOperatorEnum;
    }

    public DataOperatorEnum getDataOperatorEnum() {
        return dataOperatorEnum;
    }

    public void setDataOperatorEnum(DataOperatorEnum dataOperatorEnum) {
        this.dataOperatorEnum = dataOperatorEnum;
    }
}
