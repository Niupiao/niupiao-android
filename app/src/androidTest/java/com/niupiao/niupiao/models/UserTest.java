package com.niupiao.niupiao.models;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import static org.fest.assertions.api.ANDROID.assertThat;
/**
 * Created by kmchen1 on 3/20/15.
 */

@RunWith(RobolectricTestRunner.class)
//robolectric doesn't currently support api 19 - emulate api 18
@Config(emulateSdk = 18)
public class UserTest {
}
