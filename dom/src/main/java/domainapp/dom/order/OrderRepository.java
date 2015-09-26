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
package domainapp.dom.order;

import java.util.List;
import java.util.SortedSet;

import org.apache.isis.applib.DomainObjectContainer;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.annotation.Programmatic;
import org.apache.isis.applib.query.QueryDefault;

import domainapp.dom.event.Event;
import domainapp.dom.menuitem.MenuItem;
import domainapp.dom.person.Person;

@DomainService(
        nature = NatureOfService.DOMAIN,
        repositoryFor = Order.class
)
public class OrderRepository {

    //region > listAll (programmatic)

    @Programmatic
    public List<Order> listAll() {
        return container.allInstances(Order.class);
    }
    //endregion

    //region > findByPerson (programmatic)

    @Programmatic
    public List<Order> findByPerson(
            final Person person) {
        return container.allMatches(
                new QueryDefault<>(Order.class,
                        "findByPerson", "person", person));
    }
    //endregion

    //region > findByEvent (programmatic)

    @Programmatic
    public List<Order> findByEvent(
            final Event event) {
        return container.allMatches(
                new QueryDefault<>(Order.class,
                        "findByEvent", "event", event));
    }
    //endregion


    //region > findByPersonAndEvent (programmatic)

    @Programmatic
    public Order findByPersonAndEvent(
            final Person person,
            final Event event
    ) {
        return container.uniqueMatch(
                new QueryDefault<>(
                        Order.class,
                        "findByPersonAndEvent",
                        "person", person,
                        "event", event));
    }
    //endregion


    //region > create (programmatic)

    @Programmatic
    public Order create(final Person person, final Event event) {
        final Order order = container.newTransientInstance(Order.class);
        order.setEvent(event);
        order.setPerson(person);
        order.setStatus(OrderStatus.InProgress);

        container.persistIfNotAlready(order);

        final SortedSet<MenuItem> items = event.getMenu().getItems();

        for (MenuItem item : items) {
            if(item.isMandatory()) {
                order.newItem(item, 1);
            }
        }
        return order;
    }

    //endregion

    //region > injected services

    @javax.inject.Inject
    DomainObjectContainer container;

    @Programmatic
    public Order findOrCreate(final Person person, final Event event) {
        final Order order = findByPersonAndEvent(person, event);
        if(order == null) {
            return create(person, event);
        }
        return order;
    }

    //endregion
}
