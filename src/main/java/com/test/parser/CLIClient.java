package com.test.parser;

import org.apache.commons.cli.*;

import java.util.Arrays;
import java.util.Enumeration;
import java.util.Properties;

public class CLIClient {

    /**
     * Use the formatter class to show how to use the CLI Client
     *
     * @param options
     */
    private static void usage(Options options) {
        HelpFormatter formatter = new HelpFormatter();
        formatter.printHelp("CLI", options);
    }

    /**
     * Main entry point for CLI Client
     *
     * @param args user commands
     */
    public static void main(String args[]) {
        new CLIClient().parseCommands(args);
    }

    public void parseCommands(String[] arguments) {

        Properties properties = parse(arguments);
        displayResult(properties);

    }

    public Properties parse(String[] arguments) {
        Certificate x509Object = new X509CertificateParser().parseCertificate(arguments[0]);
        Options options = createOptions();
        String[] commands = Arrays.copyOfRange(arguments, 0, arguments.length);
        /* Parse the input */
        CommandLineParser parser = new BasicParser();
        CommandLine cmd = null;
        try {
            cmd = parser.parse(options, commands);
        } catch (ParseException pe) {
            usage(options);
        }

        Properties properties = executeCommands(options, cmd, x509Object);
        return properties;
    }


    public Options createOptions() {
        Options options = new Options();
        options.addOption(new Option("help", false, "Help"));
        options.addOption(new Option("ls", false, "List all"));
        options.addOption(new Option("cn", false, "Common name"));
        options.addOption(new Option("o", false, "Organization"));
        options.addOption(new Option("l", false, "Locality"));
        options.addOption(new Option("c", false, "Country"));
        options.addOption(new Option("vf", false, "Valid From"));
        options.addOption(new Option("vt", false, "Valid To"));
        options.addOption(new Option("sn", false, "Serial Number"));
        options.addOption(new Option("vl", false, "Check Validity"));
        options.addOption(new Option("vs", false, "Self signed certificate"));
        options.addOption(new Option("i", false, "Issuer"));

        return options;
    }

    /**
     * Interrogate the options and saves the relevant results in properties
     *
     * @param options    contains all options
     * @param cmd        all user commands
     * @param x509Object certificate object
     * @return properties containing all relavant data
     */
    public Properties executeCommands(Options options, CommandLine cmd, Certificate x509Object) {

        Properties prop = new Properties();
        if (cmd != null) {
            prop.put("o", fetchValues("o", "Organization name : ", cmd, x509Object.getOrganization()));
            prop.put("cn", fetchValues("cn", "Common name : ", cmd, x509Object.getCommonName()));
            prop.put("l", fetchValues("l", "Locality : ", cmd, x509Object.getLocality()));
            prop.put("c", fetchValues("c", "Country : ", cmd, x509Object.getCountry()));
            prop.put("i", fetchValues("i", "Issuer : ", cmd, x509Object.getIssuerName()));
            prop.put("vl", fetchValues("vl", "Validity : ", cmd, x509Object.getValidityStatus()));
            if (cmd.hasOption("vf") || cmd.hasOption("ls")) {
                prop.put("vf ", "Valid From : " + x509Object.getValidFrom());
            }
            if (cmd.hasOption("vt") || cmd.hasOption("ls")) {
                prop.put("vt ", "Valid To : " + x509Object.getValidTo());
            }
            if (cmd.hasOption("sn") || cmd.hasOption("ls")) {
                prop.put("sn ", "Serial number : " + x509Object.getSerialNumber());
            }
            if (cmd.hasOption("vs") || cmd.hasOption("ls")) {
                prop.put("vs ", "Self Signed : " + x509Object.isSelfSigned());
            }

            if (cmd.hasOption("help")) {
                usage(options);
            }
        }

        return prop;
    }

    private String fetchValues(String command, String title, CommandLine cmd, String data) {
        if ((cmd.hasOption(command) || cmd.hasOption("ls")) && data != null) {
            return title + data;
        }
        return "";
    }

    /**
     * Fetch the results from properties and displays them
     *
     * @param properties
     */
    private void displayResult(Properties properties) {
        Enumeration e = properties.propertyNames();
        while (e.hasMoreElements()) {
            String key = (String) e.nextElement();
            if (!properties.getProperty(key).equals("")) {
                System.out.println(properties.getProperty(key));
            }
        }
    }
}
