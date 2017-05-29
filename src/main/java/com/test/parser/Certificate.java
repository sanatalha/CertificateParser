package com.test.parser;

import java.math.BigInteger;
import java.util.Date;

public class Certificate {

    /**
     * Common name of the certificate
     **/
    private String commonName;

    /**
     * Organization name in the certificate
     **/
    private String organization;

    /**
     * Locality of certificate
     **/
    private String locality;

    /**
     * Country of certificate
     **/
    private String country;

    /**
     * Validity start date in the certificate
     **/
    private Date validFrom;

    /**
     * Validity end date in the certificate
     **/
    private Date validTo;

    /**
     * Issuer name of the certificate
     **/
    private String issuerName;

    /**
     * Serial number of the certificate
     **/
    private BigInteger serialNumber;

    /**
     * Validity status of certificate
     **/
    private String validityStatus;

    /**
     * Represent if certificate is self signed or not
     **/
    private boolean selfSigned;

    /**
     * Getter and Setter methods
     **/

    public String getCommonName() {
        return commonName;
    }

    public void setCommonName(String commonName) {
        this.commonName = commonName;
    }

    public String getOrganization() {
        return organization;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
    }

    public String getLocality() {
        return locality;
    }

    public void setLocality(String locality) {
        this.locality = locality;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Date getValidFrom() {
        return validFrom;
    }

    public void setValidFrom(Date validFrom) {
        this.validFrom = validFrom;
    }

    public Date getValidTo() {
        return validTo;
    }

    public void setValidTo(Date validTo) {
        this.validTo = validTo;
    }

    public String getIssuerName() {
        return issuerName;
    }

    public void setIssuerName(String issuerName) {
        this.issuerName = issuerName;
    }

    public BigInteger getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(BigInteger serialNumber) {
        this.serialNumber = serialNumber;
    }

    public String getValidityStatus() {
        return validityStatus;
    }

    public void setValidityStatus(String validityStatus) {
        this.validityStatus = validityStatus;
    }

    public boolean isSelfSigned() {
        return selfSigned;
    }

    public void setSelfSigned(boolean selfSigned) {
        this.selfSigned = selfSigned;
    }
}
