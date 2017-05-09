package fr.ebiz.nurdiales.trainingJava.util;

import java.sql.Connection;

public class MyThreadLocal {
    public static final ThreadLocal USER_THREAD_LOCAL = new ThreadLocal<Connection>();

    /**
     * .
     * @param user .
     */
    public static void set(Context user) {
        USER_THREAD_LOCAL.set(user);
    }

    /**
     * .
     */
    public static void unset() {
        USER_THREAD_LOCAL.remove();
    }

    /**
     * .
     * @return .
     */
    public static Context get() {
        return (Context) USER_THREAD_LOCAL.get();
    }

}
