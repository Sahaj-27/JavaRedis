package RedisProtocol;

// This class will represent a bulk string in Redis

public class RedisBulkString extends RedisData {

    private final String value;
    // This is the value of the bulk string
    public RedisBulkString(String value) {
        this.value = value;
    }

    @Override
    public RedisDataType getType() {
        // This method will return the type of the Redis data
        return RedisDataType.BULK_STRING;
    }

    @Override
    public String getFormattedValue() {
        // This method will return the formatted value of the Redis data
        if (value == null)
            return "$-1\r\n";
        return "$" + value.length() + "\r\n" + value + "\r\n";
    }

    @Override
    public String getRawValue() {
        // This method will return the raw value of the Redis data
        return value;
    }

}