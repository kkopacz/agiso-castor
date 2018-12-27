/* org.agiso.castor.core.test.ArrayCellularGridTest (27-12-2018)
 * 
 * ArrayCellularGridTest.java
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

import java.util.Random;

import org.agiso.castor.core.ICellularGrid;
import org.agiso.castor.core.impl.ArrayCellularGrid;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

/**
 * 
 * @author Karol Kopacz
 * @since 1.0
 */
public class ArrayCellularGridTest {
	private int x = 100, y = 100, z = 100, states = 1 << 10;
	private Random generator = new Random();

	private int[][][] dataS = new int[x][y][z];
	private int[][][] dataT = new int[x][y][z];

//	--------------------------------------------------------------------------
	@BeforeClass
	public void intialize() throws Exception {
		for(int i = 0; i < x; i++) {
			for(int j = 0; j < y; j++) {
				for(int k = 0; k < z; k++) {
					dataS[i][j][k] = generator.nextInt(states);
				}
			}
		}
	}

//	--------------------------------------------------------------------------
	@Test
	public void testInitGrid1D() throws Exception {
		System.out.println("testInitGrid1D()...");

		long start = System.nanoTime();

		// Allokacja przestrzeni:
		ICellularGrid grid = new ArrayCellularGrid(states, x);
		long alloc = System.nanoTime();
		System.out.println("Allocation time: " + (alloc - start) + "ns");

		// Zapis przestrzeni:
		for(int i = 0; i < x; i++) {
			grid.setCellValue(dataS[i][0][0], i);
		}
		long write = System.nanoTime();
		System.out.println("Writing time: " + (write - alloc) + "ns");

		// Odczyt przestrzeni:
		for(int i = 0; i < x; i++) {
			dataT[i][0][0] = grid.getCellValue(i);
		}
		long read = System.nanoTime();
		System.out.println("Reading time: " + (read - write) + "ns");

		// Sprawdzanie poprawności odczytanych danych
		for(int i = 0; i < x; i++) {
			assert dataS[i][0][0] == dataT[i][0][0];
		}
	}

	@Test
	public void testInitGrid2D() throws Exception {
		System.out.println("testInitGrid2D()...");

		long start = System.nanoTime();

		// Allokacja przestrzeni:
		ICellularGrid grid = new ArrayCellularGrid(states, x, y);
		long alloc = System.nanoTime();
		System.out.println("Allocation time: " + (alloc - start) + "ns");

		// Zapis przestrzeni:
		for(int i = 0; i < x; i++) {
			for(int j = 0; j < y; j++) {
				grid.setCellValue(dataS[i][j][0], i, j);
			}
		}
		long write = System.nanoTime();
		System.out.println("Writing time: " + (write - alloc) + "ns");

		// Odczyt przestrzeni:
		for(int i = 0; i < x; i++) {
			for(int j = 0; j < y; j++) {
				dataT[i][j][0] = grid.getCellValue(i, j);
			}
		}
		long read = System.nanoTime();
		System.out.println("Reading time: " + (read - write) + "ns");

		// Sprawdzanie poprawności odczytanych danych
		for(int i = 0; i < x; i++) {
			for(int j = 0; j < y; j++) {
				assert dataS[i][j][0] == dataT[i][j][0];
			}
		}
	}

	@Test
	public void testInitGrid3D() throws Exception {
		System.out.println("testInitGrid3D()...");

		long start = System.nanoTime();

		// Allokacja przestrzeni:
		ICellularGrid grid = new ArrayCellularGrid(states, x, y, z);
		long alloc = System.nanoTime();
		System.out.println("Allocation time: " + (alloc - start) + "ns");

		// Zapis przestrzeni:
		for(int i = 0; i < x; i++) {
			for(int j = 0; j < y; j++) {
				for(int k = 0; k < z; k++) {
					grid.setCellValue(dataS[i][j][k], i, j, k);
				}
			}
		}
		long write = System.nanoTime();
		System.out.println("Writing time: " + (write - alloc) + "ns");

		// Odczyt przestrzeni:
		for(int i = 0; i < x; i++) {
			for(int j = 0; j < y; j++) {
				for(int k = 0; k < z; k++) {
					dataT[i][j][k] = grid.getCellValue(i, j, k);
				}
			}
		}
		long read = System.nanoTime();
		System.out.println("Reading time: " + (read - write) + "ns");

		// Sprawdzanie poprawności odczytanych danych:
		for(int i = 0; i < x; i++) {
			for(int j = 0; j < y; j++) {
				for(int k = 0; k < z; k++) {
					assert dataS[i][j][k] == dataT[i][j][k];
				}
			}
		}
	}

