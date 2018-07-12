package com.vicenteobregon.tools;

import android.content.ContentProviderClient;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.RemoteException;

import com.android.volley.AuthFailureError;
import com.android.volley.ExecutorDelivery;
import com.android.volley.Network;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.ResponseDelivery;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.RequestFuture;
import com.android.volley.toolbox.StringRequest;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class TestTools {
    public static RequestQueue newRequestQueueForTest(final Context context) {
        final File cacheDir;
        final Network network;
        final ResponseDelivery responseDelivery;
        final RequestQueue queue;

        cacheDir = new File(context.getCacheDir(), "volley");
        network = new BasicNetwork(new HurlStack());
        responseDelivery = new ExecutorDelivery(Executors.newSingleThreadExecutor());
        queue = new RequestQueue(new DiskBasedCache(cacheDir), network, 4, responseDelivery);
        queue.start();
        return queue;
    }

    public static String getToken(final RequestQueue requestQueue, final String email, final String password) {
        String response;
        final String url;
        final RequestFuture<String> future;
        final StringRequest request;

        response = null;
        url = "http://192.168.0.2/recfish/rest/security/users/login";
        future = RequestFuture.newFuture();
        request = new StringRequest(Request.Method.POST, url, future, future) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                final Map<String, String> params;

                params = new HashMap<>();
                params.put("email", email);
                params.put("password", password);
                return params;
            }
        };
        requestQueue.add(request);
        try {
            response = future.get(1, TimeUnit.MINUTES);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
        return response;
    }

    public static void logTable(final ContentProviderClient contentProviderClient, final Uri uri) throws RemoteException {
        final Cursor cursor;
        String separator;

        System.out.println(uri);
        cursor = contentProviderClient.query(uri, null, null, null, null);
        assert cursor != null;
        while (cursor.moveToNext()) {
            separator = "";
            for (int i = 0, m = cursor.getColumnCount(); i < m; ++i) {
                System.out.print(separator + cursor.getColumnName(i) + "=" + cursor.getString(i) + "(" + TestTools.getTypeName(cursor.getType(i)) + ")");
                separator = ",";
            }
            System.out.println();
        }
    }

    private static String getTypeName(final int type) {
        switch (type) {
            case Cursor.FIELD_TYPE_NULL:
                return "Null";
            case Cursor.FIELD_TYPE_INTEGER:
                return "Integer";
            case Cursor.FIELD_TYPE_FLOAT:
                return "Float";
            case Cursor.FIELD_TYPE_STRING:
                return "String";
            case Cursor.FIELD_TYPE_BLOB:
                return "Blob";
            default:
                return "Unknown";
        }
    }
}
