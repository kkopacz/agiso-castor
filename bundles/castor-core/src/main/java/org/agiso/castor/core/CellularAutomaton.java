package org.agiso.castor.core;

import org.agiso.castor.core.ICellularGrid.ICellJob;

/**
 * Automat komórkowy. Opisywany przez: przestrzeń, sąsiedztwo, regułę.
 */
public class CellularAutomaton implements ICellularAutomaton {
	private ICellularGrid grid;
	private ICellularNhood nhood;
	private ICellularRule rule;

//	--------------------------------------------------------------------------
	public CellularAutomaton(ICellularGrid grid, ICellularNhood nhood, ICellularRule rule) {
		this.grid = grid;
		this.nhood = nhood;
		this.rule = rule;
	}

//	--------------------------------------------------------------------------
	@Override
	public int getCellValue(int... coordinates) {
		return grid.getCellValue(coordinates);
	}
	@Override
	public void setCellValue(int value, int... coordinates) {
		grid.setCellValue(value, coordinates);
	}

	@Override
	public ICellularGrid step() {
		final ICellularGrid newGrid = grid.createEmpty();

		// Dla każdej komórki przestrzeni wyznaczamy jej nową wartość:
		grid.doCellJob(new ICellJob() {
			@Override
			public void doJob(int[] sCoords) {
//				System.out.print("( ");
//				for(int s : sCoords) { System.out.print(s + " "); }
//				System.out.print(") -> [");

				// Pobieramy sąsiedztwo:
				int[][] vCoords = nhood.getCoordinates();

				// Wartości komórek z sąsiedztwa:
				int[] cValues = new int[vCoords.length];

				// Rozmiary przestrzeni:
				int[] dimmensions = grid.getDimmensions();

				// Dla każdej komórki przestrzeni na podstawie współrzędnych komórek
				// sąsiedztwa ustalamy przechowywane w nich wartości:
				for(int i = vCoords.length - 1; i >= 0; i--) {
					int[] cCoords = vCoords[i].clone();
					for(int j = cCoords.length - 1; j >= 0; j--) {
						int dSize = dimmensions[j];				// rozmiar j-tego wymiaru
						int coord = sCoords[j] + cCoords[j];
						if(coord < 0) {
							coord = dSize + coord;
						} else if(coord >= dSize) {
							coord = coord - dSize;
						}
						cCoords[j] = coord;
					}

//					System.out.print("( ");
//					for(int c : cCoords) { System.out.print(c + " "); }
//					System.out.print(")");
					cValues[i] = grid.getCellValue(cCoords);
				}
//				System.out.println("]");

				newGrid.setCellValue(rule.calcCellValue(cValues), sCoords);
			}
		});

		return grid = newGrid;
	}
}
