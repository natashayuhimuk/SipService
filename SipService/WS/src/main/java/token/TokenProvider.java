package token;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lib.TypeLog;
import service.LoggerService;

import javax.ejb.EJB;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Properties;

public class TokenProvider {

    @EJB
    static LoggerService LOGGER;

    private static byte[] apiKeySecretBytes;
    private static byte[] passKeySecretBytes;

    static {
        Properties prop = new Properties();
        if (LOGGER == null){
            try {
                LOGGER = (LoggerService) new InitialContext().lookup("mappedLogService#service.LoggerService");
            } catch (NamingException e) {
                throw new RuntimeException("Failed initiation LOGGER in AuthorizationService" + e.getMessage());
            }
        }
        try {
            if (apiKeySecretBytes == null) {
                LOGGER.addLog(TypeLog.INFO,"'TokenProvider' Entering init secret key << " , new Object[]{null});
                prop.load(TokenProvider.class.getClassLoader().getResourceAsStream("application.properties"));
                apiKeySecretBytes = prop.getProperty("auth.tokenSecret").getBytes("UTF-8");
                passKeySecretBytes = prop.getProperty("pass.tokenSecret").getBytes("UTF-8");
                LOGGER.addLog(TypeLog.INFO,"'TokenProvider' Finish init secret key >> " , new Object[]{null});
            }
        } catch (UnsupportedEncodingException e) {
            LOGGER.addLog(TypeLog.ERRORR, "'TokenProvider' init secret key -> Unexpected error" , new Object[]{e});
        } catch (IOException e) {
            LOGGER.addLog(TypeLog.ERRORR, "'TokenProvider' init secret key -> Unexpected error" , new Object[]{e});
        }
    }

    public static String createToken(String phoneNumber) {
        return Jwts.builder()
                .setSubject(phoneNumber)
                .signWith(
                        SignatureAlgorithm.HS256,
                        apiKeySecretBytes
                )
                .compact();
    }

    public static String createPassword(String pass){
        return Jwts.builder()
                .setSubject(pass)
                .signWith(
                        SignatureAlgorithm.HS256,
                        passKeySecretBytes
                )
                .compact();
    }

    public static String getPhoneNumberFromToken(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(apiKeySecretBytes)
                .parseClaimsJws(token).getBody();
        return claims.getSubject();
    }

    public static boolean isValidToken(String token) {
        try {
            Jwts.parser()
                    .setSigningKey(apiKeySecretBytes)
                    .parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

}
