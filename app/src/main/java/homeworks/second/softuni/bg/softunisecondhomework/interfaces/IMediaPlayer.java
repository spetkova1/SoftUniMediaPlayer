package homeworks.second.softuni.bg.softunisecondhomework.interfaces;

/**
 * Created by spetkova on 9/23/16.
 */

public interface IMediaPlayer {

    void play(int songId);

    void stop();

    void fw(int songId);

    void ffw(int songId);

    void pause();

}
