package mx.gob.nl.fragment.model;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by Ramses on 10/11/14.
 */
public class WebService {

    private String sReturn;
    Context _context;
    Service _service;

    public enum Service
    {
        CATEGORIA,
        PROVEEDORES,
        SUBCATEGORIA,
        PRODUCTOS,
        PROVEEDORSUBCATEGORIA,
        FOTOS
    }

    public enum Accion
    {
        ALL,
        INSERT,
        UPDATE
    }

    public String OnLineCallWebService(Service service,String fecha)
    {
        String urlfinal = getURL(service) + "?fecha=" + fecha;
        _service=service;
        sReturn = readJSONFeed(urlfinal);
        return sReturn;
    }

    public String OnLineCallWebService(Service service)
    {
        String urlfinal = getURL(service);
        _service=service;
        sReturn =  readJSONFeed(urlfinal);
        return sReturn;
    }

    public String CallWebService(Context context,Service service,String fecha)
    {
        _context=context;
        String urlfinal = getURL(service) + "?fecha=" + fecha;
        _service=service;
        new ReadWeatherJSONFeedTask().execute(urlfinal);
        return sReturn;
    }

    public String CallWebService(Context context,Service service)
    {
        _context=context;
        String urlfinal = getURL(service);
        _service=service;
        new ReadWeatherJSONFeedTask().execute(urlfinal);
        return sReturn;
    }

    private String getURL(Service service) {

        String sResult = null;

        switch(service)
        {
            case CATEGORIA:
                sResult= WShelper.URL_BASE+WShelper.URL_CATEGORIA;
                break;
            case PRODUCTOS:
                sResult= WShelper.URL_BASE+WShelper.URL_PRODUCTOS;
                break;
            case PROVEEDORES:
                sResult= WShelper.URL_BASE+WShelper.URL_PROVEEDORES;
                break;
            case PROVEEDORSUBCATEGORIA:
                sResult= WShelper.URL_BASE+WShelper.URL_PROVEEDORSUBCATEGORIA;
                break;
            case SUBCATEGORIA:
                sResult= WShelper.URL_BASE+WShelper.URL_SUBCATEGORIA;
                break;
            case FOTOS:
                sResult= WShelper.URL_BASE+WShelper.URL_FOTOS;
                break;
            default:
                break;

        }


        return sResult;
    }

    public Object[][] readJSONToObject() {
        return readJSONToObject(Accion.ALL);
    }

    public Object[][] readJSONToObject(Accion objAccion) {
        return readJSONToObject(sReturn, objAccion);
    }

