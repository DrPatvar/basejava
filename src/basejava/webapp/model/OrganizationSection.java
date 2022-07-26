package basejava.webapp.model;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;


public class OrganizationSection extends Section {
  private List<Organization> organizations;

  public OrganizationSection() {
  }

  public OrganizationSection(Organization... organizations) {
    this.organizations = Arrays.asList(organizations);
  }

  public OrganizationSection(List<Organization> organizations) {
    Objects.requireNonNull(organizations, "organization is not null");
    this.organizations = organizations;
  }

  public List<Organization> getOrganizations() {
    return organizations;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    OrganizationSection that = (OrganizationSection) o;
    return Objects.equals(organizations, that.organizations);
  }

  @Override
  public int hashCode() {
    return Objects.hash(organizations);
  }

  @Override
  public String toString() {
    return "OrganizationSection{" +
            "organizations=" + organizations +
            '}';
  }
}
