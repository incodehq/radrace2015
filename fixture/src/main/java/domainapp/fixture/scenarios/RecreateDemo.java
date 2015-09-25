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

package domainapp.fixture.scenarios;

import java.util.List;

import com.google.common.collect.Lists;

import org.apache.isis.applib.fixturescripts.FixtureScript;

import domainapp.dom.quick.QuickObject;
import domainapp.fixture.dom.DemoTearDown;
import domainapp.fixture.scenarios.spreadsheets.CreateUsingSpreadsheet;
import domainapp.fixture.scenarios.spreadsheets.IngredientImport;

public class RecreateDemo extends FixtureScript {


    public RecreateDemo() {
        withDiscoverability(Discoverability.DISCOVERABLE);
    }


    //region > quickObjects (output)
    private final List<QuickObject> quickObjects = Lists.newArrayList();

    /**
     * The simpleobjects created by this fixture (output).
     */
    public List<QuickObject> getQuickObjects() {
        return quickObjects;
    }
    //endregion

    @Override
    protected void execute(final ExecutionContext ec) {

        // defaults
        final int number = defaultParam("number", ec, 3);

        //
        // execute
        //
        ec.executeChild(this, new DemoTearDown());

//        getQuickObjects().addAll(execute(ec, QuickObject.class).getObjects());

        ec.executeChild(this, new CreateUsingSpreadsheet<>(IngredientImport.class));

    }

    private <T> CreateUsingSpreadsheet<T> execute(final ExecutionContext ec, final Class<T> aClass) {
        CreateUsingSpreadsheet fs1 = new CreateUsingSpreadsheet<>(aClass);
        ec.executeChild(this, fs1);
        return fs1;
    }
}
