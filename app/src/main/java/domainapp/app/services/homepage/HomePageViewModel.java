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
package domainapp.app.services.homepage;

import java.util.List;

import javax.inject.Inject;

import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;

import org.apache.isis.applib.annotation.CollectionLayout;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.RenderType;
import org.apache.isis.applib.annotation.ViewModel;
import org.apache.isis.applib.services.clock.ClockService;

import domainapp.dom.event.Event;
import domainapp.dom.event.EventRepository;

@ViewModel
public class HomePageViewModel {

    //region > title
    public String title() {
        return "Events";
    }
    //endregion

    //region > object (collection)
    //@org.apache.isis.applib.annotation.HomePage
    @CollectionLayout(
            render = RenderType.EAGERLY
    )
    @MemberOrder(sequence = "1")
    public List<Event> getCurrentEvents() {

        return Lists.newArrayList(Iterables.filter(eventRepository.listAll(), new Predicate<Event>() {
            @Override public boolean apply(final Event input) {
                return input.isActiveOn(clockService.now());
            }
        }));
    }

    @CollectionLayout(
            render = RenderType.EAGERLY
    )
    @MemberOrder(sequence = "2")
    public List<Event> getOpenEvents() {

        return Lists.newArrayList(Iterables.filter(eventRepository.listAll(), new Predicate<Event>() {
            @Override public boolean apply(final Event input) {
                return input.isOpenOn(clockService.now());
            }
        }));
    }

    @CollectionLayout(
            render = RenderType.EAGERLY
    )
    @MemberOrder(sequence = "3")
    public List<Event> getFutureEvents() {

        return Lists.newArrayList(Iterables.filter(eventRepository.listAll(), new Predicate<Event>() {
            @Override public boolean apply(final Event input) {
                return input.isFutureOn(clockService.now());
            }
        }));
    }

    @CollectionLayout(
            render = RenderType.EAGERLY
    )
    @MemberOrder(sequence = "4")
    public List<Event> getPassedEvents() {

        return Lists.newArrayList(Iterables.filter(eventRepository.listAll(), new Predicate<Event>() {
            @Override public boolean apply(final Event input) {
                return input.isPassedOn(clockService.now());
            }
        }));
    }


    //endregion

    //region > injected services

    @javax.inject.Inject
    EventRepository eventRepository;

    @Inject
    ClockService clockService;

    //endregion
}
