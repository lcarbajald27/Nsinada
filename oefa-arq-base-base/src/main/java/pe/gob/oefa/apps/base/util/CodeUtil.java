package pe.gob.oefa.apps.base.util;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

public class CodeUtil {

    public static String parseEncode(String data) {
        if (data == null) {
            data = "";
        }
        try {
            data = URLEncoder.encode(data, "ISO-8859-1");
            data = URLDecoder.decode(data, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            //e.printStackTrace();
        }
        return data;
    }

    public static String replaceHTMLCode(String str) {
        String tmpSrt = "";
        String tmpChar = "";
        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            switch (c) {

                case 'á':
                    tmpChar = "&aacute;";
                    break;
                case 'Á':
                    tmpChar = "&Aacute;";
                    break;

                case 'é':
                    tmpChar = "&eacute;";
                    break;
                case 'É':
                    tmpChar = "&Eacute;";
                    break;

                case 'í':
                    tmpChar = "&iacute;";
                    break;
                case 'Í':
                    tmpChar = "&Iacute;";
                    break;

                case 'ó':
                    tmpChar = "&oacute;";
                    break;
                case 'Ó':
                    tmpChar = "&Oacute;";
                    break;

                case 'ú':
                    tmpChar = "&uacute;";
                    break;
                case 'Ú':
                    tmpChar = "&Uacute;";
                    break;

                case 'ñ':
                    tmpChar = "&ntilde;";
                    break;
                case 'Ñ':
                    tmpChar = "&Ntilde;";
                    break;

                default:
                    tmpChar = String.valueOf(c);
                    break;
            }
            tmpSrt += tmpChar;
        }

        return tmpSrt;
    }

}
