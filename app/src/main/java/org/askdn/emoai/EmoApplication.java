package org.askdn.emoai;

import android.app.Application;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

public class EmoApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        //Using ChristenJinx's Calligraphy, the application wide default font is set
        setCustomApplicationFont();
    }

    private void setCustomApplicationFont() {
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/NovaMono.ttf")
                .setFontAttrId(R.attr.fontPath).build());
    }

}
