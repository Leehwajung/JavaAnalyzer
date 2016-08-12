package tool.compiler.java.ast.stmt;

import java.util.ArrayList;

import polyglot.ast.Node;
import polyglot.ast.TypeNode;
import polyglot.ext.jl5.types.JL5ClassType;
import polyglot.ext.jl7.ast.MultiCatch;
import polyglot.util.SerialVersionUID;
import tool.compiler.java.effect.EffectSet;
import tool.compiler.java.effect.ExnEffect;
import tool.compiler.java.util.ReportUtil;
import tool.compiler.java.util.ReportUtil.EffectSetVarGoal;
import tool.compiler.java.util.ReportUtil.EffectSetVarSource;
import tool.compiler.java.visit.EquGenerator;

/**
 * MultiCatch <: Catch <: CompoundStmt <: Stmt <: Term <: Node
 * @author LHJ
 */
public class EquGenMultiCatchExt extends EquGenCatchExt {
	private static final long serialVersionUID = SerialVersionUID.generate();
	public static final String KIND = "Multi-Catch";
	
	@Override
	public EquGenerator equGenEnter(EquGenerator v) {
		ReportUtil.enterReport(this);
//		MultiCatch multiCatch = (MultiCatch) this.node();
		
		return super.equGenEnter(v);
	}
	
	@Override
	public Node equGenLeave(EquGenerator v) {
		ReportUtil.leaveReport(this);
		MultiCatch multiCatch = (MultiCatch) this.node();
		
		ArrayList<ExnEffect> effectElems = new ArrayList<>();
		for (TypeNode alternative : multiCatch.alternatives()) {
			effectElems.add(new ExnEffect((JL5ClassType) alternative.type()));
		}
		EffectSet exnEffect = new EffectSet(effectElems);
		setFormalTypes(exnEffect);
		ReportUtil.report(exnEffect, EffectSetVarSource.New, EffectSetVarGoal.Return);
		
		return super.equGenLeave(v);
	}
	
	@Override
	public String getKind() {
		return KIND;
	}
}