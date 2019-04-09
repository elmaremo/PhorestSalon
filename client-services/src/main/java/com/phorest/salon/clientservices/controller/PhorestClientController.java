package com.phorest.salon.clientservices.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.phorest.salon.clientservices.constants.PhorestConstants;
import com.phorest.salon.clientservices.dto.ResponseDTO;
import com.phorest.salon.clientservices.processor.PhorestCSVProcessorFactory;

@RestController
@RequestMapping("/phorest")
public class PhorestClientController {

	@Autowired
	private PhorestCSVProcessorFactory phorestCSVProcessorFactory;

	private static final Logger logger = LoggerFactory.getLogger(PhorestClientController.class);

	@PostMapping(path = "/importcsv", consumes = { MimeTypeUtils.ALL_VALUE })
	@ResponseBody
	public ResponseDTO importClientInformation(@RequestParam(value = "clients_csv") MultipartFile client,
			@RequestParam(value = "appointment_csv") MultipartFile appointment,
			@RequestParam(value = "services_csv") MultipartFile services,
			@RequestParam(value = "purchases_csv") MultipartFile purchases) {
		logger.debug("Starting debugging");
		ResponseDTO responseDTO = new ResponseDTO();
		phorestCSVProcessorFactory.getProcessor(PhorestConstants.CLIENT).processCSV(client);
		phorestCSVProcessorFactory.getProcessor(PhorestConstants.APPOINTMENT).processCSV(appointment);
		phorestCSVProcessorFactory.getProcessor(PhorestConstants.SERVICE).processCSV(services);
		phorestCSVProcessorFactory.getProcessor(PhorestConstants.PURCHASE).processCSV(purchases);
		responseDTO.setValue(true);
		responseDTO.setMessage("Success");
		logger.debug("End debugging");
		return responseDTO;

		

	}

}
