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
package  com.entrib.mongoslate.visitor.stmt.semantics.select;

import com.entrib.mongoslate.expr.GroupByListExpression;
import com.entrib.mongoslate.expr.TagListExpression;
import com.entrib.mongoslate.stmt.SelectStatement;
import com.entrib.mongoslate.type.IdentifierValue;
import com.entrib.mongoslate.type.TagValue;

import java.util.List;

/**
 * This checker implements following check.
 *
 * If a group by clause is specified then for each tag used in group by clause, the tag has to be used in the select clause.
 * The tag used in the select clause may or may not use the aggr function. This behaviour is same as traditional sql
 *
 * @author Atul M Dambalkar (atul@entrib.com)
 */
public class SelectAndGroupByChecker {

    public static boolean checkSemantics(SelectStatement selectStatement) {
        TagListExpression tagListExpr = selectStatement.getTagListExpression();
        GroupByListExpression groupByExpr = selectStatement.getGroupByListExpression();

        if (groupByExpr != null) {
            List<IdentifierValue> groupByTags = groupByExpr.getValues();
            List<TagValue> tags = tagListExpr.getValues();

            for (IdentifierValue identifier: groupByTags) {
                if (!checkIfUsed(identifier, tags)) {
                    return false;
                }
            }
            return true;
        }       
        return true;
    }

    private static boolean checkIfUsed(IdentifierValue identifier, List<TagValue> tags) {
        for (TagValue tag: tags) {
            if (identifier.getValueAsString().equals(tag.getValueAsString())) {
                return true;
            }
        }
        return false;
    }
}
