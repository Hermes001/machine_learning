package com.socket.pad.paddemo.Utils;

import android.util.Log;

public class ByteUtils {

    /*
    * 16进制字符串转byte[]
    * */
    public static byte[] hexString2Bytes(String hex)
    {
        if ((hex == null) || (hex.equals("")))
        {
            return null;
        } else if (hex.length()%2 != 0) {
            return null;
        } else{
            hex = hex.toUpperCase();
            int len = hex.length()/2;
            byte[] b = new byte[len];
            char[] hc = hex.toCharArray();
            for (int i=0; i<len; i++){
                int p=2*i;
                b[i] = (byte) (charToByte(hc[p]) << 4 | charToByte(hc[p+1]));
            }
            return b;
        }
    }

    /*
     * 字符转换为字节
     */
    private static byte charToByte(char c) {
        return (byte) "0123456789ABCDEF".indexOf(c);
    }

    /*
    * 十进制转成4字节十六进制
    * */
    public static String IntTo4Hex(int myInt)
    {
        int tu5 =myInt;
        byte[] bytes5 = new byte[2];
        bytes5[1] = (byte)(tu5 & 0xFF);
        bytes5[0] = (byte)(tu5 >> 8 & 0xFF);
        return  bytes2HexString(bytes5);
    }

    /*
     * 字节数组转16进制字符串
     */
    public static String bytes2HexString(byte[] b) {
        String r = "";

        for (int i = 0; i < b.length; i++) {
            String hex = Integer.toHexString(b[i] & 0xFF);
            if (hex.length() == 1) {
                hex = '0' + hex;
            }
            r += hex.toUpperCase();
        }

        return r;
    }

    public static String bytes2HexString(byte b) {
        String r = "";
        String hex = Integer.toHexString(b & 0xFF);
        if (hex.length() == 1) {
                hex = '0' + hex;
        }
            r += hex.toUpperCase();

        return r;
    }

    public static byte intToByte(int x) {
        return (byte) x;
    }

    public static int byteToInt(byte b) {
        //Java 总是把 byte 当做有符处理；我们可以通过将其和 0xFF 进行二进制与得到它的无符值
        return b & 0xFF;
    }
}
