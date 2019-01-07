package se.inera.ifv.casebox.core.service.impl.validation;

import se.inera.ifv.casebox.core.entity.Answer;

import static se.inera.ifv.casebox.core.entity.MessageStatus.*;
import se.inera.ifv.casebox.core.repository.AnswerRepository;
import static se.inera.ifv.casebox.core.service.impl.validation.CBIllegalState.*;
import java.util.List;
import java.util.Set;

public class DeleteAnswerValidation {
  private AnswerRepository answerRepository;

  public DeleteAnswerValidation(AnswerRepository answerRepository) {
    this.answerRepository = answerRepository;
  }

  public void execute(String careUnit, Set<Long> ids) {
    if (careUnit == null) {
      throw new DeleteAnswerValidationException(CARE_UNIT_NULL);
    }
    List<Answer> answers;
    try {
      answers = answerRepository.findAllAnswersInIds(ids);
    } catch (Exception e) {
      throw new DeleteAnswerValidationException(HQL_EXCEC_EXEPT, e, ids);
    }

    if (answers == null || answers.size() == 0) {
      throw new DeleteAnswerValidationException(ANSWER_NOT_FOUND, ids);
    }

    if (ids.size() != answers.size()) {
      throw new DeleteAnswerValidationException(SOME_ANSWERS_NOT_FOUND, ids);
    }

    for (Answer answer : answers) {
      if (!careUnit.equals(answer.getCareUnit())) {
        throw new DeleteAnswerValidationException(
            WRONG_CARE_UNIT, answer.getId(), answer.getCareUnit(), careUnit);
      }

      if (!RETRIEVED.equals(answer.getStatus())) {
        throw new DeleteAnswerValidationException(
            WRONG_STATUS, answer.getId(), answer.getStatus(), RETRIEVED);
      }
    }

  }
}
