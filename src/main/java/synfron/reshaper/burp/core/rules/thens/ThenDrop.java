package synfron.reshaper.burp.core.rules.thens;

import lombok.Getter;
import lombok.Setter;
import synfron.reshaper.burp.core.messages.IEventInfo;
import synfron.reshaper.burp.core.rules.RuleOperationType;
import synfron.reshaper.burp.core.rules.RuleResponse;

public class ThenDrop extends Then<ThenDrop> {
    @Getter @Setter
    private boolean dropMessage = true;

    public RuleResponse perform(IEventInfo eventInfo) {
        eventInfo.setShouldDrop(dropMessage);
        if (eventInfo.getDiagnostics().isEnabled()) eventInfo.getDiagnostics().logValue(this, false, dropMessage);
        return RuleResponse.Continue;
    }

    @Override
    public RuleOperationType<ThenDrop> getType() {
        return ThenType.Drop;
    }
}
