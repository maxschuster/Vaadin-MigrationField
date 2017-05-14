package eu.maxschuster.vaadin.v7.migrationfield;

import com.vaadin.v7.ui.TextField;
import junit.framework.Assert;
import org.junit.Test;

/**
 * Tests of the {@link MigrationField} extension.
 * 
 * @author Max Schuster
 * @see MigrationField
 */
public class MigrationFieldTest {

    @Test
    public void returnsTheSameInstance() {
        TextField textField = new TextField();
        MigrationField<?, ?> migrationFieldA = MigrationField.of(textField);
        MigrationField<?, ?> migrationFieldB = MigrationField.of(textField);
        Assert.assertSame(migrationFieldA, migrationFieldB);
    }
    
    @Test
    public void returnsTheDifferentInstances() {
        TextField textFieldA = new TextField();
        TextField textFieldB = new TextField();
        MigrationField<?, ?> migrationFieldA = MigrationField.of(textFieldA);
        MigrationField<?, ?> migrationFieldB = MigrationField.of(textFieldB);
        Assert.assertNotSame(migrationFieldA, migrationFieldB);
    }
    
    @Test
    public void changeValue() {
        String value = "Lorem impsum";
        TextField textField = new TextField();
        MigrationField<TextField, String> migrationField
                = MigrationField.of(textField);
        migrationField.setValue(value);
        Assert.assertEquals(textField.getValue(), value);
        Assert.assertEquals(migrationField.getValue(), value);
    }
    
    @Test
    public void changeReadOnly() {
        boolean readOnly = true;
        TextField textField = new TextField();
        MigrationField<TextField, String> migrationField
                = MigrationField.of(textField);
        migrationField.setReadOnly(readOnly);
        Assert.assertEquals(textField.isReadOnly(), readOnly);
        Assert.assertEquals(migrationField.isReadOnly(), readOnly);
    }
    
    @Test
    public void changeRequired() {
        boolean required = true;
        TextField textField = new TextField();
        MigrationField<TextField, String> migrationField
                = MigrationField.of(textField);
        migrationField.setRequiredIndicatorVisible(required);
        Assert.assertEquals(textField.isRequired(), required);
        Assert.assertEquals(migrationField.isRequiredIndicatorVisible(), required);
    }
    
    @Test
    public void changeValueWhenReadOnly() {
        String value = "Lorem impsum";
        MigrationField<TextField, String> migrationField
                = MigrationField.of(new TextField());
        migrationField.setReadOnly(true);
        migrationField.setValue(value);
        Assert.assertEquals(migrationField.getValue(), value);
        
        // Check if read only is still true
        Assert.assertEquals(migrationField.isReadOnly(), true);
    }
    
}
