package us.tason.recfish;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.ContentProviderClient;
import android.content.ContentResolver;
import android.content.Context;
import android.content.SyncResult;
import android.os.RemoteException;

import com.android.volley.RequestQueue;
import com.vicenteobregon.tools.TestTools;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;

@RunWith(RobolectricTestRunner.class)
public class SyncAdapterTest {
    private static final String sEmail = "desarrollogis@gmail.com";
    private static final String sPassword = "testing";
    private Context mContext;
    private RequestQueue mRequestQueue;
    private SyncAdapter mSyncAdapter;
    private ContentProviderClient mContentProviderClient;

    @Before
    public void setup() {
        final ContentResolver contentResolver;

        this.mContext = RuntimeEnvironment.application;
        this.mRequestQueue = TestTools.newRequestQueueForTest(this.mContext);
        this.mSyncAdapter = new SyncAdapter(this.mContext, true);
        contentResolver = RuntimeEnvironment.application.getContentResolver();
        this.mContentProviderClient = contentResolver.acquireContentProviderClient(SyncProvider.AUTHORITY);
    }

    @Test
    public void sync() throws RemoteException {
        final AccountManager accountManager;
        final String accountName;
        final String accountType;
        final Account account;
        final String token;
        final SyncResult syncResult;

        Assert.assertNotNull(this.mContext);
        accountManager = AccountManager.get(this.mContext);
        Assert.assertNotNull(accountManager);
        accountName = SyncAdapterTest.sEmail;
        accountType = this.mContext.getString(R.string.account_type);
        account = new Account(accountName, accountType);
        Assert.assertTrue(accountManager.addAccountExplicitly(account, SyncAdapterTest.sPassword, null));
        Assert.assertNotNull(this.mRequestQueue);
        token = TestTools.getToken(this.mRequestQueue, SyncAdapterTest.sEmail, SyncAdapterTest.sPassword);
        Assert.assertNotNull(token);
        accountManager.setAuthToken(account, AccountManager.KEY_AUTHTOKEN, token);
        syncResult = new SyncResult();
        Assert.assertNotNull(this.mSyncAdapter);
        this.mSyncAdapter.setRequestQueue(this.mRequestQueue);
        Assert.assertNotNull(this.mContentProviderClient);
        this.mSyncAdapter.onPerformSync(account, null, SyncProvider.AUTHORITY, this.mContentProviderClient, syncResult);
        TestTools.logTable(this.mContentProviderClient, SyncProvider.URI_BOATS);
    }
}
