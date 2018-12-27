/* org.agiso.castor.core.impl.ArrayCellularGrid (27-12-2018)
 * 
 * ArrayCellularGrid.java
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

import java.lang.reflect.Array;

import org.agiso.castor.core.ICellularGrid;

/**
 * 
 * @author Karol Kopacz
 * @since 1.0
 */
public class ArrayCellularGrid implements ICellularGrid {
	private int states;				// ilość możliwych stanów komórki
	private int cellBits;			// ilość bitów pamiętających stan komórki
	private int[] cellSizes;		// tablica rozmiarów komórkowych wymiarów przestrzeni
	private int[] byteSizes;		// tablica rozmiarów bajtowych wymiarów przestrzeni

	private Object gridArray;		// przestrzeń komórkowa

	private boolean notInitialized;	// status inicjalizacji

//	--------------------------------------------------------------------------
	public ArrayCellularGrid() {
		notInitialized = true;
	}
	public ArrayCellularGrid(int states, int... dimmensions) {
		notInitialized = true;
		initialize(states, dimmensions);
	}

//	--------------------------------------------------------------------------
	@Override
	public int getStates() {
		return states;
	}

	@Override
	public int[] getDimmensions() {
		return cellSizes.clone();
	}

//	--------------------------------------------------------------------------
	@Override
	public void initialize(int states, int... dimmensions) {
		this.states = states;
		this.cellBits = 1;
		this.cellSizes = dimmensions.clone();

		// Wyznaczanie ilości bitów wymaganych do przechowywania pojedynczej komórki:
		states -= 1;
		while((states >>>= 1) > 0) {
			cellBits++;
		}

		// Przeliczanie rozmiarów komórkowych przestrzeni na rozmiary bajtowe:
		byteSizes = new int[cellSizes.length];
		states = cellSizes[cellSizes.length - 1] * cellBits;
		if((states & 0x07) > 0) {
			byteSizes[cellSizes.length - 1] = (states >> 3) + 1;
		} else {
			byteSizes[cellSizes.length - 1] = (states >> 3);
		}

		if(cellSizes.length > 1) {
			for(int i = byteSizes.length - 2; i >= 0; i--) {
				byteSizes[i] = cellSizes[i];
			}
		}

		// Inicjalizacja cellSizes.length-wymiarowej przestrzeni komórkowej:
		gridArray = Array.newInstance(byte.class, byteSizes);

		notInitialized = false;
	}

	@Override
	public void fill(final int value) {
		if(notInitialized) {
			throw new IllegalStateException("Grid not initialized!");
		}

		doCellJob(new ICellJob() {
			@Override
			public void doJob(int[] coordinates) {
				ArrayCellularGrid.this.setCellValue(value, coordinates);
			}
		});
	}

//	--------------------------------------------------------------------------
	@Override
	public void setCellValue(int value, int... coordinates) {
		if(notInitialized) {
			throw new IllegalStateException("Grid not initialized!");
		}
		if(value >= states) {
			throw new IllegalStateException("Cell value to big!");
		}
		if(coordinates.length != cellSizes.length) {
			throw new IllegalStateException("Invalid coordinates!");
		}

		// Pobieranie z przestrzeni komórkowej tablicy do modyfikacji:
		Object array = gridArray;
		if(coordinates.length > 1) {
			for(int i = 0; i < coordinates.length - 1; i++) {
				try {
					array = Array.get(array, coordinates[i]);
				} catch(IndexOutOfBoundsException ioobe) {
					throw new IllegalStateException("Invalid coordinates!", ioobe);
				}
			}
		}

		// Wyznaczanie pozycji najstarszego bitu i pierwszej współrzędnej bajtowej:
		int sBit = coordinates[coordinates.length - 1] * cellBits;
		int startByte = (sBit >> 3);

		// Wyznaczanie pozycji najmłodszego bitu i ostatniej współrzędnej bajtowej
		int eBit = sBit + cellBits - 1;
		int stopByte = (eBit >> 3);

		// Obliczanie przesunięcia i wyznaczanie masek AND i OR:
		eBit = (((1 + stopByte) << 3) - eBit - 1);		// przesunięcie maski
		long om = (0x0000000000000000L | value) << eBit;
		long am = ((0xFFFFFFFFFFFFFFFFL << cellBits) << eBit | ~(~0L << eBit));

		for(int i = stopByte; i >= startByte; i--) {
			Array.setByte(array, i, (byte)(Array.getByte(array, i) & am | om));
			am >>= 8;
			om >>= 8;
		}
	}

	@Override
	public int getCellValue(int... coordinates) {
		if(notInitialized) {
			throw new IllegalStateException("Grid not initialized!");
		}
		if(coordinates.length != cellSizes.length) {
			throw new IllegalStateException("Invalid coordinates!");
		}

		// Pobieranie z przestrzeni komórkowej tablicy do odczytu:
		Object array = gridArray;
		if(coordinates.length > 1) {
			for(int i = 0; i < coordinates.length - 1; i++) {
				try {
					array = Array.get(array, coordinates[i]);
				} catch(IndexOutOfBoundsException ioobe) {
					throw new IllegalStateException("Invalid coordinates!", ioobe);
				}
			}
		}

		// Wyznaczanie pozycji najstarszego bitu i pierwszej współrzędnej bajtowej:
		int sBit = coordinates[coordinates.length - 1] * cellBits;
		int startByte = (sBit >> 3);

		// Wyznaczanie pozycji najmłodszego bitu i ostatniej współrzędnej bajtowej
		int eBit = sBit + cellBits - 1;
		int stopByte = (eBit >> 3);

		// Obliczanie przesunięcia i wyznaczanie masek AND i OR:
		eBit = (((1 + stopByte) << 3) - eBit - 1);		// przesunięcie maski
		long am = ~((0xFFFFFFFFFFFFFFFFL << cellBits) << eBit | ~(~0L << eBit));

		long value = 0x00L;
		for(int i = stopByte, j = 0; i >= startByte; i--, j++) {
			value |= ((0xFFL & (byte)(Array.getByte(array, i) & am)) << (j << 3));
			am >>= 8;
		}

		return (int)(value >>> eBit);
	}

	@Override
	public ICellularGrid createEmpty() {
		return new ArrayCellularGrid(states, cellSizes);
	}

	@Override
	public void doCellJob(ICellJob job) {
		doCellJob(job, new int[0], 0);
	}

//	--------------------------------------------------------------------------
	private void doCellJob(ICellJob job, int[] coordinates, int level) {
		int[] coords = new int[coordinates.length + 1];
		for(int c = coordinates.length - 1; c >= 0; c--) {
			coords[c] = coordinates[c];
		}

		// Jeśli nie jesteśmy na ostatnim poziomie, to schodzimy niżej:
		if(level < cellSizes.length - 1) {
			for(int index = cellSizes[level] - 1; index >= 0; index--) {
				coords[coordinates.length] = index;
				doCellJob(job, coords, level + 1);
			}
		} else {
			for(int index = cellSizes[level] - 1; index >= 0; index--) {
				coords[coordinates.length] = index;
				job.doJob(coords);
			}
		}
	}
}
