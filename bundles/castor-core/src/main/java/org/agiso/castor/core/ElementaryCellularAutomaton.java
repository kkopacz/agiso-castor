package org.agiso.castor.core;

import org.agiso.castor.core.impl.ArrayCellularGrid;
import org.agiso.castor.core.impl.ArrayCellularNhood;
import org.agiso.castor.core.impl.ElementaryCellularRule;

/**
 * 
 */
public class ElementaryCellularAutomaton extends CellularAutomaton {
	public ElementaryCellularAutomaton(int states, int[] nhood, int rule, int width) {
		super(new ArrayCellularGrid(states, width),
				new ArrayCellularNhood(nhood),
				new ElementaryCellularRule(states, nhood.length, rule));
	}
}
