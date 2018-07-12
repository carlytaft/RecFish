package us.tason.recfish;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.ContentProviderClient;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.RemoteException;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.RequestFuture;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import static org.junit.Assert.assertNotNull;

@RunWith(AndroidJUnit4.class)
public class SyncTest {
    @Test
    public void test() {
        final Context context;
        final String email;
        final String password;
        final String token;
        final Account account;
        final ContentProviderClient provider;
        Uri uri;
        Integer count;
        final SyncAdapter adapter;

        context = InstrumentationRegistry.getTargetContext();
        email = "desarrollogis@gmail.com";
        password = "testing";
        token = SyncTest.getToken(context, email, password);
        assertNotNull(token);
        Helpers.addAccount(context, LoginActivity.ACCOUNT_TYPE, email, password, token);
        account = AbstractMainActivity.getAccount(context, LoginActivity.ACCOUNT_TYPE, email);
        assertNotNull(account);
        this.setToken(context, token, account);
        Helpers.setAccount(context, email);
        provider = context.getContentResolver().acquireContentProviderClient(SyncProvider.AUTHORITY);
        assertNotNull(provider);
//        this.uploadAll(provider);
        this.deleteAll(provider);
//        this.example(context);
        adapter = new SyncAdapter(context, true);
        assertNotNull(adapter);
        adapter.onPerformSync(account, null, SyncProvider.AUTHORITY, provider, null);
        try {
            this.query(provider, SyncProvider.URI_SPECIES);
            this.query(provider, SyncProvider.URI_BOATS);
            this.query(provider, SyncProvider.URI_EVENTS);
            this.query(provider, SyncProvider.URI_CAPTURES);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    private void example(Context context) {
        final Uri uriSpecies;
        final Uri uriBoat;
        final Uri uriEvent;

        assertNotNull(uriSpecies = SyncProvider.addSpecies(context.getContentResolver(), "species #1"));
        assertNotNull(uriBoat = SyncProvider.addBoat(context.getContentResolver(), "panga #1"));
        assertNotNull(uriEvent = SyncProvider.addEvent(context.getContentResolver(), Long.parseLong(uriBoat.getLastPathSegment()), "test"));
    }

    private void uploadAll(ContentProviderClient contentProviderClient) {
        ContentValues contentValues;
        Uri uri;
        Integer count;

        contentValues = new ContentValues();
        contentValues.put("synced", 0);
        try {
            uri = SyncProvider.URI_CAPTURES;
            count = contentProviderClient.update(uri, contentValues, null, null);
            Log.i(SyncTest.class.getSimpleName(), "updated:" + uri.toString() + ":" + count.toString());
            uri = SyncProvider.URI_SPECIES;
            count = contentProviderClient.update(uri, contentValues, null, null);
            Log.i(SyncTest.class.getSimpleName(), "updated:" + uri.toString() + ":" + count.toString());
            uri = SyncProvider.URI_EVENTS;
            count = contentProviderClient.update(uri, contentValues, null, null);
            Log.i(SyncTest.class.getSimpleName(), "updated:" + uri.toString() + ":" + count.toString());
            uri = SyncProvider.URI_BOATS;
            count = contentProviderClient.update(uri, contentValues, null, null);
            Log.i(SyncTest.class.getSimpleName(), "updated:" + uri.toString() + ":" + count.toString());
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    private void deleteAll(ContentProviderClient contentProviderClient) {
        Uri uri;
        Integer count;

        try {
            uri = SyncProvider.URI_CAPTURES;
            count = contentProviderClient.delete(uri, null, null);
            Log.i(SyncTest.class.getSimpleName(), "deleted:" + uri.toString() + ":" + count.toString());
            uri = SyncProvider.URI_SPECIES;
            count = contentProviderClient.delete(uri, null, null);
            Log.i(SyncTest.class.getSimpleName(), "deleted:" + uri.toString() + ":" + count.toString());
            uri = SyncProvider.URI_EVENTS;
            count = contentProviderClient.delete(uri, null, null);
            Log.i(SyncTest.class.getSimpleName(), "deleted:" + uri.toString() + ":" + count.toString());
            uri = SyncProvider.URI_BOATS;
            count = contentProviderClient.delete(uri, null, null);
            Log.i(SyncTest.class.getSimpleName(), "deleted:" + uri.toString() + ":" + count.toString());
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    protected static void query(final ContentProviderClient provider, final Uri uri) throws RemoteException {
        final Cursor cursor;

        cursor = provider.query(uri, null, null, null, null);
        if (cursor != null) {
            while (cursor.moveToNext()) {
                for (int i = 0, m = cursor.getColumnCount(); i < m; ++i) {
                    Log.d(uri.toString(), cursor.getColumnName(i) + ":" + cursor.getString(i));
                }
            }
            cursor.close();
        }
    }

    protected static void setToken(final Context context, final String token, final Account account) {
        final AccountManager manager;

        manager = AccountManager.get(context);
        manager.setAuthToken(account, AccountManager.KEY_AUTHTOKEN, token);
    }

    protected static String getToken(final Context context, final String email, final String password) {
        final RequestQueue queue;
        final String url;
        final RequestFuture<String> future;
        final StringRequest request;
        String response;

        queue = Volley.newRequestQueue(context);
        url = LoginActivity.getUrl();
        future = RequestFuture.newFuture();
        request = new StringRequest(Request.Method.POST, url, future, future) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params;

                params = new HashMap<>();
                params.put("email", email);
                params.put("password", password);
                return params;
            }
        };
        queue.add(request);
        response = null;
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
}
