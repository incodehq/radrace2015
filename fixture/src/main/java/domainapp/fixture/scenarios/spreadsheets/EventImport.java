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
import java.util.Objects;

import javax.inject.Inject;

import org.joda.time.LocalDate;

import org.apache.isis.applib.annotation.Optionality;
import org.apache.isis.applib.annotation.Property;
import org.apache.isis.applib.annotation.ViewModel;

import domainapp.dom.event.Event;
import domainapp.dom.event.EventRepository;
import domainapp.dom.ingredient.Ingredient;
import domainapp.dom.ingredient.IngredientRepository;
import domainapp.dom.ingredientcategory.IngredientCategory;
import domainapp.dom.ingredientcategory.IngredientCategoryRepository;
import domainapp.dom.menu.Menu;
import domainapp.dom.menu.MenuRepository;
import domainapp.dom.menuitem.MenuItem;
import domainapp.dom.supplier.Supplier;
import domainapp.dom.supplier.SupplierRepository;

@ViewModel
public class EventImport implements Importable {

    // Event parameters
    private String eventName;
    private LocalDate eventStart;
    private LocalDate eventEnd;
    private LocalDate eventInscriptionStart;
    private LocalDate eventInscriptionEnd;
    private BigDecimal eventNonMemberSupplement;
    private String eventLocation;
    private String eventAccessibleTo;

    // Menu Item paramenters
    private String menuItem;
    private String menuItemCategory;
    private String menuItemMandatory;
    private String menuItemDependsUpon;
    private BigDecimal menuItemMemberPrice;

    // Ingredient parameters
    private String ingredient;
    private String ingredientCategory;
    private String ingredientSupplier;

    @Property(optionality = Optionality.OPTIONAL)
    public String getEventLocation() {
        return eventLocation;
    }

    public void setEventLocation(final String eventLocation) {
        this.eventLocation = eventLocation;
    }

    @Property(optionality = Optionality.OPTIONAL)
    public String getEventAccessibleTo() {
        return eventAccessibleTo;
    }

    public void setEventAccessibleTo(final String eventAccessibleTo) {
        this.eventAccessibleTo = eventAccessibleTo;
    }

    @Property(optionality = Optionality.OPTIONAL)
    public LocalDate getEventInscriptionStart() {
        return eventInscriptionStart;
    }

    public void setEventInscriptionStart(final LocalDate eventInscriptionStart) {
        this.eventInscriptionStart = eventInscriptionStart;
    }

    @Property(optionality = Optionality.OPTIONAL)
    public LocalDate getEventInscriptionEnd() {
        return eventInscriptionEnd;
    }

    public void setEventInscriptionEnd(final LocalDate eventInscriptionEnd) {
        this.eventInscriptionEnd = eventInscriptionEnd;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(final String eventName) {
        this.eventName = eventName;
    }

    @Property(optionality = Optionality.OPTIONAL)
    public LocalDate getEventStart() {
        return eventStart;
    }

    public void setEventStart(final LocalDate eventStart) {
        this.eventStart = eventStart;
    }

    @Property(optionality = Optionality.OPTIONAL)
    public LocalDate getEventEnd() {
        return eventEnd;
    }

    public void setEventEnd(final LocalDate eventEnd) {
        this.eventEnd = eventEnd;
    }

    @Property(optionality = Optionality.OPTIONAL)
    public BigDecimal getEventNonMemberSupplement() {
        return eventNonMemberSupplement;
    }

    public void setEventNonMemberSupplement(final BigDecimal eventNonMemberSupplement) {
        this.eventNonMemberSupplement = eventNonMemberSupplement;
    }

    @Property(optionality = Optionality.OPTIONAL)
    public String getMenuItem() {
        return menuItem;
    }

    public void setMenuItem(final String menuItem) {
        this.menuItem = menuItem;
    }

    @Property(optionality = Optionality.OPTIONAL)
    public String getMenuItemCategory() {
        return menuItemCategory;
    }

    public void setMenuItemCategory(final String menuItemCategory) {
        this.menuItemCategory = menuItemCategory;
    }

    @Property(optionality = Optionality.OPTIONAL)
    public String getMenuItemMandatory() {
        return menuItemMandatory;
    }

    public void setMenuItemMandatory(final String menuItemMandatory) {
        this.menuItemMandatory = menuItemMandatory;
    }

    public String getMenuItemDependsUpon() {
        return menuItemDependsUpon;
    }

    public void setMenuItemDependsUpon(final String menuItemDependsUpon) {
        this.menuItemDependsUpon = menuItemDependsUpon;
    }

    @Property(optionality = Optionality.OPTIONAL)
    public String getIngredient() {
        return ingredient;
    }

    public void setIngredient(final String ingredient) {
        this.ingredient = ingredient;
    }

    @Property(optionality = Optionality.OPTIONAL)
    public String getIngredientCategory() {
        return ingredientCategory;
    }

    public void setIngredientCategory(final String ingredientCategory) {
        this.ingredientCategory = ingredientCategory;
    }

    @Property(optionality = Optionality.OPTIONAL)
    public String getIngredientSupplier() {
        return ingredientSupplier;
    }

    public void setIngredientSupplier(final String ingredientSupplier) {
        this.ingredientSupplier = ingredientSupplier;
    }

    @Property(optionality = Optionality.OPTIONAL)
    public BigDecimal getMenuItemMemberPrice() {
        return menuItemMemberPrice;
    }

    public void setMenuItemMemberPrice(final BigDecimal menuItemMemberPrice) {
        this.menuItemMemberPrice = menuItemMemberPrice;
    }

    @Override
    public void importData() {
        final Event event = eventRepository.findOrCreate(
                getEventName(),
                getEventStart(),
                getEventEnd(),
                getEventInscriptionStart(),
                getEventInscriptionEnd(),
                getEventLocation() ,getEventAccessibleTo()==null?null:Event.AccessLevel.valueOf(getEventAccessibleTo()) );
        final Menu menu = menuRepository.findOrCreate(event, eventNonMemberSupplement== null? BigDecimal.ZERO : eventNonMemberSupplement);
        final Supplier supplier = supplierRepository.findOrCreate(getIngredientSupplier());
        final IngredientCategory ingredientCategory = ingredientCategoryRepository.findOrCreate(getIngredientCategory());
        final Ingredient ingredient = ingredientRepository.findOrCreate(getIngredient(), ingredientCategory, supplier);

        final Boolean menuItemMandatory = Objects.equals(getMenuItemMandatory(), "Yes");

        final MenuItem menuItem = menu.newItem2(getMenuItem(), getMenuItemMemberPrice(), menuItemMandatory);

        if(menuItem != null && ingredient != null) {

            menuItem.addIngredient(ingredient);

            final MenuItem menuItemDependsUpon = menu.newItem2(getMenuItemDependsUpon(), BigDecimal.ZERO, null);
            if(menuItemDependsUpon != null) {
                menuItem.dependsUpon(menuItemDependsUpon);
            }

            //menuItem.getDependencies().add()
        }


    }

    @Inject
    EventRepository eventRepository;

    @Inject
    IngredientRepository ingredientRepository;

    @Inject
    MenuRepository menuRepository;

    @Inject
    IngredientCategoryRepository ingredientCategoryRepository;

    @Inject
    SupplierRepository supplierRepository;


}
