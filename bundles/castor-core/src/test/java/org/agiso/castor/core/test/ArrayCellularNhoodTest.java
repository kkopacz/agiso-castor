/* org.agiso.castor.core.test.ArrayCellularNhoodTest (27-12-2018)
 * 
 * ArrayCellularNhoodTest.java
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

import org.agiso.castor.core.ICellularNhood;
import org.agiso.castor.core.impl.ArrayCellularNhood;
import org.testng.annotations.Test;

/**
 * 
 * @author Karol Kopacz
 * @since 1.0
 */
public class ArrayCellularNhoodTest {
	@Test
	public void testNhood1D() throws Exception {
		// 1D nearest-neighbor neighborhood
		ICellularNhood nhood = new ArrayCellularNhood(
				-1,
				 0,
				 1
		);

		int[][] coords = nhood.getCoordinates();
		assert -1 == coords[0][0];
		assert  0 == coords[1][0];
		assert  1 == coords[2][0];
	}

	@Test
	public void testNhood2D() throws Exception {
		// 2D nearest-neighbor neighborhood
		ICellularNhood nhood = new ArrayCellularNhood(
				new int[] {-1, -1},
				new int[] {-1,  0},
				new int[] {-1,  1},
				new int[] { 0, -1},
				new int[] { 0,  0},
				new int[] { 0,  1},
				new int[] { 1, -1},
				new int[] { 1,  0},
				new int[] { 1,  1}
		);

		int[][] coords = nhood.getCoordinates();
		assert -1 == coords[0][0]; assert -1 == coords[0][1];
		assert -1 == coords[1][0]; assert  0 == coords[1][1];
		assert -1 == coords[2][0]; assert  1 == coords[2][1];
		assert  0 == coords[3][0]; assert -1 == coords[3][1];
		assert  0 == coords[4][0]; assert  0 == coords[4][1];
		assert  0 == coords[5][0]; assert  1 == coords[5][1];
		assert  1 == coords[6][0]; assert -1 == coords[6][1];
		assert  1 == coords[7][0]; assert  0 == coords[7][1];
		assert  1 == coords[8][0]; assert  1 == coords[8][1];
	}


	@Test
	public void testNhood3D() throws Exception {
		// Sample 3D neighborhood
		ICellularNhood nhood = new ArrayCellularNhood(
				new int[] {1, 2, 3},
				new int[] {4, 5, 6},
				new int[] {7, 8, 9}
		);

		int[][] coords = nhood.getCoordinates();
		assert 1 == coords[0][0]; assert 2 == coords[0][1]; assert 3 == coords[0][2];
		assert 4 == coords[1][0]; assert 5 == coords[1][1]; assert 6 == coords[1][2];
		assert 7 == coords[2][0]; assert 8 == coords[2][1]; assert 9 == coords[2][2];
	}
}
