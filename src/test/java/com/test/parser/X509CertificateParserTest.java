package com.test.parser;

import org.junit.Test;

import static org.junit.Assert.*;

public class X509CertificateParserTest {

    /**
     * To test the validity of certificate, if it's still valid or not
     */
    @Test
    public void testValidCertificate() {
        Certificate x509Object = new X509CertificateParser().parseCertificate("./resources/c1.pem");
        assertEquals("Valid Certificate", x509Object.getValidityStatus());
    }

    /**
     * To test the results when certificate is self signed
     */
    @Test
    public void testWhenSelfSigned() {
        Certificate x509Object = new X509CertificateParser().parseCertificate("./resources/c2.pem");
        assertTrue(x509Object.isSelfSigned());
    }

    /**
     * To test the results when certificate is not self signed
     */
    @Test
    public void testWhenNotSelfSigned() {
        Certificate x509Object1 = new X509CertificateParser().parseCertificate("./resources/c1.pem");
        assertFalse(x509Object1.isSelfSigned());
    }

    /**
     * To test if the certificate has right locality
     */
    @Test
    public void testValidLocality() {
        Certificate x509Object = new X509CertificateParser().parseCertificate("./resources/c1.pem");
        assertEquals("Stockholm", x509Object.getLocality());
    }
}