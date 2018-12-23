/* org.agiso.castor.core.ICellularGrid (23-12-2018)
 * 
 * ICellularGrid.java
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
package org.agiso.castor.core;

/**
 * Cellular automaton grid interface.
 * 
 * @author Karol Kopacz
 * @since 1.0
 */
public interface ICellularGrid {
	public interface ICellJob {
		public void doJob(int[] coordinates);
	}

//	--------------------------------------------------------------------------
	/**
	 * Gets the number of possible states of a single cellular automaton grid
	 * cell.
	 * 
	 * @return A value specifying the number of possible single cell states,
	 *     or <code>null</code> if the grid has not been initiated.
	 */
	public int getStates();

	/**
	 * Gets the array of cellular automaton grid dimensions sizes.
	 * 
	 * @return An array containing cellular automaton grid dimensions sizes,
	 *     or <code>null</code> if the grid has not been initiated.
	 */
	public int[] getDimmensions();

//	--------------------------------------------------------------------------
	/**
	 * Initializes the cellular grid with the dimensions specified in the
	 * <code>dimmensions</code> array and the possible number of single cell
	 * states defined by the <code>states</code> variable.
	 * 
	 * This method should be called before performing any operations on the
	 * cellular grid.
	 * 
	 * @param states Number of possible states of a single cellular grid cell.
	 * @param dimmensions Array of cellular automaton grid dimensions sizes.
	 */
	public void initialize(int states, int... dimmensions);

	/**
	 * Fills the cellular grid with the value specified by the variable <code>
	 * value</code>.
	 * 
	 * @param value Value to set in all cellular grid cells.
	 * 
	 * @throws IllegalStateException if cellular grid has not been initialized
	 *     with {@link #initialize(int, int...)} method.
	 * 
	 * @throws IllegalStateException if <code>value</code> is greater than the
	 *     maximum value of the cell specified during grid initialization.
	 */
	public void fill(int value);

//	--------------------------------------------------------------------------
	/**
	 * Sets the single cell value in the cellular grid.
	 * 
	 * @param value New cell value to set.
	 * @param coordinates Coordinates of the cell to be set.
	 * 
	 * @throws IllegalStateException if cellular grid has not been initialized
	 *     with {@link #initialize(int, int...)} method.
	 * 
	 * @throws IllegalStateException if <code>value</code> is greater than the
	 *     maximum value of the cell specified during grid initialization.
	 * 
	 * @throws IllegalStateException if cell coordinates are incorrect (do not
	 *     correspond to the number of dimensions or their sizes determined
	 *     during grid initialization).
	 */
	public void setCellValue(int value, int... coordinates);

	/**
	 * Gets the value of a single grid cell with specified coordinates.
	 * 
	 * @param coordinates Coordinates of the cell to be get.
	 * @return Cell value.
	 * 
	 * @throws IllegalStateException if cellular grid has not been initialized
	 *     with {@link #initialize(int, int...)} method.
	 * 
	 * @throws IllegalStateException if cell coordinates are incorrect (do not
	 *     correspond to the number of dimensions or their sizes determined
	 *     during grid initialization).
	 */
	public int getCellValue(int... coordinates);

	public ICellularGrid createEmpty();

	public void doCellJob(ICellJob job);
}