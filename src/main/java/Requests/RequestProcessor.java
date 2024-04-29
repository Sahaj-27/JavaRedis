package Requests;

import RedisProtocol.*;

// This class is responsible for processing the request from the client

public class RequestProcessor {

    public RedisData processRequest(RedisData data) {
        // Get the command from the request and process it accordingly
        String command = getString(data);
        return switch (command.toUpperCase()) {
            case "PING" -> processPing(data);
            case "ECHO" -> processEcho(data);
            default -> new RedisError("unknown command");
        };
    }

    private String getString(RedisData data) {
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

    private RedisData processPing(RedisData data) {
        return new RedisSimpleString("PONG");
    }

    private RedisData processEcho(RedisData data) {
        if (data.getType() == RedisDataType.ARRAY && ((RedisArray) data).getRawValue().length == 2) {
            return ((RedisArray) data).getRawValue()[1];
        } else return new RedisError("wrong number of arguments for command");
    }

}