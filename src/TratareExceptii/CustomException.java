package TratareExceptii;

public class CustomException extends Exception {

    public CustomException(String mess) {

        super(mess);
    }

    public CustomException(String mess, Throwable cause) {

        super(mess, cause);
    }
}
