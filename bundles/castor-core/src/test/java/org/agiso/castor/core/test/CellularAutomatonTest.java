package org.agiso.castor.core.test;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.agiso.castor.core.CellularAutomaton;
import org.agiso.castor.core.ElementaryCellularAutomaton;
import org.agiso.castor.core.ICellularAutomaton;
import org.agiso.castor.core.TotalisticCellularAutomaton;
import org.testng.annotations.Test;

/**
 * 
 */
public class CellularAutomatonTest {
//	@Test
	public void testElementaryCellularAutomaton() throws Exception {
		int[] nhood = {-1, 0, 1};
		int[] states = {
				Color.WHITE.getRGB(),
				Color.BLACK.getRGB()
		};
		int width = 601;
		int steps = 300;
		int rule = 30;

		ICellularAutomaton automaton = new ElementaryCellularAutomaton(
				states.length, nhood, rule, width);
		automaton.setCellValue(1, width/2);

		BufferedImage img = new BufferedImage(width, steps, BufferedImage.TYPE_INT_RGB);

		long start = System.currentTimeMillis();
		for(int s = 0; s < steps; s++) {
			for(int x = 0; x < width; x++) {
				img.setRGB(x, s, states[automaton.getCellValue(x)]);
			}
			automaton.step();
		}
		System.out.println("Czas wykonania: " + (System.currentTimeMillis() - start));

		saveImg(img, "png", rule + ".png");
	}

	@Test
	public void testTotalisticCellularAutomaton() throws Exception {
		int[] nhood = {-1, 0, 1};
		int[] states = {
				Color.WHITE.getRGB(),
				Color.BLUE.getRGB(),
				Color.BLACK.getRGB()
		};
		int width = 5001;
		int steps = 5000;
		int rule = 1635;

		// Tworzenie automatu:
		CellularAutomaton automaton = new TotalisticCellularAutomaton(
				states.length, nhood, rule, width);
		automaton.setCellValue(1, width/2);

		BufferedImage img = new BufferedImage(width, steps, BufferedImage.TYPE_INT_RGB);

		long start = System.currentTimeMillis();
		for(int s = 0; s < steps; s++) {
			for(int x = 0; x < width; x++) {
				img.setRGB(x, s, states[automaton.getCellValue(x)]);
			}
			automaton.step();
		}
		System.out.println("Czas wykonania: " + (System.currentTimeMillis() - start));

		saveImg(img, "png", rule + ".png");
	}

	private void saveImg(BufferedImage img, String format, String fileName) {
		try {
			File f = new File(fileName);
			ImageIO.write(img, format, f);
		} catch(IOException e){
			System.out.println("Error: " + e);
		}
	}
}
