package domainapp.fixture.scenarios.spreadsheets;

import javax.inject.Inject;

import org.apache.isis.applib.DomainObjectContainer;

import domainapp.dom.person.Person;
import domainapp.dom.person.PersonRepository;

public class CreateUsingSpreadsheetForPeople extends CreateUsingSpreadsheet<PeopleImport> {

    public CreateUsingSpreadsheetForPeople() {
        super(PeopleImport.class);
    }

    @Override public String getResourceName() {
        return "PeopleImportA.xls";
    }

    @Override protected void doPersist(final PeopleImport obj) {

        try {

            Person p = personRepository.findByMemberId(obj.getNumber().toString());

            if (p == null) {
                p = personRepository.create(obj.getFirstName(), obj.getLastName());
                p.setMemberId(obj.getNumber().toString());
            }
            p.setStreet(obj.getStreet());
            p.setStreetNumber(obj.getStreetNumber().toString());
            p.setPostCode(obj.getPostCode());
            p.setCity(obj.getCity());
            p.setCountry(obj.getCountry());

            p.setDateOfBirth(obj.getDateOfBirth());
            p.setCityOfBirth(obj.getLocationOfBirth());
            p.setCountryOfBirth(obj.getCountryOfBirth());

            if (obj.getMemberType() == "member") {
                p.setMember(true);
            }
            ;

            container.flush();
        } catch (Exception e) {

        }

    }

    @Inject
    DomainObjectContainer container;

    @Inject
    private PersonRepository personRepository;

}
