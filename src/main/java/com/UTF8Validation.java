package com;

import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;

@Component
public class UTF8Validation {
    private byte[] strConvertedToBytes;


   public boolean  isStrUtf8(String strToCheck) {

       try{
           byte[]strConvertedToBytes = strToCheck.getBytes("UTF-8");
       }catch(UnsupportedEncodingException e){
           e.printStackTrace();
       }
       return true;
    }
}

