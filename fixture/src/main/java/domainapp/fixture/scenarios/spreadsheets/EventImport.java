/*
 *  Licensed to the Apache Software Foundation (ASF) under one
 *  or more contributor license agreements.  See the NOTICE file
 *  distributed with this work for additional information
 *  regarding copyright ownership.  The ASF licenses this file
 *  to you under the Apache License, Version 2.0 (the
 *  "License"); you may not use this file except in compliance
 *  with the License.  You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing,
 *  software distributed under the License is distributed on an
 *  "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 *  KIND, either express or implied.  See the License for the
 *  specific language governing permissions and limitations
 *  under the License.
 */
package domainapp.fixture.scenarios.spreadsheets;

import java.math.BigDecimal;

import javax.inject.Inject;

import org.joda.time.LocalDate;

import org.apache.isis.applib.annotation.ViewModel;

import domainapp.dom.event.Event;
import domainapp.dom.event.EventRepository;
import domainapp.dom.menu.MenuRepository;

@ViewModel
public class EventImport implements Importable {

    private String name;
    private LocalDate date;

    private BigDecimal nonMemberSupplement;

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(final LocalDate date) {
        this.date = date;
    }

    public BigDecimal getNonMemberSupplement() {
        return nonMemberSupplement;
    }

    public void setNonMemberSupplement(final BigDecimal nonMemberSupplement) {
        this.nonMemberSupplement = nonMemberSupplement;
    }

    @Override
    public void importData() {
        final Event event = eventRepository.findOrCreate(getName(), getDate());
        menuRepository.findOrCreate(event, nonMemberSupplement);
    }

    @Inject
    EventRepository eventRepository;

    @Inject
    MenuRepository menuRepository;

}
