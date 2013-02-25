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
public class OrderByValue extends IdentifierValue {

    public enum OrderEnum {
        ASC,
        DESC;

        /**
         * Needed to workaround valueOf issue for inner enums.
         * 
         * @param name
         * @return
         */
        public static OrderEnum toOrderEnum(String name) {
            if (name.equalsIgnoreCase("asc")) {
                return ASC;
            } else if (name.equalsIgnoreCase("desc")) {
                return DESC;
            }
            throw new IllegalArgumentException("Illegal value for order enum string!");
        }

    };


    private OrderEnum orderEnum;

    public OrderByValue() {
        super.setValueType(ValueType.ORDER_BY);
    }

    public OrderEnum getOrderEnum() {
        return orderEnum;
    }

    public void setOrderEnum(OrderEnum orderEnum) {
        this.orderEnum = orderEnum;
    }

}
