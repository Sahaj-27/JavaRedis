package RedisProtocol;

// This class will represent an integer in Redis

public class RedisInteger extends RedisData {

    private final long value;
    // This is the value of the integer
    public RedisInteger(long value) {
        this.value = value;
    }

    @Override
    public RedisDataType getType() {
        // This method will return the type of the Redis data
        return RedisDataType.INTEGER;
    }

    @Override
    public String getFormattedValue() {
        // This method will return the formatted value of the Redis data
        return ":" + value + "\r\n";
    }

    @Override
    public Long getRawValue() {
        // This method will return the raw value of the Redis data
        return value;
    }

}