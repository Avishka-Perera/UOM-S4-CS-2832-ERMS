package utilities;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.jetbrains.annotations.Nullable;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.Arrays;

public class Utilities {
    // extracts the data in the request to a string
    public static String requestJSONToString (HttpServletRequest request) throws IOException {
        StringBuilder sb = new StringBuilder();
        BufferedReader br = request.getReader();
        String str;
        while( (str = br.readLine()) != null ){
            sb.append(str);
        }
        return sb.toString();
    }

    public static  <T> T objFromJSONString(@Nullable String string, Class<T> classOfT ) {
        GsonBuilder builder = new GsonBuilder();
        builder.setPrettyPrinting();
        Gson gson = builder.create();
        return gson.fromJson(string, classOfT);
    }

    public static  <T> T objFromRequest( HttpServletRequest request, Class<T> classOfT ) throws IOException {
        String str = requestJSONToString(request);
        return objFromJSONString( str, classOfT );
    }

    public static boolean contains(final int[] arr, final int key) {
        return Arrays.stream(arr).anyMatch(i -> i == key);
    }
}
