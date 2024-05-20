
public class Song {

    //Declareing song class data memebers
    private final String songName;
    private final String artistName;
    private final int playingTime;
    private final String videoFileType;

    //Song constructor to add the values
    public Song(String songName, String artistName, int playingTime, String videoFileType) {
        this.songName = songName;
        this.artistName = artistName;
        this.playingTime = playingTime;
        this.videoFileType = videoFileType;
    }

    //returns song name
    public String getSongName() {
        return songName;
    }

    //reruns artist name
    public String getArtistName() {
        return artistName;
    }

    //returns song playing time
    public int getPlayingTime() {
        return playingTime;
    }

    //returns video type file e.g. for song its .mp4
    public String getVideoFileType() {
        return videoFileType;
    }

    //returns all the values in string format. The tabs were used in the sample song file thereofore, i have used the same way to format them.
    @Override
    public String toString() {
        return songName + "\t" + artistName + "\t" + playingTime + "\t" + videoFileType;
    }
}
