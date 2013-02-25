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
package  com.entrib.mongoslate.stmt;

import com.entrib.mongoslate.expr.*;
import com.entrib.mongoslate.type.IdentifierValue;
import com.entrib.mongoslate.visitor.stmt.StatementVisitable;
import com.entrib.mongoslate.visitor.stmt.StatementVisitor;

/**
 * Class to represent the SQL select statement catered for MongoDB.
 *
 * @author Atul M Dambalkar (atul@entrib.com)
 */
public class SelectStatement implements Statement, StatementVisitable {

    private boolean isDistinct;
    private boolean isAggregation;
    private TagListExpression tagListExpression;
    private IdentifierValue collection;
    private Expression logicalExpression;
    private GroupByListExpression groupByListExpression;
    private Expression havingExpression;
    private OrderByListExpression orderByListExpression;
    private LimitExpression limitExpression;

    public SelectStatement() {
    }

    public StatementTypeEnum getStatementTypeEnum() {
        return Statement.StatementTypeEnum.SELECT;
    }

    public boolean isDistinct() {
        return isDistinct;
    }

    public void setDistinct(boolean distinct) {
        isDistinct = distinct;
    }

    public boolean isAggregation() {
        return isAggregation;
    }

    public void setAggregation(boolean aggregation) {
        isAggregation = aggregation;
    }

    public TagListExpression getTagListExpression() {
        return tagListExpression;
    }

    public void setTagListExpression(TagListExpression tagListExpression) {
        this.tagListExpression = tagListExpression;
    }

    public IdentifierValue getCollection() {
        return collection;
    }

    public void setCollection(IdentifierValue collection) {
        this.collection = collection;
    }

    public Expression getLogicalExpression() {
        return logicalExpression;
    }

    public void setLogicalExpression(Expression logicalExpression) {
        this.logicalExpression = logicalExpression;
    }

    public OrderByListExpression getOrderByListExpression() {
        return orderByListExpression;
    }

    public GroupByListExpression getGroupByListExpression() {
        return groupByListExpression;
    }

    public void setGroupByListExpression(GroupByListExpression groupByListExpression) {
        this.groupByListExpression = groupByListExpression;
    }

    public Expression getHavingExpression() {
        return havingExpression;
    }

    public void setHavingExpression(Expression havingExpression) {
        this.havingExpression = havingExpression;
    }

    public void setOrderByListExpression(OrderByListExpression orderByListExpression) {
        this.orderByListExpression = orderByListExpression;
    }

    public LimitExpression getLimitExpression() {
        return limitExpression;
    }

    public void setLimitExpression(LimitExpression limitExpression) {
        this.limitExpression = limitExpression;
    }

    public Object accept(StatementVisitor visitor) {
        return visitor.visit(this);
    }
}
