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
package se.inera.ifv.casebox.core.service;


import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;


import org.dbunit.dataset.ITable;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import se.inera.ifv.casebox.core.entity.Answer;
import se.inera.ifv.casebox.core.service.impl.validation.DeleteAnswerValidationException;
import se.inera.ifv.casebox.core.service.impl.validation.CBIllegalState;

import static org.junit.Assert.fail;
import static se.inera.ifv.casebox.core.service.impl.validation.CBIllegalState.*;
import se.inera.ifv.casebox.util.JpaRepositoryTestBase;

@RunWith(SpringJUnit4ClassRunner.class)
public class AnswerServiceTest extends JpaRepositoryTestBase {

  @Autowired private AnswerService answerService;

  @PersistenceContext EntityManager entityManager;

  @Test
  public void deleteAnswers() throws Exception {
    Answer answer = new Answer("careUnit", "Some serializable object");
    answerService.saveAnswer(answer);

    entityManager.flush();

    Set<Long> ids = new HashSet<Long>();
    ids.add(answer.getId());
    testWithIllegalStatus(answer, ids);
    testWithNullCareUnit(ids);
    testWithAnswerNotFound(answer);
    testWithIllegalCareUnit(answer,ids);
    testOnlySomeIdsFound(answer,ids);
    testWithBadHQL(answer);
  }

  private void testWithAnswerNotFound(Answer answer) {

    try {
      Set<Long> setOfUnknownId = new HashSet<Long>();
        setOfUnknownId.add(new Long(-999));
      answerService.deleteAnswersForCareUnit(answer.getCareUnit(), setOfUnknownId);
      fail("Expected DeleteAnswerValidationException");
    } catch (DeleteAnswerValidationException e) {
        failIfUnexpectedState(ANSWER_NOT_FOUND,e);
    }
  }

  private void testWithNullCareUnit(Set<Long> ids) {
    try {
      answerService.deleteAnswersForCareUnit(null, ids);
      fail("Expected DeleteAnswerValidationException");
    } catch (DeleteAnswerValidationException e) {
        failIfUnexpectedState(CARE_UNIT_NULL,e);
    }
  }

  private void testWithIllegalCareUnit(Answer answer, Set<Long> ids) {
    try {
      answerService.deleteAnswersForCareUnit(answer.getCareUnit()+"No to be found", ids);
      fail("Expected DeleteAnswerValidationException");
    } catch (DeleteAnswerValidationException e) {
      failIfUnexpectedState(WRONG_CARE_UNIT,e);
    }
  }

  private void testOnlySomeIdsFound(Answer answer, Set<Long> ids) {
    try {
      Set<Long> someIds = new HashSet<>();
      someIds.add(new Long(-999));
      someIds.addAll(ids);
      answerService.deleteAnswersForCareUnit(answer.getCareUnit()+"No to be found", someIds);
      fail("Expected DeleteAnswerValidationException");
    } catch (DeleteAnswerValidationException e) {
      failIfUnexpectedState(SOME_ANSWERS_NOT_FOUND,e);
    }
  }

  private void testWithIllegalStatus(Answer answer, Set<Long> ids) {
    try {
      answerService.deleteAnswersForCareUnit(answer.getCareUnit(), ids);
      fail("Expected DeleteAnswerValidationException");
    } catch (DeleteAnswerValidationException e) {
        failIfUnexpectedState(WRONG_STATUS,e);
    }
  }

  private void testWithBadHQL(Answer answer) {
    try {
      Set<Long> myObjectSet = new HashSet<>();
      answerService.deleteAnswersForCareUnit(answer.getCareUnit(), myObjectSet);
      fail("Expected DeleteAnswerValidationException");
    } catch (DeleteAnswerValidationException e) {
      failIfUnexpectedState(HQL_EXCEC_EXEPT,e);
    }
  }

  private void failIfUnexpectedState(CBIllegalState expected, DeleteAnswerValidationException e) {
    if (!e.getState().equals(expected)) {
      fail(
          String.format(
              "Expected DeleteAnswerValidationException of state : %s but actual state is: %s exception.getMessage():  %s",
              expected, e.getState(), e.getMessage()));
    }
  }

  @Test
  public void checkStatistics() throws Exception {
    Answer answer = new Answer("careUnit", "Some serializable object");
    answer.setStatusRetrieved();
    answerService.saveAnswer(answer);

    Set<Long> ids = new HashSet<Long>();
    ids.add(answer.getId());
    answerService.deleteAnswersForCareUnit("careUnit", ids);

    entityManager.flush();

    ITable result =
        getConnection()
            .createQueryTable("STATISTIC", "SELECT * FROM STATISTIC WHERE CARE_UNIT = 'careUnit'");
    Assert.assertEquals(1, result.getRowCount());
    Assert.assertEquals("Answer", result.getValue(0, "MESSAGE_TYPE"));

    Calendar cal = Calendar.getInstance();
    cal.set(Calendar.HOUR, 0);
    cal.set(Calendar.MINUTE, 0);
    cal.set(Calendar.SECOND, 0);
    cal.set(Calendar.MILLISECOND, 0);
    cal.set(Calendar.AM_PM, Calendar.AM);
    Assert.assertEquals(cal.getTime(), result.getValue(0, "CREATED"));
  }

  @Test
  public void createStatisticsForSameDay() throws Exception {

    // Create first answer and delete it
    Answer answer = new Answer("careUnit", "Some serializable object");
    answer.setStatusRetrieved();
    answerService.saveAnswer(answer);

    Set<Long> ids = new HashSet<Long>();
    ids.add(answer.getId());

    // delete answers
    answerService.deleteAnswersForCareUnit("careUnit", ids);
    entityManager.flush();

    // create second answer and delete it
    answer = new Answer("careUnit", "Some serializable object");
    answer.setStatusRetrieved();
    answerService.saveAnswer(answer);

    ids = new HashSet<Long>();
    ids.add(answer.getId());

    answerService.deleteAnswersForCareUnit("careUnit", ids);
    entityManager.flush();

    // Check so that it only exist one statistics row for this careunit and day
    ITable result =
        getConnection()
            .createQueryTable("STATISTIC", "SELECT * FROM STATISTIC WHERE CARE_UNIT = 'careUnit'");
    Assert.assertEquals(1, result.getRowCount());
    Assert.assertEquals(2, result.getValue(0, "NUMBER"));
  }
}
