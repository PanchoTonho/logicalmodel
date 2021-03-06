package org.colomoto.logicalmodel;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.colomoto.TestHelper;
import org.colomoto.logicalmodel.io.LogicalModelFormat;
import org.colomoto.logicalmodel.services.ServiceManager;

public class ReferenceModels {

	private static final File dir = TestHelper.getTestResource("reference_models");
	private static LogicalModelFormat format = ServiceManager.getManager().getFormat("rawfunctions");
	
	
	static {
		if (!dir.isDirectory()) {
			throw new RuntimeException("Could not find the reference model folder: "+dir.getAbsolutePath());
		}
		
		if (format == null || !format.canImport()) {
			throw new RuntimeException("Could not find the reference format");
		}
	}
	
	public static String[] getNames() {
		return dir.list();
	}
	
	public static LogicalModel getModel(String name) throws IOException {
		return format.importFile(new File(dir, name));
	}

	/**
	 * Private constructor: pure static class
	 */
	private ReferenceModels() {
	}
}
