package Requests;

import RedisProtocol.*;
import Storage.InMemoryStorage;

// This class is responsible for processing the request from the client

public class RequestProcessor {

    public RedisData processRequest(RedisData data) {
        // Get the command from the request and process it accordingly
        String command = getString(data);
        return switch (command.toUpperCase()) {
            case "PING" -> processPing(data);
            case "ECHO" -> processEcho(data);
            case "GET" -> processGet(data);
            case "SET" -> processSet(data);
            default -> new RedisError("unknown command");
        };
    }

    private static String getString(RedisData data) {
        if (data == null)
            return null;
        if (data.getType() == RedisDataType.ARRAY) {
            RedisArray array = (RedisArray) data;
            RedisData[] values = array.getRawValue();
            if (values.length == 0)
                return null;
            if (values[0].getType() == RedisDataType.BULK_STRING) {
                return ((RedisBulkString) values[0]).getRawValue();
            } else if (values[0].getType() == RedisDataType.SIMPLE_STRING) {
                return ((RedisSimpleString) values[0]).getRawValue();
            }
        } else if (data.getType() == RedisDataType.BULK_STRING) {
            return ((RedisBulkString) data).getRawValue();
        } else if (data.getType() == RedisDataType.SIMPLE_STRING) {
            return ((RedisSimpleString) data).getRawValue();
        }
        return null;
    }

    private static Long getLong(RedisData data) {
        if (data == null)
            return null;
        if (data.getType() == RedisDataType.ARRAY) {
            RedisArray array = (RedisArray) data;
            RedisData[] values = array.getRawValue();
            if (values.length == 0)
                return null;
            if (values[0].getType() == RedisDataType.BULK_STRING) {
                return Long.parseLong(((RedisBulkString) values[0]).getRawValue());
            } else if (values[0].getType() == RedisDataType.SIMPLE_STRING) {
                return Long.parseLong(((RedisSimpleString) values[0]).getRawValue());
            } else if (values[0].getType() == RedisDataType.INTEGER) {
                return ((RedisInteger) values[0]).getRawValue();
            }
        } else if (data.getType() == RedisDataType.BULK_STRING) {
            return Long.parseLong(((RedisBulkString) data).getRawValue());
        } else if (data.getType() == RedisDataType.SIMPLE_STRING) {
            return Long.parseLong(((RedisSimpleString) data).getRawValue());
        } else if (data.getType() == RedisDataType.INTEGER) {
            return ((RedisInteger) data).getRawValue();
        }
        return null;
    }

    @SuppressWarnings("unused")
    private RedisData processPing(RedisData data) {
        return new RedisSimpleString("PONG");
    }

    private RedisData processEcho(RedisData data) {
        if (data.getType() == RedisDataType.ARRAY && ((RedisArray) data).getRawValue().length == 2) {
            return ((RedisArray) data).getRawValue()[1];
        } else return new RedisError("wrong number of arguments for command");
    }

    private static RedisData processGet(RedisData data) {
        if (data.getType() == RedisDataType.ARRAY && ((RedisArray) data).getRawValue().length == 2) {
            data = ((RedisArray) data).getRawValue()[1];
            if (data.getType() == RedisDataType.SIMPLE_STRING) {
                String val = InMemoryStorage.get(((RedisSimpleString) data).getRawValue());
                return new RedisBulkString(val);
            } else if (data.getType() == RedisDataType.BULK_STRING) {
                String val = InMemoryStorage.get(((RedisBulkString) data).getRawValue());
                return new RedisBulkString(val);
            }
        }
        return new RedisError("wrong number of arguments for command");
    }

    private static RedisData processSet(RedisData data) {
        if (data.getType() == RedisDataType.ARRAY && ((RedisArray) data).getRawValue().length >= 3) {
            RedisData[] rawValue = ((RedisArray) data).getRawValue();
            String key = getString(rawValue[1]);
            String value = getString(rawValue[2]);
            if (key == null || value == null)
                return new RedisError("wrong number of arguments for command");
            Long expire = null;
            if (rawValue.length >= 5) {
                String option = getString(rawValue[3]);
                if (option == null) return new RedisError("wrong number of arguments for command");
                if (option.equalsIgnoreCase("PX")) {
                    expire = getLong(rawValue[4]);
                    if (expire == null)
                        return new RedisError("wrong number of arguments for command");
                }
            }
            String previousValue = InMemoryStorage.put(key, value, expire);
            if (previousValue == null) return new RedisSimpleString("OK");
            else return new RedisBulkString(previousValue);
        }
        return new RedisError("wrong number of arguments for command");
    }

}