package com.leigq.quartzlite.autoconfigure.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Base64Utils;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.cert.CertificateException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

/**
 * RSA非对称加密解密工具类
 *
 * <p>
 * 这种算法1978年就出现了，它是第一个既能用于数据加密也能用于数字签名的算法。它易于理解和操作，也很流行。 算法的名字以发明者的名字命名：Ron
 * Rivest, AdiShamir 和Leonard Adleman。 这种加密算法的特点主要是密钥的变化，RSA同时有两把钥匙，公钥与私钥。
 * 同时支持数字签名。数字签名的意义在于，对传输过来的数据进行校验。确保数据在传输工程中不被修改。
 * <p>
 * 参考：
 * <br/>
 * <ul>
 *     <li><a href='https://www.iteye.com/blog/snowolf-381767'>Java加密技术（四）——非对称加密算法RSA</a></li>
 *     <li><a href='https://my.oschina.net/jiangli0502/blog/171263'>RSA加密解密及数字签名Java实现</a></li>
 * </ul>
 * <p>
 * <a href="https://github.com/wwwtyro/cryptico">前端demo<a> <br>
 * <br>
 *
 * @author leigq
 */
public class RsaCoder {
    private static final Logger log = LoggerFactory.getLogger(RsaCoder.class);

    /**
     * 填充方式。这里有【RSA/ECB/PKCS1Padding】填充（默认）和【RSA/ECB/NoPadding】填充两种可选。
     * <p>
     * 注意：使用填充时，公钥每次加密的字符串都会不一样，这样更安全；不使用则每次都一样。因为java默认是填充的，而安卓默认不填充，
     * 所以安卓默认加密的密文，java默认不能解密！！必须手动指定他们用一致的填充方式，才能正确加密解密。
     */
    private static final String CIPHER_MODE = "RSA/ECB/PKCS1Padding";

    /**
     * 算法类型
     */
    private static final String ALGORITHM = "RSA";

    /**
     * RSA密钥长度必须是64的倍数，在512~65536之间。默认是1024
     */
    private static final int KEY_SIZE = 1024;

    /**
     * 由公钥字节数组用Base64编码成的字符串，方便传播、储存
     */
    public static String PUB_KEY_BASE64 = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCxKRX+P64kA0qrd3JYYZIZ5jam63DsAlx5PKlfC0hOAhJ3wfD2Bjl3CHKNMtEKwcnZlunvikOt7/7uKdVdxDYzwpU2ivwNXDA5kMPsx8prjwS7FsdCMWnOTGWBTCYeReFHWVmSj4KxYaOO7csPWBR0AhQX9qiPSWDEKcnH5YNiiQIDAQAB";

    /**
     * 由私钥字节数组用Base64编码成的字符串，方便传播、储存
     */
    public static String PRI_KEY_BASE64 = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBALEpFf4/riQDSqt3clhhkhnmNqbrcOwCXHk8qV8LSE4CEnfB8PYGOXcIco0y0QrBydmW6e+KQ63v/u4p1V3ENjPClTaK/A1cMDmQw+zHymuPBLsWx0Ixac5MZYFMJh5F4UdZWZKPgrFho47tyw9YFHQCFBf2qI9JYMQpycflg2KJAgMBAAECgYBltrwc1HzrykQFvDeXTLWwTv+TyFt19UkYhl6L5hNmTkRCI8RvzFUT5XK3ZLSmY2q7lazMTerbo44POU96XVvsV+ltmUW4ohh1cf+q7ICz73r+OEaFdxa+wHFthXvMuKpFbDiH89HfAmGGUVezf7dByClyVxn3yuKlb42ZC6AdsQJBAOyA+mBot7a98txTrMl4jRD1MI9t0dWHA51JzJ2vLBX4IlEr944Qhb6N0lNtYChEkEOCrTlLIDxWUtQhZbTP6R0CQQC/w8PcHulcbCY1JhBTefyzA4jmm9LZ0c+byqCSEiffE6/neTMLOxUpt9zDvtdWw7UvMZWgQ4a8QGZrlCw3Lw9dAkEAg9cqvE/kChU9q6FhszZmvUtR9MLttLD9TNN1I3ohg2W+C64M5L9FL4Lz+toAPrJqEZhpZIUCxWAB8ItlnTRB6QJBAKUMwsv3kxUoRG5kV5LxoK0XMsKBhaZSrmTBrxhqJgUbtb/+Eg/th1aD2LBl1oPoKE75V3Y8CICI0V5whunsSEUCQE1ZvMp5a0yblGENWU5F+kWT3aBCkmMN8Zqp2+R5p8kQ7Chxv7llCZ405YXnTdEQyLp+q6OW+eu0TdIQ3qHkA4c=";

    /**
     * 公钥对象
     */
    public static PublicKey PUB_KEY;

    /**
     * 私钥对象
     */
    public static PrivateKey PRI_KEY;

