package us.tason.recfish;

import android.accounts.Account;
import android.content.ContentProviderClient;
import android.content.Context;
import android.os.RemoteException;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertNotNull;

@RunWith(AndroidJUnit4.class)
public class SpeciesTest {
    private static final String TAG = SpeciesTest.class.getSimpleName();

    @Test
    public void test() {
        final Context context;
        final String email;
        final String password;
        final String token;
        final Account account;
        final ContentProviderClient provider;

        context = InstrumentationRegistry.getTargetContext();
        email = "desarrollogis@gmail.com";
        password = "testing";
        token = SyncTest.getToken(context, email, password);
        assertNotNull(token);
        Helpers.addAccount(context, LoginActivity.ACCOUNT_TYPE, email, password, token);
        account = AbstractMainActivity.getAccount(context, LoginActivity.ACCOUNT_TYPE, email);
        assertNotNull(account);
        SyncTest.setToken(context, token, account);
        Helpers.setAccount(context, email);
        provider = context.getContentResolver().acquireContentProviderClient(SyncProvider.AUTHORITY);
        assertNotNull(provider);
        try {
            SyncTest.query(provider, SyncProvider.URI_SPECIES);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
}
