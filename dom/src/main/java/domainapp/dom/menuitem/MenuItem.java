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
package domainapp.dom.menuitem;

import java.math.BigDecimal;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

import javax.inject.Inject;
import javax.jdo.JDOHelper;
import javax.jdo.annotations.Column;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.VersionStrategy;

import com.google.common.collect.Lists;

import org.apache.isis.applib.DomainObjectContainer;
import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.BookmarkPolicy;
import org.apache.isis.applib.annotation.Collection;
import org.apache.isis.applib.annotation.CollectionLayout;
import org.apache.isis.applib.annotation.DomainObject;
import org.apache.isis.applib.annotation.DomainObjectLayout;
import org.apache.isis.applib.annotation.Editing;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.Parameter;
import org.apache.isis.applib.annotation.ParameterLayout;
import org.apache.isis.applib.annotation.Property;
import org.apache.isis.applib.annotation.RenderType;
import org.apache.isis.applib.annotation.SemanticsOf;
import org.apache.isis.applib.annotation.Title;
import org.apache.isis.applib.services.i18n.TranslatableString;
import org.apache.isis.applib.util.ObjectContracts;

import domainapp.dom.Named;
import domainapp.dom.ingredient.Ingredient;
import domainapp.dom.ingredient.IngredientRepository;
import domainapp.dom.menu.Menu;

@javax.jdo.annotations.PersistenceCapable(
        identityType=IdentityType.DATASTORE,
        schema = "simple",
        table = "MenuItem"
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
                        + "FROM domainapp.dom.menuitem.MenuItem "),
        @javax.jdo.annotations.Query(
                name = "findByMenu", language = "JDOQL",
                value = "SELECT "
                        + "FROM domainapp.dom.menuitem.MenuItem "
                        + "WHERE menu == :menu "),
        @javax.jdo.annotations.Query(
                name = "findByIngredient", language = "JDOQL",
                value = "SELECT "
                        + "FROM domainapp.dom.menuitem.MenuItem "
                        + "WHERE ingredient == :ingredient ")
})
@javax.jdo.annotations.Unique(name="MenuItem_menu_name_UNQ", members = {"menu", "name"})
@DomainObject(
        editing = Editing.DISABLED
)
@DomainObjectLayout(
        bookmarking = BookmarkPolicy.AS_ROOT
)
public class MenuItem implements Comparable<MenuItem>, Named {


    //region > identificatiom
    public TranslatableString title() {
        return TranslatableString.tr(
                "{name} @ {memberPrice}",
                "name", getName(),
                "memberPrice", getMemberPrice()
                );
    }
    //endregion

    //region > menu (property)
    private Menu menu;

    @Column(allowsNull = "false", name = "menu_id")
    public Menu getMenu() {
        return menu;
    }

    public void setMenu(final Menu menu) {
        this.menu = menu;
    }
    //endregion

    //region > name (property)

    private String name;

    @javax.jdo.annotations.Column(allowsNull="false", length = 40)
    @Title(sequence="1")
    @Property
    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    // endregion

    //region > updateName (action)

    @Action
    public MenuItem updateName(
            @Parameter(maxLength = 40)
            @ParameterLayout(named = "New name")
            final String name) {
        setName(name);
        return this;
    }

    public String default0UpdateName() {
        return getName();
    }

    public TranslatableString validateUpdateName(final String name) {
        return name.contains("!")? TranslatableString.tr("Exclamation mark is not allowed"): null;
    }

    //endregion


    //region > ingredients (collection)
    @javax.jdo.annotations.Persistent(table="MenuItemIngredients")
    @javax.jdo.annotations.Join(column="menu_item_id")
    @javax.jdo.annotations.Element(column="ingredient_id")
    private SortedSet<Ingredient> ingredients = new TreeSet<Ingredient>();

    @MemberOrder(sequence = "1")
    @CollectionLayout(render = RenderType.EAGERLY)
    public SortedSet<Ingredient> getIngredients() {
        return ingredients;
    }

    public void setIngredients(final SortedSet<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }
    //endregion

    //region > addIngredient (action)
    public void addIngredient(final Ingredient ingredient) {
        getIngredients().add(ingredient);
    }

