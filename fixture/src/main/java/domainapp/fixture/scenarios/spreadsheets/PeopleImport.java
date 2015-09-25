package domainapp.fixture.scenarios.spreadsheets;

import org.joda.time.LocalDate;

import org.apache.isis.applib.annotation.ViewModel;

@ViewModel
public class PeopleImport {

    private Integer number;
    private String lastName;
    private String firstName;
    private String password;
    private String street;
    private Integer streetNumber;
    private String postCode;
    private String city;
    private String country;
    private LocalDate dateOfBirth;
    private String locationOfBirth;
    private String countryOfBirth;
    private String memberType;

    public Integer getNumber() {
        return number;
    }

    public void setNumber(final Integer number) {
        this.number = number;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(final String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(final String firstName) {
        this.firstName = firstName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(final String password) {
        this.password = password;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(final String street) {
        this.street = street;
    }

    public Integer getStreetNumber() {
        return streetNumber;
    }

    public void setStreetNumber(final Integer streetNumber) {
        this.streetNumber = streetNumber;
    }

    public String getPostCode() {
        return postCode;
    }

    public void setPostCode(final String postCode) {
        this.postCode = postCode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(final String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(final String country) {
        this.country = country;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(final LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getLocationOfBirth() {
        return locationOfBirth;
    }

    public void setLocationOfBirth(final String locationOfBirth) {
        this.locationOfBirth = locationOfBirth;
    }

    public String getCountryOfBirth() {
        return countryOfBirth;
    }

    public void setCountryOfBirth(final String countryOfBirth) {
        this.countryOfBirth = countryOfBirth;
    }

    public String getMemberType() {
        return memberType;
    }

    public void setMemberType(final String memberType) {
        this.memberType = memberType;
    }
}