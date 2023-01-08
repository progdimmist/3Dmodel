package com.cgvsu.objreader;


public abstract class ReaderExceptions extends RuntimeException {
    protected ReaderExceptions(String message) {
        super(message);
    }

    public static class ObjFormatException extends ReaderExceptions {
        public ObjFormatException(String errorMessage, int lineInd) {
            super("Error parsing OBJ file on line: " + lineInd + ". " + errorMessage);
        }
    }

    public static class ObjContentException extends ReaderExceptions {
        public ObjContentException(String errorMessage) {
            super("Error! Impossible format! " + errorMessage);
        }
    }
}