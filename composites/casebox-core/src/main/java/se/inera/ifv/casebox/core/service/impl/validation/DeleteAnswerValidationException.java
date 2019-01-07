package se.inera.ifv.casebox.core.service.impl.validation;

public class DeleteAnswerValidationException extends IllegalStateException{
    private CBIllegalState state;
    public DeleteAnswerValidationException(CBIllegalState state, Object... objects) {
        super(stateToMessage(state,objects));
        this.state = state;
    }
    public DeleteAnswerValidationException(CBIllegalState state, Throwable e, Object... objects) {
        super(stateToMessage(state,objects),e);
        this.state = state;
    }

    private static String stateToMessage(CBIllegalState state, Object... objects){
        return String.format(state.getMessage(),objects);
    }

    public CBIllegalState getState(){
        return  state;
    }


}
