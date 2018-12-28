/* org.agiso.castor.core.test.TotalisticCellularRuleTest (27-12-2018)
 * 
 * TotalisticCellularRuleTest.java
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
import org.agiso.castor.core.impl.TotalisticCellularRule;
import org.testng.annotations.Test;

/**
 * 
 * @author Karol Kopacz
 * @since 1.0
 */
public class TotalisticCellularRuleTest {
	@Test
	public void testTotalisticRule() throws Exception {
		ICellularRule rule = new TotalisticCellularRule(3, 3, 600);
		// 6 5 4 3 2 1 0
		// 0 2 1 1 0 2 0
		for(int i = 0; i <= 2; i++) {
			for(int j = 0; j <= 2; j++) {
				for(int k = 0; k <= 2; k++) {
					switch(i + j + k) {
						case 6:
							assert 0 == rule.calcCellValue(i, j, k);
							break;
						case 5:
							assert 2 == rule.calcCellValue(i, j, k);
							break;
						case 4:
							assert 1 == rule.calcCellValue(i, j, k);
							break;
						case 3:
							assert 1 == rule.calcCellValue(i, j, k);
							break;
						case 2:
							assert 0 == rule.calcCellValue(i, j, k);
							break;
						case 1:
							assert 2 == rule.calcCellValue(i, j, k);
							break;
						case 0:
							assert 0 == rule.calcCellValue(i, j, k);
							break;
					}
				}
			}
		}
	}
}
