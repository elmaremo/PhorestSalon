package com.phorest.salon.clientservices.processor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.phorest.salon.clientservices.jpa.client.Purchases;
import com.phorest.salon.clientservices.jpa.repository.AppointmentRepository;
import com.phorest.salon.clientservices.jpa.repository.PurchasesRepository;

@Component
public class PhorestPurchasesCSVProcessor implements PhorestCSVProcessor {

	@Autowired
	private PurchasesRepository purchaserepo;
	
	@Autowired
	private AppointmentRepository appointmentrepo;
	
	private static final Logger logger = LoggerFactory.getLogger(PhorestPurchasesCSVProcessor.class);

	@Override
	public Boolean processCSV(MultipartFile file) {
		logger.debug("START - Inside processCSV of PhorestPurchasesCSVProcessor");
		BufferedReader br;
		List<Purchases> listOfPurchases = new ArrayList<>();
		try {
			 int count = 0;
		     String line;
		     InputStream is = file.getInputStream();
		     br = new BufferedReader(new InputStreamReader(is));
		     while ((line = br.readLine()) != null) {
		    	 if(count>=1) {
		    	 String[] arrayofPurchases = line.split(",");
		    	 Purchases purchase = new Purchases();
		    	 purchase.setId(arrayofPurchases[0].trim());
		    	 purchase.setAppointment(appointmentrepo.findById(arrayofPurchases[1].trim()).get());
		    	 purchase.setName(arrayofPurchases[2].trim());
			     
		    	 purchase.setPrice(Double.valueOf(arrayofPurchases[3].trim()));
		    	 purchase.setLoyalty_points(Integer.valueOf(arrayofPurchases[4].trim()));
		    	 listOfPurchases.add(purchase);
			     
		    	 }
			     count++;
		     }
		     purchaserepo.saveAll(listOfPurchases);
		     logger.debug("END - Inside processCSV of PhorestPurchasesCSVProcessor");
		
	 } catch (IOException e) {
		 logger.error(e.getMessage());       
	  }
		return Boolean.TRUE;
	}
}
