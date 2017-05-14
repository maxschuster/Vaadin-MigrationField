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
import java.util.Locale;
import java.util.Optional;

/**
 *
 * @author Max Schuster
 * @param <P> Presentation type
 * @param <M> Model type
 * @deprecated Please use Vaadin 8 compatible fields as soon as possible.
 */
@Deprecated
public class MigrationConverter<P, M>
        implements com.vaadin.data.Converter<P, M> {
    
    /**
     * Vaadin 7 compatibility converter.
     */
    private final Converter<P, M> converter;
    
    /**
     * Presentation type.
     */
    private final Class<P> presentationType;
    
    /**
     * Model type.
     */
    private final Class<M> modelType;
    
    /**
     * Optional error message.
     */
    private Optional<String> message = Optional.empty();

    /**
     * Creates a new MigrationConverter instance.
     * 
     * @param converter Vaadin 7 compatibility converter.
     * @param presentationType Presentation type.
     * @param modelType Model type.
     */
    protected MigrationConverter(Converter<P, M> converter,
            Class<P> presentationType, Class<M> modelType) {
        this.converter = converter;
        this.presentationType = presentationType;
        this.modelType = modelType;
    }
    
    /**
     * Creates a new MigrationConverter.
     * 
     * @param <P> Presentation type
     * @param <M> Model type
     * @param converter Vaadin 7 compatibility converter
     * @param presentationType Presentation type
     * @param modelType Model type
     * @return A new MigrationConverter.
     */
    public static <P, M> MigrationConverter<P, M> of( Converter<P, M> converter,
            Class<P> presentationType, Class<M> modelType) {
        return new MigrationConverter<>(converter, presentationType, modelType);
    }

    @Override
    public Result<M> convertToModel(P value, ValueContext context) {
        Locale locale = context.getLocale().orElse(null);
        try {
            return Result.ok(converter.convertToModel(value, modelType, locale));
        } catch (Converter.ConversionException ex) {
            return Result.error(message.orElse(ex.getMessage()));
        }
    }

    @Override
    public P convertToPresentation(M value, ValueContext context) {
        Locale locale = context.getLocale().orElse(null);
        return converter.convertToPresentation(value, presentationType, locale);
    }

    /**
     * Gets the error message of this converter.
     * 
     * @return The error message of this converter.
     */
    public String getMessage() {
        return message.orElse(null);
    }

    /**
     * Sets the new error message for this converter.
     * 
     * @param message The new error message for this converter.
     */
    public void setMessage(String message) {
        this.message = Optional.ofNullable(message);
    }
    
    /**
     * Sets the new error message for this converter.
     * 
     * @param message The new error message for this converter.
     * @return This for method chaining
     */
    public MigrationConverter<P, M> withMessage(String message) {
        setMessage(message);
        return this;
    }
    
}
