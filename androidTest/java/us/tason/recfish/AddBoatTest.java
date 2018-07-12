package us.tason.recfish;

import android.support.test.espresso.Espresso;
import android.support.test.espresso.action.ViewActions;
import android.support.test.espresso.assertion.ViewAssertions;
import android.support.test.espresso.matcher.ViewMatchers;
import android.test.ActivityInstrumentationTestCase2;

import org.junit.Assert;

public class AddBoatTest extends ActivityInstrumentationTestCase2<MainActivity> {
    public AddBoatTest() {
        super(MainActivity.class);
    }

    public void testDialog() {
        final MainActivity activity;
        final AddBoatDialogFragment dialogFragment;

        activity = this.getActivity();
        activity.onAddBoat(null);
        this.getInstrumentation().waitForIdleSync();
        dialogFragment = (AddBoatDialogFragment) activity.getSupportFragmentManager().findFragmentByTag("dialog");
        Assert.assertNotNull(dialogFragment);
        Espresso.onView(ViewMatchers.withId(R.id.name)).perform(ViewActions.click());
        Espresso.onView(ViewMatchers.withId(R.id.name)).perform(ViewActions.replaceText(""));
        Espresso.onView(ViewMatchers.withText("Agregar")).perform(ViewActions.click());
        Espresso.onView(ViewMatchers.withText("Agregar")).check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
        Espresso.onView(ViewMatchers.withId(R.id.name)).perform(ViewActions.click());
        Espresso.onView(ViewMatchers.withId(R.id.name)).perform(ViewActions.replaceText(""));
        Espresso.onView(ViewMatchers.withId(R.id.name)).perform(ViewActions.typeText("test"));
        Espresso.onView(ViewMatchers.withText("Agregar")).perform(ViewActions.click());
        Espresso.onView(ViewMatchers.withText("Agregar")).check(ViewAssertions.doesNotExist());
    }
}
