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
package domainapp.dom.order;

import java.math.BigInteger;
import java.util.List;

import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.ActionLayout;
import org.apache.isis.applib.annotation.CollectionLayout;
import org.apache.isis.applib.annotation.Contributed;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.annotation.RenderType;
import org.apache.isis.applib.annotation.SemanticsOf;

import domainapp.dom.event.Event;

@DomainService(
        nature = NatureOfService.VIEW_CONTRIBUTIONS_ONLY
)
public class OrderContributionsOnEvent {

    @javax.inject.Inject
    OrderRepository orderRepository;
    //endregion

    //region > orders (contributed collection)
    @Action(
            semantics = SemanticsOf.SAFE
    )
    @ActionLayout(
            contributed = Contributed.AS_ASSOCIATION
    )
    @CollectionLayout(
            render = RenderType.EAGERLY
    )
    public List<Order> orders(final Event event) {
        return orderRepository.findByEvent(event);
    }

    //endregion

    //region > injected services

    @Action(semantics = SemanticsOf.SAFE)
    @ActionLayout(contributed = Contributed.AS_ASSOCIATION)
    public BigInteger total(Event event) {
        BigInteger sum = BigInteger.ZERO;
        for (Order order : orderRepository.findByEvent(event)) {
                sum = sum.add(BigInteger.ONE);
        }
        return sum;
    }

    @Action(semantics = SemanticsOf.SAFE)
    @ActionLayout(contributed = Contributed.AS_ASSOCIATION)
    public BigInteger totalMembers(Event event) {
        BigInteger sum = BigInteger.ZERO;
        for (Order order : orderRepository.findByEvent(event)) {
            if(order.getPerson().isMember()){
                sum = sum.add(BigInteger.ONE);
            }
        }
        return sum;
    }

    @Action(semantics = SemanticsOf.SAFE)
    @ActionLayout(contributed = Contributed.AS_ASSOCIATION)
    public BigInteger totalNonMembers(Event event) {
        BigInteger sum = BigInteger.ZERO;
        for (Order order : orderRepository.findByEvent(event)) {
            if(!order.getPerson().isMember()){
                sum = sum.add(BigInteger.ONE);
            }
        }
        return sum;
    }

    @Action(semantics = SemanticsOf.SAFE)
    @ActionLayout(contributed = Contributed.AS_ASSOCIATION)
    public BigInteger totalAdults(Event event) {
        BigInteger sum = BigInteger.ZERO;
        for (Order order : orderRepository.findByEvent(event)) {
            if(order.getPerson().isMember() && order.getPerson().isAdult()){
                sum = sum.add(BigInteger.ONE);
            }
        }
        return sum;
    }

    @Action(semantics = SemanticsOf.SAFE)
    @ActionLayout(contributed = Contributed.AS_ASSOCIATION)
    public BigInteger totalChildren(Event event) {
        BigInteger sum = BigInteger.ZERO;
        for (Order order : orderRepository.findByEvent(event)) {
            if(order.getPerson().isMember() && !order.getPerson().isAdult()){
                sum = sum.add(BigInteger.ONE);
            }
        }
        return sum;
    }

}
