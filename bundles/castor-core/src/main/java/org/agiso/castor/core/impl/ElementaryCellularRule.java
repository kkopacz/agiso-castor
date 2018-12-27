/* org.agiso.castor.core.impl.ElementaryCellularRule (27-12-2018)
 * 
 * ElementaryCellularRule.java
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
import java.util.Arrays;

import org.agiso.castor.core.ICellularRule;

/**
 * 
 * @author Karol Kopacz
 * @since 1.0
 */
public class ElementaryCellularRule implements ICellularRule {
	private ArrayCellularGrid ruleGrid;

//	--------------------------------------------------------------------------
	public ElementaryCellularRule(int cellStates, int nhoodSize, int rule) {
		this(cellStates, nhoodSize, new BigInteger(String.valueOf(rule)));
	}

	public ElementaryCellularRule(int cellStates, int nhoodSize, BigInteger rule) {
		// Ilość znaków pełnej reprezentacji reguły w systemie o podstawie cellStates:
		BigInteger ruleStates = new BigInteger(String.valueOf(cellStates));
		BigInteger ruleLength = ruleStates.pow(nhoodSize);

		System.out.println("Weryfikacja parametrów...");

		// Sprawdzamy, czy reprezentacja reguły nie jest zbyt długa:
		if(ruleLength.longValue() >= Integer.MAX_VALUE) {
			throw new IllegalStateException("Illegal rule size!");
		}

		// Maksymalna dozwolona wartość reguły:
		BigInteger ruleMaximum = ruleStates.pow(ruleLength.intValue()).subtract(BigInteger.ONE);
		if(rule.signum() < 0 || rule.compareTo(ruleMaximum) > 0) {
			throw new IllegalStateException("Illegal rule value!");
//			System.out.println("Maksymalna wartość reguły: " + toCharSequence(ruleMaximum, cellStates, ruleLength.intValue()));
		}

		System.out.println("Tworzenie przestrzeni reguły (macierz " + nhoodSize + "-wymiarowa o wielkości " + cellStates + ")...");

		// Tworzenie przestrzeni komórkowej pamiętającej regułę:
		int[] dimmensions = new int[nhoodSize];		// tyle wymiarów ile komórek w otoczeniu
		Arrays.fill(dimmensions, cellStates);		// taka wielkość wymiaru ile stanów komórki

		ruleGrid = new ArrayCellularGrid();
		ruleGrid.initialize(cellStates, dimmensions);

		System.out.println("Wypełnianie przestrzeni reguły...");

		// Wypełnianie przestrzeni reguły:
		CharSequence ruleString = toCharSequence(rule, cellStates, ruleLength.intValue());
		for(int index = ruleLength.intValue() - 1; index >= 0; index--) {
			BigInteger cellValue = new BigInteger(String.valueOf(ruleString.charAt(ruleLength.intValue() - index - 1)), cellStates);
			CharSequence cellCoords = toCharSequence(new BigInteger(String.valueOf(index)), cellStates, nhoodSize);

			int[] coords = new int[nhoodSize];
			for(int i = 0; i < nhoodSize; i++) {
				coords[i] = new BigInteger("" + cellCoords.charAt(i), cellStates).intValue();
			}

			ruleGrid.setCellValue(cellValue.intValue(), coords);
		}

		System.out.println("Zrobione!");
	}

//	--------------------------------------------------------------------------
	@Override
	public int calcCellValue(int... nhoodValues) {
		return ruleGrid.getCellValue(nhoodValues);
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
