package eu.maxschuster.vaadin.v7.migrationfield;

import com.vaadin.data.HasValue;
import com.vaadin.server.AbstractClientConnector;
import com.vaadin.server.AbstractExtension;
import com.vaadin.server.ErrorMessage;
import com.vaadin.server.Extension;
import com.vaadin.shared.Registration;
import com.vaadin.ui.AbstractComponent;
import com.vaadin.ui.Component;
import com.vaadin.v7.data.Property;
import com.vaadin.v7.ui.AbstractField;
import com.vaadin.v7.ui.Field;
import eu.maxschuster.vaadin.v7.migrationfield.shared.MigrationFieldState;

/**
 * An implementation of the {@link HasValue} interface for use with Vaadin 7
 * compatibility {@link Field}s.
 * 
 * @author Max Schuster
 * @param <F> Vaadin 7 compatibility package {@link Field} type.
 * @param <T> Value type.
 * @see MigrationBinder
 * @see MigrationConverter
 * @deprecated Please use Vaadin 8 compatible fields as soon as possible. 
 */
@Deprecated
public class MigrationField<F extends AbstractClientConnector & Component & Field<T>, T>
        extends AbstractExtension implements HasValue<T> {
    
    /**
     * Last known value.
     */
    private T lastKnownValue;
    
    /**
     * Current error message.
     */
    private ErrorMessage errorMessage;
    
    /**
     * {@code true} if events of this field originate from he client,
     * {@code false} otherwise.
     */
    private boolean userOriginated = false;

    /**
     * Creates a new MigrationField instance by extending the given field.
     * 
     * @param field The field that should be extended.
     */
    protected MigrationField(F field) {
        super(field);
        field.addValueChangeListener(this::onVaadin7ValueChange);
        if (field instanceof AbstractField) {
            AbstractField<T> abstractField = (AbstractField<T>) field;
            abstractField.setImmediate(true);
        }
    }
    
    /**
     * Gets or creates the {@link MigrationField} instance of the given field.
     * 
     * There is only one instance of {@link MigrationField} for a field. So
     * you can later use this method to get the existing {@link MigrationField}
     * without creating a new one.
     * 
     * @param <F> Vaadin 7 compatibility package {@link Field} type.
     * @param <T> Value type.
     * @param field The Vaadin 7 compatibility package {@link Field}
     * @return The corresponding MigrationField instance.
     */
    public static <F extends AbstractClientConnector & Component & Field<T>, T> MigrationField<F, T> of(F field) {
        for (Extension extension : field.getExtensions()) {
            if (extension instanceof MigrationField) {
                return (MigrationField<F, T>) extension;
            }
        }
        return new MigrationField<>(field);
    }

    @Override
    public F getParent() {
        return (F) super.getParent();
    }
    
    /**
     * Event listener for the Vaadin 7 compatibility value change event.
     * 
     * @param event 
     */
    protected void onVaadin7ValueChange(Property.ValueChangeEvent event) {
        T value = (T) event.getProperty().getValue();
        fireValueChangeEvent(value);
    }

    /**
     * Triggers the value change event.
     * 
     * @param newValue New value
     */
    protected void fireValueChangeEvent(T newValue) {
        T oldValue = lastKnownValue;
        lastKnownValue = newValue;
        fireEvent(new ValueChangeEvent<>(
                getParent(), this, oldValue, userOriginated));
    }
    
    /**
     * Gets the fields error message.
     * 
     * @return This fields error message.
     * @see AbstractComponent#getComponentError()
     */
    public ErrorMessage getComponentError() {
        return errorMessage;
    }
    
    /**
     * Sets the fields error message.
     * 
     * @param errorMessage New error message.
     * @see AbstractComponent#setComponentError(com.vaadin.server.ErrorMessage)
     */
    public void setComponentError(ErrorMessage errorMessage) {
        if (getParent() instanceof AbstractComponent) {
            ((AbstractComponent) getParent()).setComponentError(errorMessage);
        }
        this.errorMessage = errorMessage;
    }

    @Override
    public void setValue(T value) {
        boolean readOnly = isReadOnly();
        if (readOnly) {
            setReadOnly(false); // make value changeble
        }
        getParent().setValue(value);
        if (readOnly) {
            setReadOnly(true); // restore read only state
        }
    }

    @Override
    public T getValue() {
        return getParent().getValue();
    }

    @Override
    public Registration addValueChangeListener(ValueChangeListener<T> listener) {
        return addListener(ValueChangeEvent.class, listener,
                ValueChangeListener.VALUE_CHANGE_METHOD);
    }

    @Override
    public boolean isRequiredIndicatorVisible() {
       return getParent().isRequired();
    }

    @Override
    public void setRequiredIndicatorVisible(boolean requiredIndicatorVisible) {
        getParent().setRequired(requiredIndicatorVisible);
    }

    @Override
    public boolean isReadOnly() {
        return getParent().isReadOnly();
    }

    @Override
    public void setReadOnly(boolean readOnly) {
        getParent().setReadOnly(readOnly);
    }

    @Override
    protected MigrationFieldState getState() {
        return (MigrationFieldState) super.getState();
    }

    @Override
    protected MigrationFieldState getState(boolean markAsDirty) {
        return (MigrationFieldState) super.getState(markAsDirty);
    }

    /**
     * Returns whether events of this field are triggered by user interaction,
     * on the client side, or programmatically, on the server side.
     *
     * @return {@code true} {@code true} if events of this field originate from
     * the client, {@code false} otherwise.
     * @see ValueChangeEvent#isUserOriginated()
     */
    public boolean isUserOriginated() {
        return userOriginated;
    }

    /**
     * Sets whether events of this field are triggered by user interaction, on
     * the client side, or programmatically, on the server side.
     * 
     * @param userOriginated {@code true} if events of this field originate from
     * the client, {@code false} otherwise.
     * @see ValueChangeEvent#isUserOriginated()
     */
    public void setUserOriginated(boolean userOriginated) {
        this.userOriginated = userOriginated;
    }
    
    /**
     * Sets whether events of this field are triggered by user interaction, on
     * the client side, or programmatically, on the server side.
     * 
     * @param userOriginated {@code true} if events of this field originate from
     * the client, {@code false} otherwise.
     * @return Current instance for method chaining
     * @see #setUserOriginated(boolean)
     */
    public MigrationField<F, T> withUserOriginated(boolean userOriginated) {
        setUserOriginated(userOriginated);
        return this;
    }
    
}
