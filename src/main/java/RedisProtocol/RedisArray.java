package RedisProtocol;

// This class will represent a Redis array

public class RedisArray extends RedisData {

    private final RedisData[] values;
    // This is the values of the Redis array
    public RedisArray(RedisData[] values) {
        this.values = values;
    }

    @Override
    public RedisDataType getType() {
        // This method will return the type of the Redis data
        return RedisDataType.ARRAY;
    }

    @Override
    public String getFormattedValue() {
        // This method will return the formatted value of the Redis data
        StringBuilder sb = new StringBuilder();
        sb.append("*");
        sb.append(values.length);
        sb.append("\r\n");
        for (RedisData value : values) sb.append(value.getFormattedValue());
        return sb.toString();
    }

    @Override
    public RedisData[] getRawValue() {
        // This method will return the raw value of the Redis data
        return values;
    }

}