/**
 * (C) Copyright IBM Corp. 2019
 *
 * SPDX-License-Identifier: Apache-2.0
 */

package com.ibm.watsonhealth.fhir.model.type;

import java.util.Collection;
import java.util.Objects;
import java.util.regex.Pattern;

import javax.annotation.Generated;

import com.ibm.watsonhealth.fhir.model.util.ValidationSupport;

/**
 * <p>
 * Any combination of letters, numerals, "-" and ".", with a length limit of 64 characters. (This might be an integer, an 
 * unprefixed OID, UUID or any other identifier pattern that meets these constraints.) Ids are case-insensitive.
 * </p>
 */
@Generated("com.ibm.watsonhealth.fhir.tools.CodeGenerator")
public class Id extends String {
    private static final Pattern PATTERN = Pattern.compile("[A-Za-z0-9\\-\\.]{1,64}");

    private volatile int hashCode;

    private Id(Builder builder) {
        super(builder);
        ValidationSupport.checkValue(value, PATTERN);
    }

    @Override
    public boolean hasValue() {
        return (value != null);
    }

    public static Id of(java.lang.String value) {
        return Id.builder().value(value).build();
    }

    public static String string(java.lang.String value) {
        return Id.builder().value(value).build();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        Id other = (Id) obj;
        return Objects.equals(id, other.id) && 
            Objects.equals(extension, other.extension) && 
            Objects.equals(value, other.value);
    }

    @Override
    public int hashCode() {
        int result = hashCode;
        if (result == 0) {
            result = Objects.hash(id, 
                extension, 
                value);
            hashCode = result;
        }
        return result;
    }

    @Override
    public Builder toBuilder() {
        return new Builder().from(this);
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder extends String.Builder {
        private Builder() {
            super();
        }

        /**
         * <p>
         * unique id for the element within a resource (for internal references)
         * </p>
         * 
         * @param id
         *     xml:id (or equivalent in JSON)
         * 
         * @return
         *     A reference to this Builder instance
         */
        @Override
        public Builder id(java.lang.String id) {
            return (Builder) super.id(id);
        }

        /**
         * <p>
         * May be used to represent additional information that is not part of the basic definition of the resource. To make the 
         * use of extensions safe and manageable, there is a strict set of governance applied to the definition and use of 
         * extensions. Though any implementer can define an extension, there is a set of requirements that SHALL be met as part 
         * of the definition of the extension.
         * </p>
         * <p>
         * Adds new element(s) to existing list
         * </p>
         * 
         * @param extension
         *     Additional content defined by implementations
         * 
         * @return
         *     A reference to this Builder instance
         */
        @Override
        public Builder extension(Extension... extension) {
            return (Builder) super.extension(extension);
        }

        /**
         * <p>
         * May be used to represent additional information that is not part of the basic definition of the resource. To make the 
         * use of extensions safe and manageable, there is a strict set of governance applied to the definition and use of 
         * extensions. Though any implementer can define an extension, there is a set of requirements that SHALL be met as part 
         * of the definition of the extension.
         * </p>
         * <p>
         * Replaces existing list with a new one containing elements from the Collection
         * </p>
         * 
         * @param extension
         *     Additional content defined by implementations
         * 
         * @return
         *     A reference to this Builder instance
         */
        @Override
        public Builder extension(Collection<Extension> extension) {
            return (Builder) super.extension(extension);
        }

        /**
         * <p>
         * Primitive value for id
         * </p>
         * 
         * @param value
         *     Primitive value for id
         * 
         * @return
         *     A reference to this Builder instance
         */
        @Override
        public Builder value(java.lang.String value) {
            return (Builder) super.value(value);
        }

        @Override
        public Id build() {
            return new Id(this);
        }

        private Builder from(Id id) {
            this.id = id.id;
            extension.addAll(id.extension);
            value = id.value;
            return this;
        }
    }
}
