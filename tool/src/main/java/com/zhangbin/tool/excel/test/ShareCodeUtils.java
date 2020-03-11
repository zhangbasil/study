package com.zhangbin.tool.excel.test;

import java.util.Random;

/**
 * @author <a href="mailto:hbsy_zhb@163.com">zhangbin</a>
 */
public class ShareCodeUtils {

    /**
     * 序列化编码字典
     * 从左往右 在没有出现扰码前，都为正常序列化编码
     */
    private static final char[] SERIAL_CODE_DICT = new char[]{'q', 'w', '%', '8', 'a', '-', '2', 'd', '#', 'x', '9', 'o', 'c', '7', '_', '5', 'i', 'k', '3', 'm', 'j', 'u', 'f', 'r', '4', 'v', 'y', 'l', 't', 'n', '6', 'b', 'g', 'h'};


    /**
     * 扰码
     * 出现这些字符后的剩余字符 则统统为 随机字符
     */
    private static final char[] SCRAMBLER_DICT = new char[]{'s', 'z', '$', 'e', 'p', '0', '1'};


    /**
     * 全部字典
     */
    private static final char[] All_DICT = new char[]{'q', 'w', '%', '8', 'a', '-', '2', 'd', '#', 'x', '9', 'o', 'c', '7', '_', '5', 'i', 'k', '3', 'm', 'j', 'u', 'f', 'r', '4', 'v', 'y', 'l', 't', 'n', '6', 'b', 'g', 'h', 's', 'z', '$', 'e', 'p', '0', '1'};


    private static final int BIN_LEN = SERIAL_CODE_DICT.length;

    /**
     * 邀请码最小长度
     */
    private static final int SERIAL_CODE_LENGTH = 6;


    public static String idToCode(long id) {
        return idToCode(id, SERIAL_CODE_LENGTH);
    }

    public static String idToCode(long id, int len) {
        //存储得到的真实序列化id后的编码 最长32个字符
        final char[] codes = new char[32];
        // 下标
        int index = codes.length;
        //id大于进制位数则进入循环
        while (id > BIN_LEN) {
            // id与位数的余数  10 /33 = 10
            int residue = (int) (id % BIN_LEN);
            // 保存序列化后的字符
            codes[--index] = SERIAL_CODE_DICT[residue];
            id /= BIN_LEN;
        }
        codes[--index] = SERIAL_CODE_DICT[(int) (id % BIN_LEN)];
        StringBuilder code = new StringBuilder(new String(codes, index, (32 - index)));
        // 不够长度的自动随机补全
        if (code.length() < len) {
            Random random = new Random();
            //扰码
            code.append(SCRAMBLER_DICT[random.nextInt(SCRAMBLER_DICT.length)]);
            for (int i = 1; code.length() < len; i++) {
                //扰码
                if (random.nextInt(3) == 1) {
                    code.insert(0, SCRAMBLER_DICT[random.nextInt(SCRAMBLER_DICT.length)]);
                } else {
                    code.append(All_DICT[random.nextInt(All_DICT.length)]);
                }
            }
        }
        return code.toString();
    }

    /**
     * 将序列化好的邀请码进行解码操作
     *
     * @return
     */
    public static long codeToId(String code) {
        final char[] codes = code.toCharArray();
        //排除左边的扰码 找到正常码开始的下标
        long id = 0L;
        //从左往右  扰码是否结束
        boolean scramblerIsEnd = false;
        for (int i = 0; i < codes.length; i++) {
            //排除前面扰码部分
            if (!scramblerIsEnd) {
                //如果是扰码  直接跳过本次循环
                if (isScrambler(codes[i])) {
                    continue;
                } else {
                    scramblerIsEnd = true;
                }
            }
            //如果是扰码  直接停止循环
            if (isScrambler(codes[i])) {
                break;
            }
            //找到对应编码下标
            int index = 0;
            for (int j = 0; j < BIN_LEN; j++) {
                if (codes[i] == SERIAL_CODE_DICT[j]) {
                    index = j;
                    break;
                }
            }
            if (i > 0) {
                id = id * BIN_LEN + index;
            } else {
                id = index;
            }
        }
        return id;
    }

    private static Boolean isScrambler(char scramblerCode) {
        for (char code : SCRAMBLER_DICT) {
            if (scramblerCode == code) {
                return true;
            }
        }
        return false;
    }


    public static void main(String[] args) {

        String code = idToCode(530670, 64);

        System.out.println("code = " + code);

        long id = codeToId(code);

        System.out.println("id = " + id);

    }
}
