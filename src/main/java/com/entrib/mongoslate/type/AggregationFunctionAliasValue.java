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
 * @author Atul M Dambalkar (atul@entrib.com)
 */
public class AggregationFunctionAliasValue extends TagAliasValue {

    private AggregationFunctionEnum aggregationFunctionEnum;

    public AggregationFunctionAliasValue() {
        super.setValueType(ValueType.AGGREGATION_FUNCTION_ALIAS);
    }

    public AggregationFunctionAliasValue(AggregationFunctionEnum aggregationFunctionEnum,
                                         String value, String alias) {
        super(value, alias);
        super.setValueType(ValueType.AGGREGATION_FUNCTION_ALIAS);
        this.aggregationFunctionEnum = aggregationFunctionEnum;
    }

    public AggregationFunctionEnum getAggregationFunctionEnum() {
        return aggregationFunctionEnum;
    }

    public void setAggregationFunctionEnum(AggregationFunctionEnum aggregationFunctionEnum) {
        this.aggregationFunctionEnum = aggregationFunctionEnum;
    }
}
