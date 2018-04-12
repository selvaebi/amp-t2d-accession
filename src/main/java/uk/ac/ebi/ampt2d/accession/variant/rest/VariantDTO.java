/*
 *
 * Copyright 2018 EMBL - European Bioinformatics Institute
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */
package uk.ac.ebi.ampt2d.accession.variant.rest;

import uk.ac.ebi.ampt2d.accession.variant.VariantModel;
import uk.ac.ebi.ampt2d.accession.variant.VariantType;

import javax.validation.constraints.NotNull;

public class VariantDTO implements VariantModel {

    @NotNull(message = "Variant assemblyAccession should not be null")
    private String assemblyAccession;

    @NotNull(message = "Variant projectAccession should not be null")
    private String projectAccession;

    @NotNull(message = "Variant chromosome should not be null")
    private String chromosome;

    @NotNull(message = "Variant start should not be null")
    private long start;

    @NotNull(message = "Variant Type should not be null")
    private VariantType type;

    VariantDTO() {
    }

    public VariantDTO(String assemblyAccession, String projectAccession, String chromosome, long start, VariantType type) {
        this.assemblyAccession = assemblyAccession;
        this.projectAccession = projectAccession;
        this.chromosome = chromosome;
        this.start = start;
        this.type = type;
    }

    @Override
    public String getAssemblyAccession() {
        return assemblyAccession;
    }

    @Override
    public String getProjectAccession() {
        return projectAccession;
    }

    @Override
    public String getChromosome() {
        return chromosome;
    }

    @Override
    public long getStart() {
        return start;
    }

    @Override
    public VariantType getType() {
        return type;
    }

}
