package itmo.ser.commands.exceptions;

public class SocketException extends  RuntimeException{
    public SocketException(String message) {
        super(message);
    }
}
