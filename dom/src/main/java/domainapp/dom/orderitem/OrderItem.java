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
package domainapp.dom.orderitem;

import javax.jdo.JDOHelper;
import javax.jdo.annotations.Column;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.VersionStrategy;

import org.apache.isis.applib.DomainObjectContainer;
import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.BookmarkPolicy;
import org.apache.isis.applib.annotation.DomainObject;
import org.apache.isis.applib.annotation.DomainObjectLayout;
import org.apache.isis.applib.annotation.Editing;
import org.apache.isis.applib.annotation.Parameter;
import org.apache.isis.applib.annotation.ParameterLayout;
import org.apache.isis.applib.services.i18n.TranslatableString;
import org.apache.isis.applib.util.ObjectContracts;

import domainapp.dom.menuitem.MenuItem;
import domainapp.dom.order.Order;

@javax.jdo.annotations.PersistenceCapable(
        identityType=IdentityType.DATASTORE,
        schema = "simple",
        table = "OrderItem"
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
                        + "FROM domainapp.dom.orderitem.OrderItem "),
        @javax.jdo.annotations.Query(
                name = "findByOrder", language = "JDOQL",
                value = "SELECT "
                        + "FROM domainapp.dom.orderitem.OrderItem "
                        + "WHERE order == :order ")
})
@javax.jdo.annotations.Unique(name="OrderItem_order_menuItem_UNQ", members = {"order", "menuItem"})
@DomainObject(
        editing = Editing.DISABLED
)
@DomainObjectLayout(
        bookmarking = BookmarkPolicy.AS_ROOT
)
public class OrderItem implements Comparable<OrderItem> {


    //region > identificatiom
    public TranslatableString title() {
        return TranslatableString.tr(
                "{quantity} x {menuItem}",
                "quantity", getQuantity(),
                "order", container.titleOf(getOrder()));
    }
    //endregion


    //region > order (property)
    private Order order;

    @Column(allowsNull = "false")
    public Order getOrder() {
        return order;
    }

    public void setOrder(final Order order) {
        this.order = order;
    }
    //endregion

    //region > menuItem (property)
    private MenuItem menuItem;

    @Column(allowsNull = "false")
    public MenuItem getMenuItem() {
        return menuItem;
    }

    public void setMenuItem(final MenuItem menuItem) {
        this.menuItem = menuItem;
    }
    //endregion

    //region > quantity (property)
    private int quantity;

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(final int quantity) {
        this.quantity = quantity;
    }
    //endregion

    //region > updateQuantity (action)

    @Action
    public OrderItem updateQuantity(
            @Parameter(maxLength = 40)
            @ParameterLayout(named = "New quantity")
            final int quantity) {
        setQuantity(quantity);
        return this;
    }

    public int default0UpdateQuantity() {
        return getQuantity();
    }

    //endregion


    //region > version (derived property)
    public Long getVersionSequence() {
        return (Long) JDOHelper.getVersion(this);
    }
    //endregion

    //region > compareTo

    @Override
    public int compareTo(final OrderItem other) {
        return ObjectContracts.compare(this, other, "order", "menuItem");
    }

    //endregion

    //region > injected services

    @javax.inject.Inject
    @SuppressWarnings("unused")
    private DomainObjectContainer container;

    //endregion


}
