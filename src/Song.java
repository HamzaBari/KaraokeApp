
public class Song {

    private final String songName;
    private final String artistName;
    private final int playingTime;
    private final String videoFileType;

    public Song(String songName, String artistName, int playingTime, String videoFileType) {
        this.songName = songName;
        this.artistName = artistName;
        this.playingTime = playingTime;
        this.videoFileType = videoFileType;
    }

    public String getSongName() {
        return songName;
    }

    public String getArtistName() {
        return artistName;
    }

    public int getPlayingTime() {
        return playingTime;
    }

    public String getVideoFileType() {
        return videoFileType;
    }

    @Override
    public String toString() {
        return songName + "\t" + artistName + "\t" + playingTime + "\t" + videoFileType;
    }

}
