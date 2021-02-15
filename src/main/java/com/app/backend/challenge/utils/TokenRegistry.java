package com.app.backend.challenge.utils;

import com.app.backend.challenge.dao.DBApi;
import com.app.backend.challenge.resources.UserResource;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;

import java.security.SecureRandom;

/**
 * Token Registry
 */
public class TokenRegistry {

    private static TokenRegistry instance = null;

    /**
     * The secret chain to sign token
     */
    private byte[] sharedSecret;

    /**
     * Constructor
     */
    private TokenRegistry() {
        SecureRandom random = new SecureRandom();
        sharedSecret = new byte[32];
        random.nextBytes(sharedSecret);
    }


    /**
     *
     * @return TokenRegistry instance
     */
    public static TokenRegistry getInstance() {
        if (instance == null) {
            instance = new TokenRegistry();
        }
        return instance;

    }

    /**
     * It check the token signature and validate it contains a valid username in payload
     *
     * @param token to be verified
     * @return true if token was verfied otherwise false.
     * @throws Exception when something was wrong
     */
    public boolean verify(final String token) throws Exception {
        JWSVerifier verifier = new MACVerifier(sharedSecret);
        JWSObject jwsObject = JWSObject.parse(token);
        final String username = jwsObject.getPayload().toString();
        DBApi dbApi = new DBApi();
        UserResource userByName = dbApi.getUserByName(username);
        return userByName.getUsername().equals(username) && jwsObject.verify(verifier);
    }


    /**
     *
     * @param username user name to be added as payload
     * @return a jwt serialized/signed token
     */
    public String getToken(final String username) throws JOSEException {
        try {
            final JWSSigner signer = new MACSigner(sharedSecret);
            final JWSObject jwsObject = new JWSObject(new JWSHeader(JWSAlgorithm.HS256), new Payload(username));
            jwsObject.sign(signer);
            return jwsObject.serialize();
        } catch (JOSEException e) {
            System.out.println(e.getMessage());
            throw e;
        }
    }


}
