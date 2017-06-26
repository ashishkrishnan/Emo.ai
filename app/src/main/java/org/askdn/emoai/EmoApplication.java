package org.askdn.emoai;

import android.app.Application;

import timber.log.Timber;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

public class EmoApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        //Using ChristenJinx's Calligraphy, the application wide default font is set
        setCustomApplicationFont();

        // Initialize Timber.
        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        }
    }

    private void setCustomApplicationFont() {
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/LobsterR.ttf")
                .setFontAttrId(R.attr.fontPath).build());
    }

}
