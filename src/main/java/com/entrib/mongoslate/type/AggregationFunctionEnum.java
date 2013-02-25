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
public enum AggregationFunctionEnum {
    SUM,
    COUNT,
    MAX,
    MIN,
    AVG;

    private static Map<String, AggregationFunctionEnum> enumMap =
            new HashMap<String, AggregationFunctionEnum>();

    static {
        enumMap.put("sum", SUM);
        enumMap.put("count", COUNT);
        enumMap.put("max", MAX);
        enumMap.put("min", MIN);
        enumMap.put("avg", AVG);
    }

    /**
     * Needed to workaround valueOf issue for inner enums.
     *
     * @param name
     * @return
     */
    public static AggregationFunctionEnum toAggregationFunctionEnum(String name) {
        return enumMap.get(name.toLowerCase());
    }

}
