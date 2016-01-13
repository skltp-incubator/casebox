
/*
 * 
 */

package se.inera.ifv.receivemedicalcertificatequestion.v1.rivtabp20;

import java.net.MalformedURLException;
import java.net.URL;
import javax.xml.namespace.QName;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceFeature;
import javax.xml.ws.Service;

/**
 * This class was generated by Apache CXF 2.3.0
 * Wed Jan 13 14:59:16 CET 2016
 * Generated source version: 2.3.0
 * 
 */


@WebServiceClient(name = "ReceiveMedicalCertificateQuestionResponderService", 
                  wsdlLocation = "file:/Users/matsekhammar/Documents/GIT/SKLTP/incubator/casebox/composites/casebox-schemas/src/main/resources/schemas/interactions/ReceiveMedicalCertificateQuestionInteraction/ReceiveMedicalCertificateQuestionInteraction_1.0_rivtabp20.wsdl",
                  targetNamespace = "urn:riv:insuranceprocess:healthreporting:ReceiveMedicalCertificateQuestion:1:rivtabp20") 
public class ReceiveMedicalCertificateQuestionResponderService extends Service {

    public final static URL WSDL_LOCATION;
    public final static QName SERVICE = new QName("urn:riv:insuranceprocess:healthreporting:ReceiveMedicalCertificateQuestion:1:rivtabp20", "ReceiveMedicalCertificateQuestionResponderService");
    public final static QName ReceiveMedicalCertificateQuestionResponderPort = new QName("urn:riv:insuranceprocess:healthreporting:ReceiveMedicalCertificateQuestion:1:rivtabp20", "ReceiveMedicalCertificateQuestionResponderPort");
    static {
        URL url = null;
        try {
            url = new URL("file:/Users/matsekhammar/Documents/GIT/SKLTP/incubator/casebox/composites/casebox-schemas/src/main/resources/schemas/interactions/ReceiveMedicalCertificateQuestionInteraction/ReceiveMedicalCertificateQuestionInteraction_1.0_rivtabp20.wsdl");
        } catch (MalformedURLException e) {
            System.err.println("Can not initialize the default wsdl from file:/Users/matsekhammar/Documents/GIT/SKLTP/incubator/casebox/composites/casebox-schemas/src/main/resources/schemas/interactions/ReceiveMedicalCertificateQuestionInteraction/ReceiveMedicalCertificateQuestionInteraction_1.0_rivtabp20.wsdl");
            // e.printStackTrace();
        }
        WSDL_LOCATION = url;
    }

    public ReceiveMedicalCertificateQuestionResponderService(URL wsdlLocation) {
        super(wsdlLocation, SERVICE);
    }

    public ReceiveMedicalCertificateQuestionResponderService(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public ReceiveMedicalCertificateQuestionResponderService() {
        super(WSDL_LOCATION, SERVICE);
    }
    
    public ReceiveMedicalCertificateQuestionResponderService(WebServiceFeature ... features) {
        super(WSDL_LOCATION, SERVICE, features);
    }
    public ReceiveMedicalCertificateQuestionResponderService(URL wsdlLocation, WebServiceFeature ... features) {
        super(wsdlLocation, SERVICE, features);
    }

    public ReceiveMedicalCertificateQuestionResponderService(URL wsdlLocation, QName serviceName, WebServiceFeature ... features) {
        super(wsdlLocation, serviceName, features);
    }

    /**
     * 
     * @return
     *     returns ReceiveMedicalCertificateQuestionResponderInterface
     */
    @WebEndpoint(name = "ReceiveMedicalCertificateQuestionResponderPort")
    public ReceiveMedicalCertificateQuestionResponderInterface getReceiveMedicalCertificateQuestionResponderPort() {
        return super.getPort(ReceiveMedicalCertificateQuestionResponderPort, ReceiveMedicalCertificateQuestionResponderInterface.class);
    }

    /**
     * 
     * @param features
     *     A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns ReceiveMedicalCertificateQuestionResponderInterface
     */
    @WebEndpoint(name = "ReceiveMedicalCertificateQuestionResponderPort")
    public ReceiveMedicalCertificateQuestionResponderInterface getReceiveMedicalCertificateQuestionResponderPort(WebServiceFeature... features) {
        return super.getPort(ReceiveMedicalCertificateQuestionResponderPort, ReceiveMedicalCertificateQuestionResponderInterface.class, features);
    }

}