    // 初始化
    // ↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓
    // 静态代码块，初始化默认密钥对象，供后面使用
    static {
        try {
            PUB_KEY = restorePubKey(RsaCoder.PUB_KEY_BASE64);
            PRI_KEY = restorePriKey(RsaCoder.PRI_KEY_BASE64);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            log.error("初始化出错", e);
        }
    }


    // 加密、解密
    // ↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓

    /**
     * 用公钥加密 <br>
     * <br>
     * 创建人： leigq <br>
     * 创建时间： 2017年10月24日 下午2:00:49 <br>
     *
     * @param data 明文字符串
     * @return 密文字节数组用Base64算法编码成的字符串，方便传输、储存
     */
    public static String encryptByPubKey(String data) throws InvalidKeyException, NoSuchAlgorithmException,
            NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException {
        return encrypt(PUB_KEY, data);
    }

    /**
     * 用公钥加密 <br>
     * <br>
     * 创建人： leigq <br>
     * 创建时间： 2017年10月24日 下午2:00:49 <br>
     *
     * @param data   明文字符串
     * @param pubKey 公钥 BASE64加密
     * @return 密文字节数组用Base64算法编码成的字符串，方便传输、储存
     */
    public static String encryptByPubKey(String data, String pubKey)
            throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException,
            BadPaddingException, InvalidKeySpecException {
        PublicKey publicKey = restorePubKey(pubKey);
        return encrypt(publicKey, data);
    }

    /**
     * 用私钥解密 <br>
     * <br>
     * 创建人： leigq <br>
     * 创建时间： 2017年10月24日 下午1:57:42 <br>
     *
     * @param data 密文字符串（由密文字节数组用Base64算法编码成的字符串）
     * @return 明文字符串
     */
    public static String decryptByPriKey(String data) throws NoSuchPaddingException, BadPaddingException,
            NoSuchAlgorithmException, IllegalBlockSizeException, InvalidKeyException {
        return decrypt(PRI_KEY, data);
    }

    /**
     * 用私钥解密 <br>
     * <br>
     * 创建人： leigq <br>
     * 创建时间： 2017年10月24日 下午1:57:42 <br>
     *
     * @param data   密文字符串（由密文字节数组用Base64算法编码成的字符串）
     * @param priKey 私钥 BASE64 加密
     * @return 明文字符串
     */
    public static String decryptByPriKey(String data, String priKey)
            throws NoSuchPaddingException, BadPaddingException, NoSuchAlgorithmException, IllegalBlockSizeException, InvalidKeyException, InvalidKeySpecException {
        PrivateKey privateKey = restorePriKey(priKey);
        return decrypt(privateKey, data);
    }

    /**
     * 用私钥加密
     * <p>
     * 创建人：leigq <br>
     * 创建时间：2018年8月18日 下午5:37:32 <br>
     * <p>
     * 修改人： <br>
     * 修改时间： <br>
     * 修改备注： <br>
     * </p>
     *
     * @param data 明文字符串
     * @return 密文字节数组用Base64算法编码成的字符串，方便传输、储存
     */
    public static String encryptByPriKey(String data)
            throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException,
            BadPaddingException {
        return encrypt(PRI_KEY, data);
    }


    /**
     * 用私钥加密
     * <p>
     * 创建人：leigq <br>
     * 创建时间：2018年8月18日 下午5:37:32 <br>
     * <p>
     * 修改人： <br>
     * 修改时间： <br>
     * 修改备注： <br>
     * </p>
     *
     * @param data   明文字符串
     * @param priKey 私钥 BASE64 加密
     * @return 密文字节数组用Base64算法编码成的字符串，方便传输、储存
     */
    public static String encryptByPriKey(String data, String priKey)
            throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException,
            BadPaddingException, InvalidKeySpecException {
        PrivateKey privateKey = restorePriKey(priKey);
        return encrypt(privateKey, data);
    }

