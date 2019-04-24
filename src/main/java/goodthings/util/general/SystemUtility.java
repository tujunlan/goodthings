package goodthings.util.general;

import goodthings.util.http.HttpCommons;
import org.apache.commons.codec.binary.Base64;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.slf4j.Logger;

import java.io.*;
import java.lang.reflect.Method;
import java.net.URLDecoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;


public class SystemUtility {
    public final static int SECONDS_ONE_DAY = 86400;
    public final static int MillSECONDS_ONE_DAY = 86400000;

    private static final ThreadLocal<DateFormat> df = new ThreadLocal<DateFormat>() {
        @Override
        protected DateFormat initialValue() {
            return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        }
    };
    private static final ThreadLocal<DateFormat> dfDay = new ThreadLocal<DateFormat>() {
        @Override
        protected DateFormat initialValue() {
            return new SimpleDateFormat("yyyy-MM-dd");
        }
    };
    public static final ThreadLocal<UUID> TRACKER_UUID=new ThreadLocal<>( );
    /**获取系统时间
     */
    public static String getSysTime(){
        return df.get().format(new Date()).toString();
    }

    public static String formatDate(Date date) {
        return df.get().format(date);
    }
    //计算两个时间字符串相差多少秒
    public static int subTime(String datetime1, String datetime2) throws ParseException {
        Date d1=df.get().parse(datetime1);
        Date d2=df.get().parse(datetime2);

        Calendar cal1 = Calendar.getInstance();
        Calendar cal2 = Calendar.getInstance();
        cal1.setTime(d1);
        cal2.setTime(d2);

        long res=(cal2.getTimeInMillis()-cal1.getTimeInMillis())/1000;
        return (int)res;
    }

