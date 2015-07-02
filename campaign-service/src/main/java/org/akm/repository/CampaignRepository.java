package org.akm.repository;

import org.akm.model.Campaign;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CampaignRepository extends MongoRepository<Campaign, String> {
	
	
	public Campaign findByName(final String name);

}
