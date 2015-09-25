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
package domainapp.dom.menu;

import java.math.BigDecimal;
import java.util.SortedSet;
import java.util.TreeSet;

import javax.inject.Inject;
import javax.jdo.JDOHelper;
import javax.jdo.annotations.Column;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.VersionStrategy;

import com.google.common.base.Objects;
import com.google.common.base.Optional;
import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;

import org.apache.isis.applib.DomainObjectContainer;
import org.apache.isis.applib.annotation.BookmarkPolicy;
import org.apache.isis.applib.annotation.DomainObject;
import org.apache.isis.applib.annotation.DomainObjectLayout;
import org.apache.isis.applib.annotation.Editing;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.ParameterLayout;
import org.apache.isis.applib.annotation.Programmatic;
import org.apache.isis.applib.annotation.Title;
import org.apache.isis.applib.services.i18n.TranslatableString;
import org.apache.isis.applib.util.ObjectContracts;

import domainapp.dom.event.Event;
import domainapp.dom.menuitem.MenuItem;
import domainapp.dom.menuitem.MenuItemRepository;

@javax.jdo.annotations.PersistenceCapable(
        identityType=IdentityType.DATASTORE,
        schema = "simple",
        table = "Menu"
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
                        + "FROM domainapp.dom.menu.Menu "),
        @javax.jdo.annotations.Query(
                name = "findByEvent", language = "JDOQL",
                value = "SELECT "
                        + "FROM domainapp.dom.menu.Menu "
                        + "WHERE event == :event ")
})
@javax.jdo.annotations.Unique(name="Menu_event_UNQ", members = {"event"})
@DomainObject(
        editing = Editing.DISABLED
)
@DomainObjectLayout(
        bookmarking = BookmarkPolicy.AS_ROOT,
        cssClassFa = "fa-flag"
)
public class Menu implements Comparable<Menu> {


    //region > identificatiom
    public TranslatableString title() {
        return TranslatableString.tr(
                "{event}'s Menu",
                "event",
                container.titleOf(getEvent()));
    }
    //endregion

    //region > event (property)
    private Event event;

    @Title(sequence="1")
    @Column(allowsNull = "false")
    public Event getEvent() {
        return event;
    }

    public void setEvent(final Event event) {
        this.event = event;
    }
    //endregion

    //region > nonMemberSupplement (property)
    private BigDecimal nonMemberSupplement;

    @Column(allowsNull = "false", scale = 2)
    @MemberOrder(sequence = "1")
    public BigDecimal getNonMemberSupplement() {
        return nonMemberSupplement;
    }

    public void setNonMemberSupplement(final BigDecimal nonMemberSupplement) {
        this.nonMemberSupplement = nonMemberSupplement;
    }
    //endregion

    //region > items (collection)
    @Persistent(mappedBy = "menu", dependentElement = "true")
    private SortedSet<MenuItem> items = new TreeSet<MenuItem>();

    @MemberOrder(sequence = "1")
    public SortedSet<MenuItem> getItems() {
        return items;
    }

    public void setItems(final SortedSet<MenuItem> items) {
        this.items = items;
    }
    //endregion

    //region > newItem (action)
    public Menu newItem(
            @ParameterLayout(named = "Name")
            final String name,
            @ParameterLayout(named = "Member price")
            final BigDecimal memberPrice) {

        newItem2(name, memberPrice);

        return this;
    }

    @Programmatic
    public MenuItem newItem2(
            final String name,
            final BigDecimal memberPrice) {
        if (name == null){
            return null;
        }

        final Optional<MenuItem> menuItemIfAny = Iterables.tryFind(getItems(), new Predicate<MenuItem>() {
            @Override public boolean apply(final MenuItem input) {
                return Objects.equal(input.getName(), name);
            }
        });
        if(menuItemIfAny.isPresent()) {
            return menuItemIfAny.get();
        }

        final MenuItem menuItem = container.newTransientInstance(MenuItem.class);
        menuItem.setMenu(this);
        menuItem.setName(name);
        menuItem.setMemberPrice(memberPrice);

        container.persistIfNotAlready(menuItem);

        return menuItem;
    }
    //endregion


    //region > version (derived property)
    public Long getVersionSequence() {
        return (Long) JDOHelper.getVersion(this);
    }
    //endregion

    //region > compareTo

    @Override
    public int compareTo(final Menu other) {
        return ObjectContracts.compare(this, other, "event");
    }

    //endregion

    //region > injected services

    @Inject
    MenuItemRepository menuItemRepository;

    @Inject
    DomainObjectContainer container;

    //endregion


}
