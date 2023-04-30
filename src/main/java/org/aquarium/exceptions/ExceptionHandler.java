package org.aquarium.exceptions;

public final class ExceptionHandler {
    private ExceptionHandler(){}
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
