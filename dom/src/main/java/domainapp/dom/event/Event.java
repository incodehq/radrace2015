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
package domainapp.dom.event;

import javax.inject.Inject;
import javax.jdo.JDOHelper;
import javax.jdo.annotations.Column;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.VersionStrategy;

import org.joda.time.LocalDate;

import org.apache.isis.applib.DomainObjectContainer;
import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.ActionLayout;
import org.apache.isis.applib.annotation.BookmarkPolicy;
import org.apache.isis.applib.annotation.DomainObject;
import org.apache.isis.applib.annotation.DomainObjectLayout;
import org.apache.isis.applib.annotation.Editing;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.Optionality;
import org.apache.isis.applib.annotation.Parameter;
import org.apache.isis.applib.annotation.ParameterLayout;
import org.apache.isis.applib.annotation.Programmatic;
import org.apache.isis.applib.annotation.Property;
import org.apache.isis.applib.annotation.SemanticsOf;
import org.apache.isis.applib.annotation.Title;
import org.apache.isis.applib.annotation.Where;
import org.apache.isis.applib.services.i18n.TranslatableString;
import org.apache.isis.applib.util.ObjectContracts;

import org.isisaddons.wicket.fullcalendar2.cpt.applib.CalendarEvent;
import org.isisaddons.wicket.fullcalendar2.cpt.applib.CalendarEventable;
import org.isisaddons.wicket.gmap3.cpt.applib.Locatable;
import org.isisaddons.wicket.gmap3.cpt.applib.Location;
import org.isisaddons.wicket.gmap3.cpt.service.LocationLookupService;

import domainapp.dom.Named;
import domainapp.dom.menu.Menu;
import domainapp.dom.menu.MenuRepository;

@javax.jdo.annotations.PersistenceCapable(
        identityType=IdentityType.DATASTORE,
        schema = "simple",
        table = "Event"
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
                        + "FROM domainapp.dom.event.Event "),
        @javax.jdo.annotations.Query(
                name = "findByName", language = "JDOQL",
                value = "SELECT "
                        + "FROM domainapp.dom.event.Event "
                        + "WHERE name.indexOf(:name) >= 0 ")
})
@javax.jdo.annotations.Unique(name="Event_name_UNQ", members = {"name"})
@DomainObject(
        editing = Editing.DISABLED,
        bounded = true
)
@DomainObjectLayout(
        bookmarking = BookmarkPolicy.AS_ROOT
)
public class Event implements Comparable<Event>, Named, CalendarEventable, Locatable {


    //region > identificatiom
    public TranslatableString title() {
        return TranslatableString.tr("{name}", "name", getName());
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
    
    @Action
    public Event updateName(
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

    //region > accessibleTo (property)
    private AccessLevel accessibleTo;

    @MemberOrder(sequence = "1")
    @Column(allowsNull = "false")
    public AccessLevel getAccessibleTo() {
        return accessibleTo;
    }

    public void setAccessibleTo(final AccessLevel accessibleTo) {
        this.accessibleTo = accessibleTo;
    }
    //endregion


    //region > date (property)
    private LocalDate start;

    @Column(allowsNull = "true")
    public LocalDate getStart() {
        return start;
    }

    public void setStart(final LocalDate start) {
        this.start = start;
    }
    //endregion

    //region > date (property)
    private LocalDate end;

    @Column(allowsNull = "true")
    public LocalDate getEnd() {
        return end;
    }

    public void setEnd(final LocalDate end) {
        this.end = end;
    }
    //endregion


    public boolean isActiveOn(LocalDate date){
        return getStart().compareTo(date)>= 0 && (getEnd() == null || getEnd().compareTo(date)<=0);
    }

    public boolean isOpenOn(LocalDate date){
        return (getInscriptionStart() == null || getInscriptionStart().compareTo(date)>= 0) && (getInscriptionEnd() == null || getInscriptionEnd().compareTo(date)<=0);
    }


    //region > closingDate (property)
    @Column(allowsNull = "false")
    private LocalDate inscriptionStart;

    @MemberOrder(sequence = "1")
    @Column(allowsNull = "false")
    public LocalDate getInscriptionStart() {
        return inscriptionStart;
    }

    public void setInscriptionStart(final LocalDate inscriptionStart) {
        this.inscriptionStart = inscriptionStart;
    }
    //endregion

    //region > closingDate (property)
    @Column(allowsNull = "false")
    private LocalDate inscriptionEnd;

    @MemberOrder(sequence = "1")
    @Column(allowsNull = "false")
    public LocalDate getInscriptionEnd() {
        return inscriptionEnd;
    }

    public void setInscriptionEnd(final LocalDate inscriptionEnd) {
        this.inscriptionEnd = inscriptionEnd;
    }
    //endregion


    //region > location (property)
    private String address;

    @MemberOrder(sequence = "1")
    @Column(allowsNull = "false")
    public String getAddress() {
        return address;
    }

    public void setAddress(final String address) {
        this.address = address;
    }
    //endregion

    //region > location (property)
    @Persistent
    private Location location;

    @Property(editing = Editing.DISABLED, optionality = Optionality.OPTIONAL, hidden = Where.ALL_TABLES)
    public Location getLocation() {
        return location;
    }

    public void setLocation(final Location location) {
        this.location = location;
    }

    @Action(semantics = SemanticsOf.IDEMPOTENT)
    @ActionLayout(named = "Lookup")
    public Event updateLocation(
            final @ParameterLayout(named = "Address", describedAs = "Example: Herengracht 469, Amsterdam, NL") String location) {
        setAddress(location);
        if (locationLookupService != null) {
            // TODO: service does not seem to be loaded in tests
            setLocation(locationLookupService.lookup(location));
        }
        return this;
    }

    public String default0UpdateLocation() {
        return getAddress();
    }

    //endregion
    
    //region > menu (derived property)
    private Menu menu;

    @javax.jdo.annotations.NotPersistent // REVIEW: alternatively, map a 1:1 relationship
    public Menu getMenu() {
        return menuRepository.findByEvent(this);
    }

    //endregion
    
    //region > version (derived property)
    public Long getVersionSequence() {
        return (Long) JDOHelper.getVersion(this);
    }
    //endregion

    //region > compareTo

    @Override
    public int compareTo(final Event other) {
        return ObjectContracts.compare(this, other, "name");
    }

    //endregion

    //region > injected services

    @Inject
    MenuRepository menuRepository;

    @Inject
    LocationLookupService locationLookupService;

    //endregion

    //region > Calendarable
    @SuppressWarnings("unused")
    private DomainObjectContainer container;

    @Programmatic
    public String getCalendarName() {
        return "events";
    }

    @Programmatic
    public CalendarEvent toCalendarEvent() {
        return new CalendarEvent(getStart().toDateTimeAtStartOfDay(), "events", container.titleOf(this), "");
    }
    //endregion

    public enum AccessLevel {
        ALL,
        MEMBERS_ONLY,
        INVITEES_ONLY
    }


}
