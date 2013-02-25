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

import java.util.HashMap;
import java.util.Map;

/**
* @author Atul M Dambalkar (atul@entrib.com)
*/
public enum DataOperatorEnum {
    YEAR,
    MONTH,
    WEEK,
    DAY_OF_YEAR,
    DAY_OF_MONTH,
    DAY_OF_WEEK,
    HOUR,
    MINUTE,
    SECOND,
    TO_UPPER,
    TO_LOWER;

    private static Map<String, DataOperatorEnum> enumMap =
            new HashMap<String, DataOperatorEnum>();

    static {
        enumMap.put("year", YEAR);
        enumMap.put("month", MONTH);
        enumMap.put("week", WEEK);
        enumMap.put("day_of_year", DAY_OF_YEAR);
        enumMap.put("day_of_month", DAY_OF_MONTH);
        enumMap.put("day_of_week", DAY_OF_WEEK);
        enumMap.put("hour", HOUR);
        enumMap.put("minute", MINUTE);
        enumMap.put("second", SECOND);
        enumMap.put("to_upper", TO_UPPER);
        enumMap.put("to_lower", TO_LOWER);
    }

    /**
     * Needed to workaround valueOf issue for inner enums.
     *
     * @param name
     * @return
     */
    public static DataOperatorEnum toDataOperatorEnum(String name) {
        return enumMap.get(name.toLowerCase());
    }

}
