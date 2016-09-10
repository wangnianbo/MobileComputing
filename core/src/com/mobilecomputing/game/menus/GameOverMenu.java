package com.mobilecomputing.game.menus;

import com.mobilecomputing.game.Controller;
import com.mobilecomputing.game.FontController;
import com.mobilecomputing.game.UGameLogic;

/**
 * Created by Venom on 5/09/2016.
 */
public class GameOverMenu extends Menu {


    public GameOverMenu(float x,float y){
        super(x,y);
        addElement(new TextButton(Controller.projectionWidth/2,Controller.projectionHeight/3,"Return to Menu",true,"returntomenu"));
        addElement(new TextButton(Controller.projectionWidth/2,Controller.projectionHeight*2/3,"High Scores",true,"highscores"));
    }

    @Override
    public void receiveMessage(MenuElement sender,String msg){
        super.receiveMessage(sender, msg);
        msg=msg.toLowerCase();

        switch(msg){
            case "returntomenu":
                UGameLogic.LogMsg("returntomenu");
                Controller.swapGameMode(Controller.GameMode.SPLASH);
                break;
            case "highscores":
                UGameLogic.LogMsg("highscores");
                Controller.swapGameMode(Controller.GameMode.SPLASH);
                break;
        }

    }
    @Override
    public void render(){
        super.render();
        FontController.ResetProperties();
        FontController.fontSize=40;
        FontController.DrawString("Game Over",Controller.projectionWidth/2,Controller.projectionHeight/8);
        FontController.fontSize=20;

        if(Controller.world!=null && Controller.world.activeCharacter!=null)
            FontController.DrawString("SCORE "+Controller.world.activeCharacter.getScore(),Controller.projectionWidth/2,Controller.projectionHeight/5);

    }
}
