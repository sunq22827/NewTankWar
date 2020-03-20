package com.sunq22827.newtankwar;

import javax.swing.*;
import java.awt.*;

public class Tools {
    public static Image getImage(String imageName){
         return new ImageIcon("assets/images/" + imageName).getImage();
    }
}
