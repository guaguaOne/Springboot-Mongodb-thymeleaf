package com.tumei.web;

import com.tumei.utils.RandomUtil;
import org.joda.time.DateTime;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import static javax.xml.crypto.dsig.SignatureMethod.HMAC_SHA1;

public class TokenInfo {
    private String account;
    private String token;
    private DateTime time;
    private int zone;
    private String idfa;

    public TokenInfo(String account, String token, DateTime time, int zone, String idfa) {
        this.account = account;
        this.token = token;
        this.time = time;
        this.zone = zone;
        this.idfa = idfa;
    }

    public static TokenInfo create(String _account, String _digest, int _zone) {
        String tn = String.format("%d-%d", DateTime.now(), RandomUtil.getRandom());
        Mac mac = null;
        try {
            mac = Mac.getInstance(HMAC_SHA1);
            SecretKeySpec signingKey = new SecretKeySpec(_digest.getBytes(), HMAC_SHA1);
            mac.init(signingKey);
            byte[] rawHmac = mac.doFinal(tn.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte b : rawHmac) {
                sb.append(byteToHexString(b));
            }

            return new TokenInfo(_account, sb.toString(), DateTime.now(), _zone, "");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private static String byteToHexString(byte ib) {
        char[] Digit = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
        char[] ob = new char[2];
        ob[0] = Digit[(ib >>> 4) & 0X0f];
        ob[1] = Digit[ib & 0X0F];
        String s = new String(ob);
        return s;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public DateTime getTime() {
        return time;
    }

    public void setTime(DateTime time) {
        this.time = time;
    }

    public int getZone() {
        return zone;
    }

    public void setZone(int zone) {
        this.zone = zone;
    }

    public String getIdfa() {
        return idfa;
    }

    public void setIdfa(String idfa) {
        this.idfa = idfa;
    }
}