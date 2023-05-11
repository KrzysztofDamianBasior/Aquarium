package org.aquarium.utils.logger;

public final class Logger {
    private Logger(){}
    public static void writeMessagesAndExit(Object... messagesTable) {
        for (int i = 0; i < messagesTable.length; i++) {
            System.err.print((i + 1) + ": ");
            if (messagesTable[i] instanceof Throwable) {
                ((Throwable) messagesTable[i]).printStackTrace();
            } else {
                System.err.println(messagesTable[i].toString());
            }
        }
        System.exit(-1);
    }
}
