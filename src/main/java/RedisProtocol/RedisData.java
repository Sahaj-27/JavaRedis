package RedisProtocol;

// This is the abstract class that all Redis data types will extend

public abstract class RedisData {
    abstract public RedisDataType getType();
    // This method will return the type of the Redis data
    abstract public String getFormattedValue();
    // This method will return the formatted value of the Redis data
    abstract public Object getRawValue();
    // This method will return the raw value of the Redis data
    public String toString() {
        // This method will return the type and formatted value of the Redis data in a nice way
        return (getType() + "[" + getFormattedValue() + "]").replaceAll("\\r\\n", " ");
    }
}
