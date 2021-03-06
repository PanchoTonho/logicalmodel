package org.colomoto.logicalmodel.tool.reduction;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.colomoto.TestHelper;
import org.colomoto.logicalmodel.LogicalModel;
import org.colomoto.logicalmodel.NodeInfo;
import org.colomoto.logicalmodel.io.LogicalModelFormat;
import org.colomoto.logicalmodel.io.rawfunctions.TextFunctionFormat;
import org.colomoto.logicalmodel.services.ServiceManager;
import org.junit.Test;

public class TestReduction {

	@Test
	public void testCoreReduction() throws IOException {
		File f = TestHelper.getTestResource("simpleFunctions.txt");
		LogicalModelFormat format = ServiceManager.getManager().getFormat(TextFunctionFormat.ID);
		LogicalModel model = format.importFile(f);

		List<NodeInfo> core = model.getNodeOrder();
		List<NodeInfo> extra = model.getExtraComponents();
		
		int nbCore = core.size();
		int nbExtra = extra.size();

		assertEquals(5, nbCore);
		assertEquals(0, nbExtra);

		ModelReducer reducer = new ModelReducer(model);
		int[] toRemove = new int[] {3,4};
		for (int idx: toRemove) {
			reducer.remove(idx);
		}
		LogicalModel reducedModel = reducer.getModel();
		
		core = reducedModel.getNodeOrder();
		extra = reducedModel.getExtraComponents();
		
		assertEquals(nbCore-toRemove.length, core.size());
		assertEquals(nbExtra+toRemove.length, extra.size());
	}

	@Test
	public void testOutputReduction() throws IOException {
		checkOutputReduction("simpleFunctions.txt", 1, 1);
	}
	
	public void checkOutputReduction(String name, int expectedOutputs, int expectedPseudoOutputs) throws IOException {
		LogicalModelFormat format = ServiceManager.getManager().getFormat(TextFunctionFormat.ID);

		LogicalModel model = format.importFile(TestHelper.getTestResource(name));
		List<NodeInfo> core = model.getNodeOrder();
		List<NodeInfo> extra = model.getExtraComponents();
		int nbCore = core.size();
		int nbExtra = extra.size();

		assertEquals(5, nbCore);
		assertEquals(0, nbExtra);

		ModelReducer reducer = new ModelReducer(model);
		int removed = reducer.removePseudoOutputs();
		assertEquals(expectedPseudoOutputs, removed);
		
		LogicalModel reducedModel = reducer.getModel();
		core = reducedModel.getNodeOrder();
		extra = reducedModel.getExtraComponents();
		
		int eRemoved = expectedOutputs + expectedPseudoOutputs;
		assertEquals(nbCore-eRemoved, core.size());
		assertEquals(nbExtra+eRemoved, extra.size());
	}

}
