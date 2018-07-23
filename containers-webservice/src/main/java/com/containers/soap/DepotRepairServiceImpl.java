package com.containers.soap;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import org.apache.cxf.feature.Features;

import com.estimatesofrepairs.CreateRepairRequest;
import com.estimatesofrepairs.CreateRepairResponse;
import com.estimatesofrepairs.DeleteRepairRequest;
import com.estimatesofrepairs.DeleteRepairResponse;
import com.estimatesofrepairs.DepotRepairPortType;
import com.estimatesofrepairs.Estimate;
import com.estimatesofrepairs.GetRepairRequest;
import com.estimatesofrepairs.GetRepairResponse;
import com.estimatesofrepairs.Repair;

@Features(features="org.apache.cxf.feature.LoggingFeature")
public class DepotRepairServiceImpl implements DepotRepairPortType {
	
	Map<BigInteger, List<Repair>> depotRepairs = new HashMap<>();
	int currentId;
	
	public DepotRepairServiceImpl() throws DatatypeConfigurationException {
		init();
	}
	
	public void init() throws DatatypeConfigurationException {
		List<Repair> repairs = new ArrayList<>();
		Repair repair = new Repair();
		repair.setId(BigInteger.valueOf(1));
		repairs.add(repair);
		
		Estimate estimate = new Estimate();
		estimate.setEstimateId(1);
		estimate.setEstimateCurrency("EUR");
		estimate.setEstimateAmount(1000.23);
		estimate.setEstimateDepot("Hamburg");
		estimate.setEstimateNumber("DEHAMHCCE20187");		
		
		estimate.setEstimateDate(getXMLGregorianCalendarNow());
		repair.getEstimate().add(estimate);
		
		depotRepairs.put(BigInteger.valueOf(++currentId), repairs);
	}
	
	public XMLGregorianCalendar getXMLGregorianCalendarNow() 
            throws DatatypeConfigurationException
    {
        GregorianCalendar gregorianCalendar = new GregorianCalendar();
        DatatypeFactory datatypeFactory = DatatypeFactory.newInstance();
        XMLGregorianCalendar now = 
            datatypeFactory.newXMLGregorianCalendar(gregorianCalendar);
        return now;
    }

	@Override
	public CreateRepairResponse createRepair(CreateRepairRequest request) {
		BigInteger depotId = request.getDepotId();
		Repair repair = request.getRepair();		
		
		List<Repair> repairs = depotRepairs.get(depotId);
		repairs.add(repair);
		
		CreateRepairResponse response = new CreateRepairResponse();
		response.setResult(true);
		
		return response;
	}

	@Override
	public DeleteRepairResponse deleteRepair(DeleteRepairRequest request) {
		BigInteger depotId = request.getDepotId();
		List<Repair> repairs = depotRepairs.get(depotId);
		
		DeleteRepairResponse response = new DeleteRepairResponse();
		repairs.removeAll(repairs);
		response.setResult(true);
		
		return response;


	}

	@Override
	public GetRepairResponse getRepair(GetRepairRequest request) {
		BigInteger depotId = request.getDepotId();
		List<Repair> repairs = depotRepairs.get(depotId);
		
		GetRepairResponse response = new GetRepairResponse();
		response.getRepair().addAll(repairs);
		
		return response;
	}

}
