/*
 * Copyright 2017 Max Schuster.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package eu.maxschuster.vaadin.v7.migrationfield;

import com.vaadin.data.Result;
import com.vaadin.data.ValueContext;
import com.vaadin.v7.data.util.converter.Converter;
import com.vaadin.v7.data.util.converter.StringToDateConverter;
import com.vaadin.v7.data.util.converter.StringToIntegerConverter;
import java.util.Date;
import java.util.Locale;
import junit.framework.Assert;
import org.junit.Test;

/**
 * MigrationConverter tests.
 * 
 * @author Max Schuster
 */
public class MigrationConverterTest {
    
    private final Locale locale = Locale.getDefault();
    
    private final ValueContext valueContext = new ValueContext(locale);
    
    @Test
    public void testDateConverterSuccess() {
        StringToDateConverter oldConverter = new StringToDateConverter();
        Date model = new Date();
        String presentation = new com.vaadin.data.converter.StringToDateConverter()
                .convertToPresentation(model, valueContext);
        testToPresentationSuccess(oldConverter, String.class, Date.class, model);
        testToModelSuccess(oldConverter, String.class, Date.class, presentation);
    }
    
    @Test
    public void testIntegerConverterSuccess() {
        StringToIntegerConverter oldConverter = new StringToIntegerConverter();
        Integer model = 5;
        String presentation = "5";
        testToPresentationSuccess(oldConverter, String.class, Integer.class, model);
        testToModelSuccess(oldConverter, String.class, Integer.class, presentation);
    }
    
    private <P, M> void testToPresentationSuccess(Converter<P, M> oldConverter,
            Class<P> presentationType, Class<M> modelType, M model) {
        com.vaadin.data.Converter<P, M> migrationConverter
                = MigrationConverter.of(oldConverter, presentationType, modelType);
        P oldConverterResult = oldConverter
                .convertToPresentation(model, presentationType, locale);
        P migrationConverterResult = migrationConverter
                .convertToPresentation(model, valueContext);
        Assert.assertEquals(oldConverterResult, migrationConverterResult);
    }
    
    private <P, M> void testToPresentationFail(Converter<P, M> oldConverter,
            Class<P> presentationType, Class<M> modelType, M model) {
        com.vaadin.data.Converter<P, M> migrationConverter
                = MigrationConverter.of(oldConverter, presentationType, modelType);
        P oldConverterResult = oldConverter
                .convertToPresentation(model, presentationType, locale);
        P migrationConverterResult = migrationConverter
                .convertToPresentation(model, valueContext);
        Assert.assertEquals(oldConverterResult, migrationConverterResult);
    }
    
    private <P, M> void testToModelSuccess(Converter<P, M> oldConverter,
            Class<P> presentationType, Class<M> modelType, P presentation) {
        com.vaadin.data.Converter<P, M> migrationConverter
                = MigrationConverter.of(oldConverter, presentationType, modelType);
        M oldConverterResult = oldConverter
                .convertToModel(presentation, modelType, locale);
        Result<M> migrationConverterResult = migrationConverter
                .convertToModel(presentation, valueContext);
        migrationConverterResult.ifError(v -> Assert.fail());
        migrationConverterResult.ifOk(v -> Assert.assertEquals(oldConverterResult, v));
    }
    
    private <P, M> void testToModelFail(Converter<P, M> oldConverter,
            Class<P> presentationType, Class<M> modelType, P presentation) {
        com.vaadin.data.Converter<P, M> migrationConverter
                = MigrationConverter.of(oldConverter, presentationType, modelType);
        M oldConverterResult = oldConverter
                .convertToModel(presentation, modelType, locale);
        Result<M> migrationConverterResult = migrationConverter
                .convertToModel(presentation, valueContext);
        migrationConverterResult.ifError(v -> Assert.fail());
        migrationConverterResult.ifOk(v -> Assert.assertEquals(oldConverterResult, v));
    }
    
}
