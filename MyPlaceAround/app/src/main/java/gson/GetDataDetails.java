package gson;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;

import com.example.tranmanhhung.myplacearound.DetailsActivity;
import com.example.tranmanhhung.myplacearound.R;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import adapter.ImageDetailsAdapter;
import adapter.ReviewsAdapter;
import fragment.ImageViewFragment;
import fragment.ReviewsFragment;
import utils.Details;
import utils.Reviews;

/**
 * Created by TranManhHung on 04-Apr-16.
 */
public class GetDataDetails extends AsyncTask<String, Void, Details> {
    ArrayList<String> arrayListIamge;
    Context mContext;
    TextView tvPhone;
    TextView tvWeb;


    public GetDataDetails(Context context) {
        mContext = context;
    }

    public GetDataDetails(Context context, TextView tvPhone, TextView tvWeb) {
        mContext = context;
        this.tvPhone = tvPhone;
        this.tvWeb = tvWeb;

    }

    @Override
    protected Details doInBackground(String... params) {

        Details details = new Details();

        try {
            String data = downloadUrl(params[0]);
            Gson gson = new Gson();
            DataJsonDetails myGsonDetails = gson.fromJson(data, DataJsonDetails.class);
            Log.e("Day la Json Lay Duoc", myGsonDetails.result.toString());
            JsonElement jsonElementDetails = myGsonDetails.result;

            try {
                ResultsDetails myresults = gson.fromJson(jsonElementDetails, ResultsDetails.class);
                //String icon = myresults.formatted_address.toString();
                // String place_id = myresults.place_id.toString();

                String website = myresults.website.toString();
                website =website.replace("\"", "");
                String phone = myresults.formatted_phone_number.toString();
                phone =phone.replace("\"", "");
                //String address = myresults.formatted_address.toString();
                details.setWebsite(website);
                details.setFormatted_phone_number(phone);

                try {
                    String weekday_text = myresults.weekday_text.toString();
                    details.setWeekday_text(weekday_text);
                } catch (Exception e) {
                    Log.e("Ngay lam viec", " Khong co ngay");
                }
                try {

                    JsonArray reviews = myresults.reviews;
                    ArrayList<Reviews> review = new ArrayList<>();
                    for (JsonElement mElement : reviews) {
                        ReviewsDetails reviewsDetails = gson.fromJson(mElement, ReviewsDetails.class);
                        String author = reviewsDetails.author_name.toString();
                        author =author.replace("\"", "");
                        String textcmt = reviewsDetails.text.toString();
                        textcmt = textcmt.replace("\"", "");
                        Reviews rv = new Reviews(author, textcmt);
                        review.add(rv);
                    }
                    details.setReviewses(review);
                } catch (Exception e) {
                    Log.e("Khong co Reviews", "No reviews");

                }

                try {
                    JsonArray linkImage = myresults.photos;
                    arrayListIamge = new ArrayList<>();
                    for (JsonElement jsonElement : linkImage) {
                        Gson gson1 = new Gson();
                        String photo_references = gson1.fromJson(jsonElement, Photo.class).photo_reference.toString();
                        while (photo_references.contains("\"")) {
                            photo_references = photo_references.replace("\"", "");
                        }

                        String link = "https://maps.googleapis.com/maps/api/place/photo?maxwidth=400&photoreference="
                                + photo_references + "&key=AIzaSyBrKcTILPHqW09IOvVmYqn5RnWUjJVXZGI";
                        arrayListIamge.add(link);
                    }
                    details.setListImage(arrayListIamge);

                } catch (Exception e) {
                    Log.e("Loi o Image", " Error");
                }
            } catch (Exception e) {
                e.printStackTrace();

            }

        } catch (Exception e) {
            Log.e("Loi o Image", " Error");
        }
        return details;
    }

    @Override
    protected void onPostExecute(Details details) {
        super.onPostExecute(details);

        try {
            tvPhone.setText(details.getFormatted_phone_number());

        } catch (Exception e) {
            Log.e("Loi o Phone", e.toString());
        }
        try {
            tvWeb.setText(details.getWebsite());
        } catch (Exception e) {
            Log.e("Khong co website", e.toString());
        }

        try {
            if(details.getReviewses().size() == 0 || details.getReviewses() == null)
            {
                DetailsActivity.count = 1;
            }
            else {
                ReviewsAdapter reviewsAdapter = new ReviewsAdapter(mContext, R.layout.layout_items_reviews, details.getReviewses());
                ReviewsFragment.lvReviews.setAdapter(reviewsAdapter);
            }
        } catch (Exception e) {
            Log.e("Khong co comment", e.toString());
        }

        try {
            if(details.getListImage().size() == 0 || details.getListImage() == null)
            {
                DetailsActivity.count = 1;
            }
            else {
                ImageDetailsAdapter imgAdapter = new ImageDetailsAdapter(mContext, R.layout.layout_items_details_image, details.getListImage());
                ImageViewFragment.lvImage.setAdapter(imgAdapter);
            }
        } catch (Exception e) {
            Log.e("Khong co image", e.toString());
        }

    }


    protected String downloadUrl(String strUrl) throws IOException {
        String data = "";
        InputStream iStream = null;
        HttpURLConnection urlConnection = null;
        try {
            URL url = new URL(strUrl);

            // Creating an http connection to communicate with url
            urlConnection = (HttpURLConnection) url.openConnection();
            // Connecting to url
            urlConnection.connect();
            // Reading data from url
            iStream = urlConnection.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(iStream));

            StringBuffer sb = new StringBuffer();

            String line = "";
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }

            data = sb.toString();

            br.close();

        } catch (Exception e) {
            Log.d("Exception while downloading url", e.toString());
        }
        return data;
    }


}
