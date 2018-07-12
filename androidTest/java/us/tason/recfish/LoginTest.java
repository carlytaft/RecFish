package us.tason.recfish;

import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.Espresso;
import android.support.test.espresso.action.ViewActions;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.uiautomator.By;
import android.support.test.uiautomator.UiDevice;
import android.support.test.uiautomator.UiObject;
import android.support.test.uiautomator.UiObjectNotFoundException;
import android.support.test.uiautomator.UiScrollable;
import android.support.test.uiautomator.UiSelector;
import android.support.test.uiautomator.Until;
import android.widget.ListView;
import android.widget.TextView;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static junit.framework.TestCase.assertNotNull;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class LoginTest {
    @Rule
    public ActivityTestRule<LoginActivity> mActivityRule = new ActivityTestRule<>(LoginActivity.class);
    private UiDevice mDevice;

    @Before
    public void setUp() {
        this.mDevice = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());
    }

    @Test
    public void test() throws UiObjectNotFoundException {
        final UiScrollable listView;
        final UiObject listViewItem;

        Espresso.onView(ViewMatchers.withId(R.id.sign_in_button)).perform(ViewActions.click());
        this.mDevice.wait(Until.findObject(By.clazz(ListView.class)), 1000 * 60);
        listView = new UiScrollable(new UiSelector());
        assertNotNull(listView);
        listViewItem = listView.getChildByText(new UiSelector().className(TextView.class.getName()), "desarrollogis@gmail.com");
        assertNotNull(listViewItem);
        listViewItem.click();
    }
}
