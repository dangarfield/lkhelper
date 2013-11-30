package com.dangarfield.lkhelper.jms.event;

import java.util.Random;

public abstract class AbstractLKEvent {

	protected String createRandomString() {
        Random random = new Random(System.currentTimeMillis());
        long randomLong = random.nextLong();
        return Long.toHexString(randomLong);
    }
}
