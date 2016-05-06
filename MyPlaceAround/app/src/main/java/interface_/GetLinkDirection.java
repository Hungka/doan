package interface_;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by TranManhHung on 04-May-16.
 */
public interface GetLinkDirection {
    public String GetDirctionLink(LatLng origin, LatLng dest, String mode);
}
