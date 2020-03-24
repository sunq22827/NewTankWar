package com.sunq22827.newtankwar;

import java.awt.*;

public enum  Direction {

    UP("U"),
    DOWN("D"),
    LEFT("L"),
    RIGHT("R"),

    LEFT_UP("LU"),
    LEFT_DOWN("LD"),
    RIGHT_DOWN("RD"),
    RIGHT_UP("RU");

    private final String abbrev;

    Direction(String abbrev) {
        this.abbrev = abbrev;
    }

    Image getImage(String prefix){
       return Tools.getImage(prefix + abbrev + ".gif");
    }
}
