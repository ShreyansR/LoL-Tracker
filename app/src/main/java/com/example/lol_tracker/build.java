package com.example.lol_tracker;

import android.content.Intent;

import java.util.List;

public class build {
    String name;
    List <String> champions;
    List <String> synergy;
    int [] imgid;

    public build(String name, List<String> champions, List<String> synergy, int[] imgid) {
        this.name = name;
        this.champions = champions;
        this.synergy = synergy;
        this.imgid = imgid;
    }

}
