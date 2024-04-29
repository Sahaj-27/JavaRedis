package RedisProtocol;

// This enum will be used to represent the different types of Redis data

public enum RedisDataType {
    SIMPLE_STRING,
    INTEGER,
    BULK_STRING,
    ARRAY,
    ERROR
}
