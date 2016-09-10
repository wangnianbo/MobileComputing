package com.mobilecomputing.game.GameObjects.Tanks;

import com.badlogic.gdx.math.Circle;
import com.mobilecomputing.game.GameObjects.*;
import com.mobilecomputing.game.SoundController;
import com.mobilecomputing.game.UGameLogic;
import com.mobilecomputing.game.Drawables.SpriteImageData;

public class Explosion extends LegacyGameObject {
    public float initRadius = 15;
    public float radius = 1;
    public float intensity;
    public float grow = 0.9f;
    public float fade = 0.6f;
	public float alpha=1;
	public float scaleY=1;
	public float scaleX=1;

    public float baseDamage = 0.5f;
	
	
	public Explosion(LegacyGameObject creator,float x,float y,float intensity){
		super(x,y);
        grow = 0.3f;
        fade = 0.2f;
        setCreator(creator);
        shapeCollider=new Circle(0,0,10);

        this.intensity = intensity;

	}
	@Override
    public void update()
    {
		super.update();
        //Gradually fade out
        if (alpha > 0.2)
        {

            alpha = alpha - fade / ((intensity + 1) / 2);

            scaleX = scaleX + grow;
            scaleY = scaleY + grow;
            radius = initRadius * scaleX;
            ((Circle)shapeCollider).radius=((int)radius);
        }
        //Destroy the explosion instance
        else
        {
            instanceDestroy();
        }
    }
	
    //Draw the explosion
	@Override
    public void render()
    {
		
        super.render();

        String spriteName = "explosionflare1";
        SpriteImageData.ResetProperties();

        SpriteImageData imageData = SpriteImageData.GetByName(spriteName);
        SpriteImageData.scaleX=(radius * 2 / (imageData.getWidth()));
        SpriteImageData.scaleY=(radius * 2 / (imageData.getHeight()));
        SpriteImageData.Draw("explosionflare1", x, y);

    }
	@Override
	public void onDestroy(){

	}
	
	public void OnAddToWorld(){
		SoundController.PlaySound("explosion");
	}
	
    //On colliding with another object
	@Override
    public void OnCollision(LegacyGameObject o)
    {
        super.OnCollision(o);

        //Damage done, is a function of 
        float dist = (o.x - x) * (o.x - x) + (o.y - y) * (o.y - y);
        int damage = (int)(baseDamage * intensity*1.5f);

        //UGameLogic.LogMsg("Testing" + o);
        double dir=UGameLogic.dirToPoint(x,y,o.centerX(),o.centerY());
        double dirR=UGameLogic.TrueBearingsToRadians(dir);
        if (!(o instanceof Projectile))
        {

        	o.injure(this.getCreator(),damage);
            o.ApplyMomentum((float)(1000 * damage * Math.cos(dirR)), (float)(1000 * damage * -Math.sin(dirR)));
        }
        //o.Injure(new DamageSourceExplosion(this), damage*1*7,x,y);

        

        //UGameLogic.LogMsg(o+".health"+o.health);
    }

}
