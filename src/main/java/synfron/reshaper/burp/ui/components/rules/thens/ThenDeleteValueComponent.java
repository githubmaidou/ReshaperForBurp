package synfron.reshaper.burp.ui.components.rules.thens;

import synfron.reshaper.burp.core.messages.MessageValue;
import synfron.reshaper.burp.core.rules.thens.ThenDeleteValue;
import synfron.reshaper.burp.core.utils.DeleteItemPlacement;
import synfron.reshaper.burp.ui.models.rules.thens.ThenDeleteValueModel;
import synfron.reshaper.burp.ui.utils.ComponentVisibilityManager;
import synfron.reshaper.burp.ui.utils.DocumentActionListener;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ThenDeleteValueComponent extends ThenComponent<ThenDeleteValueModel, ThenDeleteValue> {
    protected JComboBox<MessageValue> messageValue;
    protected JTextField identifier;
    protected JComboBox<DeleteItemPlacement> identifierPlacement;
    private final Set<MessageValue> excludedMessageValues = new HashSet<>(List.of(
            MessageValue.DestinationAddress,
            MessageValue.DestinationPort,
            MessageValue.HttpProtocol,
            MessageValue.Url,
            MessageValue.HttpRequestMessage,
            MessageValue.HttpRequestMethod,
            MessageValue.HttpResponseMessage,
            MessageValue.HttpRequestStatusLine,
            MessageValue.HttpResponseStatusLine,
            MessageValue.SourceAddress,
            MessageValue.HttpRequestUri,
            MessageValue.HttpResponseStatusCode
    ));

    public ThenDeleteValueComponent(ThenDeleteValueModel then) {
        super(then);
        initComponent();
    }

    private void initComponent() {
        messageValue = createComboBox(Arrays.stream(MessageValue.values())
                .filter(value -> !excludedMessageValues.contains(value))
                .toArray(MessageValue[]::new)
        );
        identifier = createTextField(true);
        identifierPlacement = createComboBox(DeleteItemPlacement.values());

        messageValue.setSelectedItem(model.getMessageValue());
        identifier.setText(model.getIdentifier());
        identifierPlacement.setSelectedItem(model.getIdentifierPlacement());

        messageValue.addActionListener(this::onMessageValueChanged);
        identifier.getDocument().addDocumentListener(new DocumentActionListener(this::onIdentifierChanged));
        identifierPlacement.addActionListener(this::onIdentifierPlacementChanged);

        mainContainer.add(getLabeledField("Message Value", messageValue), "wrap");
        mainContainer.add(ComponentVisibilityManager.withVisibilityFieldChangeDependency(
                getLabeledField("Identifier *", identifier),
                messageValue,
                () -> ((MessageValue) messageValue.getSelectedItem()).isIdentifierRequired()
        ), "wrap");
        mainContainer.add(ComponentVisibilityManager.withVisibilityFieldChangeDependency(
                getLabeledField("Identifier Placement", identifierPlacement),
                messageValue,
                () -> ((MessageValue) messageValue.getSelectedItem()).isIdentifierRequired()
        ), "wrap");
        mainContainer.add(getPaddedButton(validate));
    }

    private void onMessageValueChanged(ActionEvent actionEvent) {
        model.setMessageValue((MessageValue) messageValue.getSelectedItem());
    }

    private void onIdentifierChanged(ActionEvent actionEvent) {
        model.setIdentifier(identifier.getText());
    }

    private void onIdentifierPlacementChanged(ActionEvent actionEvent) {
        model.setIdentifierPlacement((DeleteItemPlacement) identifierPlacement.getSelectedItem());
    }
}