    public Object[][] readJSONToObject(String result, Accion objAccion) {
        Object[][] objResult= null;
        try {
            JSONArray jsonArray = new JSONArray(result);
            JSONObject jsonobject;
            int iAccion;
            switch(_service)
            {
                case CATEGORIA:
                    objResult = new Object[jsonArray.length()][3];
                    for (int i = 0; i < jsonArray.length(); i++) {
                        //HashMap<String, String> map = new HashMap<String, String>();
                        jsonobject = jsonArray.getJSONObject(i);
                        iAccion = jsonobject.getInt("Accion");
                        if((objAccion == Accion.ALL || objAccion == Accion.INSERT) && iAccion==0) {
                            objResult[i][0] = jsonobject.getString("Id_Categoria");
                            objResult[i][1] = jsonobject.getString("Descripcion");
                        }

                        if((objAccion == Accion.ALL || objAccion == Accion.UPDATE) && iAccion==1) {
                            objResult[i][0] = jsonobject.getString("Id_Categoria");
                            objResult[i][1] = jsonobject.getString("Descripcion");
                        }
                    }
                    break;
                case PRODUCTOS:
                    objResult = new Object[jsonArray.length()][3];
                    for (int i = 0; i < jsonArray.length(); i++) {
                        //HashMap<String, String> map = new HashMap<String, String>();
                        jsonobject = jsonArray.getJSONObject(i);
                        iAccion = jsonobject.getInt("Accion");
                        if((objAccion == Accion.ALL || objAccion == Accion.INSERT) && iAccion==0) {
                            objResult[i][0] = jsonobject.getString("Id_Categoria");
                            objResult[i][1] = jsonobject.getString("Descripcion");
                        }

                        if((objAccion == Accion.ALL || objAccion == Accion.UPDATE) && iAccion==1) {
                            objResult[i][0] = jsonobject.getString("Id_Categoria");
                            objResult[i][1] = jsonobject.getString("Descripcion");
                        }
                    }
                    break;
                case PROVEEDORES:
                    objResult = new Object[jsonArray.length()][3];
                    for (int i = 0; i < jsonArray.length(); i++) {
                        //HashMap<String, String> map = new HashMap<String, String>();
                        jsonobject = jsonArray.getJSONObject(i);
                        iAccion = jsonobject.getInt("Accion");
                        if((objAccion == Accion.ALL || objAccion == Accion.INSERT) && iAccion==0) {
                            objResult[i][0] = jsonobject.getString("Id_Categoria");
                            objResult[i][1] = jsonobject.getString("Descripcion");
                        }

                        if((objAccion == Accion.ALL || objAccion == Accion.UPDATE) && iAccion==1) {
                            objResult[i][0] = jsonobject.getString("Id_Categoria");
                            objResult[i][1] = jsonobject.getString("Descripcion");
                        }
                    }
                    break;
                case PROVEEDORSUBCATEGORIA:
                    objResult = new Object[jsonArray.length()][3];
                    for (int i = 0; i < jsonArray.length(); i++) {
                        //HashMap<String, String> map = new HashMap<String, String>();
                        jsonobject = jsonArray.getJSONObject(i);
                        iAccion = jsonobject.getInt("Accion");
                        if((objAccion == Accion.ALL || objAccion == Accion.INSERT) && iAccion==0) {
                            objResult[i][0] = jsonobject.getString("Id_Categoria");
                            objResult[i][1] = jsonobject.getString("Descripcion");
                        }

                        if((objAccion == Accion.ALL || objAccion == Accion.UPDATE) && iAccion==1) {
                            objResult[i][0] = jsonobject.getString("Id_Categoria");
                            objResult[i][1] = jsonobject.getString("Descripcion");
                        }
                    }
                    break;
                case SUBCATEGORIA:
                    objResult = new Object[jsonArray.length()][3];
                    for (int i = 0; i < jsonArray.length(); i++) {
                        //HashMap<String, String> map = new HashMap<String, String>();
                        jsonobject = jsonArray.getJSONObject(i);
                        iAccion = jsonobject.getInt("Accion");
                        if((objAccion == Accion.ALL || objAccion == Accion.INSERT) && iAccion==0) {
                            objResult[i][0] = jsonobject.getString("Id_Categoria");
                            objResult[i][1] = jsonobject.getString("Descripcion");
                        }

                        if((objAccion == Accion.ALL || objAccion == Accion.UPDATE) && iAccion==1) {
                            objResult[i][0] = jsonobject.getString("Id_Categoria");
                            objResult[i][1] = jsonobject.getString("Descripcion");
                        }
                    }
                    break;
                case FOTOS:
                    objResult = new Object[jsonArray.length()][3];
                    for (int i = 0; i < jsonArray.length(); i++) {
                        //HashMap<String, String> map = new HashMap<String, String>();
                        jsonobject = jsonArray.getJSONObject(i);
                        iAccion = jsonobject.getInt("Accion");
                        if((objAccion == Accion.ALL || objAccion == Accion.INSERT) && iAccion==0) {
                            objResult[i][0] = jsonobject.getString("Id_Categoria");
                            objResult[i][1] = jsonobject.getString("Descripcion");
                        }

                        if((objAccion == Accion.ALL || objAccion == Accion.UPDATE) && iAccion==1) {
                            objResult[i][0] = jsonobject.getString("Id_Categoria");
                            objResult[i][1] = jsonobject.getString("Descripcion");
                        }
                    }
                    break;
                default:
                    break;

            }

        } catch (Exception e) {
            Log.d("ReadWeatherJSONFeedTask", e.getLocalizedMessage());
        }

        return objResult;
    }

