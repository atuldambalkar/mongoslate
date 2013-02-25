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

import com.entrib.mongoslate.MongoslateParser;
import com.entrib.mongoslate.expr.*;
import com.entrib.mongoslate.type.IdentifierValue;
import com.entrib.mongoslate.type.ValueParseException;
import org.antlr.runtime.tree.CommonTree;

import java.util.List;

/**
 * Class to build the statement object based on the given AST.
 * 
 * @author Atul M Dambalkar (atul@entrib.com)
 */
public class StatementTreeBuilder {

    private static SelectStatement buildSelectStatement(CommonTree tree) throws ValueParseException {
        if (tree == null) {
            throw new IllegalArgumentException("Tree object can't be null!");
        }
        SelectStatement statement = new SelectStatement();
        List children = tree.getChildren();
        for (int i = 0; i < children.size(); i++) {
            CommonTree selectNode = (CommonTree)children.get(i);
            switch(selectNode.getType()) {
                case MongoslateParser.DISTINCT:
                    statement.setDistinct(true);
                    break;

                case MongoslateParser.TAG_LIST:
                    TagListExpression tagListExpression =
                            (TagListExpression)ExpressionTreeBuilder.build(selectNode);
                    statement.setTagListExpression(tagListExpression);
                    if (tagListExpression.isAggregation()) {
                        statement.setAggregation(true);
                    }
                    break;

                case MongoslateParser.COLLECTION:
                    statement.setCollection(
                            (IdentifierValue)ExpressionTreeBuilder.build(selectNode));
                    break;

                case MongoslateParser.LOGICAL_EXPRESSION:
                    statement.setLogicalExpression(
                            ExpressionTreeBuilder.build(selectNode));
                    break;

                case MongoslateParser.GROUP_BY:
                    statement.setGroupByListExpression(
                            (GroupByListExpression)ExpressionTreeBuilder.build(selectNode));
                    statement.setAggregation(true);
                    break;

                case MongoslateParser.ORDER_BY:
                    statement.setOrderByListExpression(
                            (OrderByListExpression)ExpressionTreeBuilder.build(selectNode));
                    break;

                case MongoslateParser.HAVING:
                    statement.setHavingExpression(
                            ExpressionTreeBuilder.build(selectNode));
                    break;

                case MongoslateParser.LIMIT_EXPRESSION:
                    statement.setLimitExpression(
                            (LimitExpression)ExpressionTreeBuilder.build(selectNode));
                    break;
            }            
        }
        return statement;
    }

    public static Statement build(CommonTree tree) throws ValueParseException {
        if (tree == null) {
            throw new IllegalArgumentException("Tree object can't be null!");
        }
        switch(tree.getType()) {
            case MongoslateParser.SELECT:
                return buildSelectStatement(tree);
        }
        throw new IllegalArgumentException("Illegal token type found while parsing the statement tree");
    }

}
