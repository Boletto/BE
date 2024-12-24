package com.demoboletto.utility;

import io.jsonwebtoken.JwsHeader;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.apache.commons.io.FileUtils;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.bouncycastle.asn1.pkcs.PrivateKeyInfo;
import org.bouncycastle.openssl.PEMParser;
import org.bouncycastle.openssl.jcajce.JcaPEMKeyConverter;
import org.springframework.core.io.ClassPathResource;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.security.PrivateKey;
import java.util.Calendar;
import java.util.Date;

public class AppleTokenUtil {

    public static String createClientSecret(String clientId, String teamId, String keyId, String keyFilePath) throws Exception {
        String clientSecret = "";
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, 1);

        try {
            InputStream inputStream = new ClassPathResource(keyFilePath).getInputStream();
            File file = File.createTempFile("appleKeyFile", ".p8");
            try {
                FileUtils.copyInputStreamToFile(inputStream, file);
            } finally {
                IOUtils.closeQuietly(inputStream);
            }


            PEMParser pemParser = new PEMParser(new FileReader(file));
            JcaPEMKeyConverter converter = new JcaPEMKeyConverter();
            PrivateKeyInfo privateKeyInfo = (PrivateKeyInfo) pemParser.readObject();

            PrivateKey privateKey = converter.getPrivateKey(privateKeyInfo);

            clientSecret = Jwts.builder()
                    .setHeaderParam(JwsHeader.KEY_ID, keyId)
                    .setIssuer(teamId)
                    .setAudience("https://appleid.apple.com")
                    .setSubject(clientId)
                    .setExpiration(calendar.getTime())
                    .setIssuedAt(new Date(System.currentTimeMillis()))
                    .signWith(privateKey, SignatureAlgorithm.ES256)
                    .compact();

            // Save the client secret to a file

        } catch (IOException e) {
            e.printStackTrace();
        }
        return clientSecret;

    }

}