package com.mehdi.rosary;

public class SongsDetail {

    private String name;
    private String chikh;
    private int back;
    private String song_name;

    public SongsDetail(String name, String chikh,int back, String song_name ){
        this.name = name;
        this.chikh = chikh;
        this.back = back;
        this.song_name = song_name;
    }

    public String getSong_name() {
        return song_name;
    }

    public String getName() {
        return name;
    }

    public int getBack() {
        return back;
    }

    public String getChikh() {
        return chikh;
    }
}