    public static String getAfterSysTime(int minutes) {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MINUTE, minutes);
        return df.get().format(cal.getTime());
    }
    //时间相加
    public static String addTime(String datetime1, int seconds) throws ParseException {
        Date d=df.get().parse(datetime1);
        Calendar cal = Calendar.getInstance();
        cal.setTime(d);
        cal.add(Calendar.SECOND, seconds);
        return df.get().format(cal.getTime());
    }
    public static byte[] decodeBase64(String str) {
        return Base64.decodeBase64(str);
    }
    //日期相加
    public static String addDate(String date, int day) throws ParseException {
        Date d = null;
        if(date==null){
            d = new Date();
        }else{
            d = dfDay.get().parse(date);
        }
        Calendar cal = Calendar.getInstance();
        cal.setTime(d);
        cal.add(Calendar.DATE, day);
        return dfDay.get().format(cal.getTime());
    }
    //日期之差
    public static int subDate(String date1, String date2) throws ParseException {
        Date d1=dfDay.get().parse(date1);
        Date d2=dfDay.get().parse(date2);

        Calendar cal1 = Calendar.getInstance();
        Calendar cal2 = Calendar.getInstance();
        cal1.setTime(d1);
        cal2.setTime(d2);

        long res=(cal2.getTimeInMillis()-cal1.getTimeInMillis())/MillSECONDS_ONE_DAY;
        return (int)res;
    }
    /**
     * 字符编码转换
     *
     * @param str
     * @param chatset
     * @return
     */
    public static byte[] convertCharset(String str, String chatset) {
        byte b[] = null;
        try {
            b = str.getBytes(chatset);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return b;
    }
    public static String decodeBase64String(String str, String encode) {
        byte[] strBuff = SystemUtility.decodeBase64(str);
        String strCode = "";
        try {
            strCode = new String(strBuff, encode);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return strCode;
    }

    public static String[] getHashedPathNamesById(UUID objectId, int partNum) {
        if (partNum < 0 || partNum > 5) {
            return null;
        }
        if (partNum == 0) {
            partNum = 3;
        }
        byte[] idData = SystemUtility.uuidAsByteArray(objectId);
        String base64Str = SystemUtility.encodeBase64(idData);
        base64Str = base64Str.replace("=", "");
        base64Str = base64Str.replace("+", "_");
        base64Str = base64Str.replace("/", ".");
        String[] pathParts = new String[partNum];
        int avgLen = base64Str.length() / partNum;
        for (int i = 0; i < partNum; i++) {
            int endIdx = (i + 1) * avgLen - 1;
            if (endIdx > base64Str.length() - 1) {
                endIdx = base64Str.length() - 1;
            }
            pathParts[i] = base64Str.substring(i * avgLen, endIdx);
            pathParts[i] = pathParts[i].replace('.', '_');
            int sc = pathParts[0].charAt(0);
            if (sc >= ((int) '0') && sc <= ((int) '9')) {
                pathParts[i] = "_" + pathParts[i];
            }
        }
        return pathParts;
    }

    public static String encodeBase64(byte[] bstr) {
        return Base64.encodeBase64String(bstr);
    }

    public static String encodeBase64String(String str) {
        return encodeBase64(str.getBytes());
    }

    @SuppressWarnings("rawtypes")
    public static List<Class> getImplementingClassesByInterface(
            Class interfaceType) {
        List<Class> typeList = new ArrayList<Class>();
        try {
            Class[] clazArr = interfaceType.getClasses();
            for (int i = 0; i < clazArr.length; i++) {
                typeList.add(clazArr[i]);
            }
        } catch (Exception ex) {
        }
        return typeList;
    }

    @SuppressWarnings("rawtypes")
    protected static Boolean isInterfaceImplemented(String className, String interfaceName) {
        try {
            Class classType = Class.forName(className);
            Class interfaceType = Class.forName(interfaceName);
            if (!classType.isLocalClass() || !interfaceType.isInterface()) {
                return false;
            }
            return isInterfaceImplemented(classType, interfaceType);
        } catch (Exception ex) {
            return false;
        }

    }

    @SuppressWarnings("rawtypes")
    protected static Boolean isInterfaceImplemented(Object obj, Class interfaceType) {
        return isInterfaceImplemented(obj.getClass(), interfaceType);
    }

    @SuppressWarnings("rawtypes")
    protected static Boolean isInterfaceImplemented(Class classType,
                                                    Class interfaceType) {
        Class[] interfaceList = classType.getInterfaces();
        for (int i = 0; i < interfaceList.length; i++) {
            if (interfaceList[i].equals(classType)) {
                return true;
            }
        }
        return false;
    }

    public static byte[] uuidAsByteArray(UUID uuid) {
        long msb = uuid.getMostSignificantBits();
        long lsb = uuid.getLeastSignificantBits();
        byte[] buffer = new byte[16];

        for (int i = 0; i < 8; i++) {
            buffer[i] = (byte) (msb >>> 8 * (7 - i));
        }
        for (int i = 8; i < 16; i++) {
            buffer[i] = (byte) (lsb >>> 8 * (7 - i));
        }

        return buffer;
    }

    // byte数组转换为16进制字符串
    public static String byte2hex(byte[] data) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < data.length; i++) {
            String temp = Integer.toHexString(((int) data[i]) & 0xFF);
            for (int t = temp.length(); t < 2; t++) {
                sb.append("0");
            }
            sb.append(temp);
        }
        return sb.toString();
    }

    // 16进制转换为byte数组
    public static byte[] hex2byte(String hexStr) {
        byte[] bts = new byte[hexStr.length() / 2];
        for (int i = 0, j = 0; j < bts.length; j++) {
            bts[j] = (byte) Integer.parseInt(hexStr.substring(i, i + 2), 16);
            i += 2;
        }
        return bts;
    }

    public static String firstLower(String str) {
        return str.substring(0, 1).toLowerCase() + str.substring(1);
    }

    public static String firstUpper(String str) {
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }

    public static String getMD5Str(String str) {
        MessageDigest messageDigest = null;

        try {
            messageDigest = MessageDigest.getInstance("MD5");

            messageDigest.reset();

            messageDigest.update(str.getBytes("UTF-8"));
        } catch (NoSuchAlgorithmException e) {
            System.out.println("NoSuchAlgorithmException caught!");
            System.exit(-1);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        byte[] byteArray = messageDigest.digest();

        StringBuffer md5StrBuff = new StringBuffer();

        for (int i = 0; i < byteArray.length; i++) {
            if (Integer.toHexString(0xFF & byteArray[i]).length() == 1) {
                md5StrBuff.append("0").append(
                        Integer.toHexString(0xFF & byteArray[i]));
            } else {
                md5StrBuff.append(Integer.toHexString(0xFF & byteArray[i]));
            }
        }

        return md5StrBuff.toString();
    }

    public final static byte[] getBytes(short s, boolean asc) {
        byte[] buf = new byte[2];
        if (asc)
            for (int i = buf.length - 1; i >= 0; i--) {
                buf[i] = (byte) (s & 0x00ff);
                s >>= 8;
            }
        else
            for (int i = 0; i < buf.length; i++) {
                buf[i] = (byte) (s & 0x00ff);
                s >>= 8;
            }
        return buf;
    }

    public final static byte[] getBytes(int s, boolean asc) {
        byte[] buf = new byte[4];
        if (asc)
            for (int i = buf.length - 1; i >= 0; i--) {
                buf[i] = (byte) (s & 0x000000ff);
                s >>= 8;
            }
        else
            for (int i = 0; i < buf.length; i++) {
                buf[i] = (byte) (s & 0x000000ff);
                s >>= 8;
            }
        return buf;
    }

    public final static byte[] getBytes(long s, boolean asc) {
        byte[] buf = new byte[8];
        if (asc)
            for (int i = buf.length - 1; i >= 0; i--) {
                buf[i] = (byte) (s & 0x00000000000000ff);
                s >>= 8;
            }
        else
            for (int i = 0; i < buf.length; i++) {
                buf[i] = (byte) (s & 0x00000000000000ff);
                s >>= 8;
            }
        return buf;
    }

    public final static short getShort(byte[] buf, boolean asc) {
        if (buf == null) {
            throw new IllegalArgumentException("byte array is null!");
        }
        if (buf.length > 2) {
            throw new IllegalArgumentException("byte array size > 2 !");
        }
        short r = 0;
        if (asc)
            for (int i = buf.length - 1; i >= 0; i--) {
                r <<= 8;
                r |= (buf[i] & 0x00ff);
            }
        else
            for (int i = 0; i < buf.length; i++) {
                r <<= 8;
                r |= (buf[i] & 0x00ff);
            }
        return r;
    }

    public final static int getInt(byte[] buf, boolean asc) {
        if (buf == null) {
            throw new IllegalArgumentException("byte array is null!");
        }
        if (buf.length > 4) {
            throw new IllegalArgumentException("byte array size > 4 !");
        }
        int r = 0;
        if (asc)
            for (int i = buf.length - 1; i >= 0; i--) {
                r <<= 8;
                r |= (buf[i] & 0x000000ff);
            }
        else
            for (int i = 0; i < buf.length; i++) {
                r <<= 8;
                r |= (buf[i] & 0x000000ff);
            }
        return r;
    }

    public final static long getLong(byte[] buf, boolean asc) {
        if (buf == null) {
            throw new IllegalArgumentException("byte array is null!");
        }
        if (buf.length > 8) {
            throw new IllegalArgumentException("byte array size > 8 !");
        }
        long r = 0;
        if (asc)
            for (int i = buf.length - 1; i >= 0; i--) {
                r <<= 8;
                r |= (buf[i] & 0x00000000000000ff);
            }
        else
            for (int i = 0; i < buf.length; i++) {
                r <<= 8;
                r |= (buf[i] & 0x00000000000000ff);
            }
        return r;
    }

    public static <T> T SpringMvcMapperReadValue(String body, Class<T> class1, String charset, boolean isbasedecode, boolean isurdecode) {
        if (isbasedecode) {
            body = SystemUtility.decodeBase64String(body, charset);
        }
        if (isurdecode) {
            body = HttpCommons.urlDecode(body);
        }
        return SpringMvcMapperReadValue(body, class1);
    }

    public static <T> T SpringMvcMapperReadValue(String body, Class<T> class1, boolean isbasedecode, boolean isurdecode) {
        return SpringMvcMapperReadValue(body, class1, "utf-8", isbasedecode, isurdecode);
    }

    public static String SpringMvcMapperWriteJSON(Object body) {
        ObjectMapper mapper = new ObjectMapper();
        String json = "{}";
        try {
            mapper.setSerializationInclusion(JsonSerialize.Inclusion.NON_NULL);
            json = mapper.writeValueAsString(body);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return json;
    }

    public static void SpringMvcMapperWriteJSON(Object body, OutputStream out) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            mapper.writeValue(out, body);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static <T> T SpringMvcMapperReadValue(String body, Class<T> class1) {
        ObjectMapper mapper = new ObjectMapper();
        T t = null;
        try {
            t = mapper.readValue(body, class1);
        } catch (JsonParseException e) {
            e.printStackTrace();
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return t;
    }

    public static void traceLog (Throwable e, Logger LOGGER){
        StringWriter writer = new StringWriter();
        PrintWriter s = new PrintWriter(writer);
        s.println(e.getMessage());
        e.printStackTrace(s);
        s.flush();

        LOGGER.error(writer.toString());
        s.close();
    }
    /**
     * 通过反射机制动态对对象的方法进行设置参数
     *
     * @param tag
     *            对象
     * @param methodname
     *            方法名
     * @param classes
     *            方法参数类型,方法类型是按照预先设置的进行查找
     * @param params
     *            参数内容，参数内容与方法类型顺序保持一致
     * @return 返回方法返回的结果，如果返回null 表示方法没有返回值或者设置失败，否则返回实际的内容
     */
    public static Object setMethodVlaue(Object tag, String methodname,
                                        Class classes[], Object params[]) {
        Object method_return = null;
        try {

            Method method = tag.getClass().getMethod(methodname, classes);
            if (method != null) {
                method_return = method.invoke(tag, params);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return method_return;
    }
    public static String urlDecode(String str) {
        try {
            str = URLDecoder.decode(str, "UTF-8");
        } catch (Exception var2) {
            var2.printStackTrace();
        }

        return str;
    }
    /**
     * MD5
     * @param s 加密内容
     * @param lowercase MD5内容是否小写
     * @return
     */
    public static String md5(String s, boolean lowercase) {
        char hexDigits[]={'0','1','2','3','4','5','6','7','8','9','A','B','C','D','E','F'};
        try {
            byte[] btInput = s.getBytes();
            // 获得MD5摘要算法的 MessageDigest 对象
            MessageDigest mdInst = MessageDigest.getInstance("MD5");
            // 使用指定的字节更新摘要
            mdInst.update(btInput);
            // 获得密文
            byte[] md = mdInst.digest();
            // 把密文转换成十六进制的字符串形式
            int j = md.length;
            char str[] = new char[j * 2];
            int k = 0;
            for (int i = 0; i < j; i++) {
                byte byte0 = md[i];
                str[k++] = hexDigits[byte0 >>> 4 & 0xf];
                str[k++] = hexDigits[byte0 & 0xf];
            }
            String md5string=new String(str);
            return lowercase? md5string.toLowerCase() :md5string;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    public static Object[] createObjectArray(Object... objects) {
        return objects;
    }
    public static Class[] createClassArray(Class... classes) {
        return classes;
    }
}
