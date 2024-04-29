package RedisProtocol;

// This class will represent an error in Redis

public class RedisError extends RedisData {

    private final String value;
    // This is the value of the error
    public RedisError(String value) {
        this.value = value;
    }

    @Override
    public RedisDataType getType() {
        // This method will return the type of the Redis data
        return RedisDataType.ERROR;
    }

    @Override
    public String getFormattedValue() {
        // This method will return the formatted value of the Redis data
        return "-" + value + "\r\n";
    }

    @Override
    public String getRawValue() {
        // This method will return the raw value of the Redis data
        return value;
    }

}