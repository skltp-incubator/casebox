package se.inera.ifv.casebox.core.service.impl.validation;

public enum CBIllegalState {
  CARE_UNIT_NULL("Cannot delete answers. supplied param careUnit is null"),
  HQL_EXCEC_EXEPT("Unexpected exception during Hql excecution param: %s"),
  ANSWER_NOT_FOUND(
      "Cannot delete answers. Supplied param ids: %s has no corresponding answers in persistent layer"),
  SOME_ANSWERS_NOT_FOUND(
      "Cannot delete answers. Some of the Supplied param ids: %s has no corresponding answers in persistent layer"),
  WRONG_CARE_UNIT(
      "Cannot delete answers. Answer corresponding to id %s"
          + " has a different careUnit: %s"
          + " than the supplied param careUnit: %s"),
  WRONG_STATUS(
      "Cannot delete answers. Answer corresponding to id %s"
          + " has status: %s"
          + " but only answers with status: %s can be deleted");
  private final String message;

  CBIllegalState(String pMessage) {
    message = pMessage;
  }

  public String getMessage(){
      return message;
  }
}
