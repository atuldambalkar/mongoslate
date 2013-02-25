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

import com.entrib.mongoslate.expr.Expression;
import com.entrib.mongoslate.expr.GroupByListExpression;
import com.entrib.mongoslate.expr.TagListExpression;
import com.entrib.mongoslate.stmt.SelectStatement;

/**
 * Check the over-all correctness of the select statement.
 *
 * pgsql
 *  1. if having is used without group by it's a semantic error
 *  2. if having is used then the tag names used in having clause have to match that used in group by clause
 *  3. if a single tag is to be selected it has to match the group by tag, and then there is no need to use aggr function
 *  4. if more than one tags are to be selected, then all the tags need to be part of group_by clause
 *
 * SQL Select evaluation - 
 * First the product of all tables in the from clause is formed.
 * The where clause is then evaluated to eliminate rows that do not satisfy the search_condition.
 * Next, the rows are grouped using the columns in the group by clause.
 * Then, Groups that do not satisfy the search_condition in the having clause are eliminated.
 * Next, the expressions in the select clause target list are evaluated.
 * If the distinct keyword in present in the select clause, duplicate rows are now eliminated.
 * The union is taken after each sub-select is evaluated.
 * Finally, the resulting rows are sorted according to the columns specified in the order by clause.
 * 
 *  mongoslate sql
 *  1. If a group by clause is specified then for each tag used in group by clause, the tag has to be used in the select clause.
 *     The tag used in the select clause may or may not use the aggr function.
 *     This behaviour is same as traditional sql
 *
 *  2. Having can not be used without group by - check is needed
 *     This behavior is same as traditional sql but SQL server allows this.
 *
 *  3. Having can only use the tag names used as the identifiers defined in the alias clause (in the select clause)
 *     along with tags used with aggregation function - check needed,
 * 
 *     This is same behaviour than the traditional sql, and it's mainly needed for the pipeline
 *     paradigm in the aggregation framework.
 *
 *     The $match pipeline for having will be generated after the $group pipeline
 *     along with the alias names defined in the $group pipeline
 *
 *  4. A data operator can be used for dates and string (toupper and tolower) on the tag names in select clause
 *     in which case, aggregation function can't be used on any of the tag names in the select clause. - check is needed
 *
 *     In this case aggregation command will be generated with $project and $match as pipeline commands. 
 *  
 *  5. Throw a warning if the alias used is as the same name as a valid tag name used in the select clause,
 *     then the valid tag name will get shadowed
 *
 * @author Atul M Dambalkar (atul@entrib.com)
 */
public class SelectStatementChecker {

    private SelectStatement selectStatement;

    public SelectStatementChecker(SelectStatement selectStatement) {
        this.selectStatement = selectStatement;
    }

    public Object check() {
        if (selectStatement.isAggregation()) { // this is aggregation query
            TagListExpression tagListExpr = selectStatement.getTagListExpression();
            Expression havingExpr = selectStatement.getHavingExpression();
            GroupByListExpression groupByExpr = selectStatement.getGroupByListExpression();

            // check 1 - if a single tag is in select clause then it has to match the group by tag,
            // and then there is no need to use aggr function. of-course it's ok to use aggr function
            if (!SelectAndGroupByChecker.checkSemantics(selectStatement)) {
                return false;
            }

            // check 2 - having can't be used without a group by
            if (havingExpr != null && groupByExpr == null) {
                return false; // having specified without a group by expr
            }

            // check 3 - having can use the tag name defined in the alias clause only

        }
        return true;
    }

}
