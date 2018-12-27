/* org.agiso.castor.core.impl.ArrayCellularNhood (27-12-2018)
 * 
 * ArrayCellularNhood.java
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

import org.agiso.castor.core.ICellularNhood;

/**
 * Array based cellular automaton neighborhood interface.
 * 
 * @author Karol Kopacz
 * @since 1.0
 */
public class ArrayCellularNhood implements ICellularNhood {
	private final int[][] nhood;	// array of neighborhood cells coordinates

//	--------------------------------------------------------------------------
	public ArrayCellularNhood(int... nhood) {
		this.nhood = new int[nhood.length][1];
		for(int i = nhood.length - 1; i >= 0; i--) {
			this.nhood[i][0] = nhood[i];
		}
	}
	public ArrayCellularNhood(int[]... nhood) {
		this.nhood = nhood.clone();
	}

//	--------------------------------------------------------------------------
	@Override
	public int[][] getCoordinates() {
		return nhood;
	}
}