    public List<Ingredient> choices0AddIngredient() {
        final List<Ingredient> otherMenuItems = Lists.newArrayList();
        otherMenuItems.addAll(ingredientRepository.listAll());
        otherMenuItems.remove(getIngredients());
        return otherMenuItems;
    }

    // Jeroen doesn't think this is necessary...
//    public String disableAddIngredient(final Ingredient ingredient) {
//        return choices0AddIngredient().isEmpty()? "No more ingredients to add": null;
//    }

    //endregion

    //region > addIngredient (action)
    public MenuItem removeIngredient(final Ingredient ingredient) {
        getIngredients().remove(ingredient);
        return this;
    }

    public SortedSet<Ingredient> choices0RemoveIngredient() {
        return getIngredients();
    }

    public String disableRemoveIngredient(final Ingredient ingredient) {
        return choices0RemoveIngredient().isEmpty()? "No ingredients to remove": null;
    }

    //endregion


    //region > memberPrice (property)
    private BigDecimal memberPrice;

    @Column(allowsNull = "true", scale = 2)
    public BigDecimal getMemberPrice() {
        return memberPrice;
    }

    public void setMemberPrice(final BigDecimal memberPrice) {
        this.memberPrice = memberPrice;
    }
    //endregion
    
    //region > updateMemberPrice (action)

    @Action
    public MenuItem updateMemberPrice(
            @Parameter(maxLength = 40)
            @ParameterLayout(named = "New price")
            final BigDecimal memberPrice) {
        setMemberPrice(memberPrice);
        return this;
    }

    public BigDecimal default0UpdateMemberPrice() {
        return getMemberPrice();
    }

    //endregion

    //region > dependencies
    @javax.jdo.annotations.Persistent(table="MenuItemDependencies")
    @javax.jdo.annotations.Join(column="dependingId")
    @javax.jdo.annotations.Element(column="dependentId")

    private SortedSet<MenuItem> dependencies = new TreeSet<>();

    @Collection()
    @CollectionLayout()
    public SortedSet<MenuItem> getDependencies() {
        return dependencies;
    }

    public void setDependencies(final SortedSet<MenuItem> dependencies) {
        this.dependencies = dependencies;
    }
    //endregion

    //region > dependsUpon

    @Action(semantics = SemanticsOf.IDEMPOTENT)
    public MenuItem dependsUpon(final MenuItem dependsUpon) {
        if(!getDependencies().contains(dependsUpon)) {
            getDependencies().add(dependsUpon);
        }
        return  this;
    }

    public List<MenuItem> choices0DependsUpon() {
        final List<MenuItem> otherMenuItems = Lists.newArrayList();
        otherMenuItems.addAll(getMenu().getItems());
        otherMenuItems.remove(this);
        return otherMenuItems;
    }

    public String disableDependsUpon(final MenuItem menuItem) {
        return choices0DependsUpon().isEmpty() ? "No items": null;
    }
    //endregion

    //region > removeDependency (action)
    @MemberOrder(sequence = "1")
    public MenuItem removeDependency(final MenuItem menuItem) {
        getDependencies().remove(menuItem);
        return this;
    }

    public SortedSet<MenuItem> choices0RemoveDependency() {
        return getDependencies();
    }

    public String disableRemoveDependency(final MenuItem menuItem) {

        return choices0RemoveDependency().isEmpty() ? "No items" : null;
    }
    //endregion

    //region > mandatory (property)
    private boolean mandatory;

    public boolean isMandatory() {
        return mandatory;
    }

    public void setMandatory(final boolean mandatory) {
        this.mandatory = mandatory;
    }
    //endregion


    //region > version (derived property)
    public Long getVersionSequence() {
        return (Long) JDOHelper.getVersion(this);
    }
    //endregion

    //region > compareTo

    @Override
    public int compareTo(final MenuItem other) {
        return ObjectContracts.compare(this, other, "menu", "name");
    }

    //endregion

    //region > injected services

    @javax.inject.Inject
    @SuppressWarnings("unused")
    private DomainObjectContainer container;

    @Inject
    private IngredientRepository ingredientRepository;
    //endregion


}
