/*
 * Copyright 2010 Inera
 *
 *   This library is free software; you can redistribute it and/or modify
 *   it under the terms of version 2.1 of the GNU Lesser General Public
 *
 *   License as published by the Free Software Foundation.
 *
 *   This library is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *
 *   GNU Lesser General Public License for more details.
 *
 *   You should have received a copy of the GNU Lesser General Public
 *   License along with this library; if not, write to the
 *   Free Software Foundation, Inc., 59 Temple Place, Suite 330,
 *
 *   Boston, MA 02111-1307  USA
 */
package se.inera.ifv.caseservice;

import java.net.MalformedURLException;
import java.net.URL;

import org.w3.wsaddressing10.AttributedURIType;

import se.inera.ifv.qa.v1.Amnetyp;
import se.inera.ifv.qa.v1.InnehallType;
import se.inera.ifv.receivemedicalcertificateanswer.v1.rivtabp20.ReceiveMedicalCertificateAnswerResponderInterface;
import se.inera.ifv.receivemedicalcertificateanswer.v1.rivtabp20.ReceiveMedicalCertificateAnswerResponderService;
import se.inera.ifv.receivemedicalcertificateanswerresponder.v1.AnswerFromFkType;
import se.inera.ifv.receivemedicalcertificateanswerresponder.v1.ReceiveMedicalCertificateAnswerResponseType;
import se.inera.ifv.receivemedicalcertificateanswerresponder.v1.ReceiveMedicalCertificateAnswerType;

public final class ReceiveMedCertAnswerConsumer {

    private static final String LOGISK_ADDRESS = "/RecMedCertAnswer/1/rivtabp20";

    public static void main(String[] args) {
        String host = "localhost:8080";
        if (args.length > 0) {
            host = args[0];
        }

        // Setup ssl info for the initial ?wsdl lookup...
        //		System.setProperty("javax.net.ssl.keyStore","../certs/consumer.jks");
        //		System.setProperty("javax.net.ssl.keyStorePassword", "password");
        //		System.setProperty("javax.net.ssl.trustStore","../certs/truststore.jks");
        //		System.setProperty("javax.net.ssl.trustStorePassword", "password");

        String adress = "http://" + host + LOGISK_ADDRESS;
        System.out.println("Consumer connecting to " + adress);

        String p = null;
        try {
            p = callRecMedCertAnswer("SKL IFV App OK", adress, "Test");
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        System.out.println("Returned: " + p);
    }

    public static String callRecMedCertAnswer(String id, String serviceAddress, String logicalAddresss)
            throws Exception {

        ReceiveMedicalCertificateAnswerResponderInterface service = new ReceiveMedicalCertificateAnswerResponderService(
                createEndpointUrlFromServiceAddress(serviceAddress)).getReceiveMedicalCertificateAnswerResponderPort();

        AttributedURIType logicalAddressHeader = new AttributedURIType();
        logicalAddressHeader.setValue(logicalAddresss);

        ReceiveMedicalCertificateAnswerType request = new ReceiveMedicalCertificateAnswerType();

        // Simple Question
        AnswerFromFkType meddelande = new AnswerFromFkType();
        request.setAnswer(meddelande);
        meddelande.setAmne(Amnetyp.KONTAKT);
        InnehallType fraga = new InnehallType();
        fraga.setMeddelandeText("Kontakta mig!");
        meddelande.setFraga(fraga);

        try {
            ReceiveMedicalCertificateAnswerResponseType result = service.receiveMedicalCertificateAnswer(
                    logicalAddressHeader, request);

            if (result != null) {
                return ("Result OK");
            } else {
                return ("Result Error!");
            }

        } catch (Exception ex) {
            System.out.println("Exception=" + ex.getMessage());
            return null;
        }
    }

    public static URL createEndpointUrlFromServiceAddress(String serviceAddress) {
        try {
            return new URL(serviceAddress + "?wsdl");
        } catch (MalformedURLException e) {
            throw new RuntimeException("Malformed URL Exception: " + e.getMessage());
        }
    }
}
