package com.test.parser;

import org.bouncycastle.jce.provider.X509CertificateObject;
import org.bouncycastle.openssl.PEMReader;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.StringReader;
import java.security.*;
import java.security.cert.CertificateException;
import java.security.cert.CertificateExpiredException;
import java.security.cert.CertificateNotYetValidException;
import java.util.Date;
import java.util.Properties;

public class X509CertificateParser implements CertificateParser {

    /**
     * Override method from the CertificateParser to parse the certificate using PEMReader
     *
     * @return X509Certificate object that contains all of the certificate data
     */
    @Override
    public Certificate parseCertificate(String certificatePath) {
        FileReader fileReader = null;
        PEMReader pemReader = null;
        X509CertificateObject certObj;
        Certificate x509Object = new Certificate();

        try {
            Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
            fileReader = new FileReader(certificatePath);
            pemReader = new PEMReader(fileReader);
            Object obj = pemReader.readObject();
            if (obj instanceof X509CertificateObject) {
                certObj = (X509CertificateObject) obj;
                x509Object = setData(certObj);
            }
        } catch (FileNotFoundException ex) {
            System.out.println("Invalid file path");
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            {
                if (pemReader != null) {
                    try {
                        pemReader.close();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
                if (fileReader != null) {
                    try {
                        fileReader.close();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        }
        return x509Object;
    }

    /**
     * Fetch the data from parser and creates a new X509Certificate object that contains all data in a user friendly
     * way
     *
     * @param x509CertificateObject
     * @return X509Certificate that contains all necessary information about the certificate
     */
    private Certificate setData(X509CertificateObject x509CertificateObject) {
        Certificate x509Obj = new Certificate();
        try {

            Properties dnProp = getProperties(x509CertificateObject.getSubjectDN());
            Properties issuerProp = getProperties(x509CertificateObject.getIssuerDN());

            if (dnProp.get("CN") != null) {
                x509Obj.setCommonName(dnProp.get("CN").toString());
            }
            if (dnProp.get("L") != null) {
                x509Obj.setLocality(dnProp.get("L").toString());
            }
            if (dnProp.get("O") != null) {
                x509Obj.setOrganization(dnProp.get("O").toString());
            }
            if (dnProp.get("C") != null) {
                x509Obj.setCountry(dnProp.get("C").toString());
            }
            x509Obj.setValidFrom(x509CertificateObject.getNotBefore());

            x509Obj.setValidTo(x509CertificateObject.getNotAfter());
            x509Obj.setSerialNumber(x509CertificateObject.getSerialNumber());
            if (issuerProp.get("O") != null) {
                x509Obj.setIssuerName(issuerProp.get("O").toString());
            }

            x509Obj.setValidityStatus("Valid Certificate");
            x509Obj.setValidityStatus(checkValidity(x509CertificateObject));
            x509Obj.setSelfSigned(isSelfSigned(x509CertificateObject));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return x509Obj;
    }

    /**
     * To get the properties of a principle
     *
     * @param principal
     * @return properties
     * @throws IOException
     */
    private Properties getProperties(Principal principal) throws IOException {
        Properties prop = new Properties();
        prop.load(new StringReader(principal.getName().replaceAll(",", "\n")));
        return prop;
    }

    /**
     * Check the validity of certificate by comparing it to current date
     *
     * @param x509CertificateObject Certificate that needs to be validated
     * @return description about validity of certificate
     */
    private String checkValidity(X509CertificateObject x509CertificateObject) {
        String status = "Valid Certificate";
        try {
            x509CertificateObject.checkValidity(new Date());
        } catch (CertificateExpiredException ex) {
            status = "Certificate is expired";

        } catch (CertificateNotYetValidException ex) {
            status = "Certificate not yet valid";
        }
        return status;
    }

    /**
     * To validate if the certificate is self signed by using the public key
     *
     * @param cert
     * @return true or false depending on the verification
     */
    private boolean isSelfSigned(X509CertificateObject cert) {
        try {
            PublicKey key = cert.getPublicKey();
            cert.verify(key);
        } catch (SignatureException sigEx) {
            return false;
        } catch (InvalidKeyException keyEx) {
            return false;
        } catch (NoSuchAlgorithmException ex) {
            return false;
        } catch (CertificateException ex) {
            return false;
        } catch (NoSuchProviderException ex) {
            return false;
        }
        return true;
    }
}
