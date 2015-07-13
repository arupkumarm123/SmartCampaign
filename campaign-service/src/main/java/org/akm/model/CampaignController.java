package org.akm.model;

import java.util.Optional;
import java.util.concurrent.Future;

import org.akm.exception.UserExistsException;
import org.akm.repository.CampaignRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.util.concurrent.ListenableFutureAdapter;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;

import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.JdkFutureAdapters;
import com.google.common.util.concurrent.ListenableFuture;

import reactor.core.composable.Promise;


@RestController
@RequestMapping("/campaign")
public class CampaignController {

	@Autowired
	private CampaignRepository repository;
	
	
	
	@RequestMapping(method=RequestMethod.POST, headers="Accept=application/json")
	public DeferredResult<Campaign> create(@RequestBody Campaign campaign) {
		
		final DeferredResult<Campaign> result = new DeferredResult<Campaign>();
		final Future<Optional<Campaign>> savedCampaignFuture = repository.findByName(campaign.getName());
		final ListenableFuture<Optional<Campaign>> campaignFuture = JdkFutureAdapters.listenInPoolThread(savedCampaignFuture);
		
		Futures.addCallback(campaignFuture, new FutureCallback<Optional<Campaign>>() {

			@Override
			public void onFailure(final Throwable error) {
				result.setErrorResult(error);
			}

			@Override
			public void onSuccess(final Optional<Campaign> camp) {
				camp.ifPresent((c)->result.setResult(c));
			}
		});
		return result;
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
