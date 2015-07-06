package org.akm.model;

import org.akm.exception.UserExistsException;
import org.akm.repository.CampaignRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/campaign")
public class CampaignController {

	@Autowired
	private CampaignRepository repository;
	
	
	
	@RequestMapping(method=RequestMethod.POST, headers="Accept=application/json")
	public Campaign create(@RequestBody Campaign campaign) {
		final Campaign savedCampaign = repository.findByName(campaign.getName());
		
		if(savedCampaign != null) {
			throw new UserExistsException();
		}
		
		repository.save(campaign);
		return savedCampaign;
	}
}

//@ControllerAdvice

class CampaignControllerAdvice {
	
	@ResponseBody
	@ExceptionHandler(UserExistsException.class)
	@ResponseStatus(HttpStatus.CONFLICT)
	Error userExistsException(final UserExistsException ex) {
		throw new RuntimeException("User already exists in store");
	}
	
}
