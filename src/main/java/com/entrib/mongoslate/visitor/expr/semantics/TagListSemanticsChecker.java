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
package  com.entrib.mongoslate.visitor.expr.semantics;

import com.entrib.mongoslate.expr.Expression;
import com.entrib.mongoslate.expr.TagListExpression;
import com.entrib.mongoslate.expr.ValueListExpression;
import com.entrib.mongoslate.type.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Atul M Dambalkar (atul@entrib.com)
 */
public class TagListSemanticsChecker {

    public static boolean checkSemantics(Expression expression) {
        TagListExpression tagListExpression = (TagListExpression)expression;
        List<TagValue> values = tagListExpression.getValues();
        if (values.size() == 0) {
            return false;
        }
        // check if the alias names are not reused
        Map<String, String> aliasMap = new HashMap<String, String>();
        for (TagValue tagValue: values) {
            if (tagValue.getValueType() == ValueType.AGGREGATION_FUNCTION_ALIAS
                    || tagValue.getValueType() == ValueType.DATA_OPERATOR_ALIAS) {
                String alias = ((TagAliasValue)tagValue).getAlias();
                if (aliasMap.containsKey(alias)) {
                    return false;  // same alias is used
                } else {
                    aliasMap.put(alias, alias);
                }
            }
        }
        return true;
    }

}
