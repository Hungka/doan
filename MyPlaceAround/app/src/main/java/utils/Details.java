package utils;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;

import java.util.ArrayList;

/**
 * Created by TranManhHung on 04-Apr-16.
 */
public class Details {


    public String formatted_address;
    public String name;
    public String formatted_phone_number;
    public String website;
    public String photos;
    public String rating;
    public String weekday_text;
    public ArrayList<Reviews> reviewses;
    public ArrayList<String> listImage;


    public ArrayList<String> getListImage() {
        return listImage;
    }

    public void setListImage(ArrayList<String> listImage) {
        this.listImage = listImage;
    }


    public ArrayList<Reviews> getReviewses() {
        return reviewses;
    }

    public void setReviewses(ArrayList<Reviews> reviewses) {
        this.reviewses = reviewses;
    }


    public String getFormatted_address() {
        return formatted_address;
    }

    public void setFormatted_address(String formatted_address) {
        this.formatted_address = formatted_address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFormatted_phone_number() {
        return formatted_phone_number;
    }

    public void setFormatted_phone_number(String formatted_phone_number) {
        this.formatted_phone_number = formatted_phone_number;
    }


    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getPhotos() {
        return photos;
    }

    public void setPhotos(String photos) {
        this.photos = photos;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getWeekday_text() {
        return weekday_text;
    }

    public void setWeekday_text(String weekday_text) {
        this.weekday_text = weekday_text;
    }

    //comment


}
