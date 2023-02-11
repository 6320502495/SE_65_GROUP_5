package equipments;

import java.awt.Graphics;

import stage.Script.MGroup;

public abstract class MObject {
	public final MGroup mGroup;

	protected MObject(MGroup mGroup) {
		if (mGroup == null)
			throw new IllegalArgumentException("mGroup cannot be null");
		this.mGroup = mGroup;
	}

	public abstract void loop(Graphics g);
}
