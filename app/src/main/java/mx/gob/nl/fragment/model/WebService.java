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
                    objResult = new Object[jsonArray.length()][2];
                    break;
                case PRODUCTOS:
                    objResult = new Object[jsonArray.length()][13];
                    break;
                case PROVEEDORES:
                    objResult = new Object[jsonArray.length()][25];
                    break;
                case PROVEEDORSUBCATEGORIA:
                    objResult = new Object[jsonArray.length()][2];
                    break;
                case SUBCATEGORIA:
                    objResult = new Object[jsonArray.length()][3];
                    break;
                case FOTOS:
                    objResult = new Object[jsonArray.length()][2];
                    break;
                default:
                    break;
            }

            for (int i = 0; i < jsonArray.length(); i++) {
                jsonobject = jsonArray.getJSONObject(i);
                iAccion = jsonobject.getInt("Accion");

                if(objAccion == Accion.ALL) {
                    setValues(objResult,jsonobject,i,_service);
                }

                if(objAccion == Accion.INSERT && iAccion==0) {
                    setValues(objResult,jsonobject,i,_service);
                }

                if(objAccion == Accion.UPDATE && iAccion==1) {
                    setValues(objResult,jsonobject,i,_service);
                }
            }

        } catch (Exception e) {
            Log.d("ReadWeatherJSONFeedTask", e.getLocalizedMessage());
        }

        return objResult;
    }

    private void setValues(Object[][] objResult, JSONObject jsonobject, int i,Service service) throws JSONException {


        switch(service)
        {
            case CATEGORIA:
                objResult[i][0] = setNull(jsonobject.getString(DBhelper.CATEGORIA_ID_CATEGORIA));
                objResult[i][1] = setNull(jsonobject.getString(DBhelper.CATEGORIA_DESCRIPCION));
                break;
            case PRODUCTOS:
                objResult[i][0] = setNull(jsonobject.getString(DBhelper.PRODUCTO_ID_PRODUCTO));
                objResult[i][1] = setNull(jsonobject.getString(DBhelper.PRODUCTO_ID_PROVEEDOR));
                objResult[i][2] = setNull(jsonobject.getString(DBhelper.PRODUCTO_NOMBRE));
                objResult[i][3] = setNull(jsonobject.getString(DBhelper.PRODUCTO_DESCRIPCIONCORTA));
                objResult[i][4] = setNull(jsonobject.getString(DBhelper.PRODUCTO_DESCRIPCION));
                objResult[i][5] = setNull(jsonobject.getString(DBhelper.PRODUCTO_PRECIOMENUDEO));
                objResult[i][6] = setNull(jsonobject.getString(DBhelper.PRODUCTO_PRECIOMAYOREO));
                objResult[i][7] = setNull(jsonobject.getString(DBhelper.PRODUCTO_FOTO1));
                objResult[i][8] = setNull(jsonobject.getString(DBhelper.PRODUCTO_FOTO2));
                objResult[i][9] = setNull(jsonobject.getString(DBhelper.PRODUCTO_FOTO3));
                objResult[i][10] = setNull(jsonobject.getString(DBhelper.PRODUCTO_VIGENCIA));
                objResult[i][11] = setNull(jsonobject.getString(DBhelper.PRODUCTO_ACTIVO),1);
                objResult[i][12] = setNull(jsonobject.getString(DBhelper.PRODUCTO_ORDEN),0);
                break;
            case PROVEEDORES:
                objResult[i][0] = setNull(jsonobject.getString(DBhelper.PROVEEDOR_ID_PROVEEDOR));
                objResult[i][1] = setNull(jsonobject.getString(DBhelper.PROVEEDOR_NOMBRE));
                objResult[i][2] = setNull(jsonobject.getString(DBhelper.PROVEEDOR_PRESENTACION));
                objResult[i][3] = setNull(jsonobject.getString(DBhelper.PROVEEDOR_PRESENTACIONCORTA));
                objResult[i][4] = setNull(jsonobject.getString(DBhelper.PROVEEDOR_SERVICIO1));
                objResult[i][5] = setNull(jsonobject.getString(DBhelper.PROVEEDOR_SERVICIO2));
                objResult[i][6] = setNull(jsonobject.getString(DBhelper.PROVEEDOR_SERVICIO3));
                objResult[i][7] = setNull(jsonobject.getString(DBhelper.PROVEEDOR_TELEFONO1));
                objResult[i][8] = setNull(jsonobject.getString(DBhelper.PROVEEDOR_TELEFONO2));
                objResult[i][9] = setNull(jsonobject.getString(DBhelper.PROVEEDOR_TWITER));
                objResult[i][10] = setNull(jsonobject.getString(DBhelper.PROVEEDOR_FACEBOOK));
                objResult[i][11] = setNull(jsonobject.getString(DBhelper.PROVEEDOR_SITIOWEB));
                objResult[i][12] = setNull(jsonobject.getString(DBhelper.PROVEEDOR_MAIL));
                objResult[i][13] = setNull(jsonobject.getString(DBhelper.PROVEEDOR_CALLE));
                objResult[i][14] = setNull(jsonobject.getString(DBhelper.PROVEEDOR_NUMEROEXTERIOR));
                objResult[i][15] = setNull(jsonobject.getString(DBhelper.PROVEEDOR_NUMEROINTERIOR));
                objResult[i][16] = setNull(jsonobject.getString(DBhelper.PROVEEDOR_COLONIA));
                objResult[i][17] = setNull(jsonobject.getString(DBhelper.PROVEEDOR_MUNICIPIO));
                objResult[i][18] = setNull(jsonobject.getString(DBhelper.PROVEEDOR_ESTADO));
                objResult[i][19] = setNull(jsonobject.getString(DBhelper.PROVEEDOR_PAIS));
                objResult[i][20] = setNull(jsonobject.getString(DBhelper.PROVEEDOR_FOTO));
                objResult[i][21] = setNull(jsonobject.getString(DBhelper.PROVEEDOR_CLAVEBUSQUEDA));
                objResult[i][22] = setNull(jsonobject.getString(DBhelper.PROVEEDOR_VIGENCIA));
                objResult[i][23] = setNull(jsonobject.getString(DBhelper.PROVEEDOR_ACTIVO),1);
                objResult[i][24] = setNull(jsonobject.getString(DBhelper.PROVEEDOR_ORDEN),0);
                break;
            case PROVEEDORSUBCATEGORIA:
                objResult[i][0] = setNull(jsonobject.getString(DBhelper.PROVEEDORSUBCATEGORIA_ID_PROVEEDOR));
                objResult[i][1] = setNull(jsonobject.getString(DBhelper.PROVEEDORSUBCATEGORIA_ID_SUBCATEGORIA));
                break;
            case SUBCATEGORIA:
                objResult[i][0] = setNull(jsonobject.getString(DBhelper.SUBCATEGORIA_ID_SUBCATEGORIA));
                objResult[i][1] = setNull(jsonobject.getString(DBhelper.SUBCATEGORIA_DESCRIPCION));
                objResult[i][2] = setNull(jsonobject.getString(DBhelper.SUBCATEGORIA_ID_CATEGORIA));
                break;
            case FOTOS:
                objResult[i][0] = setNull(jsonobject.getString("Id_Producto"));
                objResult[i][1] = setNull(jsonobject.getString("URL"));
                break;
            default:
                break;

        }

    }

    private Object setNull(Object object) {
        return setNull(object,"");
    }

    private Object setNull(Object object,Object oDefault) {
        Object returnObject = "";

        if(object == null || object == "null" || object == "NULL" || object == "Null")
            returnObject = oDefault;
        else
            returnObject = object;

    return returnObject;
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
