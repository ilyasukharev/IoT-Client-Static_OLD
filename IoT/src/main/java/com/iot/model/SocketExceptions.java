package com.iot.model;


//        UnknownHostException – if the IP address of the host could not be determined.
//        IOException – if an I/O error occurs when creating the socket.
//        SecurityException – if a security manager exists and its checkConnect method doesn't allow the operation.
//        IllegalArgumentException – if the port parameter is outside the specified range of valid port values, which is between 0 and 65535, inclusive.

public enum SocketExceptions
{
    IP_ADDRESS
            {
                @Override
                public String toString() {
                    return "Verify your ip-address correctness";
                }
            },
    SECURITY
            {
                @Override
                public String toString()
                {
                    return "Security manager does not allow the connect operation";
                }
            },
    PORT
            {
                @Override
                public String toString()
                {
                    return "Verify your port correctness";
                }
            },
    IO
            {
                @Override
                public String toString()
                {
                    return "I/O socket exception";
                }
            },
    SUCCESS,
    LOST_CONNECTION

}
