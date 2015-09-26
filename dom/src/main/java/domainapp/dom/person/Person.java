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
package domainapp.dom.person;

import javax.inject.Inject;
import javax.jdo.annotations.Column;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.VersionStrategy;

import com.google.common.collect.ComparisonChain;

import org.joda.time.LocalDate;

import org.apache.isis.applib.annotation.BookmarkPolicy;
import org.apache.isis.applib.annotation.DomainObject;
import org.apache.isis.applib.annotation.DomainObjectLayout;
import org.apache.isis.applib.annotation.Editing;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.Parameter;
import org.apache.isis.applib.annotation.ParameterLayout;
import org.apache.isis.applib.annotation.Property;
import org.apache.isis.applib.annotation.Where;
import org.apache.isis.applib.services.clock.ClockService;
import org.apache.isis.applib.util.TitleBuffer;

@javax.jdo.annotations.PersistenceCapable(
        identityType=IdentityType.DATASTORE,
        schema = "simple",
        table = "Person"
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
                        + "FROM domainapp.dom.person.Person "),
        @javax.jdo.annotations.Query(
                name = "findByMemberId", language = "JDOQL",
                value = "SELECT "
                        + "FROM domainapp.dom.person.Person "
                        + "WHERE memberId == :memberId "),
        @javax.jdo.annotations.Query(
                name = "findByUsername", language = "JDOQL",
                value = "SELECT "
                        + "FROM domainapp.dom.person.Person "
                        + "WHERE username == :username "),
        @javax.jdo.annotations.Query(
                name = "findByName", language = "JDOQL",
                value = "SELECT "
                        + "FROM domainapp.dom.person.Person "
                        + "WHERE firstName.matches(:regex) || lastName.matches(:regex) ")
})
@DomainObject(
        editing = Editing.DISABLED
)
@DomainObjectLayout(
        bookmarking = BookmarkPolicy.AS_ROOT
)
public class Person implements Comparable<Person> {

    public String title() {
        TitleBuffer tb = new TitleBuffer();
        return tb.append(getLastName()).append(", ").append(getFirstName()).toString();
    }

    //region > username (property)
    private String username;

    @Property()
    @Column(allowsNull = "false")
    @MemberOrder(sequence = "1")
    public String getUsername() {
        return username;
    }

    public void setUsername(final String username) {
        this.username = username;
    }
    //endregion

    private Integer memberId;

    @Property(hidden = Where.EVERYWHERE)
    @Column(allowsNull = "true")
    public Integer getMemberId() {
        return memberId;
    }

    public void setMemberId(final Integer memberId) {
        this.memberId = memberId;
    }

    private String firstName;

    @Column(allowsNull="false", length = 50)
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(final String firstName) {
        this.firstName = firstName;
    }

    private String lastName;

    @Column(allowsNull = "false", length = 50)
    public String getLastName() {
        return lastName;
    }

    public void setLastName(final String lastName) {
        this.lastName = lastName;
    }

    public Person updateName(
            @Parameter(maxLength = 40)
            @ParameterLayout(named = "New name")
            final String firstName,
            final String lastName) {
        setFirstName(firstName);
        return this;
    }

    public String default0UpdateName() {
        return getFirstName();
    }

    public String default1UpdateName() {
        return getLastName();
    }

    //region > street (property)
    private String street;

    @MemberOrder(sequence = "1")
    @Column(allowsNull = "true")
    public String getStreet() {
        return street;
    }

    public void setStreet(final String street) {
        this.street = street;
    }
    //endregion

    //region > streetNumber (property)
    @Column(allowsNull = "true")
    private String streetNumber;

    @Column(allowsNull = "true")
    public String getStreetNumber() {
        return streetNumber;
    }

    public void setStreetNumber(final String streetNumber) {
        this.streetNumber = streetNumber;
    }
    //endregion

    //region > postcode (property)
    private String postcode;

    @MemberOrder(sequence = "1")
    @Column(allowsNull = "true")
    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(final String postcode) {
        this.postcode = postcode;
    }
    //endregion

    //region > city (property)
    private String city;

    @MemberOrder(sequence = "1")
    @Column(allowsNull = "true")
    public String getCity() {
        return city;
    }

    public void setCity(final String city) {
        this.city = city;
    }
    //endregion 

    //region > country (property)
    private String country;

    @MemberOrder(sequence = "1")
    @Column(allowsNull = "true")
    public String getCountry() {
        return country;
    }

    public void setCountry(final String country) {
        this.country = country;
    }
    //endregion

    //region > dateOfBirth (property)
    private LocalDate dateOfBirth;

    @MemberOrder(sequence = "1")
    @Column(allowsNull = "true")
    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(final LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }
    //endregion

    //region > cityOfBirth (property)
    private String cityOfBirth;

    @MemberOrder(sequence = "1")
    @Column(allowsNull = "true")
    public String getCityOfBirth() {
        return cityOfBirth;
    }

    public void setCityOfBirth(final String cityOfBirth) {
        this.cityOfBirth = cityOfBirth;
    }
    //endregion



    //region > countryOfBirth (property)
    private String countryOfBirth;

    @MemberOrder(sequence = "1")
    @Column(allowsNull = "true")
    public String getCountryOfBirth() {
        return countryOfBirth;
    }

    public void setCountryOfBirth(final String countryOfBirth) {
        this.countryOfBirth = countryOfBirth;
    }
    //endregion

    public Boolean member;

    @Column(allowsNull = "false")
    public Boolean isMember() {
        return member;
    }

    public void setMember(final Boolean member) {
        this.member = member;
    }

    public Boolean organiser;

    @Column(allowsNull = "false")
    public Boolean isOrganiser() {
        return organiser;
    }

    public void setOrganiser(final Boolean organiser) {
        this.organiser = organiser;
    }

    public Boolean isAdult(){
        if (getDateOfBirth() == null){
            return null;
        }
        return getDateOfBirth().plusYears(18).compareTo(clockService.now())>=0;
    }


    @Override
    public int compareTo(final Person o) {
        return ComparisonChain.start()
                .compare(getLastName(), o.getLastName())
                .compare(getFirstName(), o.getLastName())
                .result();
    }

    @Inject
    private ClockService clockService;
}
