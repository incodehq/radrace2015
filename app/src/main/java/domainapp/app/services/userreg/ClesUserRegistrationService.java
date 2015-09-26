package domainapp.app.services.userreg;

import java.util.Collections;
import java.util.Set;

import javax.inject.Inject;

import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.NatureOfService;

import org.isisaddons.module.security.dom.role.ApplicationRole;
import org.isisaddons.module.security.dom.role.ApplicationRoleRepository;
import org.isisaddons.module.security.userreg.SecurityModuleAppUserRegistrationServiceAbstract;

import domainapp.dom.seed.roles.DomainAppRegularRoleAndPermissions;

@DomainService(
        nature = NatureOfService.DOMAIN
)
public class ClesUserRegistrationService extends SecurityModuleAppUserRegistrationServiceAbstract {

    @Override
    protected ApplicationRole getInitialRole() {
        return applicationRoleRepository.findByName(DomainAppRegularRoleAndPermissions.ROLE_NAME);
    }

    @Override
    protected Set<ApplicationRole> getAdditionalInitialRoles() {
        return Collections.emptySet();
    }

    @Inject
    private ApplicationRoleRepository applicationRoleRepository;

}
