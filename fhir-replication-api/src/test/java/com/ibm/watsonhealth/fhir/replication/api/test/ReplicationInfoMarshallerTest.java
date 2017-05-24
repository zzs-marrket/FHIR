/**
 * (C) Copyright IBM Corp. 2017,2018,2019
 *
 * SPDX-License-Identifier: Apache-2.0
 */

package com.ibm.watsonhealth.fhir.replication.api.test;

import static org.testng.Assert.assertNotNull;

import java.util.Date;

import org.testng.annotations.Test;

import com.ibm.watsonhealth.fhir.exception.FHIRException;
import com.ibm.watsonhealth.fhir.replication.api.Marshaller;
import com.ibm.watsonhealth.fhir.replication.api.impl.ReplicationInfoMarshaller;
import com.ibm.watsonhealth.fhir.replication.api.model.ReplicationInfo;

public class ReplicationInfoMarshallerTest {
	private Marshaller<ReplicationInfo> marshaller = new ReplicationInfoMarshaller();
	
	@Test(expectedExceptions={ FHIRException.class } )
	public void marshall_verifyNullReplicationInfo() throws FHIRException {
		marshaller.marshall(null);
	}
	
	@Test
	public void marshall_verifyMarshalling() throws FHIRException {
		final ReplicationInfo replicationInfo = buildReplicationInfo();
		
		final String replicationInfoJson = marshaller.marshall(replicationInfo);
		
		assertNotNull(replicationInfoJson);
	}
	
	private ReplicationInfo buildReplicationInfo() {
		final ReplicationInfo replicationInfo = new ReplicationInfo();
		
		replicationInfo.setSourceKey("theSourceKey");
		replicationInfo.setServiceId("theServiceId");
		replicationInfo.setCreationTime(new Date());
		replicationInfo.setVersionId(1);
		replicationInfo.setTxCorrelationId("theTxCorrelationId");
		replicationInfo.setChangedBy("theChangedBy");
		replicationInfo.setCorrelationToken("theCorrelationToken");
		replicationInfo.setTenantId("theTenantId");
		replicationInfo.setReason("theReason");
		replicationInfo.setEvent("theEvent");
		replicationInfo.setResourceType("theResourceType");
		replicationInfo.setSiteId("theSiteId");
		replicationInfo.setStudyId("theStudyId");
		replicationInfo.setPatientId("thePatientId");
		
		return replicationInfo;
	}
}