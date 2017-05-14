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

import com.vaadin.data.Binder;
import com.vaadin.data.HasValue;
import com.vaadin.data.PropertySet;
import com.vaadin.server.UserError;

/**
 * A slightly modified {@link Binder} to support automatic error messages on
 * {@link MigrationField}s.
 * 
 * @author Max Schuster
 * @param <T> Bean type
 * @deprecated Please use Vaadin 8 compatible fields as soon as possible.
 * @see Binder
 * @see MigrationField
 */
@Deprecated
public class MigrationBinder<T> extends Binder<T> {

    /**
     * Creates a new MigrationBinder.
     * 
     * @param propertySet The property set implementation to use, not
     * <code>null</code>.
     */
    protected MigrationBinder(PropertySet<T> propertySet) {
        super(propertySet);
    }

    /**
     * Creates a new MigrationBinder.
     * 
     * @param beanType The bean type to use, not <code>null</code>.
     * @see Binder#Binder(java.lang.Class) 
     */
    public MigrationBinder(Class<T> beanType) {
        super(beanType);
    }

    /**
     * Creates a new MigrationBinder.
     * 
     * @see Binder#Binder() 
     */
    public MigrationBinder() {
        super();
    }

    @Override
    protected void handleError(HasValue<?> field, String error) {
        if (field instanceof MigrationField) {
            // delegate error message to the migration field
            ((MigrationField<?, ?>) field).setComponentError(new UserError(error));
        } else {
            super.handleError(field, error);
        }
    }

    @Override
    protected void clearError(HasValue<?> field) {
        if (field instanceof MigrationField) {
            // delegate clearing error message to the migration field
            ((MigrationField<?, ?>) field).setComponentError(null);
        } else {
            super.clearError(field);
        }
    }
    
}
