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
    private LocalDate eventDate;
    private BigDecimal eventNonMemberSupplement;

    // Menu Item paramenters
    private String menuItem;
    private String menuItemCategory;
    private Boolean menuItemMandatory;
    private BigDecimal menuItemMemberPrice;

    // Ingredient parameters
    private String ingredient;
    private String ingredientCategory;
    private String ingredientSupplier;

    public String getEventName() {
        return eventName;
    }

    public void setEventName(final String eventName) {
        this.eventName = eventName;
    }

    @Property(optionality = Optionality.OPTIONAL)
    public LocalDate getEventDate() {
        return eventDate;
    }

    public void setEventDate(final LocalDate eventDate) {
        this.eventDate = eventDate;
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
    public Boolean getMenuItemMandatory() {
        return menuItemMandatory;
    }

    public void setMenuItemMandatory(final Boolean menuItemMandatory) {
        this.menuItemMandatory = menuItemMandatory;
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
        final Event event = eventRepository.findOrCreate(getEventName(), getEventDate());
        final Menu menu = menuRepository.findOrCreate(event, eventNonMemberSupplement);
        final Supplier supplier = supplierRepository.findOrCreate(getIngredientSupplier());
        final IngredientCategory ingredientCategory = ingredientCategoryRepository.findOrCreate(getIngredientCategory());
        final Ingredient ingredient = ingredientRepository.findOrCreate(getIngredient(), ingredientCategory, supplier);
        final String menuItemName = getMenuItem();
        final MenuItem menuItem = menu.newItem2(menuItemName, getMenuItemMemberPrice());

        // FIXME
        if(ingredient != null) {
            menuItem.addIngredient(ingredient);
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
