package utils;

import android.graphics.drawable.Drawable;

/**
 * Created by TranManhHung on 27-Mar-16.
 */
public class Type {


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Drawable getImage() {
        return image;
    }

    public void setImage(Drawable image) {
        this.image = image;
    }

    Drawable image;
    String name;

}
