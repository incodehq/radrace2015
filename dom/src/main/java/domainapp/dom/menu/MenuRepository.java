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

import java.util.List;

import org.apache.isis.applib.DomainObjectContainer;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.annotation.ParameterLayout;
import org.apache.isis.applib.annotation.Programmatic;
import org.apache.isis.applib.query.QueryDefault;

@DomainService(
        nature = NatureOfService.DOMAIN,
        repositoryFor = Menu.class
)
public class MenuRepository {

    //region > listAll (programmatic)

    @Programmatic
    public List<Menu> listAll() {
        return container.allInstances(Menu.class);
    }
    //endregion

    //region > findByName (programmatic)

    @Programmatic
    public Menu findByName(
            @ParameterLayout(named="Name")
            final String name
    ) {
        return container.uniqueMatch(
                new QueryDefault<>(
                        Menu.class,
                        "findByName",
                        "name", name));
    }
    //endregion

    //region > create (programmatic)

    @Programmatic
    public Menu create(final String name) {
        final Menu obj = container.newTransientInstance(Menu.class);
        obj.setName(name);
        container.persistIfNotAlready(obj);
        return obj;
    }

    //endregion

    //region > injected services

    @javax.inject.Inject
    DomainObjectContainer container;

    public Menu findOrCreate(final String categoryName) {
        final Menu menu = findByName(categoryName);
        if(menu == null) {
            return create(categoryName);
        }
        return menu;
    }

    //endregion
}
