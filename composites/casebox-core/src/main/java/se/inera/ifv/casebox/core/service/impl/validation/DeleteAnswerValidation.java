package se.inera.ifv.casebox.core.service.impl.validation;

import se.inera.ifv.casebox.core.entity.Answer;
import se.inera.ifv.casebox.core.entity.MessageStatus;
import se.inera.ifv.casebox.core.repository.AnswerRepository;

import java.util.List;
import java.util.Set;

public class DeleteAnswerValidation {
    private AnswerRepository answerRepository;

    public DeleteAnswerValidation(AnswerRepository answerRepository) {
        this.answerRepository = answerRepository;
    }

    public void execute(String careUnit, Set<Long> ids) {
        if (careUnit == null) {
            throw new IllegalStateException("Cannot delete answers. supplied param careUnit is null");
        }
        List<Answer> answers;
        try{
            answers = answerRepository.findAllAnswersInIds(ids);
        }catch (Exception e){
            throw new IllegalStateException(
                    "Unexpected exception during findAllAnswersInIds param: "+ids,e);
        }


        if (answers == null || answers.size() == 0) {
            throw new IllegalStateException(
                    "Cannot delete answers. Supplied param ids: "+ids+"has no corresponding answers in persistent layer");
        }

        if (ids.size()!=answers.size()) {
            throw new IllegalStateException(
                    "Cannot delete answers. Some Supplied param ids: "+ids+"has no corresponding answers in persistent layer");
        }

        for (Answer answer : answers) {
            if (!careUnit.equals(answer.getCareUnit())) {
                throw new IllegalStateException(
                        "Cannot delete answers. Answer corresponding to id"
                                + answer.getId()
                                + " has a different careUnit: "
                                + answer.getCareUnit()
                                + " than the supplied param careUnit: "
                                + careUnit);
            }

            if (!MessageStatus.RETRIEVED.equals(answer.getStatus())) {
                throw new IllegalStateException(
                        "Cannot delete answers. Answer corresponding to id"
                                + answer.getId()
                                + " has status: "+answer.getStatus()
                                + " but only answers with status: "
                                + MessageStatus.RETRIEVED+" can be deleted");
            }
        }
    }
}
