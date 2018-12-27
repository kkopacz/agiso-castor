/* org.agiso.castor.core.test.ElementaryCellularRuleTest (27-12-2018)
 * 
 * ElementaryCellularRuleTest.java
 * 
 * Copyright 2018 agiso.org
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.agiso.castor.core.test;

import org.agiso.castor.core.ICellularRule;
import org.agiso.castor.core.impl.ElementaryCellularRule;
import org.testng.annotations.Test;

/**
 * 
 * @author Karol Kopacz
 * @since 1.0
 */
public class ElementaryCellularRuleTest {
	@Test
	public void testElementaryRule() throws Exception {
		ICellularRule rule = new ElementaryCellularRule(2, 3, 110);
		// 111 110 101 100 001 010 001 000
		//  0   1   1   0   1   1   1   0
		assert 0 == rule.calcCellValue(1, 1, 1);
		assert 1 == rule.calcCellValue(1, 1, 0);
		assert 1 == rule.calcCellValue(1, 0, 1);
		assert 0 == rule.calcCellValue(1, 0, 0);
		assert 1 == rule.calcCellValue(0, 1, 1);
		assert 1 == rule.calcCellValue(0, 1, 0);
		assert 1 == rule.calcCellValue(0, 0, 1);
		assert 0 == rule.calcCellValue(0, 0, 0);
	}
}
