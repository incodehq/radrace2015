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
package domainapp.dom.event;

import java.util.List;

import org.joda.time.LocalDate;

import org.apache.isis.applib.Identifier;
import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.ActionLayout;
import org.apache.isis.applib.annotation.BookmarkPolicy;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.DomainServiceLayout;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.annotation.ParameterLayout;
import org.apache.isis.applib.annotation.SemanticsOf;
import org.apache.isis.applib.services.eventbus.ActionDomainEvent;

@DomainService(
        nature = NatureOfService.VIEW_MENU_ONLY
)
@DomainServiceLayout(
        menuOrder = "10",
        named = "Events"
)
public class EventMenu {

    //region > listAll (action)
    @Action(
            semantics = SemanticsOf.SAFE
    )
    @ActionLayout(
            bookmarking = BookmarkPolicy.AS_ROOT
    )
    @MemberOrder(sequence = "1")
    public List<Event> allEvents() {
        return eventRepository.listAll();
    }
    //endregion

    //region > findByName (action)
    @Action(
            semantics = SemanticsOf.SAFE
    )
    @ActionLayout(
            bookmarking = BookmarkPolicy.AS_ROOT
    )
    @MemberOrder(sequence = "2")
    public Event findEvent(
            @ParameterLayout(named = "Name")
            final String name
    ) {
        return eventRepository.findByName(name);
    }
    //endregion

    //region > create (action)
    public static class CreateDomainEvent extends ActionDomainEvent<EventMenu> {
        public CreateDomainEvent(final EventMenu source, final Identifier identifier, final Object... arguments) {
            super(source, identifier, arguments);
        }
    }

    @Action(
            domainEvent = CreateDomainEvent.class
    )
    @MemberOrder(sequence = "3")
    public Event create(
            @ParameterLayout(named = "Name")
            final String name,
            @ParameterLayout(named = "Start")
            final LocalDate start,
            @ParameterLayout(named = "End")
            final LocalDate end,
            @ParameterLayout(named = "Inscription Start")
            final LocalDate inscriptionStart,
            @ParameterLayout(named = "Inscription End")
            final LocalDate inscriptionEnd,
            @ParameterLayout(named = "Location")
            final String location,
            @ParameterLayout(named = "accessibleTo")
            final Event.AccessLevel accessibleTo
            ) {
        return eventRepository.create(name, start, end, inscriptionStart,inscriptionEnd,location,accessibleTo);
    }

    //endregion

    //region > injected services

    @javax.inject.Inject
    EventRepository eventRepository;

    //endregion
}
