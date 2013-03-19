/*
 * Copyright (c) 2011, All Rights Reserved.
 */

/**
 * ClassName: StringUtils
 * Function: 继承apache common的字符处理类
 *
 * @author   <a href="mailto:jarryli@gmail.com">lichunping</a>
 * @version  
 * @since    TODO
 * @Date     2011-8-27 07:11:45
 *
 * @see      
 */

package com.jiae.iseer.basic.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.text.CharacterIterator;
import java.text.DecimalFormat;
import java.text.StringCharacterIterator;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class StringUtils extends org.apache.commons.lang.StringUtils {
    
    protected static Logger logger = LoggerFactory.getLogger(StringUtils.class);

    static char[] hex = "0123456789ABCDEF".toCharArray();

    private StringUtils() {
    }
    
    /**
     * 移除内容中的html标记，返回纯文本
     * 
     * @author lichunping
     * @version 1.0.0
     * @param content 带html标记的内容
     * @return 纯文本
     */
    public static String filterHtml(String content) {
        if (isBlank(content)) {
            return null;
        }
        return content.replaceAll("<.*?>", "").replaceAll("&nbsp;", "");
    }
    /**
     * 移除内容中的script以及内容，返回纯文本
     * 
     * @author lichunping
     * @version 1.0.0
     * @param content 带html标记的内容
     * @return 纯文本
     */
    public static String filterScript(String content) {
        if (isBlank(content)) {
            return null;
        }
        return content.replaceAll("<script.*?</script>", "");
    }

    /**
     * 将整数转换成，每个bit对应序号的整数串。 比如输入参数3，对应二进制11，序号从右往左分别对应为1和2，则返回2,1
     * 
     * @author lichunping
	 * @version 1.0.0
     * @param in
     * @return
     */
    public static String parseIntegerToNumberdListString(Integer in) {
        if (in == null) {
            return "";
        }
        String result = Integer.toBinaryString(in);
        StringBuffer sb = new StringBuffer();
        char charSet[] = result.toCharArray();
        for (int i = 0, len = charSet.length; i < len; i++) {
            if (charSet[i] == '1') {
                if (i == 0) {
                    sb.append(len - i);
                } else {
                    sb.append("," + (len - i));
                }
            }
        }
        return sb.toString();
    }

    /**
     * 解析字符串为Long型List
     * 
     * @param String
     *            source 待转换的源字符串
     * @param String
     *            token 分隔源字符串的特殊字符
     * @return List<Long> 解析所得的Long型List
     * 
     * @author lichunping
     * @version 1.0.0
     */
    public static List<Long> parseStringToLongList(String source, String token) {
        if (StringUtils.isBlank(source) || StringUtils.isBlank(token)) {
            return null;
        }
        List<Long> result = new ArrayList<Long>();
        String[] units = source.split(token);
        for (String unit : units) {
            result.add(Long.valueOf(unit));
        }
        return result;
    }

    /**
     * 解析字符串为Integer型List
     * 
     * @param String
     *            source 待转换的源字符串
     * @param String
     *            token 分隔源字符串的特殊字符
     * @return List<Long> 解析所得的Integer型List
     * 
     * @author lichunping
     * @version 1.0.0
     */
    public static List<Integer> parseStringToIntegerList(String source,
            String token) {
        if (StringUtils.isBlank(source) || StringUtils.isBlank(token)) {
            return null;
        }
        List<Integer> result = new ArrayList<Integer>();
        String[] units = source.split(token);
        for (String unit : units) {
            result.add(Integer.valueOf(unit));
        }
        return result;
    }

    /**
     * 解析字符串为String型List
     * 
     * @param String
     *            source 待转换的源字符串
     * @param String
     *            token 分隔源字符串的特殊字符
     * @return List<Long> 解析所得的String型List
     * 
     * @author lichunping
     * @version 1.0.0
     */
    public static List<String> parseStringToStringList(String source,
            String token) {
        if (StringUtils.isBlank(source) || StringUtils.isBlank(token)) {
            return null;
        }
        List<String> result = new ArrayList<String>();
        String[] units = source.split(token);
        for (String unit : units) {
            result.add(unit);
        }
        return result;
    }

    /**
     * 将数组转化为指定分隔符的字符串
     * 
     * @param List
     *            list 待转换的数组
     * @param String
     *            token 分隔源字符串的特殊字符
     * @return String 转化所得的String
     * 
     * @author lichunping
     * @version 1.0.0
     */
    public static String parseListToString(List<Object> list, String token) {
        if (null == token) {
            return null;
        }
        if (null == list || list.size() == 0) {
            return "";
        }
        StringBuilder temp = new StringBuilder();
        for (Object unit : list) {
            temp.append(unit.toString()).append(token);
        }
        if (temp.length() > 0) {
            return temp.substring(0, temp.length() - 1);
        } else {
            return "";
        }
    }

    /**
     * 将普通字符串转变为json转码字符串，参考了googlecode上的json插件代码
     * 
     * @param String
     *            source 待转换的源字符串
     * @return String 转码后的字符串
     * 
     * @author lichunping
     * @version 1.0.0
     */
    public static String escape4Json(String source) {
        if (source == null) {
            return "";
        }
        StringBuilder buf = new StringBuilder();
        CharacterIterator it = new StringCharacterIterator(source);
        for (char c = it.first(); c != CharacterIterator.DONE; c = it.next()) {
            if (c == '"') {
                buf.append("\\\"");
            } else if (c == '\\') {
                buf.append("\\\\");
            } else if (c == '/') {
                buf.append("\\/");
            } else if (c == '\b') {
                buf.append("\\b");
            } else if (c == '\f') {
                buf.append("\\f");
            } else if (c == '\n') {
                buf.append("\\n");
            } else if (c == '\r') {
                buf.append("\\r");
            } else if (c == '\t') {
                buf.append("\\t");
            } else if (Character.isISOControl(c)) {
                unicode(buf, c);
            } else {
                buf.append(c);
            }
        }
        return buf.toString();
    }

    /**
     * Represent as unicode
     * 
     * @param c
     *            character to be encoded
     */
    private static void unicode(StringBuilder buf, char c) {
        buf.append("\\u");
        int n = c;
        for (int i = 0; i < 4; ++i) {
            int digit = (n & 0xf000) >> 12;
            buf.append(hex[digit]);
            n <<= 4;
        }
    }

    /**
     * 将普通字符串转变为html实体字符转码字符串
     * 
     * @param source 待转换的源字符串
     * @return 转码后的字符串
     * 
     * @author lichunping
     * @version 1.0.0
     */
    public static String escape4Html(String source) {
        if (source == null) {
            return "";
        }
        // 不使用标准的转码，因为标准转码不转单引号，转中文不符合我们的要求
        // return StringEscapeUtils.escapeHtml(source);
        StringBuffer str = new StringBuffer();
        for (int j = 0; j < source.length(); j++) {
            char c = source.charAt(j);
            if (c < '\200') {
                switch (c) {
                case '"':
                    str.append("&#34;");
                    break;
                case '&':
                    str.append("&#38;");
                    break;
                case '<':
                    str.append("&#60;");
                    break;
                case '>':
                    str.append("&#62;");
                    break;
                case '\'':
                    str.append("&#39;");
                    break;
                case '/':
                    str.append("&#47;");
                    break;
                case '\\':
                    str.append("&#92;");
                    break;
                default:
                    str.append(c);
                }
            }
            // 中文不用转义
            /*
             * else if (c > '\256') { str.append("&#" + (long) c + ";"); }
             */
            else {
                str.append(c);
            }
        }
        return str.toString();
    }

    /**
     * 将普通字符串中的中文进行转义
     * 
     * @param source 待转换的源字符串
     * @return 转码后的字符串
     * 
     * @author lichunping
     * @version 1.0.0
     */
    public static String escape4Chinese(String source, String encode)
            throws UnsupportedEncodingException {
        if (null == source) {
            return null;
        }
        int i = 0;
        int j = 0;
        int k = 10;
        StringBuffer localStringBuffer = new StringBuffer(source.length());
        ByteArrayOutputStream localByteArrayOutputStream = new ByteArrayOutputStream(
                k);
        OutputStreamWriter localOutputStreamWriter = new OutputStreamWriter(
                localByteArrayOutputStream, encode);
        for (int l = 0; l < source.length(); ++l) {
            int i1 = source.charAt(l);
            if (i1 < '\256') {
                localStringBuffer.append((char) i1);
            } else {
                try {
                    if (j != 0) {
                        localOutputStreamWriter = new OutputStreamWriter(
                                localByteArrayOutputStream, encode);
                        j = 0;
                    }
                    localOutputStreamWriter.write(i1);
                    if ((i1 >= 55296) && (i1 <= 56319)
                            && (l + 1 < source.length())) {
                        int i2 = source.charAt(l + 1);
                        if ((i2 >= 56320) && (i2 <= 57343)) {
                            localOutputStreamWriter.write(i2);
                            ++l;
                        }
                    }
                    localOutputStreamWriter.flush();
                } catch (IOException localIOException) {
                    localByteArrayOutputStream.reset();
                    break;
                }
                byte[] arrayOfByte = localByteArrayOutputStream.toByteArray();
                for (int i3 = 0; i3 < arrayOfByte.length; ++i3) {
                    localStringBuffer.append('%');
                    char c = Character.forDigit(arrayOfByte[i3] >> 4 & 0xF, 16);
                    if (Character.isLetter(c))
                        c = (char) (c - ' ');
                    localStringBuffer.append(c);
                    c = Character.forDigit(arrayOfByte[i3] & 0xF, 16);
                    if (Character.isLetter(c))
                        c = (char) (c - ' ');
                    localStringBuffer.append(c);
                }
                localByteArrayOutputStream.reset();
                i = 1;
            }
        }
        return ((i != 0) ? localStringBuffer.toString() : source);
    }

    /**
     * 将double型转为两位小数的人民币数值
     * 
     * @param Double
     * @return String
     * 
     * @author lichunping
     * @version 1.0.0
     */
    public static String getMoney(Double num) {

        DecimalFormat df = new DecimalFormat("######0.00");
        return df.format(num);
    }

    /**
     * 将数字转换为两位小数的百分数表示
     * 
     * @param Double
     *            需要表示为百分数的数字
     * @return String 代表百分数的字符串
     * 
     * @author lichunping
     * @version 1.0.0
     */
    public static String getPercent(Double num) {
        DecimalFormat df = new DecimalFormat("############0.00");
        String rtNum = df.format(num * 100);

        return rtNum + "%";
    }

    /**
     * 将String中出现某一子串之处用另一子串代替
     * 
     * @param inString
     *            进行处理的字符串
     * @param oldPattern
     *            被替换的子串
     * @param newPattern
     *            替换的子串
     * @return String 被替换后的字符串
     * 
     * @author lichunping
     * @version 1.0.0
     */
    public static String replace(String inString, String oldPattern,
            String newPattern) {
        if (inString == null) {
            return null;
        }
        if (oldPattern == null || newPattern == null) {
            return inString;
        }

        StringBuffer sbuf = new StringBuffer();
        // output StringBuffer we'll build up
        int pos = 0; // Our position in the old string
        int index = inString.indexOf(oldPattern);
        // the index of an occurrence we've found, or -1
        int patLen = oldPattern.length();
        while (index >= 0) {
            sbuf.append(inString.substring(pos, index));
            sbuf.append(newPattern);
            pos = index + patLen;
            index = inString.indexOf(oldPattern, pos);
        }
        sbuf.append(inString.substring(pos));

        // remember to append any characters to the right of a match
        return sbuf.toString();
    }

    /**
     * 过滤字符串中csv的保留字符，目前为",|
     * 
     * @param String
     *            源字符串
     * @return String 过滤后的字符串
     * 
     * @author lichunping
     * @version 1.0.0
     */
    public static String parseForCsv(String str) {
        if (str == null)
            return null;
        else {
            str = str.replace("\"", "");
            str = str.replace(",", "");
            str = str.replace("|", "");

            return str;
        }
    }

    /**
     * 计算md5值
     * 
     * @author lichunping
     * @since 1.0.0
     * @param source
     * @return
     */
    public static String md5(String source) {
        char hexChars[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
                'a', 'b', 'c', 'd', 'e', 'f' };
        try {
            byte[] bytes = source.getBytes();
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(bytes);
            bytes = md.digest();
            int j = bytes.length;
            char[] chars = new char[j * 2];
            int k = 0;
            for (int i = 0; i < bytes.length; i++) {
                byte b = bytes[i];
                chars[k++] = hexChars[b >>> 4 & 0xf];
                chars[k++] = hexChars[b & 0xf];
            }
            return new String(chars);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 验证密码是否符合复杂度要求
     * 
     * @author lichunping
     * @since 1.0.0
     * @param password
     *            密码
     * @param patternNum要求包含几种字符
     * @return 是否合格
     */
    public static boolean isValidPassword(String password, int patterNum) {
        // add by yangchenxing since 1.1.2
        // druc要求密码长度为6-32个字符
        if (password.length() < 6 || password.length() > 32) {
            return false;
        }
        String tempchar;
        boolean upperMatch = false;
        boolean lowerMatch = false;
        boolean numMatch = false;
        int matchNum = 0;
        Pattern upperPat = Pattern.compile("^[A-Z]+$");
        Pattern lowerPat = Pattern.compile("^[a-z]+$");
        Pattern numPat = Pattern.compile("^[0-9]+$");
        Pattern otherPat = Pattern.compile("^[^A-Za-z0-9]+$");
        Matcher m;
        for (int i = 0; i < password.length(); i++) {
            tempchar = password.substring(i, i + 1);
            m = upperPat.matcher(tempchar);
            if (m.find()) {
                upperMatch = true;
            }
            m = lowerPat.matcher(tempchar);
            if (m.find()) {
                lowerMatch = true;
            }
            m = numPat.matcher(tempchar);
            if (m.find()) {
                numMatch = true;
            }
            m = otherPat.matcher(tempchar);
            if (m.find()) {
                return false;
            }
        }
        if (upperMatch) {
            matchNum++;
        }
        if (lowerMatch) {
            matchNum++;
        }
        if (numMatch) {
            matchNum++;
        }
        if (matchNum < patterNum) {
            return false;
        } else {
            return true;
        }
    }

    public static Pattern mailPattern = Pattern.compile("^\\w[-+.\\w]*@\\w[-\\w]*(\\.\\w[-\\w]*)+$");

    /**
     * 检查电子邮件地址是否合法
     * 
     * @author lichunping
     * @since 1.0.0
     * @param mail
     *            电子邮件地址
     * @return 是否合法
     */
    public static boolean checkMail(String mail) {
        return null != mail && mail.length() <= 100
                && mailPattern.matcher(mail).matches();
    }

    /**
     * 检查日期参数是否合法。
     * 
     * @author lichunping
     * @since 1.0.0
     * @param time
     * @param time_len
     * @return
     */
    public static boolean checkTimeString(String time, int time_len) {
        if (time == null || time.length() != time_len) {
            return false;
        }
        try {
            Integer.parseInt(time);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    /**
     * 非法字符定义，用于用户名检查
     * 
     * @author lichunping
     * @since 1.0.0
     */
    private static char   INVALID_USERNAME_CHARS[] = { '、', '。', '?', 'ˉ', 'ˇ',
            '¨', '〃', '々', '—', '～', '‖', '…', '‘', '’', '“', '”', '〔', '〕',
            '〈', '〉', '《', '》', '「', '」', '『', '』', '〖', '〗', '【', '】', '±',
            '×', '÷', '∶', '∧', '∨', '∑', '∏', '∪', '∩', '∈', '∷', '√', '⊥',
            '∥', '∠', '⌒', '⊙', '∫', '∮', '≡', '≌', '≈', '∽', '∝', '≠', '≮',
            '≯', '≤', '≥', '∞', '∵', '∴', '♂', '♀', '°', '′', '″', '℃', '＄',
            '¤', '￠', '￡', '‰', '§', '№', '☆', '★', '○', '●', '◎', '◇', '◆',
            '□', '■', '△', '▲', '※', '→', '←', '↑', '↓', '〓', 'ⅰ', 'ⅱ', 'ⅲ',
            'ⅳ', 'ⅴ', 'ⅵ', 'ⅶ', 'ⅷ', 'ⅸ', 'ⅹ', '⒈', '⒉', '⒊', '⒋', '⒌', '⒍',
            '⒎', '⒏', '⒐', '⒑', '⒒', '⒓', '⒔', '⒕', '⒖', '⒗', '⒘', '⒙', '⒚',
            '⒛', '⑴', '⑵', '⑶', '⑷', '⑸', '⑹', '⑺', '⑻', '⑼', '⑽', '⑾', '⑿',
            '⒀', '⒁', '⒂', '⒃', '⒄', '⒅', '⒆', '⒇', '①', '②', '③', '④', '⑤',
            '⑥', '⑦', '⑧', '⑨', '⑩', '㈠', '㈡', '㈢', '㈣', '㈤', '㈥', '㈦', '㈧',
            '㈨', '㈩', 'Ⅰ', 'Ⅱ', 'Ⅲ', 'Ⅳ', 'Ⅴ', 'Ⅵ', 'Ⅶ', 'Ⅷ', 'Ⅸ', 'Ⅹ', 'Ⅺ',
            'Ⅻ', '！', '＂', '＃', '￥', '％', '＆', '＇', '（', '）', '＊', '＋', '，',
            '－', '．', '／', '：', '；', '＜', '＝', '＞', '？', '＠', '［', '＼', '］',
            '＾', '＿', '｀', '｛', '｜', '｝', 'ぁ', 'あ', 'ぃ', 'い', 'ぅ', 'う', 'ぇ',
            'え', 'ぉ', 'お', 'か', 'が', 'き', 'ぎ', 'く', 'ぐ', 'け', 'げ', 'こ', 'ご',
            'さ', 'ざ', 'し', 'じ', 'す', 'ず', 'せ', 'ぜ', 'そ', 'ぞ', 'た', 'だ', 'ち',
            'ぢ', 'っ', 'つ', 'づ', 'て', 'で', 'と', 'ど', 'な', 'に', 'ぬ', 'ね', 'の',
            'は', 'ば', 'ぱ', 'ひ', 'び', 'ぴ', 'ふ', 'ぶ', 'ぷ', 'へ', 'べ', 'ぺ', 'ほ',
            'ぼ', 'ぽ', 'ま', 'み', 'む', 'め', 'も', 'ゃ', 'や', 'ゅ', 'ゆ', 'ょ', 'よ',
            'ら', 'り', 'る', 'れ', 'ろ', 'ゎ', 'わ', 'ゐ', 'ゑ', 'を', 'ん', 'ァ', 'ア',
            'ィ', 'イ', 'ゥ', 'ウ', 'ェ', 'エ', 'ォ', 'オ', 'カ', 'ガ', 'キ', 'ギ', 'ク',
            'グ', 'ケ', 'ゲ', 'コ', 'ゴ', 'サ', 'ザ', 'シ', 'ジ', 'ス', 'ズ', 'セ', 'ゼ',
            'ソ', 'ゾ', 'タ', 'ダ', 'チ', 'ヂ', 'ッ', 'ツ', 'ヅ', 'テ', 'デ', 'ト', 'ド',
            'ナ', 'ニ', 'ヌ', 'ネ', 'ノ', 'ハ', 'バ', 'パ', 'ヒ', 'ビ', 'ピ', 'フ', 'ブ',
            'プ', 'ヘ', 'ベ', 'ペ', 'ホ', 'ボ', 'ポ', 'マ', 'ミ', 'ム', 'メ', 'モ', 'ャ',
            'ヤ', 'ュ', 'ユ', 'ョ', 'ヨ', 'ラ', 'リ', 'ル', 'レ', 'ロ', 'ヮ', 'ワ', 'ヰ',
            'ヱ', 'ヲ', 'ン', 'ヴ', 'ヵ', 'ヶ', 'Α', 'Β', 'Γ', 'Δ', 'Ε', 'Ζ', 'Η',
            'Θ', 'Ι', 'Κ', 'Λ', 'Μ', 'Ν', 'Ξ', 'Ο', 'Π', 'Ρ', 'Σ', 'Τ', 'Υ',
            'Φ', 'Χ', 'Ψ', 'Ω', 'α', 'β', 'γ', 'δ', 'ε', 'ζ', 'η', 'θ', 'ι',
            'κ', 'λ', 'μ', 'ν', 'ξ', 'ο', 'π', 'ρ', 'σ', 'τ', 'υ', 'φ', 'χ',
            'ψ', 'ω', '︵', '︶', '︹', '︺', '︿', '﹀', '︽', '︾', '﹁', '﹂', '﹃',
            '﹄', '︻', '︼', '︷', '︸', '︱', '︳', '︴', 'А', 'Б', 'В', 'Г', 'Д',
            'Е', 'Ё', 'Ж', 'З', 'И', 'Й', 'К', 'Л', 'М', 'Н', 'О', 'П', 'Р',
            'С', 'Т', 'У', 'Ф', 'Х', 'Ц', 'Ч', 'Ш', 'Щ', 'Ъ', 'Ы', 'Ь', 'Э',
            'Ю', 'Я', '㊣', '℡', '㈱', '﹐', '﹑', '﹒', '﹔', '﹕', '﹖', '﹗', '﹙',
            '﹚', '﹛', '﹜', '﹝', '﹞', '﹟', '﹠', '﹡', '﹢', '﹣', '﹤', '﹥', '﹦',
            '﹨', '﹩', '﹪', '﹫', 'а', 'б', 'в', 'г', 'д', 'е', 'ё', 'ж', 'з',
            'и', 'й', 'к', 'л', 'м', 'н', 'о', 'п', 'р', 'с', 'т', 'у', 'ф',
            'х', 'ц', 'ч', 'ш', 'щ', 'ъ', 'ы', 'ь', 'э', 'ю', 'я' };
    public static Pattern userNamePattern = Pattern.compile(".*([@#$%^&*?!+_ =~`:;\"'|\\\\<>,./{}\\[\\]\\(\\)"
                                                               + new String(
                                                                   INVALID_USERNAME_CHARS)
                                                               + "]+).*");
    
    /**
     * 类似javascript 中join
     * 
     * @author lichunping
     * @since 1.0.0
     * @param o
     * @param flag
     * @return 连接号的字符串
     */
//    public static String join(Object[] o, String flag) {
//        if (o == null || flag == null) {
//            return null;
//        }
//        StringBuffer str = new StringBuffer();
//        int len = o.length;
//        for (int i = 0 ; i < len; i++) {
//            str.append(String.valueOf(o[i]));
//            if (i < (len - 1)) {
//                str.append(flag);
//            }
//        }
//        return str.toString();
//    }

}
