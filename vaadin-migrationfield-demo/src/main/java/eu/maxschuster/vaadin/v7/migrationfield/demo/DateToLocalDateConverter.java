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
package eu.maxschuster.vaadin.v7.migrationfield.demo;

import com.vaadin.data.Converter;
import com.vaadin.data.Result;
import com.vaadin.data.ValueContext;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

/**
 *
 * @author Max Schuster
 */
class DateToLocalDateConverter implements Converter<Date, LocalDate> {
    
    @Override
    public Result<LocalDate> convertToModel(Date value, ValueContext context) {
        if (value == null) {
            return Result.ok(null);
        }
        Instant i = value.toInstant();
        return Result.ok(LocalDateTime.ofInstant(i, ZoneId.systemDefault()).toLocalDate());
    }

    @Override
    public Date convertToPresentation(LocalDate value, ValueContext context) {
        if (value == null) {
            return null;
        }
        Instant i = value.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant();
        return Date.from(i);
    }
    
}
