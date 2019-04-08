package com.phorest.salon.clientservices.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.phorest.salon.clientservices.constants.PhorestConstants;
import com.phorest.salon.clientservices.processor.PhorestCSVProcessorFactory;

@RestController
@RequestMapping("/phorest")
public class PhorestClientController {

	@Autowired
	private PhorestCSVProcessorFactory phorestCSVProcessorFactory;
	
	@PostMapping(path = "/importcsv",consumes = {MimeTypeUtils.ALL_VALUE})
	public ResponseEntity<Boolean> importClientInformation(@RequestParam(value="clients_csv") MultipartFile client,
			@RequestParam(value="appointment_csv") MultipartFile appointment,
			@RequestParam(value="services_csv") MultipartFile services,
			@RequestParam(value="purchases_csv") MultipartFile purchases) {
		//clientServices.saveImportedClientInfo(client,appointment,services,purchases);
		phorestCSVProcessorFactory.getProcessor(PhorestConstants.CLIENT).processCSV(client);
		phorestCSVProcessorFactory.getProcessor(PhorestConstants.APPOINTMENT).processCSV(appointment);
		phorestCSVProcessorFactory.getProcessor(PhorestConstants.SERVICE).processCSV(services); 
		phorestCSVProcessorFactory.getProcessor(PhorestConstants.PURCHASE).processCSV(purchases);
		return new ResponseEntity<>(Boolean.FALSE,HttpStatus.OK);
		
	}

}
