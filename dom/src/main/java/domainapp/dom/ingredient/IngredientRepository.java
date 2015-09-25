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
package domainapp.dom.ingredient;

import java.util.List;

import org.apache.isis.applib.DomainObjectContainer;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.annotation.ParameterLayout;
import org.apache.isis.applib.annotation.Programmatic;
import org.apache.isis.applib.query.QueryDefault;

import domainapp.dom.ingredientcategory.IngredientCategory;

@DomainService(
        nature = NatureOfService.DOMAIN,
        repositoryFor = Ingredient.class
)
public class IngredientRepository {

    //region > listAll (programmatic)

    @Programmatic
    public List<Ingredient> listAll() {
        return container.allInstances(Ingredient.class);
    }
    //endregion

    //region > findByName (programmatic)

    @Programmatic
    public Ingredient findByName(
            @ParameterLayout(named="Name")
            final String name
    ) {
        return container.uniqueMatch(
                new QueryDefault<>(
                        Ingredient.class,
                        "findByName",
                        "name", name));
    }
    //endregion

    //region > create (programmatic)

    @Programmatic
    public Ingredient create(
            final String name,
            final IngredientCategory ingredientCategory) {
        final Ingredient obj = container.newTransientInstance(Ingredient.class);
        obj.setName(name);
        obj.setCategory(ingredientCategory);
        container.persistIfNotAlready(obj);
        return obj;
    }

    //endregion

    //region > injected services

    @javax.inject.Inject 
    DomainObjectContainer container;

    public Ingredient findOrCreate(
            final String name, final IngredientCategory ingredientCategory) {
        final Ingredient ingredient = findByName(name);
        if(ingredient == null) {
            return create(name, ingredientCategory);
        }
        return ingredient;
    }

    //endregion
}
