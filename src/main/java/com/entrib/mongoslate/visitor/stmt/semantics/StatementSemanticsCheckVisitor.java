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
package  com.entrib.mongoslate.visitor.stmt.semantics;

import com.entrib.mongoslate.stmt.SelectStatement;
import com.entrib.mongoslate.visitor.stmt.StatementVisitor;
import com.entrib.mongoslate.visitor.stmt.semantics.select.SelectStatementChecker;

/**
 * Check the over-all correctness of the statement objects.
 *
 * @author Atul M Dambalkar (atul@entrib.com)
 */
public class StatementSemanticsCheckVisitor implements StatementVisitor {

    public Object visit(SelectStatement selectStatement) {
        return new SelectStatementChecker(selectStatement).check();
    }
}
