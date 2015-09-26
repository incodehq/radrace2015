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

import javax.inject.Inject;

import org.apache.isis.applib.annotation.Optionality;
import org.apache.isis.applib.annotation.Property;
import org.apache.isis.applib.annotation.ViewModel;

import domainapp.dom.event.Event;
import domainapp.dom.event.EventRepository;
import domainapp.dom.menuitem.MenuItem;
import domainapp.dom.menuitem.MenuItemRepository;
import domainapp.dom.order.Order;
import domainapp.dom.order.OrderRepository;
import domainapp.dom.person.Person;
import domainapp.dom.person.PersonRepository;

@ViewModel
public class OrderImport implements Importable {

    private Integer personNumber;
    private String eventName;
    private String menuItem;
    private int quantity;

    /**
     * The import is gonna insist that everyone is a member.
     */
    @Property(optionality = Optionality.OPTIONAL)
    public Integer getPersonNumber() {
        return personNumber;
    }

    public void setPersonNumber(final Integer personNumber) {
        this.personNumber = personNumber;
    }

    @Property(optionality = Optionality.OPTIONAL)
    public String getEventName() {
        return eventName;
    }

    public void setEventName(final String eventName) {
        this.eventName = eventName;
    }

    @Property(optionality = Optionality.OPTIONAL)
    public String getMenuItem() {
        return menuItem;
    }

    public void setMenuItem(final String menuItem) {
        this.menuItem = menuItem;
    }

    @Property(optionality = Optionality.OPTIONAL)
    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(final int quantity) {
        this.quantity = quantity;
    }

    @Override
    public void importData() {
        final Person person = personRepository.findByMemberId(getPersonNumber());
        final Event event = eventRepository.findByName(getEventName());

        final Order order = orderRepository.findOrCreate(person, event);

        MenuItem menuItem = event.getMenu().findItem(getMenuItem());
        if(menuItem != null) {
            order.newItem(menuItem, getQuantity());
        } else {
            // just skip... bad data in the XLSX spreadsheet, referencing a non-existent menu item for this menu
        }
    }

    @Inject
    PersonRepository personRepository;

    @Inject
    OrderRepository orderRepository;

    @Inject
    EventRepository eventRepository;

    @Inject
    MenuItemRepository menuItemRepository;

}