    public String readJSONFeed(String URL) {
        StringBuilder stringBuilder = new StringBuilder();
        HttpClient httpClient = new DefaultHttpClient();
        HttpGet httpGet = new HttpGet(URL);
        try {
            HttpResponse response = httpClient.execute(httpGet);
            StatusLine statusLine = response.getStatusLine();
            int statusCode = statusLine.getStatusCode();
            if (statusCode == 200) {
                HttpEntity entity = response.getEntity();
                InputStream inputStream = entity.getContent();
                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(inputStream));
                String line;
                while ((line = reader.readLine()) != null) {
                    stringBuilder.append(line);
                }
                inputStream.close();
            } else {
                Log.d("JSON", "Failed to download file");
            }
        } catch (Exception e) {
            Log.d("readJSONFeed", e.getLocalizedMessage());
        }
        return stringBuilder.toString();
    }

    public Context getApplicationContext() {
        return _context;
    }

    private class ReadWeatherJSONFeedTask extends AsyncTask
            <String, Void, String> {
        protected String doInBackground(String... urls) {
            return readJSONFeed(urls[0]);
        }

        protected void onPostExecute(String result) {
            try {
                JSONObject jsonObject = new JSONObject(result);
                JSONObject jsonobject;
                JSONArray jsonarray;
                JSONArray araycus;
                String urlvid;
                JSONObject jsoncustom;

                switch(_service)
                {
                    case CATEGORIA:
                            jsonarray = jsonObject.getJSONArray("");


                            for (int i = 0; i < jsonarray.length(); i++) {
                                //HashMap<String, String> map = new HashMap<String, String>();
                                jsonobject = jsonarray.getJSONObject(i);
                                jsoncustom = jsonobject.getJSONObject("custom_fields");
                                araycus = jsoncustom.getJSONArray("dp_video_poster");
                                urlvid = araycus.getString(i);
                            }
                        break;
                   case PRODUCTOS:
                            jsonarray = jsonObject.getJSONArray("");

                            for (int i = 0; i < jsonarray.length(); i++) {
                                //HashMap<String, String> map = new HashMap<String, String>();
                                jsonobject = jsonarray.getJSONObject(i);
                                jsoncustom = jsonobject.getJSONObject("custom_fields");
                                araycus = jsoncustom.getJSONArray("dp_video_poster");
                                urlvid = araycus.getString(i);
                            }
                        break;
                    case PROVEEDORES:
                        jsonarray = jsonObject.getJSONArray("");

                        for (int i = 0; i < jsonarray.length(); i++) {
                            //HashMap<String, String> map = new HashMap<String, String>();
                            jsonobject = jsonarray.getJSONObject(i);

                            jsoncustom = jsonobject.getJSONObject("custom_fields");
                            araycus = jsoncustom.getJSONArray("dp_video_poster");
                            urlvid = araycus.getString(i);
                        }
                        break;
                    case PROVEEDORSUBCATEGORIA:
                        jsonarray = jsonObject.getJSONArray("");

                        for (int i = 0; i < jsonarray.length(); i++) {
                            //HashMap<String, String> map = new HashMap<String, String>();
                            jsonobject = jsonarray.getJSONObject(i);

                            jsoncustom = jsonobject.getJSONObject("custom_fields");
                            araycus = jsoncustom.getJSONArray("dp_video_poster");
                            urlvid = araycus.getString(i);
                        }
                        break;
                    case SUBCATEGORIA:

                         jsonarray = jsonObject.getJSONArray("");

                        for (int i = 0; i < jsonarray.length(); i++) {
                            //HashMap<String, String> map = new HashMap<String, String>();
                            jsonobject = jsonarray.getJSONObject(i);

                            jsoncustom = jsonobject.getJSONObject("custom_fields");
                            araycus = jsoncustom.getJSONArray("dp_video_poster");
                            urlvid = araycus.getString(i);
                        }
                        break;
                    case FOTOS:
                         jsonarray = jsonObject.getJSONArray("");

                        for (int i = 0; i < jsonarray.length(); i++) {
                            //HashMap<String, String> map = new HashMap<String, String>();
                            jsonobject = jsonarray.getJSONObject(i);

                            jsoncustom = jsonobject.getJSONObject("custom_fields");
                            araycus = jsoncustom.getJSONArray("dp_video_poster");
                            urlvid = araycus.getString(i);
                        }
                        break;
                    default:
                        break;

                }
                        } catch (JSONException e) {
                            e.printStackTrace();

            } catch (Exception e) {
                Log.d("ReadWeatherJSONFeedTask", e.getLocalizedMessage());
            }
        }
    }

    /*private class ReadJSONFeed extends AsyncTask<String, String, String> {
        protected void onPreExecute() {}
        @Override
        protected String doInBackground(String... urls) {
            HttpClient httpclient = new DefaultHttpClient();
            StringBuilder builder = new StringBuilder();
            HttpPost httppost = new HttpPost(urls[0]);
            try {
                HttpResponse response = httpclient.execute(httppost);
                StatusLine statusLine = response.getStatusLine();
                int statusCode = statusLine.getStatusCode();
                if (statusCode == 200) {
                    HttpEntity entity = response.getEntity();
                    InputStream content = entity.getContent();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(content));
                    String line;
                    while ((line = reader.readLine()) != null) {
                        builder.append(line);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return builder.toString();
        }

    }

    protected void onPostExecute(String result) {
        String city = "";
        String weatherInfo="Weather Report of "+city +" is: \n";
        try{
            JSONObject jsonObject = new JSONObject(result);
            JSONObject jscoordObject = new JSONObject(jsonObject.getString("coord"));
            weatherInfo+="Longitude: "+jscoordObject.getString("lon")+"\n";
            weatherInfo+="Latitude: "+jscoordObject.getString("lat")+"\n";
            JSONArray jsweatherObject = new JSONArray(jsonObject.getString("weather"));
            JSONObject jweatherObject = jsweatherObject.getJSONObject(0);
            weatherInfo+="Clouds: "+jweatherObject.getString("description")+"\n";
            JSONObject jsmainObject = new JSONObject(jsonObject.getString("main"));
            weatherInfo+="Humidity: "+jsmainObject.getString("humidity")+"% \n";
            weatherInfo+="Atmospheric Pressure: "+jsmainObject.getString("pressure")+"hPa \n";
            float temp=Float.parseFloat(jsmainObject.getString("temp"));
            temp = temp - (float) 273.15;
            NumberFormat df = NumberFormat.getNumberInstance();
            df.setMaximumFractionDigits(2);
            weatherInfo+="Temperature: "+ String.valueOf(df.format(temp)) +" C\n";
            JSONObject jswindObject = new JSONObject(jsonObject.getString("wind"));
            weatherInfo+="Wind Speed: "+jswindObject.getString("speed")+"mps \n";
        }
        catch (JSONException e) {
            e.printStackTrace();
        }
        //TextView resp = (TextView) findViewById(R.id.response);
        //if(weatherInfo.trim().length() >0 )
            //resp.setText(weatherInfo);
        //else
        //    resp.setText("Sorry no match found");

        sReturn = result;
    }*/


}
