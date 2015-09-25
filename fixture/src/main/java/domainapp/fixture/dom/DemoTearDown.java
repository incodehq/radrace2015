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

package domainapp.fixture.dom;

import org.apache.isis.applib.fixturescripts.FixtureScript;
import org.apache.isis.applib.services.jdosupport.IsisJdoSupport;

public class DemoTearDown extends FixtureScript {

    @Override
    protected void execute(ExecutionContext executionContext) {

        isisJdoSupport.executeUpdate("delete from \"simple\".\"QuickObject\"");

        isisJdoSupport.executeUpdate("delete from \"simple\".\"OrderItem\"");
        isisJdoSupport.executeUpdate("delete from \"simple\".\"Order\"");
        isisJdoSupport.executeUpdate("delete from \"simple\".\"MenuItem\"");
        isisJdoSupport.executeUpdate("delete from \"simple\".\"Menu\"");
        isisJdoSupport.executeUpdate("delete from \"simple\".\"Event\"");

        isisJdoSupport.executeUpdate("delete from \"simple\".\"Ingredient\"");
        isisJdoSupport.executeUpdate("delete from \"simple\".\"IngredientCategory\"");
        isisJdoSupport.executeUpdate("delete from \"simple\".\"Supplier\"");

    }


    @javax.inject.Inject
    private IsisJdoSupport isisJdoSupport;

}
