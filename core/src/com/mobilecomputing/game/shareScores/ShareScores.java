package com.mobilecomputing.game.shareScores;

/**
 * Share Scores Interface
 * Created by Bill on 10/10/2016.
 */
public interface ShareScores {

    /**
     * Share message
     * @param message
     */
    public void shareSconresOnSocialMedia(String message);

    /**
     * which is already shared
     * @return
     */
    public boolean isShared();

    /**
     * Which is already shared
     */
    public void alreadyShared();
}
