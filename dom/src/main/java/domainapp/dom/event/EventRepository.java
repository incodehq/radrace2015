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

import javax.inject.Inject;

import org.joda.time.LocalDate;

import org.apache.isis.applib.DomainObjectContainer;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.annotation.ParameterLayout;
import org.apache.isis.applib.annotation.Programmatic;
import org.apache.isis.applib.query.QueryDefault;

import org.isisaddons.wicket.gmap3.cpt.service.LocationLookupService;

@DomainService(
        nature = NatureOfService.DOMAIN,
        repositoryFor = Event.class
)
public class EventRepository {

    //region > listAll (programmatic)

    @Programmatic
    public List<Event> listAll() {
        return container.allInstances(Event.class);
    }
    //endregion

    //region > findByName (programmatic)

    @Programmatic
    public Event findByName(
            @ParameterLayout(named="Name")
            final String name
    ) {
        return container.uniqueMatch(
                new QueryDefault<>(
                        Event.class,
                        "findByName",
                        "name", name));
    }
    //endregion

    //region > create (programmatic)

    @Programmatic
    public Event create(
            final String name,
            final LocalDate start,
            final LocalDate end,
            final LocalDate inscriptionStart,
            final LocalDate inscriptionEnd,
            final String location,
            final Event.AccessLevel accessibleTo
) {
        final Event obj = container.newTransientInstance(Event.class);
        obj.setName(name);
        obj.setStart(start);
        obj.setEnd(end);
        obj.setAddress(location);
        obj.setLocation(locationLookupService.lookup(location));
        obj.setAccessibleTo(accessibleTo);
        obj.setInscriptionStart(inscriptionStart);
        obj.setInscriptionEnd(inscriptionEnd);
        container.persistIfNotAlready(obj);
        return obj;
    }

    //endregion

    //region > injected services

    @javax.inject.Inject
    DomainObjectContainer container;

    public Event findOrCreate(
            final String name,
            final LocalDate start,
            final LocalDate end,
            final LocalDate inscriptionStart,
            final LocalDate inscriptionEnd,
            final String location,
            final Event.AccessLevel accessibleTo
    ) {
        final Event event = findByName(name);
        if(event == null) {
            return create(name, start,end,inscriptionStart, inscriptionEnd,location,accessibleTo);
        }
        return event;
    }

    //endregion


    @Inject
    LocationLookupService locationLookupService;


}
