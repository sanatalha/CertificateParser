package com.test.parser;

import org.junit.Test;
import org.apache.commons.cli.*;

import java.util.Properties;

import static org.junit.Assert.*;

public class CLIClientTest {

    /**
     * To test the valid options in CLI client
     */
    @Test
    public void testValidOptions()
    {
        CLIClient cli = new CLIClient();
        Options options = cli.createOptions();
        assertTrue(options.hasOption("cn"));
        assertTrue(options.hasOption("sn"));
        assertFalse(options.hasOption("ui"));
    }

    /**
     * To test if parsing result returns right properties from the certificate
     */
    @Test
    public void testValidCLIProperties() {
        CLIClient cli = new CLIClient();
        Options options = cli.createOptions();
        String [] commands = {"./resources/c1.pem", "-i", "-vl"};
        Properties properties = cli.parse(commands);
        assertEquals("Issuer : EJBCA Sample", properties.getProperty("i") );
        assertEquals("Validity : Valid Certificate", properties.getProperty("vl") );
    }

}