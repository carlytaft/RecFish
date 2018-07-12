package us.tason.recfish;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;

import junit.framework.TestCase;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.android.controller.ContentProviderController;

@RunWith(RobolectricTestRunner.class)
public class SyncProviderLocalTest {
    private final ContentProviderController<SyncProvider> mContentProviderController = Robolectric.buildContentProvider(SyncProvider.class);
    private SyncProvider mSyncProvider;
    private ContentResolver mContentResolver;

    @Before
    public void setup() {
        this.mSyncProvider = this.mContentProviderController.create().get();
        this.mContentResolver = RuntimeEnvironment.application.getContentResolver();
    }

    @Test
    public void test() {
        TestCase.assertNotNull(this.mSyncProvider);
        TestCase.assertNotNull(this.mContentResolver);
        this.example();
        this.log(SyncProvider.URI_SPECIES);
        this.log(SyncProvider.URI_BOATS);
        this.log(SyncProvider.URI_EVENTS);
        this.log(SyncProvider.URI_CAPTURES);
    }

    private void log(final Uri uri) {
        String separator;
        final Cursor cursor;

        System.out.println(uri);
        separator = "";
        cursor = this.mSyncProvider.query(uri, null, null, null, null);
        TestCase.assertNotNull(cursor);
        while (cursor.moveToNext()) {
            for (int i = 0, m = cursor.getColumnCount(); i < m; ++i) {
                System.out.print(separator + cursor.getColumnName(i) + ":" + cursor.getString(i));
                separator = ", ";
            }
        }
        cursor.close();
        System.out.println();
    }

    private void example() {
        final Uri boatUri;
        final Uri speciesUri;
        Uri eventUri;

        boatUri = SyncProvider.addBoat(this.mContentResolver, "boat #1");
        TestCase.assertNotNull(boatUri);
        speciesUri = SyncProvider.addSpecies(this.mContentResolver, "species #1");
        TestCase.assertNotNull(speciesUri);
        eventUri = SyncProvider.addEvent(this.mContentResolver, Long.parseLong(boatUri.getLastPathSegment()), "event #1");
        TestCase.assertNull(eventUri);
        SyncProvider.updateSpeciesWeight(this.mContentResolver, Long.parseLong(speciesUri.getLastPathSegment()), 1.0);
        eventUri = SyncProvider.addEvent(this.mContentResolver, Long.parseLong(boatUri.getLastPathSegment()), "event #1");
        TestCase.assertNotNull(eventUri);
    }
}
