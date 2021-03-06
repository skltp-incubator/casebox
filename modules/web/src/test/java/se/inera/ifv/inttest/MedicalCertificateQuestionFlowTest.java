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

package se.inera.ifv.inttest;

import static org.junit.Assert.*;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.junit.Test;
import org.w3.wsaddressing10.AttributedURIType;

import se.inera.ifv.findallquestionsresponder.v1.FindAllQuestionsResponseType;
import se.inera.ifv.util.DbunitTestBase;
import se.inera.ifv.util.QuestionsClient;


/**
 * @author Pär Wenåker
 *
 */
public class MedicalCertificateQuestionFlowTest extends DbunitTestBase {

    @PersistenceContext
    EntityManager entityManager;


    QuestionsClient client = new QuestionsClient();

    @Test
    public void testSuccess() throws Exception {
        
        AttributedURIType logicalAddress = new AttributedURIType();
        logicalAddress.setValue("testCareUnit");

        for(int i = 0; i < 26 ; i++) {
            client.receive(logicalAddress);
        }

        Integer length = (Integer) entityManager.createNativeQuery("SELECT COUNT(*) FROM QUESTION").getSingleResult();
        assertEquals(26, length.intValue());
        
        FindAllQuestionsResponseType resp = client.findAllQuestions(logicalAddress);
        
        assertEquals(10, resp.getQuestions().getQuestion().size());
        assertEquals(16, resp.getQuestionsLeft());

        client.deleteQuestions(logicalAddress, resp.getQuestions().getQuestion());

        length = (Integer) entityManager.createNativeQuery("SELECT COUNT(*) FROM QUESTION").getSingleResult();
        assertEquals(16, length.intValue());
       
        resp = client.findAllQuestions(logicalAddress);
        
        assertEquals(10, resp.getQuestions().getQuestion().size());
        assertEquals(6, resp.getQuestionsLeft());

        client.deleteQuestions(logicalAddress, resp.getQuestions().getQuestion());

        length = (Integer) entityManager.createNativeQuery("SELECT COUNT(*) FROM QUESTION").getSingleResult();
        assertEquals(6, length.intValue());

        resp = client.findAllQuestions(logicalAddress);
        
        assertEquals(6, resp.getQuestions().getQuestion().size());
        assertEquals(0, resp.getQuestionsLeft());

        client.deleteQuestions(logicalAddress, resp.getQuestions().getQuestion());

        length = (Integer) entityManager.createNativeQuery("SELECT COUNT(*) FROM QUESTION").getSingleResult();
        assertEquals(0, length.intValue());
    }

}
