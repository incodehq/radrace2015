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

import java.util.SortedSet;
import java.util.TreeSet;

import javax.jdo.JDOHelper;
import javax.jdo.annotations.Column;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.VersionStrategy;

import org.apache.isis.applib.DomainObjectContainer;
import org.apache.isis.applib.annotation.BookmarkPolicy;
import org.apache.isis.applib.annotation.DomainObject;
import org.apache.isis.applib.annotation.DomainObjectLayout;
import org.apache.isis.applib.annotation.Editing;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.ParameterLayout;
import org.apache.isis.applib.services.i18n.TranslatableString;
import org.apache.isis.applib.util.ObjectContracts;

import domainapp.dom.event.Event;
import domainapp.dom.menuitem.MenuItem;
import domainapp.dom.orderitem.OrderItem;
import domainapp.dom.person.Person;

@javax.jdo.annotations.PersistenceCapable(
        identityType=IdentityType.DATASTORE,
        schema = "simple",
        table = "Order"
)
@javax.jdo.annotations.DatastoreIdentity(
        strategy=javax.jdo.annotations.IdGeneratorStrategy.IDENTITY,
         column="id")
@javax.jdo.annotations.Version(
        strategy=VersionStrategy.VERSION_NUMBER, 
        column="version")
@javax.jdo.annotations.Queries({
        @javax.jdo.annotations.Query(
                name = "find", language = "JDOQL",
                value = "SELECT "
                        + "FROM domainapp.dom.order.Order "),
        @javax.jdo.annotations.Query(
                name = "findByPerson", language = "JDOQL",
                value = "SELECT "
                        + "FROM domainapp.dom.order.Order "
                        + "WHERE person == :person "),
        @javax.jdo.annotations.Query(
                name = "findByEvent", language = "JDOQL",
                value = "SELECT "
                        + "FROM domainapp.dom.order.Order "
                        + "WHERE event == :event "),
        @javax.jdo.annotations.Query(
                name = "findByPersonAndEvent", language = "JDOQL",
                value = "SELECT "
                        + "FROM domainapp.dom.order.Order "
                        + "WHERE event == :event "
                        + "   && person == :person")
})
@javax.jdo.annotations.Unique(name="Order_event_person_UNQ", members = {"event", "person"})
@DomainObject(
        editing = Editing.DISABLED
)
@DomainObjectLayout(
        bookmarking = BookmarkPolicy.AS_ROOT,
        cssClassFa = "fa-flag"
)
public class Order implements Comparable<Order> {


    //region > identificatiom
    public TranslatableString title() {
        return TranslatableString.tr(
                "{person}/{event}",
                "person", container.titleOf(getPerson()),
                "event", getEvent().getName());
    }
    //endregion

    //region > person (property)
    private Person person;

    @Column(allowsNull = "false")
    public Person getPerson() {
        return person;
    }

    public void setPerson(final Person person) {
        this.person = person;
    }
    //endregion

    //region > event (property)
    private Event event;

    @Column(allowsNull = "false")
    public Event getEvent() {
        return event;
    }

    public void setEvent(final Event event) {
        this.event = event;
    }
    //endregion



    //region > items (collection)
    @Persistent(mappedBy = "order", dependentElement = "true")
    private SortedSet<OrderItem> items = new TreeSet<OrderItem>();

    @MemberOrder(sequence = "1")
    public SortedSet<OrderItem> getItems() {
        return items;
    }

    public void setItems(final SortedSet<OrderItem> items) {
        this.items = items;
    }
    //endregion

    //region > newItem (action)
    public Order newItem(
            final MenuItem menuItem,
            @ParameterLayout(named = "Quantity")
            final int quantity) {

        final OrderItem orderItem = container.newTransientInstance(OrderItem.class);

        orderItem.setOrder(this);
        orderItem.setMenuItem(menuItem);
        orderItem.setQuantity(quantity);

        container.persistIfNotAlready(orderItem);

        return this;
    }
    //endregion



    //region > version (derived property)
    public Long getVersionSequence() {
        return (Long) JDOHelper.getVersion(this);
    }
    //endregion

    //region > compareTo

    @Override
    public int compareTo(final Order other) {
        return ObjectContracts.compare(this, other, "event", "person");
    }

    //endregion

    //region > injected services

    @javax.inject.Inject
    @SuppressWarnings("unused")
    private DomainObjectContainer container;

    //endregion


}
