package utils;

/**
 * Created by TranManhHung on 04-Apr-16.
 */
public class Reviews {

    String author;
    String cmt;

    public Reviews(String author, String cmt)
    {
        this.author =author;
        this.cmt = cmt;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getCmt() {
        return cmt;
    }

    public void setCmt(String cmt) {
        this.cmt = cmt;
    }

}
