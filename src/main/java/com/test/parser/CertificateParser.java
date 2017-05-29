package com.test.parser;

/**
 * Interface to create certificate parsing rules
 *
 * @param <T>
 */
public interface CertificateParser<T> {
    public T parseCertificate(String path);
}
