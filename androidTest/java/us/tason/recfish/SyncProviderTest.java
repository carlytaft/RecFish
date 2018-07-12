package us.tason.recfish;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.database.Cursor;
import android.net.Uri;
import android.test.ProviderTestCase2;
import android.util.Log;

public class SyncProviderTest extends ProviderTestCase2<SyncProvider> {
    private static final String TAG = SyncProviderTest.class.getSimpleName();
    private ContentResolver mResolver = null;

    public SyncProviderTest() {
        super(SyncProvider.class, SyncProvider.AUTHORITY);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        Log.d(SyncProviderTest.TAG, "setUp()");
        this.mResolver = this.getMockContentResolver();
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
        Log.d(SyncProviderTest.TAG, "tearDown()");
    }

    public void test() {
        Uri boat = null;
        long boatId = 0;
        Uri species = null;
        long speciesId = 0;
        double[] value = null;
        Uri capture = null;
        double weight = 0.0;
        double price = 0.0;
        long captureId1 = 0;
        long captureId2 = 0;
        String[] selectionArgs = null;
        Cursor cursor = null;
        String name = null;
        String eventName = "testing";

        boat = SyncProvider.addBoat(this.mResolver, "panga #1");
        assertNotNull(boat);
        boatId = ContentUris.parseId(boat);
        species = SyncProvider.addSpecies(this.mResolver, "species #1");
        assertNotNull(species);
        speciesId = ContentUris.parseId(species);
        value = SyncProvider.getCapture(this.mResolver, boatId, speciesId);
        assertEquals(value[0], 0.0);
        assertEquals(value[1], 0.0);
        capture = SyncProvider.addCapture(this.mResolver, boatId, speciesId, weight, price);
        assertNotNull(capture);
        captureId1 = ContentUris.parseId(capture);
        weight = 100.0;
        price = 200.0;
        capture = SyncProvider.addCapture(this.mResolver, boatId, speciesId, weight, price);
        assertNotNull(capture);
        captureId2 = ContentUris.parseId(capture);
        assertEquals(captureId1, captureId2);
        value = SyncProvider.getCapture(this.mResolver, boatId, speciesId);
        assertEquals(value[0], 100.0);
        assertEquals(value[1], 200.0);
        species = SyncProvider.addSpecies(this.mResolver, "species #2");
        assertNotNull(species);
        selectionArgs = new String[]{Long.toString(boatId)};
        cursor = this.mResolver.query(SyncProvider.URI_SPECIES_CAPTURES, null, null, selectionArgs, null);
        assertTrue(cursor.moveToNext());
        name = cursor.getString(1);
        assertEquals(name, "species #1");
        weight = cursor.getDouble(4);
        assertEquals(weight, 100.0);
        price = cursor.getDouble(5);
        assertEquals(price, 200.0);
        assertTrue(cursor.moveToNext());
        cursor = this.mResolver.query(SyncProvider.URI_CAPTURES_SPECIES, null, null, selectionArgs, null);
        assertTrue(cursor.moveToNext());
        name = cursor.getString(1);
        assertEquals(name, "species #1");
        weight = cursor.getDouble(4);
        assertEquals(weight, 100.0);
        price = cursor.getDouble(5);
        assertEquals(price, 200.0);
        assertFalse(cursor.moveToNext());
        assertTrue(SyncProvider.storeEvent(this.mResolver, boatId, eventName));
        cursor = this.mResolver.query(SyncProvider.URI_CAPTURES_SPECIES, null, null, selectionArgs, null);
        assertFalse(cursor.moveToNext());
    }
}
