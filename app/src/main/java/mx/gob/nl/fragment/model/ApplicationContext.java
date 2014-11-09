package mx.gob.nl.fragment.model;

import android.app.Application;
import android.content.Context;

/**
 * Created by RAMSES on 09/11/14.
 */
public class ApplicationContext  extends Application {

    private static ApplicationContext instance = null;

    private ApplicationContext()
    {
        instance = this;
    }

    public static Context getInstance()
    {
        if (null == instance)
        {
            instance = new ApplicationContext();
        }

        return instance;
    }
}
