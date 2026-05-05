package com.ehr_integration_platform.specification;

import com.ehr_integration_platform.entity.Patient;
import org.springframework.data.jpa.domain.Specification;

public class PatientSpecification {

    public static Specification<Patient> hasName(String name) {
        return (root, query, cb) ->
                name == null ? null :
                        cb.like(cb.lower(root.get("name")), "%" + name.toLowerCase() + "%");
    }

    public static Specification<Patient> hasGender(String gender) {
        return (root, query, cb) ->
                gender == null ? null :
                        cb.equal(cb.lower(root.get("gender")), gender.toLowerCase());
    }

    public static Specification<Patient> hasDisease(String disease) {
        return (root, query, cb) ->
                disease == null ? null :
                        cb.like(cb.lower(root.get("disease")), "%" + disease.toLowerCase() + "%");
    }
}