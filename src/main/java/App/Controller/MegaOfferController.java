package App.Controller;

import App.Entity.Order;
import App.Repository.CustomRuleRepo;
import App.Service.RuleService;
import org.kie.api.event.rule.AgendaEventListener;
import org.kie.api.runtime.KieSession;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.slf4j.Logger;

import java.io.IOException;
import java.util.List;

@RestController
public class MegaOfferController {

	private static final Logger LOGGER = LoggerFactory.getLogger(MegaOfferController.class);

	@Autowired
	RuleService ruleService;

	@Autowired
	CustomRuleRepo customRuleRepo;

	@Autowired
	private KieSession session;

	@GetMapping("/hello")
	public String hellodemo()
	{
		return "Hello Drolls";
	}

	@PostMapping("/order")
	public Order orderNow(@RequestBody Order order) {
		Order myOrder = new Order();

		LOGGER.info("Before inserting the object into the session");
		session.insert(order);
		LOGGER.info("After inserting the object into the session");
		LOGGER.info("Before firing rules");
		int rulesfiredcount = session.fireAllRules();
		System.out.println("Number of rules fired = "+rulesfiredcount);
		LOGGER.info("After firing all rules");
		return order;
	}

	@GetMapping("/Reload")
	public String reload() throws IOException
	{
		ruleService.reload();
		return "Rules Reloaded Successfully!";

	}

	@GetMapping("/AllRules")
	public List<Order> getallrules()
	{
		List<Order> allrules = (List<Order>) customRuleRepo.findAll();
		return allrules;
	}

	@PostMapping("/AddNewRule")
	public Order addNewRule(@RequestBody Order order)
	{
		customRuleRepo.save(order);
		return order;
	}

}
