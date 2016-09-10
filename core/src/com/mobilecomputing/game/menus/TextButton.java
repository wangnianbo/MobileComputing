package com.mobilecomputing.game.menus;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.math.Rectangle;
import com.mobilecomputing.game.Drawables.SpriteImageData;
import com.mobilecomputing.game.FontController;

/**
 * Created by Venom on 5/09/2016.
 */
public class TextButton extends Button{

    public BitmapFont selectedFont;
    public int fontSize;
    public String string;
    int textWidth;
    int textHeight;
    boolean centered=false;
    public TextButton(float x,float y,String displayedText,int fontSize,String identifier,boolean center, BitmapFont font){
        super(x,y);
        construct(displayedText,center,identifier,fontSize,font);

    }

    public void construct(String displayedText,boolean center,String identifier,int fontSize,BitmapFont font){

        this.centered=center;
        setIdentifier(identifier);
        selectedFont=font;
        this.fontSize=fontSize;
        this.string=displayedText;
        FontController.TransformFont(selectedFont,fontSize);
        GlyphLayout layout = new GlyphLayout(font, displayedText);
        textWidth = (int)layout.width;
        textHeight=(int)layout.height;
        if(center){
            shapeCollider=new Rectangle(-textWidth/2,-textHeight/2,textWidth,textHeight);
        }
        else{
            shapeCollider=new Rectangle(0,0,textWidth,textHeight);
        }
    }

    public TextButton(float x,float y,String displayedText,boolean center,String identifier,int fontSize){
        super(x,y);
        construct(displayedText,center,identifier,fontSize,FontController.defaultFont);
    }

    public TextButton(float x,float y,String displayedText,boolean center,String identifier){
        super(x,y);
        construct(displayedText,center,identifier,32,FontController.defaultFont);
    }

    @Override
    public void render(){
        super.render();
        FontController.ResetProperties();
        FontController.selectedFont=this.selectedFont;
        FontController.fontSize=this.fontSize;
        if(centered){
            FontController.DrawString(this.string,x-textWidth/2,y-textHeight/2);
        }
        else{
            FontController.DrawString(this.string,x,y);
        }
        SpriteImageData.ResetProperties();
        //SpriteImageData.DrawShape(shapeCollider,x,y);
    }

}
