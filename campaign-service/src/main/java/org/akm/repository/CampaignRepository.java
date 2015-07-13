package org.akm.repository;

import java.util.Optional;
import java.util.concurrent.Future;

import org.akm.model.Campaign;
import org.springframework.data.repository.CrudRepository;
import org.springframework.scheduling.annotation.Async;


public interface CampaignRepository extends CrudRepository<Campaign, String> {
	
//	@Async
//	ListenableFuture<Campaign> findByName(final String name);
	
	@Async
	Future<Optional<Campaign>> findByName(final String name);

}
