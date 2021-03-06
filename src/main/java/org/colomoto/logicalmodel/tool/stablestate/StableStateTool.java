package org.colomoto.logicalmodel.tool.stablestate;

import java.io.IOException;

import org.colomoto.logicalmodel.LogicalModel;
import org.colomoto.logicalmodel.tool.AbstractTool;
import org.colomoto.logicalmodel.tool.LogicalModelTool;
import org.colomoto.mddlib.MDDManager;
import org.colomoto.mddlib.PathSearcher;
import org.mangosdk.spi.ProviderFor;

@ProviderFor(LogicalModelTool.class)
public class StableStateTool extends AbstractTool {

	public StableStateTool() {
		super("stable", "Search stable states", true);
	}

	@Override
	public void run(LogicalModel model) {
		StableStateSearcher ssearcher = new StableStateSearcher(model);
		int stable = ssearcher.getResult();
		MDDManager ddm = ssearcher.getMDDManager();
		
		PathSearcher psearcher = new PathSearcher(ddm, 1);
		int[] path = psearcher.setNode(stable);
		for (int v: psearcher) {
			for (int i: path) {
				System.out.print(i+" ");
			}
			System.out.println();
		}
	}

}
