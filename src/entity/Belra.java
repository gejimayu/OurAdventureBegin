package entity;

import java.io.*;

public class Belra extends Virtumon {
	public Belra(int x, int y) throws IOException{
		super("Belra", 110, 20, 20, x, y, 1);
		tile = belra;
	}
}