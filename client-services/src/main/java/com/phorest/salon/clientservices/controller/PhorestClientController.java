package com.phorest.salon.clientservices.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.phorest.salon.clientservices.constants.PhorestConstants;
import com.phorest.salon.clientservices.dto.LoyalClientDTO;
import com.phorest.salon.clientservices.dto.ResponseDTO;
import com.phorest.salon.clientservices.processor.PhorestCSVProcessorFactory;
import com.phorest.salon.clientservices.services.PhorestClientServices;

/**
 * This controller has API's to upload client related information and retrieve
 * the saved information
 *
 */
@RestController
@RequestMapping("/phorest")
public class PhorestClientController {

	@Autowired
	private PhorestCSVProcessorFactory phorestCSVProcessorFactory;

	@Autowired
	private PhorestClientServices phorestClientServices;

	private static final Logger logger = LoggerFactory.getLogger(PhorestClientController.class);

	/**
	 * This endpoint uploads and parse csv files and saves them into H2 database
	 * 
	 * @param client
	 * @param appointment
	 * @param services
	 * @param purchases
	 * @return
	 */
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

	/**
	 * This endpoint returns a list of top 'X' clients who have accumulated the most
	 * loyality points from Date 'Y'
	 * 
	 * @param date
	 * @param size
	 * @return
	 */
	@GetMapping(path = "/topclient/loyalty/{size}", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public List<LoyalClientDTO> getHighestLoyaltyPoints(@RequestParam(name = "date") String date,
			@PathVariable("size") int size) {
		List<LoyalClientDTO> list = phorestClientServices.getLoyaltyClientByDate(date);
		return list.stream().limit(size).collect(Collectors.toList());
	}

}
