package com.mobilecomputing.game.shareScores;

import com.mobilecomputing.game.AndroidLauncher;

/**
 * Created by Bill on 10/10/2016.
 */
public class AndroidShareScores implements ShareScores

{
    private boolean isShared = false;
    private AndroidLauncher androidLauncher;
    public AndroidShareScores(AndroidLauncher androidLauncher){
        this.androidLauncher = androidLauncher;
    }
    @Override
    public void shareSconresOnSocialMedia(String message) {
        androidLauncher.shareScores(message);
    }

    @Override
    public boolean isShared() {
        return isShared;
    }

    @Override
    public void alreadyShared() {
        isShared = true;
    }
}
