package eu.maxschuster.vaadin.v7.migrationfield.demo;

import javax.servlet.annotation.WebServlet;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Title;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.data.Binder;
import com.vaadin.data.ValidationResult;
import com.vaadin.data.Validator;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.DateField;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import eu.maxschuster.vaadin.signaturefield.SignatureField;
import eu.maxschuster.vaadin.v7.migrationfield.MigrationBinder;
import eu.maxschuster.vaadin.v7.migrationfield.MigrationField;
import java.io.Serializable;
import java.time.LocalDate;

@Theme("valo")
@Title("MigrationField Add-on Demo")
@SuppressWarnings("serial")
public class DemoUI extends UI {

    @WebServlet(value = "/*", asyncSupported = true)
    @VaadinServletConfiguration(productionMode = false, ui = DemoUI.class, widgetset = "eu.maxschuster.vaadin.v7.migrationfield.AppWidgetSet")
    public static class Servlet extends VaadinServlet {
    }

    private static class DTO implements Serializable {

        private String textField = "A TextField";

        private String textArea = "A TextArea";

        private Boolean checkBox = true;

        private LocalDate dateField = LocalDate.now();
        
        private String signatureField;

        public String getTextField() {
            return textField;
        }

        public void setTextField(String textField) {
            this.textField = textField;
        }

        public String getTextArea() {
            return textArea;
        }

        public void setTextArea(String textArea) {
            this.textArea = textArea;
        }

        public Boolean getCheckBox() {
            return checkBox;
        }

        public void setCheckBox(Boolean checkBox) {
            this.checkBox = checkBox;
        }

        public LocalDate getDateField() {
            return dateField;
        }

        public void setDateField(LocalDate field) {
            this.dateField = field;
        }

        public String getSignatureField() {
            return signatureField;
        }

        public void setSignatureField(String signatureField) {
            this.signatureField = signatureField;
        }

    }
    
    private static final String REQUIRED_MESSAGE
            = "This is a required error message!";
    
    private final Validator<String> invalidValidator
            = (value, context) -> "invalid".equalsIgnoreCase(value) ?
                    ValidationResult.error("This is a Validator error message!") : ValidationResult.ok();

    @Override
    protected void init(VaadinRequest request) {

        DTO dto = new DTO();
        
        // Use the MigrationBinder to support error messages
        Binder<DTO> binder = new MigrationBinder<>(DTO.class);

        VerticalLayout mainLayout = new VerticalLayout();
        setContent(mainLayout);

        HorizontalLayout horizontalLayout = new HorizontalLayout();
        mainLayout.addComponent(horizontalLayout);

        Panel v8Panel = new Panel("Vaadin 8");
        v8Panel.setWidth("300px");
        horizontalLayout.addComponent(v8Panel);

        VerticalLayout v8Layout = new VerticalLayout();
        v8Layout.setWidth("100%");
        v8Panel.setContent(v8Layout);

        TextField textField = new TextField();
        textField.setWidth("100%");
        binder.forField(textField)
                .withNullRepresentation("null")
                .asRequired(REQUIRED_MESSAGE)
                .withValidator(invalidValidator)
                .bind(DTO::getTextField, DTO::setTextField);
        v8Layout.addComponent(textField);

        TextArea textArea = new TextArea();
        textArea.setWidth("100%");
        binder.forField(textArea)
                .withNullRepresentation("null")
                .withValidator(invalidValidator)
                .asRequired(REQUIRED_MESSAGE)
                .bind(DTO::getTextArea, DTO::setTextArea);
        v8Layout.addComponent(textArea);

        CheckBox checkBox = new CheckBox();
        checkBox.setWidth("100%");
        binder.forField(checkBox)
                .asRequired(REQUIRED_MESSAGE)
                .bind(DTO::getCheckBox, DTO::setCheckBox);
        v8Layout.addComponent(checkBox);

        DateField dateField = new DateField();
        dateField.setWidth("100%");
        binder.forField(dateField)
                .asRequired(REQUIRED_MESSAGE)
                .bind(DTO::getDateField, DTO::setDateField);
        v8Layout.addComponent(dateField);
        
        TextArea signatureField = new TextArea();
        signatureField.setWidth("100%");
        signatureField.setHeight("200px");
        signatureField.setReadOnly(true);
        binder.forField(signatureField)
                .bind(DTO::getSignatureField, DTO::setSignatureField);
        v8Layout.addComponent(signatureField);

        Panel v7Panel = new Panel("Vaadin 7 Compatibility");
        v7Panel.setWidth("300px");
        horizontalLayout.addComponent(v7Panel);

        VerticalLayout v7Layout = new VerticalLayout();
        v7Layout.setWidth("100%");
        v7Panel.setContent(v7Layout);

        com.vaadin.v7.ui.TextField v7TextField = new com.vaadin.v7.ui.TextField();
        v7TextField.setWidth("100%");
        binder.forField(MigrationField.of(v7TextField))
                .withValidator(invalidValidator)
                .asRequired(REQUIRED_MESSAGE)
                .bind(DTO::getTextField, DTO::setTextField);
        v7Layout.addComponent(v7TextField);

        com.vaadin.v7.ui.TextArea v7TextArea = new com.vaadin.v7.ui.TextArea();
        v7TextArea.setWidth("100%");
        binder.forField(MigrationField.of(v7TextArea))
                .withValidator(invalidValidator)
                .asRequired(REQUIRED_MESSAGE)
                .bind(DTO::getTextArea, DTO::setTextArea);
        v7Layout.addComponent(v7TextArea);

        com.vaadin.v7.ui.CheckBox v7CheckBox = new com.vaadin.v7.ui.CheckBox();
        v7CheckBox.setWidth("100%");
        binder.forField(MigrationField.of(v7CheckBox))
                .asRequired(REQUIRED_MESSAGE)
                .bind(DTO::getCheckBox, DTO::setCheckBox);
        v7Layout.addComponent(v7CheckBox);

        com.vaadin.v7.ui.DateField v7DateField = new com.vaadin.v7.ui.DateField();
        v7DateField.setWidth("100%");
        binder.forField(MigrationField.of(v7DateField))
                .withConverter(new DateToLocalDateConverter())
                .asRequired(REQUIRED_MESSAGE)
                .bind(DTO::getDateField, DTO::setDateField);
        v7Layout.addComponent(v7DateField);
        
        SignatureField v7SignatureField = new SignatureField();
        v7SignatureField.setWidth("100%");
        v7SignatureField.setHeight("200px");
        binder.forField(MigrationField.of(v7SignatureField))
                .bind(DTO::getSignatureField, DTO::setSignatureField);
        v7Layout.addComponent(v7SignatureField);

        CheckBox readOnly = new CheckBox("Is read only");
        readOnly.addValueChangeListener(e -> binder.setReadOnly(e.getValue()));
        mainLayout.addComponent(readOnly);
        
        /*
        CheckBox required = new CheckBox("Is required");
        required.addValueChangeListener(e -> {
            for (HasValue<?> field : fields) {
                field.setRequiredIndicatorVisible(e.getValue());
            }
        });
        mainLayout.addComponent(required);
        */

        binder.addValueChangeListener(e -> {
            if (binder.validate().isOk()) {
                binder.readBean(binder.getBean());
            }
        });
        
        binder.setBean(dto);
    }
    
}
