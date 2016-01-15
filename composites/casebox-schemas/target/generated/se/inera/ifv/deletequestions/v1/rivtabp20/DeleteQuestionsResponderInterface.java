package se.inera.ifv.deletequestions.v1.rivtabp20;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.xml.bind.annotation.XmlSeeAlso;

/**
 * This class was generated by Apache CXF 2.3.0
 * Fri Jan 15 16:22:41 CET 2016
 * Generated source version: 2.3.0
 * 
 */
 
@WebService(targetNamespace = "urn:riv:insuranceprocess:healthreporting:DeleteQuestions:1:rivtabp20", name = "DeleteQuestionsResponderInterface")
@XmlSeeAlso({org.w3.wsaddressing10.ObjectFactory.class, se.inera.ifv.v2.ObjectFactory.class, se.inera.ifv.deletequestionsresponder.v1.ObjectFactory.class, iso.v21090.dt.v1.ObjectFactory.class})
@SOAPBinding(parameterStyle = SOAPBinding.ParameterStyle.BARE)
public interface DeleteQuestionsResponderInterface {

    @WebResult(name = "DeleteQuestionsResponse", targetNamespace = "urn:riv:insuranceprocess:healthreporting:DeleteQuestionsResponder:1", partName = "parameters")
    @WebMethod(operationName = "DeleteQuestions", action = "urn:riv:insuranceprocess:healthreporting:DeleteQuestionsResponder:1")
    public se.inera.ifv.deletequestionsresponder.v1.DeleteQuestionsResponseType deleteQuestions(
        @WebParam(partName = "LogicalAddress", name = "To", targetNamespace = "http://www.w3.org/2005/08/addressing", header = true)
        org.w3.wsaddressing10.AttributedURIType logicalAddress,
        @WebParam(partName = "parameters", name = "DeleteQuestions", targetNamespace = "urn:riv:insuranceprocess:healthreporting:DeleteQuestionsResponder:1")
        se.inera.ifv.deletequestionsresponder.v1.DeleteQuestionsType parameters
    );
}