    /**
     * 用公钥解密
     * <p>
     * 创建人：leigq <br>
     * 创建时间：2018年8月18日 下午5:43:34 <br>
     * <p>
     * 修改人： <br>
     * 修改时间： <br>
     * 修改备注： <br>
     * </p>
     *
     * @param data 密文字符串（由密文字节数组用Base64算法编码成的字符串）
     * @return 明文字符串
     */
    public static String decryptByPubKey(String data)
            throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        return decrypt(PUB_KEY, data);
    }

    /**
     * 用公钥解密
     * <p>
     * 创建人：leigq <br>
     * 创建时间：2018年8月18日 下午5:43:34 <br>
     * <p>
     * 修改人： <br>
     * 修改时间： <br>
     * 修改备注： <br>
     * </p>
     *
     * @param data   密文字符串（由密文字节数组用Base64算法编码成的字符串）
     * @param pubKey 公钥 BASE64 加密
     * @return 明文字符串
     */
    public static String decryptByPubKey(String data, String pubKey)
            throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException, InvalidKeySpecException {
        PublicKey publicKey = restorePubKey(pubKey);
        return decrypt(publicKey, data);
    }

    /**
     * 从 .p12 文件中读取私钥。 <br>
     * <br>
     * 创建人： leigq <br>
     * 创建时间： 2017年10月28日 下午4:21:56 <br>
     *
     * @param pfxKeyFileName .p12文件路径
     * @param aliasName      私钥别名
     * @param pfxPassword    私钥密码
     * @return 私钥对象
     */
    public static PrivateKey readP12Key(String pfxKeyFileName, String aliasName, String pfxPassword)
            throws KeyStoreException, NoSuchAlgorithmException, CertificateException, IOException, UnrecoverableKeyException {
        InputStream fis = new FileInputStream(pfxKeyFileName);
        KeyStore keyStore = KeyStore.getInstance("PKCS12");
        keyStore.load(fis, pfxPassword.toCharArray());
        return (PrivateKey) keyStore.getKey(aliasName, pfxPassword.toCharArray());
    }

    /**
     * 通用加密操作
     * <p>
     * 创建人：leigq <br>
     * 创建时间：2018年8月18日 下午5:37:32 <br>
     * <p>
     * 修改人： <br>
     * 修改时间： <br>
     * 修改备注： <br>
     * </p>
     *
     * @param key  公钥或密钥对象
     * @param data 明文字符串
     * @return 密文字节数组用Base64算法编码成的字符串，方便传输、储存
     */
    private static String encrypt(Key key, String data)
            throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException,
            BadPaddingException {
        Cipher cipher = Cipher.getInstance(CIPHER_MODE);
        cipher.init(Cipher.ENCRYPT_MODE, key);
        byte[] bytes = cipher.doFinal(data.getBytes(StandardCharsets.UTF_8));
        return Base64Utils.encodeToString(bytes);
    }

    /**
     * 通用解密操作
     * <p>
     * 创建人：leigq <br>
     * 创建时间：2018年8月18日 下午5:43:34 <br>
     * <p>
     * 修改人： <br>
     * 修改时间： <br>
     * 修改备注： <br>
     * </p>
     *
     * @param key  公钥或密钥对象
     * @param data 密文字符串（由密文字节数组用Base64算法编码成的字符串）
     * @return 明文字符串
     */
    private static String decrypt(Key key, String data)
            throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        Cipher cipher = Cipher.getInstance(CIPHER_MODE);
        cipher.init(Cipher.DECRYPT_MODE, key);
        byte[] bytes = cipher.doFinal(Base64Utils.decodeFromString(data));
        return new String(bytes, StandardCharsets.UTF_8);
    }

    /**
     * 还原公钥，X509EncodedKeySpec 用于构建公钥的规范
     * <p>
     * 创建人：leigq <br>
     * 创建时间：2018年8月18日 下午5:16:50 <br>
     * <p>
     * 修改人： <br>
     * 修改时间： <br>
     * 修改备注： <br>
     * </p>
     *
     * @return 公钥对象
     */
    public static PublicKey restorePubKey(String pubKey)
            throws NoSuchAlgorithmException, InvalidKeySpecException {
        X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(Base64Utils.decodeFromString(pubKey));
        KeyFactory factory = KeyFactory.getInstance(ALGORITHM);
        return factory.generatePublic(x509EncodedKeySpec);
    }

    /**
     * 还原私钥，PKCS8EncodedKeySpec 用于构建私钥的规范
     * <p>
     * 创建人：leigq <br>
     * 创建时间：2018年8月18日 下午5:19:09 <br>
     * <p>
     * 修改人： <br>
     * 修改时间： <br>
     * 修改备注： <br>
     * </p>
     *
     * @return 私钥对象
     */
    public static PrivateKey restorePriKey(String priKey)
            throws NoSuchAlgorithmException, InvalidKeySpecException {
        PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(Base64Utils.decodeFromString(priKey));
        KeyFactory factory = KeyFactory.getInstance(ALGORITHM);
        return factory.generatePrivate(pkcs8EncodedKeySpec);
    }


    // 更换密钥对
    // ↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓

    /**
     * 生成密钥对。注意这里是生成密钥对KeyPair，再由密钥对获取公私钥
     * <p>
     * 创建人：leigq <br>
     * 创建时间：2018年8月18日 下午7:35:21 <br>
     * <p>
     * 修改人： <br>
     * 修改时间： <br>
     * 修改备注： <br>
     * </p>
     */
    public static void generateKeyPair() {
        try {
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(ALGORITHM);
            keyPairGenerator.initialize(KEY_SIZE);
            KeyPair keyPair = keyPairGenerator.generateKeyPair();

            /* 公钥、私钥用 encryptBase64 还是 encryptBase64Sun 加密都可以，后者的 Base64 是多行的，比较适合保存到文件的方式储存 */
            RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
            log.error("新公钥：{}", Base64Utils.encodeToString(publicKey.getEncoded()));

            RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
            log.error("新私钥：{}", Base64Utils.encodeToString(privateKey.getEncoded()));

        } catch (NoSuchAlgorithmException e) {
            log.error("生成密钥对异常：", e);
        }
    }
}
