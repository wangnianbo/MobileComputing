package com.mobilecomputing.game.WormSkins;

import com.badlogic.gdx.graphics.Color;
import com.mobilecomputing.game.GameObjects.WormHead;
import com.mobilecomputing.game.GameObjects.WormSegment;

public abstract class WormSkin {

	public abstract void subRenderHead(WormHead head);
	public abstract void subRenderTailSegment(WormSegment segment);

}


