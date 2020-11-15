package unsw.gloriaromanus.View;

import java.io.File;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

// https://stackoverflow.com/questions/22490064/how-to-use-javafx-mediaplayer-correctly

public class musicUtils {
    public static MediaPlayer player;
    public static String currSong;
    public static void playSound(String fileName){
        if( currSong == null || !currSong.equals(fileName) ){
            currSong = fileName;
            Media m = new Media(new File("sounds/"+fileName).toURI().toString());
            player = new MediaPlayer(m);
            player.setOnEndOfMedia(new Runnable() {
                @Override
                public void run() {
                    player.seek(Duration.ZERO);
                }
            }); 
            player.play();
        }else{
            return;
        }
    }
    public static void stopSound(){
        player.stop();
    }

}
