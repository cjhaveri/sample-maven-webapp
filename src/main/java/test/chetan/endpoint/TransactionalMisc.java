package test.chetan.endpoint;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import test.chetan.model.Misc;
import test.chetan.repository.MiscRepository;

@Component
public class TransactionalMisc {
	
	@Autowired
	private MiscRepository miscRepository;
	
	@Transactional(propagation=Propagation.NOT_SUPPORTED)
	public void saveMisc(String price) {
		
		Misc misc = new Misc();
		misc.setPrice(price);
		try {
			miscRepository.save(misc);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	

}
