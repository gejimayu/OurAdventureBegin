package entity;

import java.io.*;

public class Dugsect extends Virtumon{
	public Dugsect(int x, int y) throws IOException{
		super("Dugsect", 150, 40, 30, x, y, 2);
		tile = dugsect;
	}
}
