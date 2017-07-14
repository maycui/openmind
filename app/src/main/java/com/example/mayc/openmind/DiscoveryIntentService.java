package com.example.mayc.openmind;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mayc on 7/13/17.
 */

/* used https://code.tutsplus.com/tutorials/android-fundamentals-intentservice-basics--mobile-6183 to learn how to do this */

public class DiscoveryIntentService extends IntentService {

    public static final String DISCOVERY_NEWS_CALL = "discoveryNews";

    public DiscoveryIntentService() {
        super("DiscoveryIntentService");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        String query = intent.getStringExtra(DISCOVERY_NEWS_CALL);

        //calling the api and receiving an array of document payloads as a response
        //api link should be: https://gateway.watsonplatform.net/discovery/api/v1/environments/d83ace81-1722-4566-bd55-47417607f2b1/collections/789640cf-7e1a-4f21-bc88-4c0e7f4824a0/query?version=2017-06-25
        DiscoveryClient test = new DiscoveryClient();
        List<DocumentPayload> result = new ArrayList<>();
        try {
            result = test.getDocuments(query);
        } catch (Exception e) {
            e.printStackTrace();
        }

        //TODO: send a broadcast to response receiver
//        Intent broadcastIntent = new Intent();
//        broadcastIntent.setAction(ResponseReceiver.ACTION_RESP);
//        broadcastIntent.addCategory(Intent.CATEGORY_DEFAULT);
//        broadcastIntent.putExtra(PARAM_OUT_MSG, resultTxt);
//        sendBroadcast(broadcastIntent);
    }

}
