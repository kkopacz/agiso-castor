package org.agiso.castor.core;

import org.agiso.castor.core.impl.ArrayCellularGrid;
import org.agiso.castor.core.impl.ArrayCellularNhood;
import org.agiso.castor.core.impl.TotalisticCellularRule;

/**
 * 
 */
public class TotalisticCellularAutomaton extends CellularAutomaton {
	public TotalisticCellularAutomaton(int states, int[] nhood, int rule, int width) {
		super(new ArrayCellularGrid(states, width),
				new ArrayCellularNhood(nhood),
				new TotalisticCellularRule(states, nhood.length, rule));
	}
}
