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

import org.apache.isis.applib.annotation.ViewModel;

import domainapp.dom.ingredient.IngredientRepository;
import domainapp.dom.ingredientcategory.IngredientCategory;
import domainapp.dom.ingredientcategory.IngredientCategoryRepository;
import domainapp.dom.supplier.Supplier;
import domainapp.dom.supplier.SupplierRepository;

@ViewModel
public class IngredientImport implements Importable {

    private String name;
    private String category;
    private String supplier;

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(final String category) {
        this.category = category;
    }

    public String getSupplier() {
        return supplier;
    }

    public void setSupplier(final String supplier) {
        this.supplier = supplier;
    }

    @Override public void importData() {
        final Supplier supplier =
                supplierRepository.findOrCreate(getSupplier());

        final IngredientCategory ingredientCategory =
                ingredientCategoryRepository.findOrCreate(getCategory());

        ingredientRepository.findOrCreate(getName(), ingredientCategory, supplier);
    }
    private void doPersist(final IngredientImport obj) {
        final Supplier supplier =
                supplierRepository.findOrCreate(obj.getSupplier());

        final IngredientCategory ingredientCategory =
                ingredientCategoryRepository.findOrCreate(obj.getCategory());

        ingredientRepository.findOrCreate(obj.getName(), ingredientCategory, supplier);
    }

    @Inject
    IngredientRepository ingredientRepository;

    @Inject
    IngredientCategoryRepository ingredientCategoryRepository;

    @Inject
    SupplierRepository supplierRepository;


}
