package com.rentalSystem.xrpl.common.utils;

public abstract class StringUtils {

    public static String encodeHexString(String stringValue) {
        var stringBytes = stringValue.getBytes();
        StringBuilder stringBuffer = new StringBuilder();
        for (byte stringByte : stringBytes) {
            stringBuffer.append(byteToHex(stringByte));
        }
        return stringBuffer.toString();
    }

    private static String byteToHex(byte num) {
        char[] hexDigits = new char[2];
        hexDigits[0] = Character.forDigit((num >> 4) & 0xF, 16);
        hexDigits[1] = Character.forDigit((num & 0xF), 16);
        return new String(hexDigits);
    }
}
