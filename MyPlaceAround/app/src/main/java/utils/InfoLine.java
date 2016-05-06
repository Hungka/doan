package utils;

import java.util.HashMap;
import java.util.List;

/**
 * Created by TranManhHung on 20-Apr-16.
 */
public class InfoLine {
    public List<List<HashMap<String, String>>> getListPoint() {
        return listPoint;
    }

    public void setListPoint(List<List<HashMap<String, String>>> listPoint) {
        this.listPoint = listPoint;
    }

    public List<Line> getListLine() {
        return listLine;
    }

    public void setListLine(List<Line> listLine) {
        this.listLine = listLine;
    }

    public List<List<HashMap<String, String>>> listPoint;
    public List<Line> listLine;
}
