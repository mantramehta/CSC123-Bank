package com.usman.csudh.bank.core;

import java.io.InputStream;
import java.io.File;
import java.io.FileInputStream;

public class InputSourceFile extends CSVReader {
	String fileLoc;

	public InputSourceFile(String fileLoc) {
		super();
		this.fileLoc = fileLoc;
	}

	@Override
	protected InputStream getInputStream() throws Exception {
		return new FileInputStream(new File(this.fileLoc));
	}
}
