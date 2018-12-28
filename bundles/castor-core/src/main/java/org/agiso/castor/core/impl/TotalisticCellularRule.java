/* org.agiso.castor.core.impl.TotalisticCellularRule (27-12-2018)
 * 
 * TotalisticCellularRule.java
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
package org.agiso.castor.core.impl;

import java.math.BigInteger;

import org.agiso.castor.core.ICellularRule;

/**
 * 
 * @author Karol Kopacz
 * @since 1.0
 */
public class TotalisticCellularRule implements ICellularRule {
	private int[] ruleGrid;

//	--------------------------------------------------------------------------
	public TotalisticCellularRule(int cellStates, int nhoodSize, int rule) {
		this(cellStates, nhoodSize, new BigInteger(String.valueOf(rule)));
	}

	public TotalisticCellularRule(int cellStates, int nhoodSize, BigInteger rule) {
		int ruleLength = nhoodSize * cellStates - nhoodSize + 1;
		CharSequence ruleString = toCharSequence(rule, cellStates, ruleLength);

		ruleGrid = new int[ruleLength];
		for(int index = ruleLength - 1; index >= 0; index--) {
			ruleGrid[index] = ruleString.charAt(ruleLength - index - 1) - '0';
		}
	}

//	--------------------------------------------------------------------------
	@Override
	public int calcCellValue(int... nhoodValues) {
		int sum = 0;
		for(int value : nhoodValues) {
			sum += value;
		}
		return ruleGrid[sum];
	}

//	--------------------------------------------------------------------------
	private CharSequence toCharSequence(BigInteger rule, int radix, int length) {
		StringBuilder value = new StringBuilder(length);

		String string = rule.toString(radix);
		for(int i = length - string.length(); i > 0; i--) {
			value.append('0');
		}
		value.append(string);

		return value;
	}

}
