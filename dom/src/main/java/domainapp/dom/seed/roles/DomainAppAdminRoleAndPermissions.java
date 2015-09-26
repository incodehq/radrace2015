/*
 *  Copyright 2014 Dan Haywood
 *
 *  Licensed under the Apache License, Version 2.0 (the
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
package domainapp.dom.seed.roles;

import org.isisaddons.module.security.dom.permission.ApplicationPermissionMode;
import org.isisaddons.module.security.dom.permission.ApplicationPermissionRule;
import org.isisaddons.module.security.seed.scripts.AbstractRoleAndPermissionsFixtureScript;

import domainapp.dom.event.EventMenu;
import domainapp.dom.ingredient.Ingredient;
import domainapp.dom.ingredient.IngredientMenu;
import domainapp.dom.ingredientcategory.IngredientCategory;
import domainapp.dom.order.OrderMenu;
import domainapp.dom.person.PersonMenu;
import domainapp.dom.supplier.Supplier;
import domainapp.dom.supplier.SupplierMenu;

public class DomainAppAdminRoleAndPermissions extends AbstractRoleAndPermissionsFixtureScript {

    public static final String ROLE_NAME = "domainapp-regular-role";

    public DomainAppAdminRoleAndPermissions() {
        super(ROLE_NAME, "Read/write access to domainapp dom");
    }

    @Override
    protected void execute(final ExecutionContext executionContext) {
        newClassPermissions(
                ApplicationPermissionRule.ALLOW,
                ApplicationPermissionMode.VIEWING,
                SupplierMenu.class,
                Supplier.class,
                IngredientMenu.class,
                PersonMenu.class,
                OrderMenu.class,
                EventMenu.class,
                Ingredient.class,
                IngredientCategory.class
                );
        newClassPermissions(
                ApplicationPermissionRule.ALLOW,
                ApplicationPermissionMode.CHANGING,
                SupplierMenu.class,
                Supplier.class,
                IngredientMenu.class,
                PersonMenu.class,
                OrderMenu.class,
                EventMenu.class,
                Ingredient.class,
                IngredientCategory.class
                );
    }

}