	@Test
	public void testFillGrid1D() throws Exception {
		System.out.println("testFillGrid1D()...");

		long start = System.nanoTime();

		// Allokacja przestrzeni:
		ICellularGrid grid = new ArrayCellularGrid(states, x);
		long alloc = System.nanoTime();
		System.out.println("Allocation time: " + (alloc - start) + "ns");

		int value = generator.nextInt(states);

		// Wypełnianie przestrzeni:
		grid.fill(value);
		long fill = System.nanoTime();
		System.out.println("Filling time: " + (fill - alloc) + "ns");

		// Odczyt przestrzeni:
		for(int i = 0; i < x; i++) {
			dataT[i][0][0] = grid.getCellValue(i);
		}
		long read = System.nanoTime();
		System.out.println("Reading time: " + (read - fill) + "ns");

		// Sprawdzanie poprawności odczytanych danych:
		for(int i = 0; i < x; i++) {
			assert value == dataT[i][0][0];
		}
	}

	@Test
	public void testFillGrid2D() throws Exception {
		System.out.println("testFillGrid2D()...");

		long start = System.nanoTime();

		// Allokacja przestrzeni:
		ICellularGrid grid = new ArrayCellularGrid(states, x, y);
		long alloc = System.nanoTime();
		System.out.println("Allocation time: " + (alloc - start) + "ns");

		int value = generator.nextInt(states);

		// Wypełnianie przestrzeni:
		grid.fill(value);
		long fill = System.nanoTime();
		System.out.println("Filling time: " + (fill - alloc) + "ns");

		// Odczyt przestrzeni:
		for(int i = 0; i < x; i++) {
			for(int j = 0; j < y; j++) {
				dataT[i][j][0] = grid.getCellValue(i, j);
			}
		}
		long read = System.nanoTime();
		System.out.println("Reading time: " + (read - fill) + "ns");

		// Sprawdzanie poprawności odczytanych danych:
		for(int i = 0; i < x; i++) {
			for(int j = 0; j < y; j++) {
				assert value == dataT[i][j][0];
			}
		}
	}

	@Test
	public void testFillGrid3D() throws Exception {
		System.out.println("testFillGrid3D()...");

		long start = System.nanoTime();

		// Allokacja przestrzeni:
		ICellularGrid grid = new ArrayCellularGrid(states, x, y, z);
		long alloc = System.nanoTime();
		System.out.println("Allocation time: " + (alloc - start) + "ns");

		int value = generator.nextInt(states);

		// Wypełnianie przestrzeni:
		grid.fill(value);
		long fill = System.nanoTime();
		System.out.println("Filling time: " + (fill - alloc) + "ns");

		// Odczyt przestrzeni:
		for(int i = 0; i < x; i++) {
			for(int j = 0; j < y; j++) {
				for(int k = 0; k < z; k++) {
					dataT[i][j][k] = grid.getCellValue(i, j, k);
				}
			}
		}
		long read = System.nanoTime();
		System.out.println("Reading time: " + (read - fill) + "ns");

		// Sprawdzanie poprawności odczytanych danych:
		for(int i = 0; i < x; i++) {
			for(int j = 0; j < y; j++) {
				for(int k = 0; k < z; k++) {
					assert value == dataT[i][j][k];
				}
			}
		}
	}
}
